package com.xingguang.sinanya.tools.makedata;

import com.xingguang.sinanya.entity.EntityRoleTag;
import com.xingguang.sinanya.entity.EntityStrManyRolls;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.PlayerSetException;
import com.xingguang.sinanya.exceptions.RollCantInZeroException;
import com.xingguang.sinanya.exceptions.SanCheckSetException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.RoleInfoCache;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entitySystemProperties;
import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;
import static com.xingguang.sinanya.tools.getinfo.RoleChoose.getRoleChooseByQQ;
import static com.xingguang.sinanya.tools.getinfo.RoleInfo.*;
import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 进行SanCheck检定
 */
public class MakeSanCheck {

    private static Logger log = LoggerFactory.getLogger(MakeSanCheck.class.getName());
    private static Pattern plus = Pattern.compile("[+*/\\-]");
    private EntityTypeMessages entityTypeMessages;
    private long qq;


    public MakeSanCheck(EntityTypeMessages entityTypeMessages) {
        qq = entityTypeMessages.getFromQq();
        this.entityTypeMessages = entityTypeMessages;
    }

    public MakeSanCheck(EntityTypeMessages entityTypeMessages, String qqId) {
        qq = Long.parseLong(qqId);
        this.entityTypeMessages = entityTypeMessages;
    }

