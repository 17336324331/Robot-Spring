package com.xingguang.service;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.model.BaseModel;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 20:24
 * @description 被监听后第一个处理的Service
 */
public interface BaseService {

    /**
     *  解析Msg
     * @return  解析结果
     */
      BaseModel dealMsg(GroupMsg msg, MsgSender sender);


}
