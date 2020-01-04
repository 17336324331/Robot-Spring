package com.xingguang.service.Impl;

import com.xingguang.mapper.MsgMapper;
import com.xingguang.model.MsgModel;
import com.xingguang.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void saveNormalDialog(MsgModel msgModel) {
        msgMapper.saveMsg(msgModel);

    }

    @Override
    public void saveCommandDialog(MsgModel msgModel) {
        msgMapper.saveMsg(msgModel);
    }

    @Override
    public MsgModel getMsgModel() {
        return null;
    }
}
