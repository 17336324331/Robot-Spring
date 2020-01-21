package com.xingguang.sinanya.exceptions;

import com.xingguang.sinanya.entity.EntityTypeMessages;

import static com.xingguang.sinanya.tools.makedata.Sender.sender;

public class WodCheckMaxCantInAException extends Exception {
    public WodCheckMaxCantInAException(EntityTypeMessages entityTypeMessages) {
        super("骰掷面数m不可以小于加骰线a或成功线n");
        sender(entityTypeMessages, "骰掷面数m不可以小于加骰线a或成功线n");
    }
}
