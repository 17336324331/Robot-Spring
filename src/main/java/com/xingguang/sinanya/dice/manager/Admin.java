package com.xingguang.sinanya.dice.manager;

import com.forte.qqrobot.beans.messages.result.inner.Group;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.SwitchBot;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import static com.forte.qqrobot.beans.messages.types.MsgGetTypes.discussMsg;

/**
 * @author SitaNya
 * 日期: 2019-08-14
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class Admin implements MakeNickToSender {
    private static final Logger log = LoggerFactory.getLogger(Admin.class.getName());
    private EntityTypeMessages entityTypeMessages;

    public Admin(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void on() {
        String tag = MessagesTag.TAG_ADMIN_ON;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        if (GetMessagesProperties.entityBanProperties.getMaster().contains(entityTypeMessages.getFromQqString())) {
            SwitchBot.botOn(Long.parseLong(msg));
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(msg, GetMessagesProperties.entitySystemProperties.getBotStart());
        } else {
            Sender.sender(entityTypeMessages, GetMessagesProperties.entityBanProperties.getNotMaster());
        }
    }

    public void off() {
        String tag = MessagesTag.TAG_ADMIN_OFF;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        if (GetMessagesProperties.entityBanProperties.getMaster().contains(entityTypeMessages.getFromQqString())) {
            SwitchBot.botOff(Long.parseLong(msg));
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(msg, GetMessagesProperties.entitySystemProperties.getBotStop());
        } else {
            Sender.sender(entityTypeMessages, GetMessagesProperties.entityBanProperties.getNotMaster());
        }
    }

    public void exit() {
        String tag = MessagesTag.TAG_ADMIN_EXIT;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        if (GetMessagesProperties.entityBanProperties.getMaster().contains(entityTypeMessages.getFromQqString())) {
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(msg, GetMessagesProperties.entitySystemProperties.getBotExit());
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
            if (entityTypeMessages.getMsgGetTypes() == discussMsg) {
                entityTypeMessages.getMsgSender().SETTER.setDiscussLeave(entityTypeMessages.getFromGroupString());
            } else {
                entityTypeMessages.getMsgSender().SETTER.setGroupLeave(entityTypeMessages.getFromGroupString());
            }

        } else {
            Sender.sender(entityTypeMessages, GetMessagesProperties.entityBanProperties.getNotMaster());
        }
    }

    public void search() {
        String tag = MessagesTag.TAG_ADMIN_SEARCH;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        long qqId;
        if (!CheckIsNumbers.isNumeric(msg)) {
            Sender.sender(entityTypeMessages, "请输入QQ号码");
            return;
        } else {
            qqId = Long.parseLong(msg);
        }
        StringBuilder hasGroup = new StringBuilder();
        hasGroup.append("你和指定人同处于以下群中:");
        Group[] groups = entityTypeMessages.getMsgSender().GETTER.getGroupList().getList();
        for (Group group : groups) {
            if (entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(group.getCode(), String.valueOf(qqId)) != null) {
                hasGroup.append("\n").append(makeGroupNickToSender(group.getName())).append("(").append(group.getCode()).append(")");
            }
        }
        Sender.sender(entityTypeMessages, hasGroup.toString());
    }
}
