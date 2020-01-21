package com.xingguang.sinanya.dice.game;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotEnableBySimpleException;
import com.xingguang.sinanya.exceptions.NotEnableException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;
import static java.lang.Math.abs;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 今日人品类，其实不是很想做……
 */
public class Jrrp implements MakeNickToSender {
    private EntityTypeMessages entityTypeMessages;

    public Jrrp(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 将系统信息Date转化为毫秒时间戳字符串
     *
     * @param date 系统日期
     * @return 系统日期的毫秒时间戳
     */
    private static String toTimestamp(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String result;
        result = df.format(date);
        return result;
    }

    /**
     * 将结果发送出去，里面使用了对方的QQ号和时间戳作为种子
     */
    public void get() throws NotEnableException, NotEnableInGroupException, NotEnableBySimpleException {
        checkEnable();
        String date = toTimestamp(new Date());
        int tmp = 1;
        char[] b = (entityTypeMessages.getFromQqString() + date).toCharArray();
        //转换成响应的ASCLL
        for (char c : b) {
            tmp *= c;
        }
        Sender.sender(entityTypeMessages, String.format(GetMessagesProperties.entityGame.getJrrpInfo(), makeNickToSender(GetNickName.getNickName(entityTypeMessages)), abs(tmp % 101)));
    }

    private void checkEnable() throws NotEnableException, NotEnableInGroupException, NotEnableBySimpleException {
        if (!GetMessagesProperties.entityGame.isJrrpSwitch()) {
            throw new NotEnableException(entityTypeMessages);
        }
        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isJrrp()) {
            throw new NotEnableInGroupException(entityTypeMessages);
        }

        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup()) && MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
            throw new NotEnableBySimpleException(entityTypeMessages);
        }
    }
}
