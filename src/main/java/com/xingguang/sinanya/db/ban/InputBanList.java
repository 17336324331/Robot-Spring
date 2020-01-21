package com.xingguang.sinanya.db.ban;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.NotBanListInputException;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.getinfo.GetTime;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.*;

/**
 * @author SitaNya
 * 日期: 2019-08-03
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class InputBanList {
    private static final Logger log = LoggerFactory.getLogger(InputBanList.class.getName());


    /**
     * 将QQ黑名单列表入库
     */
    public void insertQqBanList(String qq, String reason) {
        //language=MySQL
        String sql = "INSERT INTO test.qqBanList(botId,qqId,reason,time) VALUES(?,?,?,?)";
        insertBanList(qq, reason, sql);
    }

    /**
     * 将QQ群黑名单列表入库
     */
    public void insertGroupBanList(String groupId, String reason) {
        //language=MySQL
        String sql = "INSERT INTO test.groupBanList(botId,groupId,reason,time) VALUES(?,?,?,?)";
        insertBanList(groupId, reason, sql);
    }

    private void insertBanList(String id, String reason, String sql) {
        Timestamp timestamp = GetTime.getTime(GetTime.getNowString());
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                ps.setString(2, id);
                ps.setString(3, reason);
                ps.setTimestamp(4, timestamp);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 将QQ群黑名单列表入库
     */
    public void insertBanHisotry(String id, boolean isGroup) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql;
            if (isGroup) {
                sql = "INSERT INTO test.banHistory(botId,Id,reason,inputTime,removeTime,isGroup) SELECT botId,groupId as Id,reason,time as inputTime,now() as removeTime,1 as isGroup from test.groupBanList where groupId=?";
            } else {
                sql = "INSERT INTO test.banHistory(botId,Id,reason,inputTime,removeTime,isGroup) SELECT botId,qqId as Id,reason,time as inputTime,now() as removeTime,0 as isGroup from test.qqBanList where qqId=?";
            }
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 移除QQ群黑名单
     */
    public void removeGroupBanList(String groupId, EntityTypeMessages entityTypeMessages) throws NotBanListInputException {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "delete from test.groupBanList where groupId=? and botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, groupId);
                ps.setString(2, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new NotBanListInputException(entityTypeMessages);
        }
    }

    /**
     * 移除QQ群黑名单
     */
    public void removeQqBanList(String qq, EntityTypeMessages entityTypeMessages) throws NotBanListInputException {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "delete from test.qqBanList where qqId=? and botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, qq);
                ps.setLong(2, MessagesSystem.loginInfo.getLoginId());

                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new NotBanListInputException(entityTypeMessages);
        }
    }

    /**
     * 查询其它录入人
     */
    public String selectOthorInputBanQq(String qq, EntityTypeMessages entityTypeMessages) throws NotBanListInputException {
        String inputBot = "";
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select botId from test.qqBanList where qqId=? ";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, qq);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        inputBot = set.getString("botId");
                    }
                }
            }
        } catch (SQLException e) {
            throw new NotBanListInputException(entityTypeMessages);
        }
        return inputBot;
    }

    /**
     * 查询其它录入人
     */
    public String selectOthorInputBanGroup(String groupId, EntityTypeMessages entityTypeMessages) throws NotBanListInputException {
        String inputBot = null;
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select botId from test.groupBanList where groupId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, groupId);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        inputBot = set.getString("botId");
                    }
                }
            }
        } catch (SQLException e) {
            throw new NotBanListInputException(entityTypeMessages);
        }
        return inputBot;
    }
}
