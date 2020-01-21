package com.xingguang.sinanya.dice.game;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.xingguang.sinanya.dice.get.imal.GetRandomList;
import com.xingguang.sinanya.dice.imal.Function;
import com.xingguang.sinanya.dice.manager.Roles;
import com.xingguang.sinanya.dice.roll.RewardAndPunishment;
import com.xingguang.sinanya.dice.roll.RollAndCheck;
import com.xingguang.sinanya.entity.EntityFireRd;
import com.xingguang.sinanya.entity.EntityNickAndRandomAndSkill;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.*;
import com.xingguang.sinanya.system.MessagesFire;
import com.xingguang.sinanya.system.MessagesNPC;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckFire;
import com.xingguang.sinanya.tools.checkdata.CheckFireChecking;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.checkdata.CheckResultLevel;
import com.xingguang.sinanya.tools.checkdata.imal.CheckTypes;
import com.xingguang.sinanya.tools.getinfo.RoleChoose;
import com.xingguang.sinanya.tools.getinfo.RoleInfo;
import com.xingguang.sinanya.tools.makedata.GetNickAndRandomAndSkill;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.db.fire.SelectFire;
import com.xingguang.sinanya.dice.MakeNickToSender;

import java.util.ArrayList;
import java.util.Objects;

import static com.xingguang.sinanya.db.fire.InputFireTemp.*;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 今日人品类，其实不是很想做……
 */
public class Fire implements MakeNickToSender, GetRandomList, Function {
    private long fromQQ;
    private EntityTypeMessages entityTypeMessages;
    private SelectFire selectFire = new SelectFire(entityTypeMessages);
    private int multiple = 10;

    public Fire(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
        this.fromQQ = entityTypeMessages.getFromQq();
    }

    public void fire() throws FireRdException {
        Sender.sender(entityTypeMessages, selectFire.selectFireText(10000));
    }

    public void check() throws FireRdException, ManyRollsTimesTooMoreException, RollCantInZeroException, FireAlreadyInPageException, FireSkillException, InBigGroupException {
        String choose = selectFire.selectFireCheck(selectFire.selectFireTemp(fromQQ));
        CheckTypes checkTypes = CheckFire.checkFire(choose);
        switch (checkTypes) {
            case RD:
                String msg = entityTypeMessages.getMsgGet().getMsg();
                EntityFireRd fireRd = CheckFire.checkRD(choose, entityTypeMessages);
                if (msg.contains("rc")) {
                    checkRC(fireRd);
                } else if (msg.contains("rb")) {
                    checkRB(fireRd);
                } else if (msg.contains("rp")) {
                    checkRP(fireRd);
                }
                break;
            case CHECK:
                int check = CheckFire.checkCheck(choose);
                String inputCheck = entityTypeMessages.getMsgGet().getMsg();
                if (CheckIsNumbers.isNumeric(inputCheck)) {
                    update(CheckFireChecking.checkFireChecking(check, Integer.parseInt(inputCheck), entityTypeMessages));
                }
                RewardAndPunishment r = new RewardAndPunishment(entityTypeMessages);
                RollAndCheck rc = new RollAndCheck(entityTypeMessages);
                msg = entityTypeMessages.getMsgGet().getMsg();
                if (msg.contains("rc")) {
                    rc.rc();
                    Sender.sender(entityTypeMessages, "检定完毕后请手工输入下一页的页码");
                } else if (msg.contains("rb")) {
                    r.rb();
                    Sender.sender(entityTypeMessages, "检定完毕后请手工输入下一页的页码");
                } else if (msg.contains("rp")) {
                    r.rp();
                    Sender.sender(entityTypeMessages, "检定完毕后请手工输入下一页的页码");
                }else{
                    Sender.sender(entityTypeMessages, "向火独行模块当前处于开启状态，不响应其余指令,如需退出此模式请输入.fire stop");
                }
                break;
            case CHOOSE:
                ArrayList<Integer> chooseList = CheckFire.checkChoose(choose);
                String input = entityTypeMessages.getMsgGet().getMsg();
                if (CheckIsNumbers.isNumeric(input) && chooseList.contains(Integer.parseInt(input))) {
                    update(Integer.parseInt(input));
                } else {
                    Sender.sender(entityTypeMessages, "输入的新页码不在可选项中或不是数字，如需退出此模式请输入.fire stop");
                }
                break;
            default:
                break;
        }
    }

