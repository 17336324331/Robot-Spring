package com.xingguang.sinanya.exceptions;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;

/**
 * @author SitaNya
 * @date 2019/9/30
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class WodToMoreException extends Exception {
    public WodToMoreException(EntityTypeMessages entityTypeMessages) {
        super("N值不可以超过5000");
        Sender.sender(entityTypeMessages, "N值不可以超过5000");
    }
}
