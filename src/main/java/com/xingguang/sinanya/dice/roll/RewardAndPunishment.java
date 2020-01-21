package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityNickAndRandomAndSkill;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.RollCantInZeroException;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckResultLevel;
import com.xingguang.sinanya.tools.makedata.GetNickAndRandomAndSkill;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import com.xingguang.sinanya.dice.imal.Function;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 奖励骰、惩罚投。这两者不再支持计算公式
 */
public class RewardAndPunishment implements En, MakeNickToSender, Function {

    private EntityTypeMessages entityTypeMessages;

    private int multiple = 10;

    private Pattern timesRegex = Pattern.compile("(\\d+)#");

    public RewardAndPunishment(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }
//    奖励惩罚都是取10位数

    /**
     * 奖励骰
     */
    public void rb() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        String tag = MessagesTag.TAG_RB;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "");
        EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = GetNickAndRandomAndSkill.getNickAndRandomAndSkill(entityTypeMessages, msg);

        StringBuilder stringBuilder = new StringBuilder();

        int random = entityNickAndRandomAndSkill.getRandom();

        int resultRandom = getMin(stringBuilder, random, multiple, msg, entityTypeMessages);

        String strRes;
        String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
        String nick;
        if (msg.contains(":")) {
            nick = msg.split(":")[0];
        } else {
            nick = entityNickAndRandomAndSkill.getNick();
        }
        if (getTimesAndSkill(entityTypeMessages, msg).getSkill() != 0) {
            CheckResultLevel checkResultLevel = new CheckResultLevel(resultRandom, getTimesAndSkill(entityTypeMessages, msg).getSkill(), false);
            strRes = nick +
                    "进行奖励骰鉴定: D100=" + random + "[奖励骰:" + substring + "] = " + resultRandom + "/" + getTimesAndSkill(entityTypeMessages, msg).getSkill() +
                    checkResultLevel.getLevelResultStr(entityTypeMessages.getFromGroupString());
            checkEn(checkResultLevel.getLevel(), msg, entityTypeMessages.getFromQqString(), entityTypeMessages.getFromGroupString());
        } else {
            strRes = nick +
                    "进行奖励骰鉴定: D100=" + random + "[奖励骰:" + substring + "] = " + resultRandom;
        }

        Sender.sender(entityTypeMessages, strRes);
//        将列表打印，并根据最后确定的值进行成功登记判定
    }

    /**
     * 惩罚骰
     */
    public void rp() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        String tag = MessagesTag.TAG_RP;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "");
        int times = 1;
        Matcher findTimes = timesRegex.matcher(msg);
        while (findTimes.find()) {
            times = Integer.parseInt(findTimes.group(1));
            msg = msg.replaceAll(timesRegex.toString(), "");
        }


        StringBuilder strRes = new StringBuilder();
        for (int i = 0; i < times; i++) {
            EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = GetNickAndRandomAndSkill.getNickAndRandomAndSkill(entityTypeMessages, msg);

            StringBuilder stringBuilder = new StringBuilder();

            int random = entityNickAndRandomAndSkill.getRandom();

            int resultRandom = getMax(stringBuilder, random, multiple, msg, entityTypeMessages);

            if (i != 0) {
                strRes.append("\n");
            }
            String substring = stringBuilder.substring(0, stringBuilder.length() - 1);
            String nick;
            if (msg.contains(":")) {
                nick = msg.split(":")[0];
            } else {
                nick = entityNickAndRandomAndSkill.getNick();
            }
            if (getTimesAndSkill(entityTypeMessages, msg).getSkill() != 0) {
                CheckResultLevel checkResultLevel = new CheckResultLevel(resultRandom, getTimesAndSkill(entityTypeMessages, msg).getSkill(), false);
                strRes.append("进行惩罚骰鉴定: D100=").append(random).append("[惩罚骰:").append(substring).append("] = ").append(resultRandom).append("/").append(getTimesAndSkill(entityTypeMessages, msg).getSkill()).append("\t").append(checkResultLevel.getLevelResultStrForSimple());
                checkEn(checkResultLevel.getLevel(), msg, entityTypeMessages.getFromQqString(), entityTypeMessages.getFromGroupString());
            } else {
                strRes.append(nick).append("进行惩罚骰鉴定: D100=").append(random).append("[惩罚骰:").append(substring).append("] = ").append(resultRandom);
            }
        }
        Sender.sender(entityTypeMessages, strRes.toString());
        //        将列表打印，并根据最后确定的值进行成功登记判定
    }
}
