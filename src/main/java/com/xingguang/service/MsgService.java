package com.xingguang.service;

import com.xingguang.model.MsgModel;

/**
 * @author 陈瑞扬
 * @date 2020年01月02日 16:46
 * @description
 */
public interface MsgService {

    void saveNormalDialog(MsgModel msgModel);

    void saveCommandDialog(MsgModel msgModel);

    MsgModel getMsgModel();
}
