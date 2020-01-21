package com.xingguang.sinanya.dice.system;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesHelp;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;

public class Man {


    private EntityTypeMessages entityTypeMessages;

    public Man(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void man() {
        String tag = MessagesTag.TAG_MAN;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        Sender.sender(entityTypeMessages, MessagesHelp.helpMap.getOrDefault(msg, "未找到此命令"));
    }
}