    public void start() throws FireRdException {
        if (!MessagesFire.fireSwitch.contains(fromQQ)) {
            MessagesFire.fireSwitch.add(fromQQ);
            updateFireSwitch(fromQQ, true);
        } else {
            Sender.sender(entityTypeMessages, "您目前已启用向火独行模块，无法重复开启");
            return;
        }
        if (0 == selectFire.selectFireTempExsit(fromQQ) && Objects.requireNonNull(RoleInfo.getRoleInfoFromChooseByFromQQ(entityTypeMessages)).getOrDefault("str", 0) == 0) {
            MsgGet msgGet = new MsgGet() {
                @Override
                public String getId() {
                    return null;
                }

                @Override
                public String getMsg() {
                    return ".st向火独行测试卡非跑团用-力量50str50敏捷50dex50意志50pow50体质50con50外貌50app50教育50edu50体型50siz50智力50灵感50int50san5san值5理智5理智值50幸运50运气50mp10魔法10hp10体力10会计50人类学50估价50考古学50取悦50攀爬50计算机50计算机使用50电脑50信用50信誉50信用评级50克苏鲁50克苏鲁神话50cm50乔装50闪避50汽车50驾驶50汽车驾驶50电气维修50电子学50话术50斗殴50手枪50急救50历史50恐吓50跳跃50母语50法律50图书馆50图书馆使用50聆听50开锁50撬锁50锁匠50机械维修50医学50博物学50自然学50领航50导航50神秘学50重型操作50重型机械50操作重型机械50重型50说服50精神分析50心理学50骑术50妙手50侦查50潜行50生存50游泳50投掷50追踪50驯兽50潜水50爆破50读唇50催眠50炮术50植物学50";
                }

                @Override
                public String getFont() {
                    return null;
                }

                @Override
                public Long getTime() {
                    return null;
                }

                @Override
                public String getOriginalData() {
                    return null;
                }
            };
            EntityTypeMessages entityTypeMessages1 = new EntityTypeMessages(entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getMsgSender(), msgGet, entityTypeMessages.getMsgPrivate());
            new Roles(entityTypeMessages1).set();
            insertFireTemp(fromQQ, 1);
            Sender.sender(entityTypeMessages, selectFire.selectFireText(1));
            Sender.sender(entityTypeMessages, "您当前没有选择任何人物卡，已为您录入一张全属性全技能为50的卡《向火独行测试卡非跑团用》，您可以使用.st list查看您的所有卡片");
            Sender.sender(entityTypeMessages, "请您记住，您这张卡的职业是: " + randomFromList(MessagesNPC.fireJob) + " 本信息只会在开始发送一次，后续无法再次查询，请一定记住");
        } else if (0 == selectFire.selectFireTempExsit(fromQQ)) {
            Sender.sender(entityTypeMessages, "由于您当前已录入人物卡，将使用当前人物卡: " + RoleChoose.getRoleChooseByFromQQ(entityTypeMessages) + " 进行游戏");
            insertFireTemp(fromQQ, 1);
            Sender.sender(entityTypeMessages, selectFire.selectFireText(1));
        } else {
            Sender.sender(entityTypeMessages, selectFire.selectFireText(selectFire.selectFireTemp(fromQQ)));
            Sender.sender(entityTypeMessages, "向火独行模块重新激活，游戏继续");
        }
    }

    public void stop() {
        if (MessagesFire.fireSwitch.contains(fromQQ)) {
            while (MessagesFire.fireSwitch.contains(fromQQ)) {
                MessagesFire.fireSwitch.remove(fromQQ);
            }
            updateFireSwitch(fromQQ, false);
            Sender.sender(entityTypeMessages, "向火独行模块已关闭，您可以正常使用私聊命令了");
        } else {
            Sender.sender(entityTypeMessages, "您的向火独行模块处于关闭状态");
        }
    }

    public void restart() {
        if (MessagesFire.fireSwitch.contains(fromQQ)) {
            Sender.sender(entityTypeMessages, "您必须关闭本模块才可以进行重置");
        } else {
            deleteFireTemp(fromQQ);
            Sender.sender(entityTypeMessages, "您的向火独行副本进度已被重置，如需重新开始模块，请输入.fire start");
        }
    }

    private void update(int id) throws FireRdException, FireAlreadyInPageException {
        if (id != selectFire.selectFireTemp(fromQQ)) {
            insertFireTemp(fromQQ, id);
            Sender.sender(entityTypeMessages, selectFire.selectFireText(id));
        } else {
            throw new FireAlreadyInPageException(entityTypeMessages);
        }
    }

