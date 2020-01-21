package com.xingguang.sinanya.tools.checkdata;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantInPrivateException;

import static com.forte.qqrobot.beans.messages.types.MsgGetTypes.privateMsg;

/**
 * @author SitaNya
 * @date 2019/11/1
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class CheckCantInPrivate {
    public static void checkCantInPrivate(EntityTypeMessages entityTypeMessages) throws CantInPrivateException {
        if (entityTypeMessages.getMsgGetTypes() == privateMsg) {
            throw new CantInPrivateException(entityTypeMessages);
        }
    }
}
