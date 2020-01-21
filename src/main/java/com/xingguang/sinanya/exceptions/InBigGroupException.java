package com.xingguang.sinanya.exceptions;

import com.xingguang.sinanya.entity.EntityTypeMessages;

import static com.xingguang.sinanya.tools.makedata.Sender.sender;

/**
 * @author SitaNya
 * @date 2019/10/12
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class InBigGroupException extends Exception {
    public InBigGroupException(EntityTypeMessages entityTypeMessages) {
        super("请不要在大群中骰点影响大家，过多尝试会被警告并通知管理员，你可以私聊骰子或将骰子拉入其他群使用");
        sender(entityTypeMessages, "请不要在大群中骰点影响大家，过多尝试会被警告并通知管理员，你可以私聊骰子或将骰子拉入其他群使用");
    }
}
