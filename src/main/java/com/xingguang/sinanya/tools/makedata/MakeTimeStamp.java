package com.xingguang.sinanya.tools.makedata;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * @author SitaNya
 * 日期: 2019-06-19
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class MakeTimeStamp {
    private static final Logger log = LoggerFactory.getLogger(MakeTimeStamp.class.getName());

    private MakeTimeStamp() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 将格式化好的时间转化为时间戳
     *
     * @param time 传入格式化后的时间
     * @return 时间戳
     */
    public static Timestamp getTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d = null;

        try {
            d = format.parse(time);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new Timestamp((d != null)
                ? d.getTime()
                : 0);
    }
}
