package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.makedata.Sender;

import static com.xingguang.sinanya.db.rules.Rule.selectRule;

public class DndCondition {
    private EntityTypeMessages entityTypeMessages;

    public DndCondition(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void get5e() {
        String tag = MessagesTag.TAG_CONDITION_5e;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        Sender.sender(entityTypeMessages, selectRule("dnd", "con5e", msg));
    }

    public void get3r() {
        String tag = MessagesTag.TAG_CONDITION_3r;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        Sender.sender(entityTypeMessages, selectRule("dnd", "con3r", msg));
    }

}
