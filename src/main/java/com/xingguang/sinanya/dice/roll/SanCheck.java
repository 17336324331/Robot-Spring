package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.PlayerSetException;
import com.xingguang.sinanya.exceptions.SanCheckSetException;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.MakeSanCheck;
import com.xingguang.sinanya.tools.makedata.Sender;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 理智检定
 */
public class SanCheck {

    private EntityTypeMessages entityTypeMessages;

    public SanCheck(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * @throws PlayerSetException   可能因为用户输入格式错误而报错
     * @throws SanCheckSetException 用户可能输入无法识别的sc表达式
     */
    public void sc() throws PlayerSetException, SanCheckSetException, ManyRollsTimesTooMoreException {
        String tag = MessagesTag.TAG_SC;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        String result = new MakeSanCheck(entityTypeMessages).checkSanCheck(msg);
        Sender.sender(entityTypeMessages, result);
    }
}
