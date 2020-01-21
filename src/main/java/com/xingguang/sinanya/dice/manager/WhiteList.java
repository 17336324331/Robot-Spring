package com.xingguang.sinanya.dice.manager;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.WhiteListInputNotIdException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.exceptions.NotMasterException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class WhiteList {
    private static final Logger log = LoggerFactory.getLogger(WhiteList.class.getName());


    private EntityTypeMessages entityTypeMessages;

    public WhiteList(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     *
     */
    public void inputQqWhiteList() {
        if (!GetMessagesProperties.entityBanProperties.isWhiteUser()) {
            Sender.sender(entityTypeMessages, "未启用用户白名单");
            return;
        }
        String tag = MessagesTag.TAG_WHITE_USER;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        try {
            checkMaster();
            isQqOrGroup(msg);
            long qqId = Long.parseLong(msg);
            if (MessagesBanList.qqWhiteList.contains(qqId)) {
                Sender.sender(entityTypeMessages, "用户:\t" + qqId + "已在白名单中");
            } else {
                com.xingguang.sinanya.tools.getinfo.WhiteList.insertUserWhiteList(qqId);
                Sender.sender(entityTypeMessages, "已将用户:\t" + qqId + "加入白名单");
            }
        } catch (WhiteListInputNotIdException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void inputGroupWhiteList() {
        if (!GetMessagesProperties.entityBanProperties.isWhiteGroup()) {
            Sender.sender(entityTypeMessages, "未启群组白名单");
            return;
        }
        String tag = MessagesTag.TAG_WHITE_GROUP;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        try {
            checkMaster();
            isQqOrGroup(msg);
            long groupId = Long.parseLong(msg);
            if (MessagesBanList.groupWhiteList.contains(groupId)) {
                Sender.sender(entityTypeMessages, "群:\t" + groupId + "已在白名单中");
            } else {
                com.xingguang.sinanya.tools.getinfo.WhiteList.insertGroupWhiteList(groupId);
                Sender.sender(entityTypeMessages, "已将群:\t" + groupId + "加白名单");
            }
        } catch (WhiteListInputNotIdException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void rmQqWhiteList() {
        if (!GetMessagesProperties.entityBanProperties.isWhiteUser()) {
            Sender.sender(entityTypeMessages, "未启用用户白名单");
            return;
        }
        String tag = MessagesTag.TAG_RM_WHITE_USER;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        try {
            checkMaster();
            isQqOrGroup(msg);
            long qqId = Long.parseLong(msg);
            if (MessagesBanList.qqWhiteList.contains(qqId)) {
                com.xingguang.sinanya.tools.getinfo.WhiteList.removeQqWhiteList(qqId);
            } else {
                Sender.sender(entityTypeMessages, "用户:\t" + qqId + "不在白名单中");
            }
        } catch (WhiteListInputNotIdException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void rmGroupWhiteList() {
        if (!GetMessagesProperties.entityBanProperties.isWhiteGroup()) {
            Sender.sender(entityTypeMessages, "未启群组白名单");
            return;
        }
        String tag = MessagesTag.TAG_RM_WHITE_GROUP;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        try {
            checkMaster();
            isQqOrGroup(msg);
            long groupId = Long.parseLong(msg);
            if (MessagesBanList.groupWhiteList.contains(groupId)) {
                com.xingguang.sinanya.tools.getinfo.WhiteList.removeGroupWhiteList(groupId);
            } else {
                Sender.sender(entityTypeMessages, "群:\t" + groupId + "不在白名单中");
            }
        } catch (WhiteListInputNotIdException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void getQqWhiteList() {
        if (!GetMessagesProperties.entityBanProperties.isWhiteUser()) {
            Sender.sender(entityTypeMessages, "未启用用户白名单");
            return;
        }
        try {
            checkMaster();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("当前记录的白名单用户列表如下，白名单不做同步");
            for (long qqId : MessagesBanList.qqWhiteList) {
                stringBuilder.append("\n").append(qqId);
            }
            Sender.sender(entityTypeMessages, stringBuilder.toString());
        } catch (NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     *
     */
    public void getGroupWhiteList() {
        if (!GetMessagesProperties.entityBanProperties.isWhiteGroup()) {
            Sender.sender(entityTypeMessages, "未启群组白名单");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("当前记录的白名单群列表如下，白名单不做同步");
        for (long groupId : MessagesBanList.groupWhiteList) {
            stringBuilder.append("\n").append(groupId);
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    private void isQqOrGroup(String input) throws WhiteListInputNotIdException {
        if (!CheckIsNumbers.isNumeric(input) || input.length() > 15 || input.length() < 4) {
            throw new WhiteListInputNotIdException(entityTypeMessages);
        }
    }

    private void checkMaster() throws NotMasterException {
        if (!GetMessagesProperties.entityBanProperties.getMaster().contains(String.valueOf(entityTypeMessages.getFromQqString()))) {
            throw new NotMasterException(entityTypeMessages);
        }
    }
}
