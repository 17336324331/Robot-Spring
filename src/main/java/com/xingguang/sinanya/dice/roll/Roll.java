package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityStrManyRolls;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantInPrivateException;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.RollCantInZeroException;
import com.xingguang.sinanya.system.MessagesOb;
import com.xingguang.sinanya.system.MessagesRollMaxValue;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.checkdata.CheckResultLevel;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.getinfo.GetSkillValue;
import com.xingguang.sinanya.tools.makedata.GetRollResultAndStr;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import com.xingguang.utils.SystemParam;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 默认骰掷，最基础也是最繁复的一个方法，里面包含很多种逻辑
 */
public class Roll implements MakeNickToSender {

    private static Pattern plus = Pattern.compile("[+*/\\-]");

    private static Pattern function = Pattern.compile("([+*/\\-dDkKqQ#\\d]+)");

    private static Pattern AgainTimes = Pattern.compile("(\\d+)#");

    private EntityTypeMessages entityTypeMessages;

    public Roll(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * .r与.rd的骰点逻辑
     * 支持#表示骰点次数（分多次回复）
     * d默认为1D100
     * 1D默认为1D100
     * d60默认为1D60
     * 支持各种各样的计算表达式
     * 不支持技能名替换
     * 支持k+数字的方式取出几个较大值
     * 支持f+数字的方式取出几个较小值
     */
    public void r() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        String tag = MessagesTag.TAGR;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2)).replaceAll(" +.*$", "");

        String nick = makeNickToSender(GetNickName.getNickName(entityTypeMessages));


        StringBuilder stringBuilderFunction = new StringBuilder();
        StringBuilder resultMessage = new StringBuilder(nick + "掷出了: ");

        int intTimes = 1;
        Matcher mAgainTimes = AgainTimes.matcher(msg);
        while (mAgainTimes.find()) {
            intTimes = Integer.parseInt(mAgainTimes.group(1));
            msg = msg.replaceFirst(AgainTimes.toString(), "");
        }
//        获取是否存在#表示重复次数

        Matcher mFunction = function.matcher(msg);

        if (mFunction.find()) {
            stringBuilderFunction.append(mFunction.group(1));
            msg = msg.replaceFirst(function.toString(), "");
        }
        if (!msg.isEmpty()) {
            resultMessage.append(msg).append(": ");
        }
        String[] everyFunction = stringBuilderFunction.toString().split(plus.toString());

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < intTimes; i++) {
//            根据#次数循环
            EntityStrManyRolls entityStrManyRolls = GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, stringBuilderFunction.toString(), everyFunction);

            if (i != 0) {
                result.append("\n");
            }
            if (CheckIsNumbers.isNumeric(entityStrManyRolls.getStrResult())) {
                result.append(resultMessage).append(entityStrManyRolls.getStrFunction()).append("=").append(entityStrManyRolls.getResult());
            } else {
                result.append(resultMessage).append(entityStrManyRolls.getStrFunction()).append("=").append(entityStrManyRolls.getStrResult()).append("=").append(entityStrManyRolls.getResult());
            }
//            如果骰纯数字，则不显示表达式
        }
        Sender.sender(entityTypeMessages, result.toString());
    }

    /**
     * 隐蔽骰点
     * 同样支持上面的所有表达式
     * 支持使用技能或表达式进行判定
     */
    public void rh() throws ManyRollsTimesTooMoreException, RollCantInZeroException, CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_RH;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));

        GetSkillValue getSkillValue = new GetSkillValue(entityTypeMessages, msg);
        int skill = getSkillValue.getSkill();
//        取技能值用于判定

        int intTimes = 1;
        Matcher mAgainTimes = AgainTimes.matcher(msg);
        while (mAgainTimes.find()) {
            intTimes = Integer.parseInt(mAgainTimes.group(1));
            msg = msg.replaceFirst(AgainTimes.toString(), "");
        }
//        获取是否存在#表示重复次数

        String[] everyFunction = getSkillValue.getResStr().split(plus.toString());
//        通过运算符将表达式分段，getSkillValue.getResStr()得到的是，将技能名替换为技能值后其余不变的字符串，比如力量+20的结果是50+20


//        根据#次数循环
        for (int i = 0; i < intTimes; i++) {
            EntityStrManyRolls entityStrManyRolls = GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, getSkillValue.getResStr(), everyFunction);
