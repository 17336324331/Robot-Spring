package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.makedata.Sender;

import static com.xingguang.sinanya.db.rules.Rule.selectRule;

/**
 * @author SitaNya
 * 日期: 2019-08-20
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class DndSpeciality {
    private EntityTypeMessages entityTypeMessages;

    public DndSpeciality(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 获取煤气灯特质并发送
     */
    public void get5e() {
        String tag = MessagesTag.TAG_FEAT_5E;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        Sender.sender(entityTypeMessages, selectRule("dnd", "feat5e", msg));

    }

    /**
     * 获取煤气灯特质并发送
     */
    public void get3r() {
        String tag = MessagesTag.TAG_FEAT_3R;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        Sender.sender(entityTypeMessages, selectRule("dnd", "feat3r", msg));
    }
}
