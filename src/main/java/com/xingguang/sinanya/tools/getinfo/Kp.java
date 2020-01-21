package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotSetKpGroupException;
import com.xingguang.sinanya.system.MessagesKp;
import com.xingguang.sinanya.db.kp.InsertKp;
import com.xingguang.sinanya.db.kp.SelectKp;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: KP主群数据库交互类
 */
public class Kp {
    private static SelectKp selectKp = new SelectKp();
    private static InsertKp insertKp = new InsertKp();

    private Kp() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 从数据库读取kp主群数据刷写到静态变量，这个方法只在启动时调用一次
     */
    public static void flushKp() {
        selectKp.flushKpFromDatabase();
    }

    /**
     * 将新增的kp主群信息插入数据库
     *
     * @param entityTypeMessages 消息封装类
     * @param groupId            群号
     */
    public static void setKpGroup(EntityTypeMessages entityTypeMessages, String groupId) {
        insertKp.insertKp(entityTypeMessages.getFromQqString(), groupId);
    }

    /**
     * 获取KP主群群号
     *
     * @param entityTypeMessages 消息封装类
     * @return KP主群群号
     * @throws NotSetKpGroupException 未设定KP主群报错，会在打印日志的同时回复消息
     */
    public static String getKpGroup(EntityTypeMessages entityTypeMessages) throws NotSetKpGroupException {
        if (MessagesKp.KP_GROUP.containsKey(entityTypeMessages.getFromQqString())) {
            return MessagesKp.KP_GROUP.get(entityTypeMessages.getFromQqString());
        } else {
            throw new NotSetKpGroupException(entityTypeMessages);
        }
    }
}
