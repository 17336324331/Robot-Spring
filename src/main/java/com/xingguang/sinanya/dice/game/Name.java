package com.xingguang.sinanya.dice.game;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.GetNameTimesException;
import com.xingguang.sinanya.exceptions.GetNameTimesTooMoreException;
import com.xingguang.sinanya.exceptions.InputNameTimesForNumberException;
import com.xingguang.sinanya.exceptions.NotEnableException;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetName;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.db.heap.InsertHeap;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author SitaNya
 * 日期: 2019-08-22
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class Name {
    private static final Logger log = LoggerFactory.getLogger(InsertHeap.class.getName());

    private int times = 1;
    private StringBuilder nameList = new StringBuilder().append("您获取的随机姓名为:");
    private EntityTypeMessages entityTypeMessages;

    public Name(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void random() throws NotEnableException, GetNameTimesException {
        checkEnable();
        String tag = MessagesTag.TAG_NAME;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 3));
        times = getTimes(msg);
        for (int i = 0; i < times; i++) {
            nameList.append("\n").append(GetName.getRandomName());
        }
        Sender.sender(entityTypeMessages, nameList.toString());
    }

    public void ch() throws NotEnableException, GetNameTimesException {
        checkEnable();
        String tag = MessagesTag.TAG_NAME_CH;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 3));
        times = getTimes(msg);
        for (int i = 0; i < times; i++) {
            nameList.append("\n").append(GetName.getChineseName());
        }
        Sender.sender(entityTypeMessages, nameList.toString());
    }

    public void en() throws NotEnableException, GetNameTimesException {
        checkEnable();
        String tag = MessagesTag.TAG_NAME_EN;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 3));
        times = getTimes(msg);
        for (int i = 0; i < times; i++) {
            nameList.append("\n").append(GetName.getEnglishName());
        }
        Sender.sender(entityTypeMessages, nameList.toString());
    }

    public void jp() throws NotEnableException, GetNameTimesException {
        checkEnable();
        String tag = MessagesTag.TAG_NAME_JP;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 3));
        times = getTimes(msg);
        for (int i = 0; i < times; i++) {
            nameList.append("\n").append(GetName.getJapaneseName());
        }
        Sender.sender(entityTypeMessages, nameList.toString());
    }

    private int checkNameParam(String msg) throws GetNameTimesTooMoreException, InputNameTimesForNumberException {
        if (msg.equals(MessagesSystem.NONE)) {
            return times;
        }
        if (!CheckIsNumbers.isNumeric(msg)) {
            throw new InputNameTimesForNumberException(entityTypeMessages);
        }
        if (Integer.parseInt(msg) > 10) {
            throw new GetNameTimesTooMoreException(entityTypeMessages);
        }
        return Integer.parseInt(msg);
    }

    private void checkEnable() throws NotEnableException {
        if (!GetMessagesProperties.entityGame.isNameSwitch()) {
            throw new NotEnableException(entityTypeMessages);
        }
    }

    private int getTimes(String msg) throws GetNameTimesException {
        try {
            times = checkNameParam(msg);
        } catch (GetNameTimesTooMoreException | InputNameTimesForNumberException e) {
            log.error(e.getMessage(), e);
            throw new GetNameTimesException(entityTypeMessages);
        }
        return times;
    }
}
