package com.xingguang.sinanya.dice.game;

import com.xingguang.sinanya.entity.EntityOtherBotInfo;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotEnableException;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.db.heap.SelectOnlineBotList;
import com.xingguang.sinanya.dice.MakeNickToSender;

/**
 * @author SitaNya
 * 日期: 2019-08-22
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class DiceList implements MakeNickToSender {
    private EntityTypeMessages entityTypeMessages;

    public DiceList(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void get() throws NotEnableException {
        checkEnable();
        StringBuilder diceList = new StringBuilder();
        diceList.append("目前使用本核心、24小时开机、正在提供服务、并可以共享人物卡等数据的骰娘有:");
        SelectOnlineBotList selectOnlineBotList = new SelectOnlineBotList();
        for (EntityOtherBotInfo entityOtherBotInfo : selectOnlineBotList.selectOnlineBotList()) {
            diceList.append("\n")
                    .append(makeNickToSender(entityOtherBotInfo.getBotName()))
                    .append("(")
                    .append(entityOtherBotInfo.getBotId())
                    .append(")");
        }
        Sender.sender(entityTypeMessages, diceList.toString());
    }

    private void checkEnable() throws NotEnableException {
        if (!GetMessagesProperties.entityGame.isBotList()) {
            throw new NotEnableException(entityTypeMessages);
        }
    }
}
