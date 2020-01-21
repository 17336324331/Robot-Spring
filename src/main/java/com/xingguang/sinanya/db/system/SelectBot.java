package com.xingguang.sinanya.db.system;

import com.forte.qqrobot.beans.messages.result.inner.Group;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityGroupCensus;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.SystemInfo;
import com.xingguang.sinanya.tools.getinfo.SwitchBot;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 查询所有开关的情况，并刷新到变量中，一般只在变量中找不到时才会使用
 */
public class SelectBot {
    private static final Logger log = LoggerFactory.getLogger(SelectBot.class.getName());


    private SelectBot() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 将所有开关值刷写到静态变量中
     */
    public static void flushBot() {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select groupId,switchBot from test.switchBot where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        SystemInfo.SWITCH_BOT.put(set.getLong("groupId"), set.getBoolean("switchBot"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 通过查询开关列表，得到有多少开启的群，加入多少群
     */
    public static EntityGroupCensus selectBot() {
        int groupNum = 0;
        int onNum = 0;
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select groupId,switchBot from test.switchBot where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        groupNum++;
                        if (set.getBoolean("switchBot")) {
                            onNum++;
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return new EntityGroupCensus(groupNum + 1, onNum + 1);
    }

    /**
     * 通过查询开关列表，得到有多少开启的群，加入多少群
     */
    public static void flushBotList(MsgSender msgSender) {
        ArrayList<String> groupListInDb = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select groupId from test.switchBot where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        groupListInDb.add(set.getString("groupId"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        Group[] groups = msgSender.GETTER.getGroupList().getList();
        for (Group group : groups) {
            if (!groupListInDb.contains(group.getCode())) {
                SwitchBot.botOn(Long.valueOf(group.getCode()));
            }
        }
    }

    /**
     * 通过查询开关列表，得到有多少开启的群，加入多少群
     *
     * @return 关闭的群列表
     */
    public static ArrayList<String> selectOffBotList() {
        ArrayList<String> offGroupList = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select groupId from test.switchBot where botId=? and switchBot=0";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        offGroupList.add(set.getString("groupId"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return offGroupList;
    }

    /**
     * 通过查询开关列表，得到有多少开启的群，加入多少群
     *
     * @return 群列表
     */
    public static ArrayList<Long> selectBotList() {
        ArrayList<Long> offGroupList = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select groupId from test.switchBot where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        offGroupList.add(Long.valueOf(set.getString("groupId")));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return offGroupList;
    }

    /**
     * 通过查询开关列表，得到有多少开启的群，加入多少群
     *
     * @return 群列表
     */
    public static String selectCnModBase() {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select base from coc.cnMod";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getString("base");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
