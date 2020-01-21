package com.xingguang.sinanya.dice.imal;

import com.xingguang.sinanya.entity.EntityRewardAndPunishment;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetSkillValue;
import com.xingguang.sinanya.tools.makedata.RandomInt;

import java.util.ArrayList;

public interface Function {
    default int getMin(StringBuilder stringBuilder, int random, int multiple, String msg, EntityTypeMessages entityTypeMessages) {
        ArrayList<Integer> listDice = makeBandProll(getTimesAndSkill(entityTypeMessages, msg).getTimes());
//        得到奖励投列表

        int min = 10;
        for (int result : listDice) {
            stringBuilder.append(result).append(",");
            if (result < min && (random % 10 != 0 || result != 0)) {
                min = result;
            }
        }
//        取最小值


        if (random / multiple < min) {
            min = random / multiple;
        }

        return min * multiple + random % multiple;
//        进行替换，高位替换为整个列表中最小值
    }

    default int getMax(StringBuilder stringBuilder, int random, int multiple, String msg, EntityTypeMessages entityTypeMessages) {
        ArrayList<Integer> listDice = makeBandProll(getTimesAndSkill(entityTypeMessages, msg).getTimes());
//        得到惩罚投列表

        int max = 0;
        boolean hasZero = false;
        for (int result : listDice) {
            stringBuilder.append(result).append(",");
            if (result > max) {
                max = result;
            }
            if (result == 0) {
                hasZero = true;
            }
        }
//        取最大值

        if (random / multiple > max) {
            max = random / multiple;
        }

        int resultRandom;
        if (random % multiple == 0 && hasZero) {
            resultRandom = 100;
        } else {
            resultRandom = max * multiple + random % multiple;
        }
//        进行替换，高位替换为整个列表中最大值
        return resultRandom;
    }

    /**
     * 根据奖励骰或惩罚骰的个数，返回一定数量的0-9随机数列表
     *
     * @param times 个数
     * @return 随机数列表
     */
    default ArrayList<Integer> makeBandProll(int times) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            result.add(RandomInt.random(0, 9));
        }
        return result;
    }

    /**
     * 取出输入信息中的技能值（如果是技能名，则替换为技能值），与惩罚/奖励骰个数，包装后返回
     *
     * @param msg 参数信息字符串，是去除了.rp|.rb后剩下的所有信息
     * @return 惩罚、奖励骰方法，包含技能值和个数
     */
    default EntityRewardAndPunishment getTimesAndSkill(EntityTypeMessages entityTypeMessages, String msg) {
        int times;
        int skill;

        /*
        因为rb格式为“.rb个数 技能”，因此空格前的如果是数字，或者只包含数字则认为是个数。否则个数默认为1
         */
        if (msg.contains(MessagesSystem.SPACE) && CheckIsNumbers.isNumeric(msg.split(MessagesSystem.SPACE)[0])) {
            times = Integer.parseInt(msg.split(MessagesSystem.SPACE)[0]);
        } else if (CheckIsNumbers.isNumeric(msg)) {
            times = Integer.parseInt(msg);
        } else {
            times = 1;
        }

        /*
        如果包含空格，则第二个参数一定存在且为技能，传递给GetSkillValue方法
        如果是数字则直接认为是技能，如果文字则查找对方当前人物卡确定技能值
         */
        if (msg.contains(MessagesSystem.SPACE)) {
            GetSkillValue getSkillValue = new GetSkillValue(entityTypeMessages, msg.split(MessagesSystem.SPACE)[1]);
            skill = getSkillValue.getSkill();
        } else {
            skill = 0;
        }

        return new EntityRewardAndPunishment(times, skill);
    }
}
