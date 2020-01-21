package com.xingguang.sinanya.entity;

public class EntityNotice {
    long groupId;
    long qqId;
    String title;
    String info;

    public EntityNotice(long groupId, long qqId, String title, String info) {
        this.groupId = groupId;
        this.qqId = qqId;
        this.title = title;
        this.info = info;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public long getQqId() {
        return qqId;
    }

    public void setQqId(long qqId) {
        this.qqId = qqId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
