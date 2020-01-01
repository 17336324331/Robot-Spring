package com.xingguang.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Ignore;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.cqcode.CQCode;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.model.BotModel;
import com.xingguang.utils.CommandUtil;
import com.xingguang.utils.LogicUtil;
import com.xingguang.utils.SystemParam;
import org.apache.commons.lang.StringUtils;

/**
 * @author 陈瑞扬
 * @date 2019年12月01日 0:49
 * @description 私聊消息监听器
 */
@Listen(MsgGetTypes.privateMsg)
public class PrivateMsgListener {


    /**
     * @date 2019/12/1 11:06
     * @author 陈瑞扬
     * @description
     * @param msg
     * @param sender
     * @return
     */
    public void listen1(PrivateMsg msg, MsgSender sender,CQCodeUtil cqCodeUtil) {

        // 获取对方发送的消息
        String strMsg = msg.getMsg();
        // 获取对方QQ
        String strQQ = msg.getQQ();



        try {
            // 对方发送的是指令
            if (CommandUtil.checkCommand(strMsg)){

                // 1.帮助指令明细
                if (strMsg.contains("help")&&strMsg.contains("help指令")){
                    String resultMsg = LogicUtil.helpzhiling();
                    sender.SENDER.sendPrivateMsg(strQQ, resultMsg);
                    return ;
                }

                // 1.帮助指令
                if (strMsg.contains("help")){
                    String resultMsg = LogicUtil.help();
                    sendMsg(strQQ, resultMsg,sender);
                    return ;
                }


                // 2.今日人品
                if (strMsg.contains("jrrp")){
                    String resultMsg = LogicUtil.jrrp();
                    sendMsg(strQQ, resultMsg,sender);
                    return ;
                }

                // 3技能鉴定
                if (strMsg.contains("ra")){
                    String resultMsg = LogicUtil.ra(strMsg);
                    sendMsg(strQQ, resultMsg,sender);
                    return ;
                }

                // 4技能鉴定
                if (strMsg.contains("rc")){
                    String resultMsg = LogicUtil.ra(strMsg);
                    sendMsg(strQQ, resultMsg,sender);
                    return ;
                }

                // 4.普通投掷
                if (strMsg.contains("r")){
                    String resultMsg = LogicUtil.r(strMsg);
                    sendMsg(strQQ, resultMsg,sender);
                }

                // 10.向master发送消息
                if (strMsg.contains("send")){
                    sender.SENDER.sendPrivateMsg(strQQ, strMsg);
                }

            }

        }catch (Exception e){
            System.out.println(e);
        }

    }

    /**
     * 这里的第二个监听方法，自己携带了一个@Listen注解，并标注监听群消息。
     * 则类上的注解失效，优先使用方法上的注解。
     *
     * 此方法代表监听群消息，并在控制台打印发消息的人的QQ号和他发的消息
     */
    @Listen(MsgGetTypes.groupMsg)
    public void listen2(GroupMsg groupMsg){
        //控制台打印消息
        System.out.println(groupMsg.getQQ() + ": " + groupMsg.getMsg());
    }


    /**
     * 这里的第三个监听方法，出现了@Filter注解
     * Filter注解代表了对消息进行过滤。
     * 我只列举了部分比较常用的注解参数，全部参数的详细解释请查阅文档。
     *
     * Filter注解中， value参数代表对消息的过滤内容，
     *               keywordMatchType代表了对消息的过滤方式。
     *               code 代表了过滤发消息的人
     *               group代表了过滤发消息的群的群号
     *
     * 综上所述，以下这个@Filter注解代表了：
     *  只接收群"1111122222"中，qq号为"123456789"的人发送的消息，
     *  并且这个消息在首位去空之后，符合正则表达式："a.b"
     *  例如：123456789发送了一个"a2b"消息
     *
     *  此方法代表，接收了符合上述规则的消息之后，at发消息的这个人
     *
     */
    @Filter(value = "a.b",
            code = "1571650839",
            group = "1111122222",
            keywordMatchType= KeywordMatchType.TRIM_EQUALS)
    public void listen3(GroupMsg groupMsg, MsgSender sender, CQCodeUtil cqCodeUtil){
        //控制台打印消息
        System.out.println(groupMsg.getQQ() + ": " + groupMsg.getMsg());

        //获取at人的CQ码
        CQCode cqCodeAt = cqCodeUtil.getCQCode_At(groupMsg.getQQ());

        //发送at人的消息
        sender.SENDER.sendGroupMsg(groupMsg, cqCodeAt.toString() + " 我at你啦！");
    }


    /**
     * 这里的第4个方法，由于类上有@listen注解，但是这个方法你并不想作为监听函数使用
     * 那么添加一个@Ignore注解，这个方法就会被忽略掉
     */
    @Ignore
    public void listen4(){
        System.out.println("我不会作为监听函数被扫描");
    }

    /**
     * @date 2019/12/1 15:04
     * @author 陈瑞扬
     * @description 校验是否管理员,并按照指定内容发送
     * @param
     * @return
     */
    @Ignore
    public void sendMsg(String strQQ,String resultMsg,MsgSender sender){
        // 获取发言人对应的管理员名称,没有则为null
        String adminName = CommandUtil.checkAdmin(strQQ);
        if(StringUtils.isNotBlank(adminName)){
            resultMsg = adminName + "," + resultMsg;
            sender.SENDER.sendPrivateMsg(strQQ, resultMsg);
        }else{
            sender.SENDER.sendPrivateMsg(strQQ, resultMsg);
        }
    }


}
