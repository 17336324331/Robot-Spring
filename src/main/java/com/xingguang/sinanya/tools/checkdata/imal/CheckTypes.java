package com.xingguang.sinanya.tools.checkdata.imal;

/**
 * @author SitaNya
 * 日期: 2019-08-12
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public enum CheckTypes {
    /**
     * privateMsg 私聊类型
     * groupMsg 群聊类型
     * discussMsg 讨论组类型
     */
    CHECK(1),
    CHOOSE(2),
    RD(3),
    ERROR(4);

    private int typeId;

    private CheckTypes(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
