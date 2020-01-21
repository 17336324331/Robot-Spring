package com.xingguang.sinanya.dice.system;


import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.dice.MakeNickToSender;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 测试类，并不被任何信息调用
 */
public class Test implements MakeNickToSender {

    private static Pattern p = Pattern.compile("[+*/\\-]");

    //    private EntityTypeMessages entityTypeMessages;
    private static Pattern skillNamePatten = Pattern.compile("([^\\d+*\\-/]+)");
    Logger log = LoggerFactory.getLogger(Test.class.getName());

//    public Test(EntityTypeMessages entityTypeMessages) {
//        this.entityTypeMessages = entityTypeMessages;
//    }

    public Test() {
    }

    public void get() {

        String msg = "hp-1";
        String skillName = "";
        Matcher mSkillName = skillNamePatten.matcher(msg);
        if (mSkillName.find()) {
            skillName = mSkillName.group(1);
            msg = msg.replaceFirst(".*?" + skillNamePatten.toString(), "");
        }
//        String nick = getNickName(entityTypeMessages);
        int skill = 0;

//        根据找到的技能名查询技能值
        if (!skillName.equals(MessagesSystem.NONE)) {
            skill = 10;
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
        }
    }

}
