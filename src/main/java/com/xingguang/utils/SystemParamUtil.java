package com.xingguang.utils;

import com.xingguang.listener.GroupMsgListener;
import com.xingguang.model.BotModel;
import com.xingguang.service.BotService;
import com.xingguang.service.SystemParamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月05日 13:30
 * @description
 */
@Component
public class SystemParamUtil {

    private static final Logger logger = LoggerFactory.getLogger(SystemParamUtil.class);

    /**
     * 注入系统参数Service
     */
    @Autowired
    private static SystemParamService systemParamService;

    /**
     * 注入开关Service
     */
    @Autowired
    private static BotService botService;

    public static void checkSystemParam(){
        if (SystemParam.botList == null){
            String master = systemParamService.getSytemParam("master");

            SystemParam.master = master;

            // 2.bot初始化
            List<BotModel> botList = botService.getBotList();
            SystemParam.botList = botList;

            logger.info(SystemParam.master);
            logger.info(SystemParam.botList.toString());
        }
    }
}
