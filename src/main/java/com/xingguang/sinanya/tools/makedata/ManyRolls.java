package com.xingguang.sinanya.tools.makedata;

import com.xingguang.sinanya.entity.EntityMaxAndMinNum;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:多重骰掷
 * <p>
 * 比如3d6k2时，如何计算具体的结果，这里会返回字符串式的Process:(5+3+4)结果与Int:12两个值，分别作为
 */
public class ManyRolls {

    private ManyRolls() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 根据骰点次数，最大值，取最大值个数，返回数字字符串
     *
     * @param times         次数
     * @param rolls         骰点最大值
     * @param numsMinAndMax 取最大值个数
     * @return 格式化好的数字字符串，如(3+6+4)
     */
    public static String manyRollsProcess(int times, int rolls, int numsMinAndMax, boolean isK) {
        if (numsMinAndMax == 0) {
            numsMinAndMax = times;
        }
        int maxTimesRolls = 10;
        StringBuilder stringBuilder = new StringBuilder(200);
        int intResult = 0;
        if (times == 1) {
            int tmpResult = RandomInt.random(1, rolls);
            return String.valueOf(tmpResult);
        } else if (times > maxTimesRolls) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                list.add(rolls);
            }
            ArrayList<Integer> collect = (ArrayList<Integer>) list.stream().parallel().map(s -> new MakeManyRollsInThread(s).call()).collect(Collectors.toList());
            EntityMaxAndMinNum entityMaxAndMinNum;
            if (isK) {
                entityMaxAndMinNum = GetMaxNumsResult.getMaxNumsResult(collect, numsMinAndMax);
            } else {
                entityMaxAndMinNum = GetMaxNumsResult.getMinNumsResult(collect, numsMinAndMax);
            }
            for (int intTmp : entityMaxAndMinNum.getList()) {
                intResult += intTmp;
            }
            stringBuilder.append(intResult).append(entityMaxAndMinNum.getFunction());
        } else if (times > 1) {
            stringBuilder.append("(");
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                tmp.add(RandomInt.random(1, rolls));
            }
            int i = 0;
            EntityMaxAndMinNum entityMaxAndMinNum;
            if (isK) {
                entityMaxAndMinNum = GetMaxNumsResult.getMaxNumsResult(tmp, numsMinAndMax);
            } else {
                entityMaxAndMinNum = GetMaxNumsResult.getMinNumsResult(tmp, numsMinAndMax);
            }
            for (int tmpRandom : entityMaxAndMinNum.getList()) {
                stringBuilder.append(tmpRandom);
                if (i != (numsMinAndMax - 1)) {
                    stringBuilder.append("+");
                }
                intResult += tmpRandom;
                i++;
            }
            stringBuilder.append(")").append(entityMaxAndMinNum.getFunction());
        }
        return stringBuilder.toString();
    }

    /**
     * 人物卡车卡时大家更喜欢极端的数字，因此改用常规的骰点逻辑，增大离散度
     *
     * @param times   次数
     * @param rolls   骰点最大值
     * @param maxNums 取最大值个数
     * @return 格式化好的数字字符串，如(3+6+4)
     */
    public static String manyRollsProcessForCard(int times, int rolls, int maxNums) {
        if (maxNums == 0) {
            maxNums = times;
        }
        int maxTimesRolls = 10;
        StringBuilder stringBuilder = new StringBuilder(200);
        int intResult = 0;
        if (times == 1) {
            int tmpResult = RandomInt.overRandom(1, rolls);
            return String.valueOf(tmpResult);
        } else if (times > maxTimesRolls) {
            ArrayList<Integer> list = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                list.add(rolls);
            }
            ArrayList<Integer> collect = (ArrayList<Integer>) list.stream().parallel().map(s -> RandomInt.overRandom(1, s)).collect(Collectors.toList());

            for (int intTmp : GetMaxNumsResult.getMaxNumsResult(collect, maxNums).getList()) {
                intResult += intTmp;
            }
            stringBuilder.append(intResult);
        } else if (times > 1) {
            stringBuilder.append("(");
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                tmp.add(RandomInt.overRandom(1, rolls));
            }
            int i = 0;
            for (int tmpRandom : GetMaxNumsResult.getMaxNumsResult(tmp, maxNums).getList()) {
                stringBuilder.append(tmpRandom);
                if (i != (maxNums - 1)) {
                    stringBuilder.append("+");
                }
                intResult += tmpRandom;
                i++;
            }
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    /**
     * 根据骰点次数，最大值，取最大值个数，返回最终结果
     *
     * @param times   次数
     * @param rolls   骰点最大值
     * @param maxNums 取最大值个数
     * @return 最终结果，如15
     */
    static int manyRollsForInt(int times, int rolls, int maxNums) {
        if (maxNums == 0) {
            maxNums = times;
        }
        int maxTimesRolls = 10;
        int intResult = 0;
        if (times == 1) {
            return RandomInt.random(1, rolls);
        } else if (times > maxTimesRolls) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int i = 0; i < times; i++) {
                tmp.add(RandomInt.random(1, rolls));
            }
            for (int tmpRandom : GetMaxNumsResult.getMaxNumsResult(tmp, maxNums).getList()) {
                intResult += tmpRandom;
            }
            return intResult;
        } else {
            return 0;
        }
    }
}
