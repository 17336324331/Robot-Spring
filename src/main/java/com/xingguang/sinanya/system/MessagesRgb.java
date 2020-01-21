package com.xingguang.sinanya.system;

import java.util.HashMap;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 接口说明: 颜色的静态信息
 * <p>
 * 这里我挑了一些比较方便看的颜色，括号内颜色是灰色，骰子颜色是橘红色，kp颜色是鲜红色
 */
public class MessagesRgb {

    public static final HashMap<Integer, String> RGB_LIST = new HashMap<Integer, String>() {{
        put(0, "00B5DB");
        put(1, "244AFF");
        put(2, "FF9A24");
        put(3, "D031FF");
        put(4, "F9ADB");
        put(5, "00B756");
        put(6, "272727");
        put(7, "2E03AC");
        put(8, "FFC51C");
        put(9, "660033");
        put(10, "FF7F24");

//        括号内颜色
        put(11, "08FF24");
//        骰子颜色
        put(12, "FF0000");
//        kp颜色
    }};

    private MessagesRgb() {
        throw new IllegalStateException("Utility class");
    }
}
