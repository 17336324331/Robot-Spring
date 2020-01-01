package com.xingguang.listener;

import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;

/**
 * @author 陈瑞扬
 * @date 2019年12月01日 0:49
 * @description 通用监听器
 */
@Listen(MsgGetTypes.discussMsg)
public class CommonListener {

    /**
     * myBean1是我们自定义的一个类，并且已经将他注入到了依赖管理中心，
     * 所以在想要的时候可以直接通过@Depend进行注入。就像Spring那样子一样。
     * Depend注解也存在若干参数，当没有参数的时候，会自动根据数据类型进行判断与获取。
     * 关于@Depend的参数的详细信息，可以查阅文档。
     */
//    @Depend
//    private MyBean1 myBean1;
//
//    /**
//     * 一个监听器，监听私信消息并打印相关信息
//     */
//    public void listen1(PrivateMsg privateMsg) {
//        //控制台打印其消息信息
//        System.out.println("lis3" + privateMsg.getQQ() + ": " + privateMsg);
//        //打印MyBean1中的MyBean2的school信息
//        //理论上应该是yyyyy中学
//        System.out.println(myBean1.getBean2().getSchool());
//    }

}
