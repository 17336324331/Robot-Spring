package com.xingguang.utils;

import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.mapper.MsgMapper;
import com.xingguang.model.MsgModel;
import org.apache.ibatis.session.SqlSession;

/**
 * @author 陈瑞扬
 * @date 2019年12月15日 21:25
 * @description 发送信息工具类
 */
public class SendUtil {

    /**
     * @date 2019/12/23 17:06
     * @author 陈瑞扬
     * @description 给指定的群发送消息,不@
     * @param sender
     * @param strGroup
     * @param strMsg
     * @return
     */
    public static void sendGroupMsg(MsgSender sender,String strGroup, String strMsg){
        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        MsgMapper mapper = sqlSession.getMapper(MsgMapper.class);
        MsgModel msgModel = new MsgModel();
        //mapper.saveMsg(strMsg);

        sender.SENDER.sendGroupMsg(strGroup,strMsg);

    }

    public static void beforeSend(String sds){

    }
}
