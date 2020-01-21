package com.xingguang.sinanya.dice.getbook;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.makedata.Sender;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 获取资料集，都是存储在百度网盘里的链接，回复语句而已
 */
public class Book {

    private EntityTypeMessages entityTypeMessages;

    public Book(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 车卡指南
     */
    public void make() {
        Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBookMake());
    }

    /**
     * 人物卡Excel
     */
    public void card() {
        Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBookCard());
    }

    /**
     * 规则书
     */
    public void kp() {
        Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBookKp());
    }

    /**
     * 人物扮演自问
     */
    public void rp() {
        Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBookRp());
    }

}
