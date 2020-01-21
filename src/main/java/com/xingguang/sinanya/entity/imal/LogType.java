package com.xingguang.sinanya.entity.imal;

/**
 * @author SitaNya
 * @date 2019/9/12
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public enum LogType {
    /*
     * SPARK说话
     * HIDE括号内信息
     * ACTION行动
     * DICE骰掷
     */
    SPEAK(1),
    HIDE(2),
    ACTION(3),
    DICE(4);

    public int typeId;

    LogType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

}
