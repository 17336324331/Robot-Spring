package com.xingguang.sinanya.dice.game;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.entity.EntityWelcome;
import com.xingguang.sinanya.exceptions.NotEnableException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.exceptions.OnlyManagerException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.system.MessagesWelcome;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 今日人品类，其实不是很想做……
 */
public class Welcome implements MakeNickToSender {
    private EntityTypeMessages entityTypeMessages;

    public Welcome(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void set() throws NotEnableException, OnlyManagerException, NotEnableInGroupException {
        checkEnable();
        if (entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString()).getPowerType().isMember()) {
            throw new OnlyManagerException(entityTypeMessages);
        }
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(MessagesTag.TAG_WELCOME.substring(0, MessagesTag.TAG_WELCOME.length() - 6), "").trim();
        if (msg.contains("[cq:at") && !msg.contains(String.valueOf(MessagesSystem.loginInfo.getLoginId()))) {
            return;
        }
        long groupId = entityTypeMessages.getFromGroup();
        if (msg.equals("close")) {
            String text = "";
            if (MessagesWelcome.welcomes.containsKey(groupId)) {
                text = MessagesWelcome.welcomes.get(groupId).getText();
            }
            EntityWelcome entityWelcome = new EntityWelcome(false, text);
            com.xingguang.sinanya.tools.getinfo.Welcome.insertWelcome(entityTypeMessages.getFromGroup(), entityWelcome);
            MessagesWelcome.welcomes.put(groupId, entityWelcome);
            Sender.sender(entityTypeMessages, "已关闭，将保留原欢迎词设置");
        } else if (msg.equals("open")) {
            String text = "";
            if (MessagesWelcome.welcomes.containsKey(groupId)) {
                text = MessagesWelcome.welcomes.get(groupId).getText();
            }
            EntityWelcome entityWelcome = new EntityWelcome(true, text);
            com.xingguang.sinanya.tools.getinfo.Welcome.insertWelcome(entityTypeMessages.getFromGroup(), entityWelcome);
            MessagesWelcome.welcomes.put(groupId, entityWelcome);
            Sender.sender(entityTypeMessages, "已开启，原有的欢迎词已保留并恢复");
        } else if (!MessagesWelcome.welcomes.containsKey(groupId) || (!MessagesBanList.groupSwitchHashMap.containsKey(groupId) || MessagesBanList.groupSwitchHashMap.get(groupId).isWelcome())) {
            EntityWelcome entityWelcome = new EntityWelcome(true, msg);
            com.xingguang.sinanya.tools.getinfo.Welcome.insertWelcome(groupId, entityWelcome);
            MessagesWelcome.welcomes.put(groupId, entityWelcome);
            Sender.sender(entityTypeMessages, "已录入,置为开启");
        }
    }

    private void checkEnable() throws NotEnableException, NotEnableInGroupException {
        if (!GetMessagesProperties.entityGame.isNameSwitch()) {
            throw new NotEnableException(entityTypeMessages);
        }
        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isWelcome()) {
            throw new NotEnableInGroupException(entityTypeMessages);
        }
    }
}