//            计算得出表达式最终的
//            strFunction符号表达式:3d6k2+4d6*3+d4/2-6d
//            strResult结果表达式: (1+2)+(1+3+4+6)*3+(2)/2-(32+35+12+54)
//            Result实际结果:-87
            //Sender.sender(entityTypeMessages, String.format(GetMessagesProperties.entitySystemProperties.getHiddenDice(), GetNickName.getNickName(entityTypeMessages)));
            Sender.sender(entityTypeMessages, String.format(SystemParam.getRet("strAnTou"), GetNickName.getNickName(entityTypeMessages)));
//            在群中发出暗骰提示

            int maxRolls = MessagesRollMaxValue.ROLL_MAX_VALUE.getOrDefault(entityTypeMessages.getFromGroupString(), 100);
//            确认默认骰点最大值
            int intHidden = RandomInt.random(1, maxRolls);
//            得出骰点结果

            if (entityStrManyRolls.getStrFunction().equals(entityStrManyRolls.getStrResult())) {
                entityStrManyRolls.setStrFunction(msg);
            }
//            如果数学表达式和符号表达式相等，那么只能是因为一开始输入的就是纯数字表达式，因此把可能的技能名表达式重新复制给符号表达式

            String groupName = makeGroupNickToSender(GetNickName.getGroupName(entityTypeMessages));
            if (skill != 0) {
//                如果技能值取到了，那就不管表达式直接进行判定
                String resLevel = new CheckResultLevel(intHidden, skill, true).getLevelResultStr(entityTypeMessages.getFromGroupString());
                entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), "您在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n1D" + maxRolls + "=" + intHidden + "/" + skill + "\n" + resLevel);
                if (MessagesOb.obList.containsKey(entityTypeMessages.getFromGroupString())) {
                    for (String qqId : MessagesOb.obList.get(entityTypeMessages.getFromGroupString())) {
                        entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(qqId, makeNickToSender(entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString()).getNickName()) + "(" + entityTypeMessages.getFromQq() + ")在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n1D" + skill + "=" + intHidden + "/" + skill + "\n" + resLevel);
                    }
                }
                return;
            } else {
//                如果技能值没取到，则使用表达式进行判定
                if (CheckIsNumbers.isNumeric(entityStrManyRolls.getStrFunction())) {
                    entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), "您在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n1D" + maxRolls + "=" + entityStrManyRolls.getResult());
                    if (MessagesOb.obList.containsKey(entityTypeMessages.getFromGroupString())) {
                        for (String qqId : MessagesOb.obList.get(entityTypeMessages.getFromGroupString())) {
                            entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(qqId, makeNickToSender(entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString()).getNickName()) + "(" + entityTypeMessages.getFromQq() + ")在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n1D" + maxRolls + "=" + entityStrManyRolls.getResult());
                        }
                    }
                    return;
                }

                if (CheckIsNumbers.isNumeric(entityStrManyRolls.getStrResult())) {
                    entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), "您在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getResult());
                    if (MessagesOb.obList.containsKey(entityTypeMessages.getFromGroupString())) {
                        for (String qqId : MessagesOb.obList.get(entityTypeMessages.getFromGroupString())) {
                            entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(qqId, makeNickToSender(entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString()).getNickName()) + "(" + entityTypeMessages.getFromQq() + ")在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getResult());
                        }
                    }
                } else {
                    entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), "您在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getStrResult() + "=" + entityStrManyRolls.getResult());
                    if (MessagesOb.obList.containsKey(entityTypeMessages.getFromGroupString())) {
                        for (String qqId : MessagesOb.obList.get(entityTypeMessages.getFromGroupString())) {
                            entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(qqId, makeNickToSender(entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString()).getNickName()) + "(" + entityTypeMessages.getFromQq() + ")在群" + groupName + "(" + entityTypeMessages.getFromGroup() + ")的暗骰结果为:\n" + entityStrManyRolls.getStrFunction() + "=" + entityStrManyRolls.getStrResult() + "=" + entityStrManyRolls.getResult());
                        }
                    }
                }
            }
//            骰点结果和之前的表达式比较后得出成功等级等，私聊回复给命令发起人
        }
    }
}
