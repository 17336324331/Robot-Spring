package com.xingguang.sinanya.dice.game;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotEnableBySimpleException;
import com.xingguang.sinanya.exceptions.NotEnableException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SitaNya
 * 日期: 2019-08-22
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class Deck implements MakeNickToSender {
    private EntityTypeMessages entityTypeMessages;

    public Deck(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void get() throws NotEnableException, NotEnableInGroupException, NotEnableBySimpleException {
        checkEnable();
        String tag = MessagesTag.TAG_DECK;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        String deckType;
        String type;
        if (msg.contains(" ")) {
            deckType = msg.split(" ")[0];
            type = msg.split(" ")[1];
        } else {
            deckType = msg;
            type = "default";
        }
        String result = com.xingguang.sinanya.tools.getinfo.Deck.getDeck(entityTypeMessages, deckType, type);
        Sender.sender(entityTypeMessages, result);
    }

    public void help() throws NotEnableException, NotEnableInGroupException, NotEnableBySimpleException {
        checkEnable();
        HashMap<String, ArrayList<String>> deckHelp = com.xingguang.sinanya.tools.getinfo.Deck.getDeckHelp();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("当前装载的牌组命令:");
        for (Map.Entry<String, ArrayList<String>> entry : deckHelp.entrySet()) {
            stringBuilder.append("\n").append(entry.getKey()).append(",其子命令如下:");
            for (String type : entry.getValue()) {
                if (!type.equals("default")) {
                    stringBuilder.append("\n\t").append(".deck ").append(type);
                }
            }
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    private void checkEnable() throws NotEnableException, NotEnableInGroupException, NotEnableBySimpleException {
        if (!GetMessagesProperties.entityGame.isDeck()) {
            throw new NotEnableException(entityTypeMessages);
        }

        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isDeck()) {
            throw new NotEnableInGroupException(entityTypeMessages);
        }

        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
            throw new NotEnableBySimpleException(entityTypeMessages);
        }
    }
}
