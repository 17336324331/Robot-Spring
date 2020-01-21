package com.xingguang.sinanya.db.system;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.system.MessagesSystem;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 录入机器人在某个群内的开关情况
 */
public class InsertBot {
    private static final Logger log = LoggerFactory.getLogger(InsertBot.class.getName());


    public static void deleteBot(long groupId) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "delete from test.switchBot where botId=? and groupId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                ps.setString(2, String.valueOf(groupId));
                ps.execute();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 将机器人的开关值插入某个群，true为开启，false为关闭
     *
     * @param groupId   群号
     * @param switchBot 开关
     */
    public void insertBot(long groupId, boolean switchBot) {
        int num = 0;
        if (MessagesSystem.loginInfo.getLoginId() == 0) {
            return;
        }
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.switchBot where groupId=? and botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, groupId);
                ps.setString(2, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        num++;
                    }
                }
            }

            if (num == 0) {
                //language=MySQL
                sql = "INSERT INTO test.switchBot(" +
                        "botId," +
                        "groupId," +
                        "switchBot" +
                        ") VALUES(?,?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                    ps.setLong(2, groupId);
                    ps.setBoolean(3, switchBot);
                    ps.executeUpdate();
                    log.info("插入新的群开关" + MessagesSystem.loginInfo.getLoginId() + "groupId: " + groupId + "switchBot: " + switchBot);
                }
            } else {
                //language=MySQL
                sql = "update test.switchBot set " +
                        "switchBot=? where groupId=? and botId=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setBoolean(1, switchBot);
                    ps.setLong(2, groupId);
                    ps.setString(3, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
