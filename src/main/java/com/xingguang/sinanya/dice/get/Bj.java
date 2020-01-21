package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.dice.get.imal.GetRandomList;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotEnableBySimpleException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesBg;
import com.xingguang.sinanya.tools.makedata.Sender;

import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 获取人物背景
 */
public class Bj implements GetRandomList {

    private EntityTypeMessages entityTypeMessages;

    public Bj(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 拼装人物背景并发送，里面用到了很多com.xingguang.sinanya.system包中的静态列表信息
     */
    public void bg() throws NotEnableInGroupException, NotEnableBySimpleException {
        checkEnable();
        String stringBuilder = "个人描述:\t\t" +
                randomFromList((ArrayList<String>) MessagesBg.INFO) +
                "\n" +
                "思想信念:\t\t" +
                randomFromList((ArrayList<String>) MessagesBg.PERSUASION) +
                "\n" +
                "重要之人:\t\t" +
                randomFromList((ArrayList<String>) MessagesBg.IMPORTANT_PERSONS) +
                "\n" +
                "重要之人理由:\t" +
                randomFromList((ArrayList<String>) MessagesBg.IMPORTANT_PERSONS_INFO) +
                "\n" +
                "意义非凡之地:\t" +
                randomFromList((ArrayList<String>) MessagesBg.IMPORTANT_MAP) +
                "\n" +
                "宝贵之物:\t\t" +
                randomFromList((ArrayList<String>) MessagesBg.PRECIOUS) +
                "\n" +
                "调查员特点:\t\t" +
                randomFromList((ArrayList<String>) MessagesBg.SPECIALITY) +
                "\n" +
                "既然决定了背景，就一定要好好扮演不要出戏哦！";
        Sender.sender(entityTypeMessages, stringBuilder);
    }

    private void checkEnable() throws NotEnableInGroupException, NotEnableBySimpleException {
        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isBg()) {
            throw new NotEnableInGroupException(entityTypeMessages);
        }

        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
            throw new NotEnableBySimpleException(entityTypeMessages);
        }
    }
}
