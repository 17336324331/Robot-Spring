package com.xingguang.sinanya.exceptions;

import com.xingguang.sinanya.entity.EntityTypeMessages;

import static com.xingguang.sinanya.tools.makedata.Sender.sender;

public class WodCheckNotIsOneException extends Exception {
    public WodCheckNotIsOneException(EntityTypeMessages entityTypeMessages) {
        super("a值不可以为5以下的数字");
        sender(entityTypeMessages, "a值不可以为5以下的数字");
    }
}
