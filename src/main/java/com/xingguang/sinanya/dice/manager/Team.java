package com.xingguang.sinanya.dice.manager;

import com.xingguang.sinanya.dice.manager.imal.AtQq;
import com.xingguang.sinanya.dice.manager.imal.Role;
import com.xingguang.sinanya.entity.EntityRoleTag;
import com.xingguang.sinanya.entity.EntityStrManyRolls;
import com.xingguang.sinanya.entity.EntityTeamInfo;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.entity.imal.GetDb;
import com.xingguang.sinanya.exceptions.*;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.system.RoleInfoCache;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.RoleChoose;
import com.xingguang.sinanya.tools.getinfo.RoleInfo;
import com.xingguang.sinanya.tools.makedata.GetRollResultAndStr;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.MakeSanCheck;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.exceptions.*;
import com.xingguang.sinanya.exceptions.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.RoleChoose.getRoleChooseByQQ;
import static com.xingguang.sinanya.tools.getinfo.RoleInfo.getRoleInfoFromChooseByQQ;
import static com.xingguang.sinanya.tools.getinfo.RoleInfo.setRoleInfoFromChooseByQQ;
import static java.lang.Integer.min;
import static java.lang.Math.max;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 管理小队
 */
public class Team implements GetDb, Role, AtQq {
    private static final Logger log = LoggerFactory.getLogger(Team.class.getName());


    private Pattern plus = Pattern.compile("[+*/\\-]");

    private EntityTypeMessages entityTypeMessages;

    public Team(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 将@到的成员加入小队
     */
    public void set() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_TEAM_SET;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        if (msg.equals(MessagesSystem.NONE)) {
            Sender.sender(entityTypeMessages, "不可录入空成员");
            return;
        }
        ArrayList<String> qqList = getAtQqList(entityTypeMessages, msg);
        StringBuilder stringBuilder = new StringBuilder();
        for (String qq : qqList) {
            stringBuilder.append("已将玩家: [CQ:at,qq=").append(qq).append("]加入小队。可以使用.team查看队伍信息,.team hp/san对成员状态进行强制调整\n其余使用方式请查看.help命令\n");
        }
        EntityTeamInfo entityTeamInfo = new EntityTeamInfo(entityTypeMessages.getFromGroupString(), qqList);
        com.xingguang.sinanya.tools.getinfo.Team.addIntoTeam(entityTeamInfo);
        Sender.sender(entityTypeMessages, stringBuilder.substring(0, stringBuilder.length() - 1));
    }

