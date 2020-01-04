package com.xingguang.utils;

/**
 * @author 陈瑞扬
 * @date 2020年01月03日 22:55
 * @description
 */
public class StringUtil {

    /**
     * @date 2020/1/3 22:56
     * @author 陈瑞扬
     * @description 获取一个字符串中的所有数字
     * @param str
     * @return
     */
    public static String getIntFromStr(String str) {
        String ret = "";

        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    ret += str.charAt(i);
                }

            }
        }
        return ret;
    }

    /**
     * @date 2020/1/3 22:56
     * @author 陈瑞扬
     * @description 获取一个字符串中的所有英文
     * @param str
     * @return
     */
    public static String getstrFromStr(String str) {

        return str.replaceAll("[^a-z^A-Z]", "").trim().toLowerCase();

    }


}
