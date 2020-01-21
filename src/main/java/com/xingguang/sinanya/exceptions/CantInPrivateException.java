package com.xingguang.sinanya.exceptions;

import com.xingguang.sinanya.entity.EntityTypeMessages;

import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entitySystemProperties;
import static com.xingguang.sinanya.tools.makedata.Sender.sender;

/**
 * @author SitaNya
 * @date 2019/11/1
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class CantInPrivateException extends Exception {
    public CantInPrivateException(EntityTypeMessages entityTypeMessages) {
        super(entitySystemProperties.getCantInPrivate());
        sender(entityTypeMessages, entitySystemProperties.getCantInPrivate());
    }
}