    private void checkRC(EntityFireRd fireRd) throws ManyRollsTimesTooMoreException, RollCantInZeroException, FireRdException, FireAlreadyInPageException, FireSkillException {
        String tag = MessagesTag.TAG_RC;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2)).replaceAll(" +", "").replaceAll(" +", "");
        if (!msg.contains(fireRd.getSkill())) {
            throw new FireSkillException(entityTypeMessages);
        }
        EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = GetNickAndRandomAndSkill.getNickAndRandomAndSkill(entityTypeMessages, msg);
        CheckResultLevel checkResultLevel = new CheckResultLevel(entityNickAndRandomAndSkill.getRandom(), entityNickAndRandomAndSkill.getSkill(), true);
        String result = makeNickToSender(entityNickAndRandomAndSkill.getNick()) +
                "进行" + msg + "鉴定: D100=" + entityNickAndRandomAndSkill.getRandom() + "/" + entityNickAndRandomAndSkill.getSkill() +
                checkResultLevel.getLevelResultStr(entityTypeMessages.getFromGroupString());
        Sender.sender(entityTypeMessages, result);
        if (checkResultLevel.getLevel() > 1) {
            //如果成功及以上
            update(fireRd.getSuccess());
        } else {
            update(fireRd.getFailed());
        }

    }

    private void checkRB(EntityFireRd fireRd) throws ManyRollsTimesTooMoreException, RollCantInZeroException, FireRdException, FireAlreadyInPageException, FireSkillException {
        String tag = MessagesTag.TAG_RB;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "");
        if (!msg.contains(fireRd.getSkill())) {
            throw new FireSkillException(entityTypeMessages);
        }
        EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = GetNickAndRandomAndSkill.getNickAndRandomAndSkill(entityTypeMessages, msg);

        StringBuilder stringBuilder = new StringBuilder();

        int random = entityNickAndRandomAndSkill.getRandom();

        int resultRandom = getMin(stringBuilder, random, multiple, msg, entityTypeMessages);

        String strRes;
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        String nick = makeNickToSender(entityNickAndRandomAndSkill.getNick());
        CheckResultLevel checkResultLevel = new CheckResultLevel(resultRandom, getTimesAndSkill(entityTypeMessages, msg).getSkill(), false);
        strRes = nick +
                "进行奖励骰鉴定: D100=" + random + "[奖励骰:" + substring + "] = " + resultRandom + "/" + getTimesAndSkill(entityTypeMessages, msg).getSkill() +
                checkResultLevel.getLevelResultStr(entityTypeMessages.getFromGroupString());

        if (checkResultLevel.getLevel() > 1) {
            //如果成功及以上
            update(fireRd.getSuccess());
        } else {
            update(fireRd.getFailed());
        }
        Sender.sender(entityTypeMessages, strRes);
//        将列表打印，并根据最后确定的值进行成功登记判定
    }

    private void checkRP(EntityFireRd fireRd) throws ManyRollsTimesTooMoreException, RollCantInZeroException, FireRdException, FireAlreadyInPageException, FireSkillException {
        String tag = MessagesTag.TAG_RP;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "");

        if (!msg.contains(fireRd.getSkill())) {
            throw new FireSkillException(entityTypeMessages);
        }

        StringBuilder strRes = new StringBuilder();

        EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = GetNickAndRandomAndSkill.getNickAndRandomAndSkill(entityTypeMessages, msg);

        StringBuilder stringBuilder = new StringBuilder();

        int random = entityNickAndRandomAndSkill.getRandom();

        int resultRandom = getMax(stringBuilder, random, multiple, msg, entityTypeMessages);


        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        CheckResultLevel checkResultLevel = new CheckResultLevel(resultRandom, getTimesAndSkill(entityTypeMessages, msg).getSkill(), false);
        strRes.append("进行惩罚骰鉴定: D100=").append(random).append("[惩罚骰:").append(substring).append("] = ").append(resultRandom).append("/").append(getTimesAndSkill(entityTypeMessages, msg).getSkill()).append("\t").append(checkResultLevel.getLevelResultStrForSimple());

        if (checkResultLevel.getLevel() > 1) {
            //如果成功及以上
            update(fireRd.getSuccess());
        } else {
            update(fireRd.getFailed());
        }
        Sender.sender(entityTypeMessages, strRes.toString());
    }


}
