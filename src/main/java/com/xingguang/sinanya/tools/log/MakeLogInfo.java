package com.xingguang.sinanya.tools.log;

import com.xingguang.sinanya.entity.EntityLogText;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.entity.imal.LogType;
import com.xingguang.sinanya.tools.getinfo.GetNickName;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 日志信息规整类
 */
public class MakeLogInfo {

    static Pattern at = Pattern.compile(".*?\\[[cC][qQ]:at,qq=(\\d+?)].*");

    private MakeLogInfo() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 规整输入的单条日志信息，规整后的结果存入数据库
     *
     * @param entityTypeMessages 消息封装类
     * @param info               单条日志信息
     * @return 规整后的单条日志信息
     */
    public static EntityLogText makeLogInfo(EntityTypeMessages entityTypeMessages, String info) {
        EntityLogText entityLogText = new EntityLogText();
        entityLogText.setNick(GetNickName.getNickName(entityTypeMessages));
        info = info.trim()
                .replaceAll("([\"“”])", "\"")
                .replace("（", "(")
                .replace("）", ")")
                .replace("\\\"", "\"");
        StringBuilder result = new StringBuilder();

//        如果一个信息开头是括号且不存在括号完，或最后是括号且不存在括号开始，则是注释的一种
        boolean isNote1for1 = info.charAt(0) == '(' && !info.contains(")");
        boolean isNote1for2 = info.charAt(info.length() - 1) == ')' && !info.contains("(");
        boolean isNote1 = isNote1for1 || isNote1for2;

        boolean isNote2 = info.charAt(0) == '(' && info.charAt(info.length() - 1) == ')';

//        如果包含.log，则是.log系列命令，不予以记录
        if (info.contains(".log")) {
            return null;
        }

        Matcher hasAt = at.matcher(info);
        while (hasAt.find()) {
            info = info.replaceFirst("\\[[cC][qQ]:at,qq=\\d+?]", "[" + GetNickName.getNickName(Long.parseLong(hasAt.group(1)), entityTypeMessages) + "]");
        }

//        如果开头是。或.，则是跑团命令信息，规整化为=====分割的区块，并添加XXX发起投掷
        if (info.charAt(0) == '.' || info.trim().charAt(0) == '。') {
            result.append(GetNickName.getNickName(entityTypeMessages))
                    .append("发起骰掷");
            entityLogText.setLogType(LogType.DICE);
            entityLogText.setText(result.toString());
            return entityLogText;
        } else {
//            如果一个信息的开头结尾都是引号，则前面加昵称，冒号进行原样输出
            if (info.contains("\"")) {
                result.append(GetNickName.getNickName(entityTypeMessages))
                        .append(":\t")
                        .append(info);
                entityLogText.setLogType(LogType.SPEAK);
//                如果一个信息开头是括号但不包含结尾括号，或者一个信息开头是括号结尾也是括号，则认为整体都被括号扩住
            } else if (isNote1 || isNote2) {
                info = info.replaceAll("([(（])", "").replaceAll("([)）])", "");
                result.append("(")
                        .append(GetNickName.getNickName(entityTypeMessages))
                        .append(":")
                        .append(info)
                        .append(")");
                entityLogText.setLogType(LogType.HIDE);
//                如果#开头，则标记为行动
            } else if (info.charAt(0) == '#') {
                result.append(GetNickName.getNickName(entityTypeMessages))
                        .append("发起行动")
                        .append(info);
                entityLogText.setLogType(LogType.ACTION);
            } else {
//                如果都不符合，那么语句既不是被引号引起来，也不是被括号括起来，那就是正常对话
                result.append(GetNickName.getNickName(entityTypeMessages))
                        .append(":\t")
                        .append("\"")
                        .append(info)
                        .append("\"");
                entityLogText.setLogType(LogType.SPEAK);
            }
            entityLogText.setText(result.toString());
        }
        return entityLogText;
    }

    /**
     * 规整化骰娘的结果信息
     *
     * @param info 骰娘录入的骰点信息
     * @return 规整化后的信息
     */
    public static EntityLogText makeLogInfoDice(EntityTypeMessages entityTypeMessages, String info) {
        EntityLogText entityLogText = new EntityLogText();
        info = info.trim();
        StringBuilder result = new StringBuilder();
//        如果包含"暗骰结果"或"日志"字样则不录入日志
        if (info.contains("暗骰结果") || info.contains("日志")) {
            return null;
        }

        result
                .append("骰娘:\t")
                .append(info)
                .append("\n");
        entityLogText.setLogType(LogType.DICE);
        entityLogText.setText(result.toString());
        entityLogText.setNick(entityTypeMessages.getMsgSender().getPersonInfoByCode(entityTypeMessages.getFromQqString()).getName());
        return entityLogText;
    }
}
