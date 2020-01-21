package com.xingguang.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.service.BaseService;
import com.xingguang.service.MsgService;
import com.xingguang.service.StService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 陈瑞扬
 * @date 2020年01月05日 13:28
 * @description 图片消息Controller
 */
@Component
public class ImageController {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);


    @Autowired
    private BaseService baseService;

    @Autowired
    private MsgService msgService;

    @Autowired
    private StService stService;

    @Filter(value = "st",keywordMatchType = KeywordMatchType.CONTAINS)
    @Listen(MsgGetTypes.groupMsg)
    public void stListener(GroupMsg msg, MsgSender sender) {


    }


}
