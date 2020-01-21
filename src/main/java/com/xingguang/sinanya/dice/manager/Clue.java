package com.xingguang.sinanya.dice.manager;

import com.xingguang.sinanya.entity.EntityClue;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantInPrivateException;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.MakeTimeStamp;
import com.xingguang.sinanya.tools.makedata.Sender;

import java.sql.Timestamp;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 线索集
 */
public class Clue {

    private EntityTypeMessages entityTypeMessages;

    public Clue(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 设定线索集，注意为了跑团流畅，这里不予以回显
     * 这里记录了一个时间戳，用于删除
     */
    public void set() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_CLUE_SET;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        com.xingguang.sinanya.tools.getinfo.Clue.setClue(new EntityClue(entityTypeMessages.getFromGroupString(), new Timestamp(System.currentTimeMillis()), entityTypeMessages.getFromQqString()), msg);
    }

    /**
     * 显示线索集，同一个群内所有人的都会被打印出来
     */
    public void show() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String result = com.xingguang.sinanya.tools.getinfo.Clue.getClue(new EntityClue(entityTypeMessages.getFromGroupString()));
        Sender.sender(entityTypeMessages, result);
    }

    /**
     * 删除线索集中的某一条信息，这里除了用信息记录人和群号做过滤，还是用了上面记录的时间戳做key来确保不会删错
     */
    public void rm() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_CLUE_RM;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));

        com.xingguang.sinanya.tools.getinfo.Clue.delClue(new EntityClue(entityTypeMessages.getFromGroupString(), MakeTimeStamp.getTime(msg), entityTypeMessages.getFromQqString()));
        Sender.sender(entityTypeMessages, "已删除线索");
    }

    /**
     * 清除一个群内的全部线索
     */
    public void clr() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        com.xingguang.sinanya.tools.getinfo.Clue.clrClue(entityTypeMessages.getFromGroupString());
        Sender.sender(entityTypeMessages, "清除线索");
    }
}
