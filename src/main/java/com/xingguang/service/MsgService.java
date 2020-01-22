package com.xingguang.service;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.model.MsgModel;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月02日 16:46
 * @description
 */
public interface MsgService {

    void saveCommandDialog(MsgModel msgModel);

    MsgModel getMsgModel();

    void  selectRepeat(GroupMsg msg, MsgSender sender, CQCodeUtil cqCodeUtil,Integer num);
}
