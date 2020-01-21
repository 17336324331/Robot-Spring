package com.xingguang.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Ignore;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.mapper.*;
import com.xingguang.model.*;
import com.xingguang.service.*;
import com.xingguang.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2019年12月01日 0:49
 * @description 群聊消息监听器
 */
@Component
public class StController {

    private static final Logger logger = LoggerFactory.getLogger(StController.class);



    @Autowired
    private BaseService baseService;

    @Autowired
    private MsgService msgService;

    @Autowired
    private StService stService;

    @Filter(value = "st",keywordMatchType = KeywordMatchType.CONTAINS)
    @Listen(MsgGetTypes.groupMsg)
    public void stListener(GroupMsg msg,  MsgSender sender) {


    }



}
