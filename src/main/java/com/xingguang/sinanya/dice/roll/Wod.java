package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.entity.EntityWodDice;
import com.xingguang.sinanya.entity.EntityWodDiceStr;
import com.xingguang.sinanya.entity.EntityWodParam;
import com.xingguang.sinanya.exceptions.WodCheckMaxCantInAException;
import com.xingguang.sinanya.exceptions.WodCheckNotIsOneException;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.exceptions.WodToMoreException;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;

/**
 * @author SitaNya
 * @date 2019/9/29
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class Wod {
    private Integer[] desc = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    private Pattern getParam = Pattern.compile("(\\d+)*(a\\d+)*(k\\d+)*(m\\d+)*(\\+\\d+)*");

    private int returnCount = 0;

    private EntityTypeMessages entityTypeMessages;

    public Wod(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void ww() throws WodCheckNotIsOneException, WodToMoreException, WodCheckMaxCantInAException {
        String tag = MessagesTag.TAG_WOD_WW;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        EntityWodDice entityWodDice = getWodDice(msg);
        StringBuilder stringBuilder = new StringBuilder();
        EntityWodParam entityWodParam = makeDiceParam(msg);
        int N = entityWodParam.getNTime();
        int n = entityWodParam.getN();
        int a = entityWodParam.getA();
        int b = entityWodParam.getB();
        int m = entityWodParam.getM();
        stringBuilder.append(GetNickName.getNickName(entityTypeMessages)).append("骰出了")
                .append(N).append("a").append(a).append("k").append(n).append("m").append(m);
        if (b != 0) {
            stringBuilder.append("b").append(b);
        }
        if (N < 50) {
            stringBuilder.append("=\n")
                    .append(entityWodDice.getDiceList());
        } else if (N <= 300) {
            stringBuilder.append("=\n")
                    .append(entityWodDice.getSimple());
            entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), entityWodDice.getDiceList());
        }
        if (b != 0) {
            stringBuilder.append("+")
                    .append(b)
                    .append("=\n")
                    .append(entityWodDice.getCount() - b)
                    .append("+")
                    .append(b);
        }
        stringBuilder.append("=\n").append(entityWodDice.getCount());
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    public void w() throws WodCheckNotIsOneException, WodToMoreException, WodCheckMaxCantInAException {
        String tag = MessagesTag.TAG_WOD_W;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        EntityWodParam entityWodParam = makeDiceParam(msg);
        int N = entityWodParam.getNTime();
        int a = entityWodParam.getA();
        EntityWodDice entityWodDice = getWodDice(msg);
        Sender.sender(entityTypeMessages, GetNickName.getNickName(entityTypeMessages) + "骰出了" + N + "a" + a + "=" + entityWodDice.getCount());
    }

    public void ws() throws WodCheckNotIsOneException, WodToMoreException, WodCheckMaxCantInAException {
        String tag = MessagesTag.TAG_WOD_WS;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        EntityWodDice entityWodDice = getWodDice(msg);
        StringBuilder diceStr = new StringBuilder();
        for (int i = 1; i <= desc.length; i++) {
            diceStr.append("\n结果为").append(i).append("的个数为:\t").append(desc[i - 1]);
        }
        String stringBuilder = GetNickName.getNickName(entityTypeMessages) + "统计为:" +
                diceStr.toString() + "\n" + "最终成功个数为:\t" + entityWodDice.getCount();
        Sender.sender(entityTypeMessages, stringBuilder);
    }

    private EntityWodDice makeResult(int N, int n, int a, int b, int m) {
        int count = 0;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder simpleString = new StringBuilder();
        do {
            EntityWodDiceStr entityWodDiceStr = makeDiceResult(N, n, a, m);
            count += entityWodDiceStr.getCount();

            if (!simpleString.toString().equals("")) {
                simpleString.append("加骰:").append(returnCount).append("\t");
                returnCount = entityWodDiceStr.getRetrunCount();
            } else {
                returnCount = entityWodDiceStr.getRetrunCount();
            }
            simpleString.append("{");
            ArrayList<String> diceTimes = new ArrayList<>();
            for (int i = 1; i <= entityWodDiceStr.getDescThis().length; i++) {
                if (entityWodDiceStr.getDescThis()[i - 1] != 0) {
                    diceTimes.add("(" + i + ":" + entityWodDiceStr.getDescThis()[i - 1] + ")");
                }
            }
            simpleString.append(StringUtil.joiner(diceTimes, ","));
            simpleString.append("}\n");

            stringBuilder.append(entityWodDiceStr.getResult()).append("+");
            if (n >= 10) {
                stringBuilder.append("\n");
            }
            N = entityWodDiceStr.getRetrunCount();

        } while (N != 0);

        return new EntityWodDice(count + b, stringBuilder.substring(0, stringBuilder.length() - 1), simpleString.toString());
    }

    private EntityWodDiceStr makeDiceResult(int N, int n, int a, int m) {
        ArrayList<Integer> diceList = new ArrayList<>();
        Integer[] descThis = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int count = 0;
        int returnCount = 0;

        for (int i = 0; i < N; i++) {
            int result = RandomInt.random(1, m);
            if (result >= n) {
                count++;
            }
            if (result >= a) {
                returnCount++;
            }
            diceList.add(result);
            desc[result - 1] = desc[result - 1] + 1;
            descThis[result - 1] = descThis[result - 1] + 1;
        }
        return new EntityWodDiceStr("{" + StringUtil.joiner(diceList, ",") + "}", count, returnCount, descThis);
    }

    private EntityWodParam makeDiceParam(String msg) throws WodCheckNotIsOneException, WodToMoreException, WodCheckMaxCantInAException {

        int N = 10;
        int n = 8;
        int a = 10;
        int b = 0;
        int m = 10;

        Matcher paramMatcher = getParam.matcher(msg);
        ArrayList<String> params = new ArrayList<>();
        if (paramMatcher.find()) {
            for (int i = 1; i <= paramMatcher.groupCount(); i++) {
                params.add(paramMatcher.group(i));
            }
        }

        for (String param : params) {
            if (param == null) {
                continue;
            }
            if (param.contains("a") && CheckIsNumbers.isNumeric(param.replace("a", ""))) {
                a = Integer.parseInt(param.replace("a", ""));
            } else if (param.contains("k") && CheckIsNumbers.isNumeric(param.replace("k", ""))) {
                n = Integer.parseInt(param.replace("k", ""));
            } else if (param.contains("+") && CheckIsNumbers.isNumeric(param.replace("+", ""))) {
                b = Integer.parseInt(param.replace("+", ""));
            } else if (param.contains("m") && CheckIsNumbers.isNumeric(param.replace("m", ""))) {
                m = Integer.parseInt(param.replace("m", ""));
            } else if (CheckIsNumbers.isNumeric(param)) {
                N = Integer.parseInt(param);
            }
        }

        if (a < 5) {
            throw new WodCheckNotIsOneException(entityTypeMessages);
        }

        if (N > 5000) {
            throw new WodToMoreException(entityTypeMessages);
        }

        if (m < a || m < n) {
            throw new WodCheckMaxCantInAException(entityTypeMessages);
        }

        return new EntityWodParam(N, n, a, b, m);
    }

    private EntityWodDice getWodDice(String msg) throws WodCheckMaxCantInAException, WodCheckNotIsOneException, WodToMoreException {
        EntityWodParam entityWodParam = makeDiceParam(msg);
        int N = entityWodParam.getNTime();
        int n = entityWodParam.getN();
        int a = entityWodParam.getA();
        int b = entityWodParam.getB();
        int m = entityWodParam.getM();
        return makeResult(N, n, a, b, m);
    }
}
