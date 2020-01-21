package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.entity.EntityOtherBotInfo;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotBanListInputException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.db.ban.InputBanList;
import com.xingguang.sinanya.db.ban.SelectBanList;
import com.xingguang.sinanya.db.heap.SelectOnlineBotList;

import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class BanList {

    private static SelectBanList selectBanList = new SelectBanList();
    private static InputBanList insertBanList = new InputBanList();

    private BanList() {
        throw new IllegalStateException("Utility class");
    }

    public static void flushBanList() {
        if (GetMessagesProperties.entityBanProperties.isCloudBan()) {
            selectBanList.flushGroupBanListFromDataBase();
            selectBanList.flushQqBanListFromDataBase();
        }
    }

    public static void insertQqBanList(String qq, String reason) {
        insertBanList.insertQqBanList(qq, reason);
        MessagesBanList.qqBanList.put(qq, reason);
    }

    public static void insertGroupBanList(String groupId, String reason) {
        insertBanList.insertGroupBanList(groupId, reason);
        MessagesBanList.groupBanList.put(groupId, reason);
    }

    public static boolean checkQqInBanList(String qq) {
        return MessagesBanList.qqBanList.containsKey(qq);
    }

    public static boolean checkGroupInBanList(String groupId) {
        return MessagesBanList.groupBanList.containsKey(groupId);
    }

    public static void removeQqBanList(String qq, EntityTypeMessages entityTypeMessages) throws NotBanListInputException {
        String id = insertBanList.selectOthorInputBanQq(qq, entityTypeMessages);

        if (Long.parseLong(id) == MessagesSystem.loginInfo.getLoginId()) {
            insertBanList.insertBanHisotry(qq, false);
            insertBanList.removeQqBanList(qq, entityTypeMessages);
            MessagesBanList.qqBanList.remove(qq);
            Sender.sender(entityTypeMessages, "已将用户:\t" + qq + "移出云黑名单");
            return;
        }

        ArrayList<EntityOtherBotInfo> otherBotInfos = new SelectOnlineBotList().selectBotList();
        for (EntityOtherBotInfo entityOtherBotInfo : otherBotInfos) {
            if (entityOtherBotInfo.getBotId().equals(id)) {
                id = entityOtherBotInfo.getBotName() + "(" + entityOtherBotInfo.getBotId() + ")";
                Sender.sender(entityTypeMessages, "您无法删除此用户黑名单，录入人为: " + id);
                return;
            }
        }
        if ("2694900224".equals(id)) {
            Sender.sender(entityTypeMessages, "此黑名单为shiki处同步，请找接口人2694900224（幽幽子）咨询");
            return;
        }
        Sender.sender(entityTypeMessages, "未找到此用户: " + qq);
    }

    public static void removeGroupBanList(String groupId, EntityTypeMessages entityTypeMessages) throws NotBanListInputException {
        String id = insertBanList.selectOthorInputBanGroup(groupId, entityTypeMessages);
        if (id != null && Long.parseLong(id) == MessagesSystem.loginInfo.getLoginId()) {
            insertBanList.insertBanHisotry(groupId, true);
            insertBanList.removeGroupBanList(groupId, entityTypeMessages);
            MessagesBanList.groupBanList.remove(groupId);
            Sender.sender(entityTypeMessages, "已将群:\t" + groupId + "移出云黑名单");
            return;
        }
        if ("2694900224".equals(id)) {
            Sender.sender(entityTypeMessages, "此黑名单为shiki处同步，请找接口人2694900224（幽幽子）咨询");
            return;
        }
        ArrayList<EntityOtherBotInfo> otherBotInfos = new SelectOnlineBotList().selectBotList();
        for (EntityOtherBotInfo entityOtherBotInfo : otherBotInfos) {
            if (entityOtherBotInfo.getBotId().equals(id)) {
                id = entityOtherBotInfo.getBotName() + "(" + entityOtherBotInfo.getBotId() + ")";
                Sender.sender(entityTypeMessages, "您无法删除此群黑名单，录入人为: " + id);
                return;
            }
        }
        Sender.sender(entityTypeMessages, "未找到此群: " + groupId);
    }
}
