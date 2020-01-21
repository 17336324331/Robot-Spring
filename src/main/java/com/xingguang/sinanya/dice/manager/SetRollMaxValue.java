package com.xingguang.sinanya.dice.manager;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantInPrivateException;
import com.xingguang.sinanya.system.MessagesRollMaxValue;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.DefaultMaxRolls;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 管理最大默认骰，目前未做入库
 */
public class SetRollMaxValue {
    private EntityTypeMessages entityTypeMessages;

    public SetRollMaxValue(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 设置本群中.r的最大默认骰
     */
    public void set() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_SET_ROLL_MAX_VALUE;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        if (CheckIsNumbers.isNumeric(msg)) {
            MessagesRollMaxValue.ROLL_MAX_VALUE.put(entityTypeMessages.getFromGroupString(), Integer.parseInt(msg));
            DefaultMaxRolls.setMaxRolls(entityTypeMessages.getFromGroupString(), Integer.parseInt(msg));
            Sender.sender(entityTypeMessages, "当前群的默认骰改为" + msg);
        } else {
            Sender.sender(entityTypeMessages, "输入数值有误");
        }
    }
}
