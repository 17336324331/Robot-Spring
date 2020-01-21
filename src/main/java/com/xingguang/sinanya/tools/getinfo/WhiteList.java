package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.db.white.InputWhiteList;
import com.xingguang.sinanya.db.white.SelectWhiteList;

/**
 * @author SitaNya
 * 日期: 2019/8/28
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class WhiteList {
    static InputWhiteList inputWhiteList = new InputWhiteList();
    static SelectWhiteList selectWhiteList = new SelectWhiteList();

    public static void flushWhiteGroupList() {
        selectWhiteList.flushGroupWhiteListFromDataBase();
    }

    public static void flushWhiteUserList() {
        selectWhiteList.flushQqWhiteListFromDataBase();
    }

    public static void insertGroupWhiteList(long groupId) {
        inputWhiteList.insertGroupWhiteList(groupId);
        MessagesBanList.groupWhiteList.add(groupId);
    }

    public static void insertUserWhiteList(long qqId) {
        inputWhiteList.insertQqWhiteList(qqId);
        MessagesBanList.qqWhiteList.add(qqId);
    }

    public static boolean checkQqInWhiteList(long qq) {
        return MessagesBanList.qqWhiteList.contains(qq);
    }

    public static boolean checkGroupInWhiteList(long group) {
        return MessagesBanList.groupWhiteList.contains(group);
    }

    public static void removeQqWhiteList(long qq) {
        inputWhiteList.removeQqWhiteList(qq);
        MessagesBanList.qqWhiteList.remove(qq);
    }

    public static void removeGroupWhiteList(long group) {
        inputWhiteList.removeGroupWhiteList(group);
        MessagesBanList.groupWhiteList.remove(group);
    }
}
