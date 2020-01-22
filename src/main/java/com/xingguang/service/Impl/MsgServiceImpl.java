package com.xingguang.service.Impl;

import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.mapper.MsgMapper;
import com.xingguang.model.MsgModel;
import com.xingguang.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月02日 16:48
 * @description 会话存储
 */
@Service
public class MsgServiceImpl implements MsgService {

    @Autowired
    private MsgMapper msgMapper;


    @Override
    public void saveCommandDialog(MsgModel msgModel) {
        msgMapper.saveMsg(msgModel);
    }

    @Override
    public MsgModel getMsgModel() {
        return null;
    }

    @Override
    public void selectRepeat(GroupMsg msg, MsgSender sender, CQCodeUtil cqCodeUtil,Integer num) {
        // 获取发言人的QQ号
        String strQQ = msg.getQQ();
        // 获取发言的群
        String strGroup = msg.getGroup();
        // 获取消息
        String strMsg = msg.getMsg().trim();
        List<MsgModel> msgModels = msgMapper.selectRepeat(strGroup, 2);
        boolean repeatFlag = true;
        boolean repeatPersonFlag = true;
        if (msgModels.size()==2){
            for (int i = 0; i < msgModels.size(); i++) {
                MsgModel currentModel = msgModels.get(i);
                if(!strMsg.equals(currentModel.getStrMsg())){
                    repeatFlag = false;
                }
            }
            if (repeatFlag){
                // 说明重复
                for (int i = 1; i < msgModels.size(); i++) {
                    MsgModel currentModel = msgModels.get(i);
                    if(!strQQ.equals(currentModel.getStrQQ())){
                        repeatPersonFlag = false;
                    }
                }
                if (!repeatPersonFlag){
                    // 说明是三个不同的人发布的消息
                    //SendUtil.sendGroupMsg(sender,strGroup,msg.getMsg());
                    sender.SENDER.sendGroupMsg(strGroup,msg.getMsg());
                }else{
                    //SendUtil.sendGroupMsg(sender,strGroup,cqCodeUtil.getCQCode_At(strQQ)+"刷屏警告");
                    sender.SENDER.sendGroupMsg(strGroup,cqCodeUtil.getCQCode_At(strQQ)+"刷屏警告");
                }
            }
        }
    }



}
