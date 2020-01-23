package com.xingguang.sinanya.dice.system;

import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.xingguang.sinanya.dice.manager.imal.AtQq;
import com.xingguang.sinanya.entity.EntityGroupCensus;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.SwitchBot;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import com.xingguang.utils.SystemParam;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.ArrayList;

import static com.xingguang.sinanya.db.system.InsertBot.deleteBot;
import static com.xingguang.sinanya.db.system.SelectBot.selectBot;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 机器人控制类，如开关退群等
 */
public class Bot implements AtQq, MakeNickToSender {

    private Logger log = LoggerFactory.getLogger(Bot.class.getName());

    private EntityTypeMessages entityTypeMessages;

    public Bot(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 开启
     */
    public void on() {
        ArrayList<String> qqList = getList(MessagesTag.TAG_BOT_ON);
        for (String qq : qqList) {
            if (qq.equals(String.valueOf(MessagesSystem.loginInfo.getLoginId()))) {
                long groupId = entityTypeMessages.getFromGroup();
                if (groupId == 0) {
                    Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getCantInPrivate());
                    return;
                }
                if (SwitchBot.getBot(groupId)) {
                    Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBotAlreadyStart());
                } else {
                    SwitchBot.botOn(groupId);
                    //Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBotStart());
                    Sender.sender(entityTypeMessages, SystemParam.getRet("strStBotOn"));
                }
            }
        }
    }

    /**
     * 关闭
     */
    public void off() {
        ArrayList<String> qqList = getList(MessagesTag.TAG_BOT_OFF);

        for (String qq : qqList) {
            if (qq.equals(String.valueOf(MessagesSystem.loginInfo.getLoginId()))) {
                long groupId = entityTypeMessages.getFromGroup();
                if (groupId == 0) {
                    Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getCantInPrivate());
                    return;
                }
                if (!SwitchBot.getBot(groupId)) {
                    Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBotAlreadyStop());
                } else {
                    SwitchBot.botOff(groupId);
                    //Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBotStop());
                    Sender.sender(entityTypeMessages,SystemParam.getRet("strStBotOff"));
                }
            }
        }
    }

    /**
     * 退群
     */
    public void exit() {
        ArrayList<String> qqList = getList(MessagesTag.TAG_BOT_EXIT);
        for (String qq : qqList) {
            if (qq.equals(String.valueOf(MessagesSystem.loginInfo.getLoginId()))) {
                Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getBotExit());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                    Thread.currentThread().interrupt();
                }
                if (entityTypeMessages.getMsgGetTypes() == MsgGetTypes.groupMsg) {
                    entityTypeMessages.getMsgSender().SETTER.setGroupLeave(entityTypeMessages.getFromGroupString());
                } else if (entityTypeMessages.getMsgGetTypes() == MsgGetTypes.discussMsg) {
                    entityTypeMessages.getMsgSender().SETTER.setDiscussLeave(entityTypeMessages.getFromGroupString());
                }
                deleteBot(entityTypeMessages.getFromGroup());
            }
        }
    }

    /**
     * 机器人信息
     */
    public void info() {
        String botInfo = GetMessagesProperties.entitySystemProperties.getBotInfo();
        if (!botInfo.equals(MessagesSystem.NONE)) {
            botInfo = "\n" + botInfo;
        }
        EntityGroupCensus entityGroupCensus = selectBot();
        //Sender.sender(entityTypeMessages, MessagesSystem.STR_BOT_VERSIONS.toString() + "\n目前供职于:\t" + entityGroupCensus.getGroupNum() + " 个群，其中 " + entityGroupCensus.getOnNum() + " 个群处于开启状态" + botInfo);
        Sender.sender(entityTypeMessages,"我是由塔系版本5.52.87.78Beta改版的短刀不动行光," + "\n目前供职于:\t" + entityGroupCensus.getGroupNum() + " 个群，其中 " + entityGroupCensus.getOnNum() + " 个群处于开启状态" + botInfo);
    }

    /**
     * 机器人更新日志
     */
    public void update() {
        Sender.sender(entityTypeMessages, "当前版本:\t" + MessagesSystem.VERSIONS + "\n" + MessagesSystem.UPDATE.toString());
    }

    private ArrayList<String> getList(String tag) {
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));

        ArrayList<String> qqList = getAtQqList(msg);

        if (qqList.isEmpty()) {
            qqList.add(String.valueOf(MessagesSystem.loginInfo.getLoginId()));
        }
        return qqList;
    }
}
