package com.xingguang.listener;

import com.xingguang.mapper.MessageMapper;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.anno.depend.Beans;
import com.forte.qqrobot.anno.depend.Depend;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

/**
 *
 * 这是一个监听器，他监听一个私信消息，并将其保存至数据库中。
 * 一个监听器类尽量不要同时携带@Beans和@Component注解，如果都要携带的话，
 * 则以Spring方面的注解为主要注解，包括字段上的注解也替换为@Autowired
 * 毕竟是优先使用Spring获取
 *
 * @author ForteScarlet <[email]ForteScarlet@163.com>
 * @since JDK1.8
 **/
@Beans
public class MsgListener {

    /**
     * 字段所使用的@Depend注解为在本框架中使用
     * 而@Autowired则在Spring中被使用
     * 请注意不要搞混了
     * */
//    @Depend
//    private MessageMapper mapper;
//
//    @Listen(MsgGetTypes.privateMsg)
//    public void priMsg(PrivateMsg privateMsg){
//        //记录消息
//        System.out.println(privateMsg.getMsg());
//        System.out.println(mapper);
//
//        mapper.insertNewMsg(privateMsg.getMsg());
//        //展示全部的消息
//        System.out.println(mapper.selcetMsgs());
//    }


}
