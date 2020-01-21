package com.xingguang.sinanya.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.DiscussMsg;
import com.forte.qqrobot.beans.messages.msgget.GroupMsg;
import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.forte.qqrobot.beans.messages.msgget.PrivateMsg;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.sinanya.entity.EntityTypeMessages;

import static com.xingguang.sinanya.system.MessagesListenResult.MSG_IGNORE;
import static com.xingguang.sinanya.system.MessagesListenResult.MSG_INTERCEPT;
import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;
import static com.xingguang.sinanya.tools.makedata.MakeMessages.deleteTag;
import static com.xingguang.sinanya.tools.makedata.Sender.sender;

/**
 * @author SitaNya
 * @date 2019/12/18
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class Example {

    /**
     * @param tag1 字符串类型的正则表达式，其中^[.。][ ]*开头为.或。并存在任意空格
     * old是关键字，你可以自行设定
     * .*代表old关键字之后还可以有任意数量的任意字符
     * @param tag2 和tag1类似，用来给你对比找到该改哪里
     */
    private final String tag1 = "^[.。][ ]*" + "old" + ".*";
    private final String tag2 = "^[.。][ ]*" + "new" + ".*";

    /**
     * 一个示例发送器，作用是将发来的信息与群号、发送人QQ号组合成一句话。先私聊给发送者，再发到群里
     * 这里需要注意的是上面的2个注解：Listen和Filter
     *
     * Listen只有一个你需要注意的参数，就是value。其值共有3项
     * MsgGetTypes.groupMsg, MsgGetTypes.discussMsg, MsgGetTypes.privateMsg
     * 分别对应群聊、讨论组，私聊。意思是这个监听器可以接受以上三种途径发出的消息
     * 最后的sort=5是权重。权重越高的监听器越靠前接收消息，SinaNya自带的监听器权重都是默认值1，而你写的监听器只要比1大即可
     *
     * Filter你也只需要注意一个value。它应该等于上方的tag，意为只有符合tag（正则表达式）的信息才会被监听器处理
     *
     * @param msgGet    消息包含器，可以从里面get到符合tag要求的信息
     * @param msgGetTypes   类型包含器，可以从里面确认本条消息是来自群聊、讨论组、私聊还是什么类型
     * @param msgSender 送信器，最重要的一个类型，里面包含三种类型:
     *                  1.  SENDER发信器，用于发出消息到各种渠道，比如群、私聊等
     *                  2.  SETTER设置器，用于设置退群、改变群昵称等
     *                  3.  GETTER取信器，可以获取某个群中某个人的群昵称，某个群中有多少人，群主是谁，某个QQ号对应的昵称等
     * @param msgGroup  群组消息器，从里面可以获得一条消息、消息来自的群号、消息来自的QQ号
     * @param msgPrivate    私聊消息器，从里面可以获得一条消息，消息来自的QQ号
     * @param msgDisGroup   讨论组消息器，和群组消息器类似，现在讨论组已经非常少了
     * @return  返回值，分为  MSG_INTERCEPT 与 MSG_IGNORE。
     * 其中   MSG_INTERCEPT   意为拦截本条消息，酷Q中优先级在本条之后的所有插件不会得到这条消息。
     * 而    MSG_IGNORE  则相反，会处理消息后将消息继续向下传递
     */
//    @Listen(value = {MsgGetTypes.groupMsg, MsgGetTypes.discussMsg, MsgGetTypes.privateMsg}, sort = 5)
//    @Filter.ByName(value = tag1, keywordMatchType = "SINANYA_REGEX")
//    public ListenResult example(MsgGet msgGet, MsgGetTypes msgGetTypes, MsgSender msgSender, GroupMsg msgGroup, PrivateMsg msgPrivate, DiscussMsg msgDisGroup) {
//        EntityTypeMessages entityTypeMessages = new EntityTypeMessages(msgGetTypes, msgSender, msgGet, msgGroup, msgPrivate, msgDisGroup);
////        我个人习惯将所有包含器组装为一个EntityTypeMessages类型，如果你想要调用SinaNya里已有的各种方法，你就会经常和它打交道。
////        你可以理解为它是包含上述所有参数（并写了一些内置判断函数）的集合
//        String msg = deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag1.substring(0, tag1.length() - 2)).replaceAll(" +", "");
////        tag1的内容是.old，而我们接到的信息是.old xxxxx，通过上面这一条，msg内现在只包含有xxxxx了
//        if (msgGetTypes == MsgGetTypes.groupMsg) {
////            如果发信器类型是群组
//            String groupId = entityTypeMessages.getFromGroupString();
////            获取群号
//            String qqId = entityTypeMessages.getFromQqString();
////            获取QQ号
//            msgSender.SENDER.sendPrivateMsg(qqId,String.format("这是%s从群%s发来的测试，内容是%s", qqId, groupId, msg));
////            发送到私聊途径，并使用刚才获得的QQ号发送
//            sender(entityTypeMessages, String.format("这是%s从群%s发来的测试，内容是%s", qqId, groupId, msg));
////            发送到原途径，比如这条消息是群里发出的，那就发回群里。是讨论组发出的，就发回到讨论组
//        }
//        return MSG_INTERCEPT;
//    }
//
//    /**
//     * 和上方类似，这里只监听私聊消息，所以Listen的value只有 MsgGetTypes.privateMsg
//     * 这里监听tag2，也就是.new xxxxx 的内容
//     * 这里返回 MSG_IGNORE  意为放过消息
//     */
//    @Listen(value = {MsgGetTypes.privateMsg}, sort = 5)
//    @Filter.ByName(value = tag2, keywordMatchType = "SINANYA_REGEX")
//    public ListenResult example2(MsgGet msgGet, MsgGetTypes msgGetTypes, MsgSender msgSender, GroupMsg msgGroup, PrivateMsg msgPrivate, DiscussMsg msgDisGroup) {
//        EntityTypeMessages entityTypeMessages = new EntityTypeMessages(msgGetTypes, msgSender, msgGet, msgGroup, msgPrivate, msgDisGroup);
//        String msg = deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag1.substring(0, tag1.length() - 2)).replaceAll(" +", "");
//        String nick = getNickName(entityTypeMessages);
////        取发信人的昵称，当然我也准备了使用qqId来获取昵称的函数，你可以使用getNickName传入Long类型试试。当然，你并不需要有对方的好友
//        String qqId = entityTypeMessages.getFromQqString();
//        sender(entityTypeMessages, String.format("这是%s(%s)从私聊发来的测试，内容是%s", nick, qqId, msg));
//
//        return MSG_IGNORE;
//    }
}
