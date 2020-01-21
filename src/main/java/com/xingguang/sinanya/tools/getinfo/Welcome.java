package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.entity.EntityWelcome;
import com.xingguang.sinanya.db.welcome.InsertProperties;

/**
 * @author SitaNya
 * 日期: 2019-08-21
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class Welcome {
    static InsertProperties insertProperties = new InsertProperties();

    public static void insertWelcome(long groupId, EntityWelcome entityWelcome) {
        insertProperties.insertProperties(groupId, entityWelcome);
    }
}
