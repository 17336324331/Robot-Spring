package com.xingguang.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 陈瑞扬
 * @date 2020年01月03日 22:55
 * @description
 */
public class StringUtil {

    static Pattern getNumbersPattern = Pattern.compile("\\d+");
    static Pattern splitNotNumberPattern = Pattern.compile("\\D+");
    static Pattern hasDigitPattern = Pattern.compile(".*\\d+.*");
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

    // 判断一个字符串是否都为数字
    public boolean isDigit(String strNum) {
        return strNum.matches("[0-9]{1,}");
    }

//    // 判断一个字符串是否都为数字
//    public boolean isDigit(String strNum) {
//        Pattern pattern = Pattern.compile("[0-9]{1,}");
//        Matcher matcher = pattern.matcher((CharSequence) strNum);
//        return matcher.matches();
//    }

    //截取数字
    public static String getNumbers(String content) {
        Matcher matcher = getNumbersPattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 截取非数字
    public static String splitNotNumber(String content) {
        Matcher matcher = splitNotNumberPattern.matcher(content);
        while (matcher.find()) {
            return matcher.group(0);
        }
        return "";
    }

    // 判断一个字符串是否含有数字
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Matcher m = hasDigitPattern.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }


}