    /**
     * 将@到的成员移出小队，也会清空该队员的技能成功记录
     */
    public void remove() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_TEAM_RM;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        ArrayList<String> qqList = getAtQqList(entityTypeMessages, msg);
        EntityTeamInfo entityTeamInfo = new EntityTeamInfo(entityTypeMessages.getFromGroupString(), qqList);
        com.xingguang.sinanya.tools.getinfo.Team.removeFromTeam(entityTeamInfo);
        for (String qq : qqList) {
            com.xingguang.sinanya.tools.getinfo.Team.rmTeamEn(qq, entityTypeMessages.getFromGroupString());
            Sender.sender(entityTypeMessages, "已将玩家: [CQ:at,qq=" + qq + "]移出小队,其在这期间损失的血量和san值不会还原。");
        }
    }

    /**
     * 将本群小队清空，也会清空队员的技能成功记录
     */
    public void clr() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        com.xingguang.sinanya.tools.getinfo.Team.clearTeam(entityTypeMessages.getFromGroupString());
        com.xingguang.sinanya.tools.getinfo.Team.clrTeamEn(entityTypeMessages.getFromGroupString());
        Sender.sender(entityTypeMessages, "已清空本群小队");
    }

    /**
     * 自动@本群小队中所有成员
     */
    public void call() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("kp正在呼叫以下成员:");
        for (String qq : com.xingguang.sinanya.tools.getinfo.Team.queryTeam(entityTypeMessages.getFromGroupString())
        ) {
            stringBuilder.append("\n[CQ:at,qq=")
                    .append(qq)
                    .append("]");
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    /**
     * 调整@到的小队成员的血量，默认为减少，可以使用1D3或1D3+20之类的表达式，每个小队成员都会单独计算表达式
     * 使用+为前缀则恢复血量
     */
    public void hp() throws ManyRollsTimesTooMoreException, RollCantInZeroException, CantInPrivateException {
        String msg = checkHeader(MessagesTag.TAG_TEAM_HP);
        ArrayList<String> qqList = getAtQqList(entityTypeMessages, msg);
        String regex = "\\[cq:at,qq=([0-9]+)]";
        msg = msg.replaceAll(regex, "").replace("all", "").trim();
        boolean add = false;
        if (msg.matches("^\\+.*")) {
            msg = msg.replaceAll("\\+", "");
            add = true;
        }
        String[] everyFunction = msg.split(plus.toString());
        for (String qq : qqList) {

            EntityStrManyRolls entityStrManyRolls = GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, msg, everyFunction);

            String role;
            int hp;
            HashMap<String, Integer> prop = (HashMap<String, Integer>) RoleInfo.getRoleInfoFromChooseByQQ(qq);
            if (prop != null) {

                role = RoleChoose.getRoleChooseByQQ(qq);
                hp = prop.get("hp");
                int con = prop.get("con");
                int siz = prop.get("siz");
                int hpc = prop.getOrDefault("hpc", 0);
                if (add) {
                    int maxHp = (con + siz) / 10 + hpc;
                    int newHp = min(hp + (int) entityStrManyRolls.getResult(), maxHp);
                    prop.put("hp", newHp);
                    RoleInfo.setRoleInfoFromChooseByQQ(qq, prop);
                    Sender.sender(entityTypeMessages, "已为" + role + "恢复" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getResult() + "点血量，剩余" + newHp + "点");
                } else {
                    int newHp = max(0, hp - (int) entityStrManyRolls.getResult());
                    prop.put("hp", newHp);
                    RoleInfo.setRoleInfoFromChooseByQQ(qq, prop);
                    RoleInfoCache.ROLE_INFO_CACHE.put(new EntityRoleTag(Long.parseLong(qq), role), prop);
                    if (newHp == 0) {
                        Sender.sender(entityTypeMessages, role + "损失" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getResult() + "点血量，已死亡");
                    } else if (entityStrManyRolls.getResult() >= hp / 2) {
                        Sender.sender(entityTypeMessages, "已为" + role + "降低" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getResult() + "点血量，剩余" + newHp + "点,已进入重伤状态");
                    } else {
                        Sender.sender(entityTypeMessages, "已为" + role + "降低" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getResult() + "点血量，剩余" + newHp + "点");
                    }
                }
            } else {
                Sender.sender(entityTypeMessages, "[CQ:at,qq=" + qq + "] 未选择人物卡");
            }
        }
    }

    /**
     * 调整@到的小队成员的理智值，默认为减少，可以使用1D3/1d6或1D3+20之类的表达式，每个小队成员都会单独计算表达式
     * 使用+为前缀则恢复理智值
     */
    public void san() throws CantInPrivateException {
        String msg = checkHeader(MessagesTag.TAG_TEAM_SAN);
        ArrayList<String> qqList = getAtQqList(entityTypeMessages, msg);
        String regex = "\\[cq:at,qq=([0-9]+)]";
        msg = msg.replaceAll(regex, "").replace("all", "").trim();
        for (String qq : qqList) {
            boolean add = false;
            if (msg.matches("^\\+.*")) {
                msg = msg.replaceAll("\\+", "");
                add = true;
            }

            MakeSanCheck makeSanCheck = new MakeSanCheck(entityTypeMessages, qq);

            if (add) {
                try {
                    makeSanCheck.addSanCheck(msg);
                } catch (PlayerSetException e) {
                    log.error(e.getMessage(), e);
                }
            } else {
                try {
                    if (msg.contains("/")) {
                        String result = makeSanCheck.checkSanCheck(msg);
                        Sender.sender(entityTypeMessages, result);
                    } else {
                        int changeValue;
                        if (CheckIsNumbers.isNumeric(msg)) {
                            changeValue = Integer.parseInt(msg);
                        } else {
                            String[] everFunctions = msg.split(plus.toString());
                            changeValue = (int) GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, msg, everFunctions).getResult();
                        }
                        HashMap<String, Integer> prop = (HashMap<String, Integer>) RoleInfo.getRoleInfoFromChooseByQQ(qq);
                        if (prop != null) {
                            StringBuilder strResult = new StringBuilder();
                            int newSan = max(0, prop.get("san") - changeValue);
                            String role = RoleChoose.getRoleChooseByQQ(qq);
                            strResult.append("已为").append(role).append("减少").append(changeValue).append("点理智值，剩余").append(newSan).append("点");
                            if (newSan == 0) {
                                strResult.append("\n已永久疯狂");
                            } else if (changeValue >= 5) {
                                strResult.append("\n已进入临时性疯狂");
                            } else if (changeValue >= prop.get("san") / 5) {
                                strResult.append("\n已因单次损失值进入不定性疯狂");
                            }
                            prop.put("san", newSan);
                            RoleInfo.setRoleInfoFromChooseByQQ(qq, prop);
                            RoleInfoCache.ROLE_INFO_CACHE.put(new EntityRoleTag(Long.parseLong(qq), role), prop);
                            Sender.sender(entityTypeMessages, strResult.toString());
                        } else {
                            Sender.sender(entityTypeMessages, "未找到[CQ:at,qq=" + qq + "]的人物卡");
                        }
                    }

                } catch (SanCheckSetException | PlayerSetException | ManyRollsTimesTooMoreException | RollCantInZeroException e) {
                    log.error(e.getMessage(), e);
                }
            }


        }
    }

    /**
     * 显示当前小队的情况，会根据小队成员的当前激活人物自动计算得出
     */
    public void show() throws PlayerSetException, CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("您的小队情况目前为: ");
        ArrayList<String> qqList = (ArrayList<String>) com.xingguang.sinanya.tools.getinfo.Team.queryTeam(entityTypeMessages.getFromGroupString());
        if (qqList == null) {
            try {
                throw new TeamIsEmptyException(entityTypeMessages);
            } catch (TeamIsEmptyException e) {
                log.error(e.getMessage(), e);
                return;
            }
        }
        for (String qq : qqList) {
            HashMap<String, Integer> prop = (HashMap<String, Integer>) RoleInfo.getRoleInfoFromChooseByQQ(qq);
            if (prop != null) {
                String role = RoleChoose.getRoleChooseByQQ(qq);
                try {
                    int str = prop.get("str");
                    int pow = prop.get("pow");
                    int con = prop.get("con");
                    int siz = prop.get("siz");
                    int san = prop.get("san");
                    int hp = prop.get("hp");
                    int hpc = prop.getOrDefault("hpc", 0);
                    int cthulhuMythos = prop.get("cthulhuMythos");

                    if (hp > (con + siz) / 10 + hpc) {
                        hp = (con + siz) / 10 + hpc;
                        prop.put("hp", hp);
                        RoleInfo.setRoleInfoFromChooseByQQ(qq, prop);
                    }
                    if (san > (99 - cthulhuMythos)) {
                        san = 99 - cthulhuMythos;
                        prop.put("san", san);
                        RoleInfo.setRoleInfoFromChooseByQQ(qq, prop);
                    }
                    stringBuilder
                            .append("\n")
                            .append(role)
                            .append("  ")
                            .append("血量=")
                            .append(hp)
                            .append("/")
                            .append((con + siz) / 10 + hpc)
                            .append("  ")
                            .append("san值=")
                            .append(san)
                            .append("/")
                            .append(99 - cthulhuMythos)
                            .append("  ")
                            .append("(初始值:")
                            .append(pow)
                            .append(")  ")
                            .append("DB:")
                            .append(dbGetter(siz + str));
                } catch (NullPointerException e) {
                    throw new PlayerSetException(entityTypeMessages);
                }
            } else {
                stringBuilder.append("\n[CQ:at,qq=").append(qq).append("] 未选择人物卡");
            }
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    /**
     * 显示当前小队所有成员当前激活角色的技能情况，私聊发送给命令触发人
     */
    public void desc() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<String> qqList = (ArrayList<String>) com.xingguang.sinanya.tools.getinfo.Team.queryTeam(entityTypeMessages.getFromGroupString());
        if (qqList == null) {
            try {
                throw new TeamIsEmptyException(entityTypeMessages);
            } catch (TeamIsEmptyException e) {
                log.error(e.getMessage(), e);
                return;
            }
        } else {
            stringBuilder.append("您小队内成员的属性值为:\n");
        }
        for (String qq : qqList) {
            stringBuilder.append(new Roles(entityTypeMessages).showProp(qq));
        }
        entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), stringBuilder.toString());
    }

    /**
     * 显示当前小队所有成员当前激活角色的技能成功情况
     */
    public void en() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();

        ArrayList<String> qqList = (ArrayList<String>) com.xingguang.sinanya.tools.getinfo.Team.queryTeam(entityTypeMessages.getFromGroupString());
        if (qqList == null) {
            try {
                throw new TeamIsEmptyException(entityTypeMessages);
            } catch (TeamIsEmptyException e) {
                log.error(e.getMessage(), e);
                return;
            }
        } else {
            stringBuilder.append("以下是您小队中成员的技能成功情况:\n");
        }
        for (String qq : qqList) {
            stringBuilder.append(com.xingguang.sinanya.tools.getinfo.Team.getTeamEn(entityTypeMessages, qq)).append("\n");
        }
        Sender.sender(entityTypeMessages, stringBuilder.substring(0, stringBuilder.length() - 1));
    }

    private String checkHeader(String tag) throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));

//        String regex = "\\[cq:at,qq=([0-9]+)]";
//        msg = msg.replaceAll(regex, "").trim();
        return msg;
    }
}
