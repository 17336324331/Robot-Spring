package com.xingguang.sinanya.system;

import java.util.HashMap;

/**
 * @author 孔翠老板娘
 * @date 2019/9/10
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 * <p>
 * 所有help命令的详细说明
 */
public class MessagesMan {
    public static HashMap<String, String> MAN_INFO = new HashMap<>();

    private static StringBuilder roll = new StringBuilder()
            .append(".r")
            .append("\t")
            .append("骰点")
            .append("\n");
//    这里固定格式为"private static StringBuilder xxxxxx = new StringBuilder()"后回车.append()然后用.append不断连接，\t是tab，\t是回车，结束后；结尾。其中xxxx是你自己起的名字，比如.r你可以叫roll也可以叫r，这取决于你

    static {
        MAN_INFO.put("roll", roll.toString());
//        每当你写完一个，要在这里put一次，固定格式MAN_INFO.put("xxxxx", xxxxx.toString());即可
    }
}
