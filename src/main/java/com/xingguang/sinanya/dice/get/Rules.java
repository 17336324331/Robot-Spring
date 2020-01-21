package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;

import static com.xingguang.sinanya.db.rules.Rule.selectRule;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class Rules {
    private EntityTypeMessages entityTypeMessages;

    public Rules(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 获取煤气灯特质并发送
     */
    public void get() {
        String tag = MessagesTag.TAG_RULES;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        Sender.sender(entityTypeMessages, selectRule("coc", "rule", msg));
    }
}
