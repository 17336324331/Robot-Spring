package com.xingguang.sinanya.exceptions;

import com.xingguang.sinanya.entity.EntityTypeMessages;

import static com.xingguang.sinanya.tools.makedata.Sender.sender;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 多重骰点格式错误
 * 错误发生时会打印详细日志，并原渠道回复错误信息，内容会优先根据配置文件，配置文件没写则根据默认值，都没写会报错（反正我会写默认值的）
 */
public class NotFoundSkillInputException extends Exception {
    public NotFoundSkillInputException(EntityTypeMessages entityTypeMessages) {
        super("请输入技能名或技能值");
        sender(entityTypeMessages, "请输入技能名或技能值");
    }
}
