package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.makedata.Sender;

import static com.xingguang.sinanya.db.rules.Rule.selectRule;

/**
 * @author SitaNya
 * @date 2019/9/27
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class DndMonster {
    private EntityTypeMessages entityTypeMessages;

    public DndMonster(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void get5e() {
        String tag = MessagesTag.TAG_MONSTER_5e;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        Sender.sender(entityTypeMessages, selectRule("dnd", "monstor5e", msg));
    }

    public void get3r() {
        String tag = MessagesTag.TAG_MONSTER_3r;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        Sender.sender(entityTypeMessages, selectRule("dnd", "monstor3r", msg));
    }

}
