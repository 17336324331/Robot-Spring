package com.xingguang.sinanya.tools.getinfo;

import com.forte.qqrobot.beans.messages.result.GroupInfo;

public class GetGroupInfo {
    GroupInfo group;

    public GetGroupInfo(GroupInfo group) {
        this.group = group;
    }

    public String call() {
        return "群号: " + group.getCode()
                + "\t" + "群名: "
                + group.getName() +
                "\n共有成员: " + group.getMemberNum() +
                "\t群上限为: " + group.getMaxMember();
    }
}
