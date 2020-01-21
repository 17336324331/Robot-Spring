package com.xingguang.sinanya.dice;

import com.xingguang.sinanya.tools.checkdata.CheckValue;

/**
 * @author SitaNya
 * 日期: 2019-07-09
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public interface MakeNickToSender {

    /**
     * 给人物昵称包裹上[]
     *
     * @param nick 人物昵称
     * @return 加工后返回
     */
//    default String makeNickToSender(String nick) {
//        return "[" + nick + "]";
//    }
    default String makeNickToSender(String nick) {
        return "" + nick + "";
    }

    /**
     * 给群名包裹上<>
     *
     * @param nick 群名
     * @return 加工后返回
     */
//    default String makeGroupNickToSender(String nick) {
//        return "<" + nick + ">";
//    }
    default String makeGroupNickToSender(String nick) {
        return "" + nick + "";
    }

    /**
     * 给日志包裹上《》
     *
     * @param logName 日志名
     * @return 加工后返回
     */
    default String makeLogNickToSender(String logName) {
        return "《" + CheckValue.checkValue(logName) + "》";
    }
}
