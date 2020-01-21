package com.xingguang.sinanya.db.white;

import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class SelectWhiteList {
    private static final Logger log = LoggerFactory.getLogger(SelectWhiteList.class.getName());


    /**
     * 读取数据库中的骰点历史信息到缓存
     */
    public void flushQqWhiteListFromDataBase() {
        MessagesBanList.qqWhiteList.clear();
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.qqWhiteList where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, MessagesSystem.loginInfo.getLoginId());
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        MessagesBanList.qqWhiteList.add(set.getLong("qqId"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 读取数据库中的骰点历史信息到缓存
     */
    public void flushGroupWhiteListFromDataBase() {
        MessagesBanList.groupWhiteList.clear();
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.groupWhiteList where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, MessagesSystem.loginInfo.getLoginId());
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        MessagesBanList.groupWhiteList.add(set.getLong("groupId"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
