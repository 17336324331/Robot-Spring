package com.xingguang.sinanya.dice.manager;

import com.xingguang.sinanya.dice.manager.imal.Role;
import com.xingguang.sinanya.entity.EntityNickAndRandomAndSkill;
import com.xingguang.sinanya.entity.EntityRoleTag;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.RollCantInZeroException;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.system.RoleInfoCache;
import com.xingguang.sinanya.tools.getinfo.*;
import com.xingguang.sinanya.tools.imal.GetSkillNameFromMuiltRole;
import com.xingguang.sinanya.tools.makedata.GetNickAndRandomAndSkill;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.db.roles.InsertRoles;
import com.xingguang.sinanya.tools.getinfo.GetMessagesToValue;
import com.xingguang.sinanya.tools.getinfo.MakeRolesInfo;
import com.xingguang.sinanya.tools.getinfo.*;
import com.xingguang.utils.SystemParam;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.db.roles.SelectRoles.flushRoleInfoCacheFromDatabase;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 人物卡角色
 */
public class Roles implements Role, GetSkillNameFromMuiltRole {
    private static Pattern functioin = Pattern.compile("[\\-+*/]");
    Pattern p = Pattern.compile(".*\\d+.*");
    private EntityTypeMessages entityTypeMessages;

    public Roles(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    private static int findSkillName(int i, String msg, StringBuilder strSkillName) {
        while (i < msg.length() && !Character.isSpaceChar(msg.charAt(i)) &&
                !Character.isDigit(msg.charAt(i)) && !functioin.matcher(String.valueOf(msg.charAt(i))).find()) {
            strSkillName.append(msg.charAt(i));
            i++;
        }
        return i;
    }

    private static int findSkillValue(int i, String msg, StringBuilder strSkillValue) {
        while (i < msg.length() && (Character.isDigit(msg.charAt(i)) || msg.charAt(i) == 'd' || msg.charAt(i) == 'D' || functioin.matcher(String.valueOf(msg.charAt(i))).find())) {
            strSkillValue.append(msg.charAt(i));
            i++;
        }
        return i;
    }

    /**
     * 人物卡变更，“.st角色”视为切换角色
     * “.st角色-属性50”视为修改或添加属性，这取决于是否可以找到这张人物卡，找到的话只修改提及的值，未找到的话其余所有属性置为初始属性
     * 初始属性情况可以参见com.xingguang.sinanya.system.MakeRolesInfo
     *
     * @return set是否成功
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    public boolean set() {
        String tag = MessagesTag.TAG_ST_SET;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2)).replaceAll("[\r\n]", "").replace("--", "-").replace("－", "-").replace(" ", "").replace("\t", "");
        String sepRoleAndPro = "-";
        String defaultRole = "自定义";
        int lenRoleAndPro = 2;
        String properties;
        InsertRoles insertRoles = new InsertRoles();
        long qqId = entityTypeMessages.getFromQq();

        String role;
        if (msg.contains(sepRoleAndPro) && msg.split(sepRoleAndPro).length == lenRoleAndPro) {
            role = msg.split(sepRoleAndPro)[0].trim();
            EntityRoleTag roleTag = new EntityRoleTag(qqId, role);
            if (!role.equals(MessagesSystem.NONE)) {
                properties = msg.split(sepRoleAndPro)[1];
                insertRoles.insertRoleInfo(properties, role, qqId);
                if (!RoleInfoCache.ROLE_NAME_CACHE.contains(roleTag)) {
                    RoleInfoCache.ROLE_NAME_CACHE.add(roleTag);
                }
               // Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getSetPropSuccess());
                Sender.sender(entityTypeMessages, SystemParam.getRet("strStRet"));
                return true;
            } else {
                return false;
            }
        } else if (!msg.equals(MessagesSystem.NONE)) {
            role = msg;
            String nick = GetNickName.getTrueNickName(entityTypeMessages);
            EntityRoleTag roleTag = new EntityRoleTag(qqId, defaultRole);
            if (RoleInfo.checkRoleInfoExistByQQ(qqId, role)) {
                RoleChoose.setRoleChooseByQQ(qqId, role);
                roleTag = new EntityRoleTag(qqId, role);
                if (!RoleInfoCache.ROLE_NAME_CACHE.contains(roleTag)) {
                    RoleInfoCache.ROLE_NAME_CACHE.add(roleTag);
                }
            } else if (defaultRole.equals(role)) {
                RoleInfoCache.ROLE_CHOOSE.put(qqId, defaultRole);
                if (!RoleInfoCache.ROLE_NAME_CACHE.contains(roleTag)) {
                    RoleInfoCache.ROLE_NAME_CACHE.add(roleTag);
                }
                insertRoles.insertRoleInfo("", defaultRole, qqId);
                Sender.sender(entityTypeMessages, "已为" + nick + "切换到自定义档位:\t" + msg + "\n此状态下无法使用.team\\.en功能，但所有技能使用不会受到限制。");
                return true;
            } else if (HasDigit(msg)) {
                if (RoleChoose.checkRoleChooseExistByFromQQ(entityTypeMessages) && msg.length() < 200) {
                    insertRoles.insertRoleInfo(msg, RoleChoose.getRoleChooseByFromQQ(entityTypeMessages), qqId);
                    //Sender.sender(entityTypeMessages, "已为" + RoleChoose.getRoleChooseByFromQQ(entityTypeMessages) + "更新属性");
                    Sender.sender(entityTypeMessages, SystemParam.getRet("strStRet"));
                    return true;
                }
                RoleInfoCache.ROLE_CHOOSE.put(qqId, defaultRole);
                if (!RoleInfoCache.ROLE_NAME_CACHE.contains(roleTag)) {
                    RoleInfoCache.ROLE_NAME_CACHE.add(roleTag);
                }
                RoleInfoCache.ROLE_INFO_CACHE.put(new EntityRoleTag(qqId, defaultRole), GetMessagesToValue.getMessagesToValue(msg));
                insertRoles.insertRoleInfo(GetMessagesToValue.getMessagesToValue(msg), defaultRole, qqId);
                Sender.sender(entityTypeMessages, "已为" + nick + "切换到自定义档位:\t" + nick + "\n此状态下无法使用.team\\.en功能，但所有技能使用不会受到限制。");
                return true;
            } else {
                RoleInfoCache.ROLE_CHOOSE.put(qqId, defaultRole);
                if (!RoleInfoCache.ROLE_NAME_CACHE.contains(roleTag)) {
                    RoleInfoCache.ROLE_NAME_CACHE.add(roleTag);
                }
                RoleInfoCache.ROLE_TMP_NAME.put(qqId, role);
                insertRoles.deleteRoleTmpName(qqId);
                insertRoles.insertRoleTmpName(qqId,role);
                RoleInfoCache.ROLE_INFO_CACHE.put(new EntityRoleTag(qqId, defaultRole), (HashMap<String, Integer>) new MakeRolesInfo().getPropertiesForRole());
                insertRoles.insertRoleInfo((HashMap<String, Integer>) new MakeRolesInfo().getPropertiesForRole(), defaultRole, qqId);
                Sender.sender(entityTypeMessages, "已为" + nick + "切换到自定义档位:\t" + msg + "\n此状态下无法使用.team\\.en功能，但所有技能使用不会受到限制。");
                return true;

            }
            Sender.sender(entityTypeMessages, "已为" + nick + "切换到人物卡: " + role);
            return true;
        } else {
            return false;
        }
    }


    public boolean HasDigit(String content) {
        boolean flag = false;
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    /**
     * 返回当前查询QQ的角色列表
     */
    public void list() {
        long qqId = entityTypeMessages.getFromQq();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("您当前使用角色: \n");
        if (RoleChoose.checkRoleChooseExistByQQ(qqId)) {
            String role = RoleChoose.getRoleChooseByQQ(qqId);
            if (role.equals("自定义") && RoleInfoCache.ROLE_TMP_NAME.containsKey(qqId)) {
                stringBuilder.append("自定义").append("(").append(RoleInfoCache.ROLE_TMP_NAME.get(qqId)).append(")").append("\n");
            } else {
                stringBuilder.append(role).append("\n");
            }
        } else {
            stringBuilder.append("自定义").append(RoleInfoCache.ROLE_TMP_NAME.getOrDefault(qqId, "")).append("\n");
        }
        stringBuilder.append("当前备选角色:\n");
        StringBuilder standbyRole = new StringBuilder();

        flushRoleInfoCacheFromDatabase();
        for (EntityRoleTag entityRoleTag : RoleInfoCache.ROLE_NAME_CACHE) {
            if (RoleChoose.checkRoleChooseExistByQQ(qqId) && !RoleChoose.getRoleChooseByQQ(qqId).equals(entityRoleTag.getRole()) && entityRoleTag.getQq() == qqId && !entityRoleTag.getRole().contains("自定义")) {
                standbyRole.append(entityRoleTag.getRole()).append("\n");
            }
        }
        if (!stringBuilder.toString().contains("自定义")) {
            standbyRole.append("自定义").append("\n");
        } else if (standbyRole.length() == 0) {
            standbyRole.append("无").append("\n");
        }
        stringBuilder.append(standbyRole);
        String result = stringBuilder.toString();
        Sender.sender(entityTypeMessages, result.substring(0, result.length() - 1));
    }

    /**
     * 移除某个角色
     */
    public void move() {
        String tag = MessagesTag.TAG_ST_RM;
        String role = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        long qqId = entityTypeMessages.getFromQq();
        if (RoleInfo.checkRoleInfoExistByFromQQ(entityTypeMessages, role)) {
            if (RoleChoose.getRoleChooseByQQ(qqId).equals(role)) {
                Sender.sender(entityTypeMessages, "您不能删除当前选定角色");
            } else {
                RoleInfo.removeRoleByQQ(qqId, role);
                RoleInfoCache.ROLE_NAME_CACHE.remove(new EntityRoleTag(qqId, role));
                Sender.sender(entityTypeMessages, "已为您删除角色: " + role);
            }
        }
    }

    /**
     * 显示角色的技能信息
     */
    public void show() {
        String qq = entityTypeMessages.getFromQqString();
        StringBuilder stringBuilder = showProp(qq);
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    public void change() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        String tag = MessagesTag.TAG__ST_CHANGE;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        long qqId = entityTypeMessages.getFromQq();
        if (RoleChoose.checkRoleChooseExistByFromQQ(entityTypeMessages)) {
            HashMap<String, Integer> properties = (HashMap<String, Integer>) RoleInfo.getRoleInfoFromChooseByFromQQ(entityTypeMessages);
            StringBuilder restult = new StringBuilder();
            restult.append("您的技能变化情况为");
            StringBuilder strSkillValue = new StringBuilder();
            StringBuilder strSkillName = new StringBuilder();
            int i = 0;
            while (i < msg.length()) {
                int tmp = i;
                i = findSkillName(i, msg, strSkillName);
                i = findSkillValue(i, msg, strSkillValue);

                if (!strSkillValue.toString().equals(MessagesSystem.NONE)) {
                    String skillName = GetSkillNameFromMuiltRole.getSkillNameFromMuiltRole(strSkillName);
                    EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = GetNickAndRandomAndSkill.getNickAndRandomAndSkill(entityTypeMessages, skillName + strSkillValue);
                    assert properties != null;
                    properties.put(MakeSkillName.makeSkillName(skillName), entityNickAndRandomAndSkill.getSkill());
                    restult.append("\n").append(skillName).append(": ").append(strSkillValue).append(" --> ").append(entityNickAndRandomAndSkill.getSkill());
                    strSkillName = new StringBuilder();
                    strSkillValue = new StringBuilder();
                }
                if (tmp == i) {
                    i++;
                }
            }
            RoleInfo.setRoleInfoFromChooseByQQ(qqId, properties);
            Sender.sender(entityTypeMessages, restult.toString());
        }
    }

    public void clr() {
        long qqId = entityTypeMessages.getFromQq();
        String defaultRole = "自定义";
        String nick = GetNickName.getTrueNickName(entityTypeMessages);
        EntityRoleTag roleTag = new EntityRoleTag(qqId, defaultRole);

        RoleInfoCache.ROLE_CHOOSE.put(qqId, defaultRole);
        if (!RoleInfoCache.ROLE_NAME_CACHE.contains(roleTag)) {
            RoleInfoCache.ROLE_NAME_CACHE.add(roleTag);
        }
        RoleInfoCache.ROLE_TMP_NAME.remove(qqId);
        RoleInfoCache.ROLE_INFO_CACHE.remove(roleTag);
        InsertRoles insertRoles=new InsertRoles();
        insertRoles.deleteInfo(qqId, defaultRole);
        insertRoles.deleteRoleTmpName(qqId);
        insertRoles.insertRoleInfo("", defaultRole, qqId);
        Sender.sender(entityTypeMessages, "已为" + nick + "切换到自定义档位并初始化所有属性设置");
    }
}
