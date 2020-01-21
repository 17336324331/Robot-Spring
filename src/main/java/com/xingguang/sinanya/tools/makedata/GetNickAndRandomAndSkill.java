package com.xingguang.sinanya.tools.makedata;

import com.xingguang.sinanya.entity.EntityNickAndRandomAndSkill;
import com.xingguang.sinanya.entity.EntityStrManyRolls;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.RollCantInZeroException;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;

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
 * 类说明: 获取昵称、随机值、技能值
 */
public class GetNickAndRandomAndSkill {
    private static Pattern p = Pattern.compile("[+*/\\-]");
    private static Pattern skillNamePatten = Pattern.compile("([^\\d+*\\-/]+)");

    private GetNickAndRandomAndSkill() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 从传入的整体信息中获取昵称、随机值、技能值
     *
     * @param entityTypeMessages 消息封装类
     * @param msg                传入的消息字符串，可能包含骰点表达式，技能名，骰点原因（昵称）等
     * @return 包装后的EntityNickAndRandomAndSkill对象
     */
    public static EntityNickAndRandomAndSkill getNickAndRandomAndSkill(EntityTypeMessages entityTypeMessages, String msg) throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        int random = RandomInt.random(1, 100);

        String skillName = "";
        Matcher mSkillName = skillNamePatten.matcher(msg);
        if (mSkillName.find()) {
            skillName = mSkillName.group(1);
            msg = msg.replaceFirst(".*?" + skillNamePatten.toString(), "");
        }
        String nick = getNickName(entityTypeMessages);
        int skill = 0;

//        根据找到的技能名查询技能值
        if (!skillName.equals(MessagesSystem.NONE)) {
            skill = getSkillValue(entityTypeMessages, skillName.trim());
        }

//        看看剩余消息是不是数字技能值
        if (CheckIsNumbers.isNumeric(msg.trim()) && Integer.parseInt(msg.trim()) >= 0) {
            skill = Integer.parseInt(msg.trim());
        }

//        如果正段消息包含运算符，则将传入消息中的技能计算为技能值返回
        Matcher m = p.matcher(msg);
        if (m.find()) {
            if (!skillName.equals(MessagesSystem.NONE)) {
                msg = msg.replace(skillName, String.valueOf(skill));
            }
            String stringBuilderFunction = skill + msg;
            String[] everyFunction = stringBuilderFunction.split(p.toString());
            EntityStrManyRolls entityStrManyRolls = GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, stringBuilderFunction, everyFunction);
            skill = (int) entityStrManyRolls.getResult();
        }


        return new EntityNickAndRandomAndSkill(nick, random, skill);
    }

}
