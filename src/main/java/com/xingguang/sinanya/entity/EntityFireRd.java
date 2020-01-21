package com.xingguang.sinanya.entity;

public class EntityFireRd {
    String skill;
    int success;
    int failed;

    public EntityFireRd(String skill, int success, int failed) {
        this.skill = skill;
        this.success = success;
        this.failed = failed;
    }

    public String getSkill() {
        return skill;
    }

    public int getSuccess() {
        return success;
    }

    public int getFailed() {
        return failed;
    }
}
