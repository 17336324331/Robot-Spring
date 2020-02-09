package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantInPrivateException;
import com.xingguang.sinanya.exceptions.NotFoundSkillException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.getinfo.GetSkillValue;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.db.roles.InsertRoles;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;
import static com.xingguang.sinanya.tools.getinfo.GetSkillValue.getSkillValue;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 幕间成长
 */
public class SkillUp {
    private static Pattern numbers = Pattern.compile("(\\d+)");
    private EntityTypeMessages entityTypeMessages;

    public SkillUp(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 若未设定技能，则无法进行en，而单纯的.en 60从自然逻辑上讲是无意义的
     *
     * @throws NotFoundSkillException 可能因未找到技能而无法成长
     */
    public void en() throws NotFoundSkillException, CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_EN;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        String roleName = GetNickName.getNickName(entityTypeMessages);
        int skill;
        Matcher skillNumber = numbers.matcher(msg);
        boolean useRoleCard;
        if (skillNumber.find()) {
            skill = Integer.parseInt(skillNumber.group(1));
            useRoleCard = false;
        } else {
            skill = GetSkillValue.getSkillValue(entityTypeMessages, msg);
            useRoleCard = true;
        }

        if (skill == 0) {
            throw new NotFoundSkillException(entityTypeMessages);
        }

        int random = RandomInt.random(1, 100);

        int strFumbleLevel = 95;
        StringBuilder stringBuilder = new StringBuilder();
        if (random > skill || random > strFumbleLevel) {
            int skillUp = RandomInt.random(1, 10);
            stringBuilder
                    .append("[")
                    .append(roleName)
                    .append("]")
                    .append("的技能成长检定:\n")
                    .append(random)
                    .append("/")
                    .append(skill)
                    .append(" 成功啦!您又变强啦\n")
                    .append("您的技能增长了1D10=")
                    .append(skillUp)
                    .append("点，目前为:")
                    .append(skill + skillUp);
            System.out.println("useRoleCard:"+useRoleCard);
            if (useRoleCard) {
                new InsertRoles().insertRoleInfo(msg + (skill + skillUp), roleName, entityTypeMessages.getFromQq());
                stringBuilder.append("\n本次结果会自动更新到人物卡中");
            } else {
                stringBuilder.append("\n本次结果不会自动更新到人物卡中，请使用.st<角色名>-<属性><新属性值>的格式进行更新录入，没有更改的属性不需要再次录入\n.en <技能>这种格式可以自动录入人物卡，无需再次st");
            }
//            if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
//                stringBuilder.append("成功");
//            } else {
//                stringBuilder.append(GetMessagesProperties.entitySystemProperties.getEnSuccess());
//            }
            Sender.sender(entityTypeMessages, stringBuilder.toString());
        } else {
            stringBuilder
                    .append("[")
                    .append(roleName)
                    .append("]")
                    .append("的技能成长检定:\n")
                    .append(random)
                    .append("/")
                    .append(skill)
                    .append(" 失败!");
//            System.out.println("MessagesBanList.groupSwitchHashMap:"+MessagesBanList.groupSwitchHashMap);
//            System.out.println("entityTypeMessages.getFromGroup():"+entityTypeMessages.getFromGroup());
//            System.out.println("MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) :"+MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) );
//            System.out.println("MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()):"+MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()));
//            System.out.println("MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple():"+MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple());
            if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
                stringBuilder.append("失败");
            } else {
                //System.out.println("GetMessagesProperties.entitySystemProperties:"+GetMessagesProperties.entitySystemProperties);
                //System.out.println("GetMessagesProperties.entitySystemProperties.getEnFailed()"+GetMessagesProperties.entitySystemProperties.getEnFailed());
                stringBuilder.append(GetMessagesProperties.entitySystemProperties.getEnFailed());
            }

            Sender.sender(entityTypeMessages, stringBuilder.toString());
        }
    }
}
