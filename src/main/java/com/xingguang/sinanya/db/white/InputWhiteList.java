package com.xingguang.sinanya.db.white;

import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author SitaNya
 * 日期: 2019-08-03
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class InputWhiteList {
    private static final Logger log = LoggerFactory.getLogger(InputWhiteList.class.getName());


    /**
     * 将QQ白名单列表入库
     */
    public void insertQqWhiteList(long qq) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "INSERT INTO test.qqWhiteList(botId,qqId) VALUES(?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, MessagesSystem.loginInfo.getLoginId());
                ps.setLong(2, qq);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 将QQ群白名单列表入库
     */
    public void insertGroupWhiteList(long groupId) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "INSERT INTO test.groupWhiteList(botId,groupId) VALUES(?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, MessagesSystem.loginInfo.getLoginId());
                ps.setLong(2, groupId);

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 移除QQ群白名单
     */
    public void removeGroupWhiteList(long groupId) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "delete from test.groupWhiteList where groupId=? and botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, groupId);
                ps.setLong(2, MessagesSystem.loginInfo.getLoginId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 移除QQ群白名单
     */
    public void removeQqWhiteList(long qq) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "delete from test.qqWhiteList where qqId=? and botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, qq);
                ps.setLong(2, MessagesSystem.loginInfo.getLoginId());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
