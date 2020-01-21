package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.dice.get.imal.GetRandomList;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotEnableBySimpleException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesGas;
import com.xingguang.sinanya.tools.makedata.Sender;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 获取煤气灯特质
 */
public class Gas implements GetRandomList {
    private EntityTypeMessages entityTypeMessages;

    public Gas(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 获取煤气灯特质并发送
     */
    public void get() throws NotEnableInGroupException, NotEnableBySimpleException {
        checkEnable();
        Sender.sender(entityTypeMessages, randomNestList((HashMap<Integer, ArrayList<String>>) MessagesGas.GAS_LIST));
    }

    private void checkEnable() throws NotEnableInGroupException, NotEnableBySimpleException {
        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isGas()) {
            throw new NotEnableInGroupException(entityTypeMessages);
        }

        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
            throw new NotEnableBySimpleException(entityTypeMessages);
        }
    }
}
