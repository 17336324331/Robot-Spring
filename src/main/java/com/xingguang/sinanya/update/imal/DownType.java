package com.xingguang.sinanya.update.imal;

public enum DownType {
    /*
     * SPARK说话
     * HIDE括号内信息
     * ACTION行动
     * DICE骰掷
     */
    ZIP(1),
    DECK(2);

    public int typeId;

    DownType(int typeId) {
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }
}
