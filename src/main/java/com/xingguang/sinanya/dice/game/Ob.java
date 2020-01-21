package com.xingguang.sinanya.dice.game;

import com.xingguang.sinanya.dice.imal.Check;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantInPrivateException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.exceptions.OnlyManagerException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesOb;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.makedata.Sender;

import java.util.ArrayList;

/**
 * @author SitaNya
 * @date 2019/9/11
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class Ob implements Check {

    private EntityTypeMessages entityTypeMessages;

    public Ob(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void input() throws NotEnableInGroupException, CantInPrivateException {
        checkEnable();
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        if (MessagesOb.obList.containsKey(entityTypeMessages.getFromGroupString())) {
            ArrayList<String> obListInGroup = MessagesOb.obList.get(entityTypeMessages.getFromGroupString());
            if (obListInGroup.contains(entityTypeMessages.getFromQqString())) {
                Sender.sender(entityTypeMessages, "您已在本群旁观列表中");
            } else {
                obListInGroup.add(entityTypeMessages.getFromQqString());
                MessagesOb.obList.put(entityTypeMessages.getFromGroupString(), obListInGroup);
                Sender.sender(entityTypeMessages, "已将您加入本群旁观列表");
            }
        } else {
            ArrayList<String> obListInGroup = new ArrayList<>();
            obListInGroup.add(entityTypeMessages.getFromQqString());
            MessagesOb.obList.put(entityTypeMessages.getFromGroupString(), obListInGroup);
            Sender.sender(entityTypeMessages, "已将您加入本群旁观列表");
        }
    }

    public void exit() throws NotEnableInGroupException, CantInPrivateException {
        checkEnable();
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        if (MessagesOb.obList.containsKey(entityTypeMessages.getFromGroupString())) {
            ArrayList<String> obListInGroup = MessagesOb.obList.get(entityTypeMessages.getFromGroupString());
            if (obListInGroup.contains(entityTypeMessages.getFromQqString())) {
                obListInGroup.remove(entityTypeMessages.getFromQqString());
                if (obListInGroup.isEmpty()) {
                    MessagesOb.obList.remove(entityTypeMessages.getFromGroupString());
                } else {
                    MessagesOb.obList.put(entityTypeMessages.getFromGroupString(), obListInGroup);
                }
                Sender.sender(entityTypeMessages, "已将您移出本群旁观列表");
            } else {
                Sender.sender(entityTypeMessages, "您不在本群旁观列表中");
            }
        } else {
            Sender.sender(entityTypeMessages, "您不在本群旁观列表中");
        }
    }

    public void clr() throws OnlyManagerException, NotEnableInGroupException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        checkEnable();
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        MessagesOb.obList.remove(entityTypeMessages.getFromGroupString());
        Sender.sender(entityTypeMessages, "已清空本群旁观列表");
    }

    public void list() throws OnlyManagerException, NotEnableInGroupException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        checkEnable();
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        if (MessagesOb.obList.containsKey(entityTypeMessages.getFromGroupString())) {
            ArrayList<String> obListInGroup = MessagesOb.obList.get(entityTypeMessages.getFromGroupString());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("本群当前旁观列表为:");
            for (String qqId : obListInGroup) {
                stringBuilder.append("\n");
                stringBuilder.append(entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), qqId).getNickName());
                stringBuilder.append("（").append(qqId).append(")");
            }
            Sender.sender(entityTypeMessages, stringBuilder.toString());
        } else {
            Sender.sender(entityTypeMessages, "本群旁观列表为空");
        }
    }


    private void checkEnable() throws NotEnableInGroupException {
        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isOb()) {
            throw new NotEnableInGroupException(entityTypeMessages);
        }
    }
}