    public MakeSanCheck(EntityTypeMessages entityTypeMessages, long qq) {
        this.qq = qq;
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 恢复san值方法，用于team命令
     *
     * @param function 恢复的字符串，可以是1D3|1D3+5等
     * @throws PlayerSetException 如果无法解析，则返回玩家输入值报错
     */
    public void addSanCheck(String function) throws PlayerSetException {
        String role = MessagesSystem.NONE;
        int san = 0;
        int cthulhuMythos = 0;

        int changeValue;
        String changeFunction;
        String strAddValue;


        if (function.contains(MessagesSystem.SPACE) && !function.split(MessagesSystem.SPACE)[1].isEmpty()) {
            strAddValue = function.split(MessagesSystem.SPACE)[0];
            if (CheckIsNumbers.isNumeric(function.split(MessagesSystem.SPACE)[1])) {
                san = Integer.parseInt(function.split(MessagesSystem.SPACE)[1]);
            }
        } else {
            strAddValue = function;
        }
//        如果字符串中含有空格且第二位不为空，则认为是指定了san值。否则整段都是表达式


        if (CheckIsNumbers.isNumeric(strAddValue)) {
            changeValue = Integer.parseInt(strAddValue);
            changeFunction = strAddValue;
        } else {
            GetRollResultAndStr getRollResultAndStr = null;
            try {
                getRollResultAndStr = new GetRollResultAndStr(entityTypeMessages, strAddValue);
            } catch (ManyRollsTimesTooMoreException e) {

            } catch (RollCantInZeroException e) {
                log.error(e.getMessage(), e);
            }
            assert getRollResultAndStr != null;
            changeValue = getRollResultAndStr.getResInt();
            changeFunction = getRollResultAndStr.getResStr();
        }
//        如果表达式是数字，那么直接恢复即可。如果不是则需要过一下GetRollResultAndStr方法计算最终的值

        HashMap<String, Integer> prop = (HashMap<String, Integer>) getRoleInfoFromChooseByQQ(qq);
        if (san == 0 && prop != null) {
            role = getRoleChooseByQQ(qq);
            san = prop.get("san");
            cthulhuMythos = prop.get("cthulhuMythos");
            prop.put("san", san + changeValue);
            setRoleInfoFromChooseByQQ(qq, prop);
//            计算新的值后更新人物卡
        } else if (san == 0) {
            throw new PlayerSetException(entityTypeMessages);
        }
//        如果san为0，也就是玩家没有输入san值的同时，人物卡还没取到的话就报错。否则优先用玩家输入的san，然后用人物卡。
        Sender.sender(entityTypeMessages, "已为[" + role + "]恢复" + function + "=" + changeValue + "点理智值，剩余" + min((san + changeValue), 99 - cthulhuMythos) + "点");
    }

    @SuppressWarnings("AlibabaMethodTooLong")
    public String checkSanCheck(String function) throws SanCheckSetException, PlayerSetException, ManyRollsTimesTooMoreException {
        int levelFumbleLine = 100;
        String sanFunctionSeq = "/";
        String sanText = "%s/%s=%s\n你的理智值减少%s=%s点,当前剩余%s点%s";

        String strCheckValue;
        String role = null;
        int san = 0;

        /*
         * 如果字符串中含有空格且第二位不为空，则认为是指定了san值。否则整段都是表达式
         */
        if (function.contains(MessagesSystem.SPACE) && !function.split(MessagesSystem.SPACE)[1].isEmpty()) {
            strCheckValue = function.split(MessagesSystem.SPACE)[0];
            if (CheckIsNumbers.isNumeric(function.split(MessagesSystem.SPACE)[1])) {
                san = Integer.parseInt(function.split(MessagesSystem.SPACE)[1]);
            } else if (checkRoleInfoExistByFromQQ(entityTypeMessages, function.split(MessagesSystem.SPACE)[1])) {
                san = getRoleInfoByFromQQ(entityTypeMessages, function.split(MessagesSystem.SPACE)[1]).getOrDefault("san", 0);
                role = function.split(MessagesSystem.SPACE)[1];
            }
        } else {
            strCheckValue = function;
        }


        String tagNone = MessagesSystem.NONE;
        boolean containsSeq = strCheckValue.contains(sanFunctionSeq) && strCheckValue.split(sanFunctionSeq).length == 2;
        boolean firstFunctionError = strCheckValue.split(sanFunctionSeq)[0].equals(tagNone);
        boolean secondFunctionError = containsSeq && strCheckValue.split(sanFunctionSeq)[1].equals(tagNone);
        boolean functionError = !containsSeq || firstFunctionError || secondFunctionError;
        if (functionError) {
            throw new SanCheckSetException(entityTypeMessages);
        }
//        确认表达式合规

        String strSuccess = strCheckValue.split(sanFunctionSeq)[0];
        String strFail = strCheckValue.split(sanFunctionSeq)[1];
//        分别取得成功与失败的表达式

        String[] everyFunctionSuccess = strSuccess.split(plus.toString());
        String[] everyFunctionFail = strFail.split(plus.toString());

        EntityStrManyRolls mSuccess;
        EntityStrManyRolls mFail;
        HashMap<String, Integer> prop;
        boolean useCard = false;
        try {
            mSuccess = GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, strSuccess, everyFunctionSuccess);
            mFail = GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, strFail, everyFunctionFail);
//        分别计算成功与失败的表达式

            if (role != null) {
                prop = (HashMap<String, Integer>) getRoleInfoByFromQQ(entityTypeMessages, function.split(MessagesSystem.SPACE)[1]);
                useCard = true;
            } else {
//        默认为未使用人物卡
                prop = (HashMap<String, Integer>) getRoleInfoFromChooseByQQ(qq);
                if (san == 0 && prop != null) {
                    role = getRoleChooseByQQ(qq);
                    san = prop.get("san");
                    useCard = true;
//            如果读到了人物卡，将使用人物卡标志位记为打开
                } else if (san == 0) {
                    throw new PlayerSetException(entityTypeMessages);
                } else {
                    role = getNickName(entityTypeMessages);
                }
            }
//        如果san为0，也就是玩家没有输入san值的同时，人物卡还没取到的话就报错。否则优先用玩家输入的san，然后用人物卡。
        } catch (Exception e) {
            return "error";
        }

        StringBuilder strResult = new StringBuilder();
        strResult.append("[").append(role).append("]")
                .append("的理智检定结果:")
                .append("\n");
//        初始化回复字符串

        int random = RandomInt.random(1, 100);
//        骰点
        String regexFunctionSeq = "[dD]";
        int newSan;

        boolean groupHasSwitch = MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup());


        if (random == levelFumbleLine) {
//            如果大失败，默认掉最大值
            int maxSan = hasFunctionSeq(strFail) ? getMaxSan(strFail.split(regexFunctionSeq)[1]) : getMaxSan(strFail);
            newSan = max(0, san - maxSan);
            if (!groupHasSwitch || !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
                strResult.append(String.format(sanText, random, san, "大失败", strFail, maxSan, newSan, entitySystemProperties.getSanCheckFumble()));
            } else {
                strResult.append(String.format(sanText, random, san, "大失败", strFail, maxSan, newSan, ""));
            }

            makeInsane(strResult, newSan, san);
        } else if (random == 1) {
            newSan = max(0, san - (int) mSuccess.getResult());
            if (!groupHasSwitch || !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
                strResult.append(String.format(sanText, random, san, "大成功", strSuccess, mSuccess.getResult(), newSan, entitySystemProperties.getSanCheckCriticalSuccess() + "\n本次理智检定大成功，调查员可考虑使用规则书第8章的自救规则，详情请听从守秘人指示"));
            } else {
                strResult.append(String.format(sanText, random, san, "大成功", strSuccess, mSuccess.getResult(), newSan, "\n本次理智检定大成功，调查员可考虑使用规则书第8章的自救规则，详情请听从守秘人指示"));
            }
            makeInsane(strResult, newSan, san);
        } else if (random <= san) {
            newSan = max(0, san - (int) mSuccess.getResult());
            if (!groupHasSwitch || !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
                strResult.append(String.format(sanText, random, san, "成功", strSuccess, mSuccess.getResult(), newSan, entitySystemProperties.getSanCheckSuccess()));
            } else {
                strResult.append(String.format(sanText, random, san, "成功", strSuccess, mSuccess.getResult(), newSan, ""));
            }


            makeInsane(strResult, newSan, san);
        } else {
            newSan = max(0, san - (int) mFail.getResult());
            if (!groupHasSwitch || !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
                strResult.append(String.format(sanText, random, san, "失败", strFail, mFail.getResult(), newSan, entitySystemProperties.getSanCheckFailure()));
            } else {
                strResult.append(String.format(sanText, random, san, "失败", strFail, mFail.getResult(), newSan, ""));
            }
            makeInsane(strResult, newSan, san);
        }
        if (useCard) {
            assert prop != null;
            setCard(newSan, prop, role);
        }
        return strResult.toString();
    }

    /**
     * 更新人物卡信息，不过这里必须注意，人工输入san值是不会更新任务卡信息
     *
     * @param newSan 新的san值
     * @param prop   包含所有属性值的HashMap列表
     * @param role   角色名
     */
    private void setCard(int newSan, HashMap<String, Integer> prop, String role) {
        prop.put("san", newSan);
        setRoleInfoByFromQQ(entityTypeMessages, role, prop);
        RoleInfoCache.ROLE_INFO_CACHE.put(new EntityRoleTag(qq, role), prop);
    }

    /**
     * 根据san值变化判断是否疯狂
     *
     * @param strResult StringBuilder对象，用于整合回复字符串
     * @param newSan    新的san值
     * @param san       原本的san值
     */
    private void makeInsane(StringBuilder strResult, int newSan, int san) {
        if (newSan == 0) {
            strResult.append("\n已永久疯狂");
        } else if (san - newSan >= 5 && (san - newSan) < (san / 5)) {
            strResult.append("\n已进入临时性疯狂（请KP注意需要int检定成功后才进行疯狂，此信息只做提示）");
        } else if ((san - newSan) >= (san / 5)) {
            strResult.append("\n已因单次损失值进入不定性疯狂");
        } else {
            return;
        }
        boolean groupHasSwitch = MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup());
        if (!groupHasSwitch || !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
            strResult.append(entitySystemProperties.getSymptom());
        }
    }

    /**
     * 判断输入字符串是否为表达式，表达式一定带有d或D
     *
     * @param function 输入字符串
     * @return 是否正常
     */
    private boolean hasFunctionSeq(String function) {
        return function.contains("D") || function.contains("d");
    }

    private int getMaxSan(String failFunction) {
        int maxSan = 0;
        if (CheckIsNumbers.isNumeric(failFunction)) {
            maxSan = Integer.parseInt(failFunction);
        } else {
            Sender.sender(entityTypeMessages, entitySystemProperties.getSanCheck());
        }
        return maxSan;
    }

    private int getMinSan(String failFunction) {
        int minSan = 0;
        if (CheckIsNumbers.isNumeric(failFunction)) {
            minSan = Integer.parseInt(failFunction);
        } else {
            Sender.sender(entityTypeMessages, entitySystemProperties.getSanCheck());
        }
        return minSan;
    }
}
