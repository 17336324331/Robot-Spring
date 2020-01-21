package com.xingguang.sinanya.db.ban;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.system.MessagesBanList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class SelectBanList {
    private static final Logger log = LoggerFactory.getLogger(SelectBanList.class.getName());


    /**
     * 读取数据库中的骰点历史信息到缓存
     */
    public void flushQqBanListFromDataBase() {
        MessagesBanList.qqBanList.clear();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.qqBanList order by qqId";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        MessagesBanList.qqBanList.put(set.getString("qqId"), set.getString("reason"));
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
    public void flushGroupBanListFromDataBase() {
        MessagesBanList.groupBanList.clear();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.groupBanList order by groupId";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        MessagesBanList.groupBanList.put(set.getString("groupId"), set.getString("reason"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public String selectQqBanInfo(long qqId) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "select botId, qqId, reason, date_format(time,'%Y-%m-%d %H:%i:%s') as time from test.qqBanList where qqId=?";
        selectBanInfo(qqId, stringBuilder, sql);
        return stringBuilder.toString();
    }

    public String selectGroupBanInfo(long groupId) {
        StringBuilder stringBuilder = new StringBuilder();
        String sql = "select botId, groupId, reason, date_format(time,'%Y-%m-%d %H:%i:%s') as time from test.groupBanList where groupId=?";
        selectBanInfo(groupId, stringBuilder, sql);
        return stringBuilder.toString();
    }

    private void selectBanInfo(long id, StringBuilder stringBuilder, String sql) {
        try (Connection conn = DbUtil.getConnection()) {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        stringBuilder.append("状态: 已被拉黑 录入时间:").append(set.getString("time")).append(" 负责人: ").append(set.getString("botId")).append(" 原因: ").append(set.getString("reason"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    public ArrayList<String> selectBanHistory(String id) {
        ArrayList<String> banHistorys = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select botId, Id, reason, isGroup, date_format(inputTime,'%Y-%m-%d %H:%i:%s') as inputTime, date_format(removeTime,'%Y-%m-%d %H:%i:%s') as removeTime from test.banHistory where Id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, id);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        StringBuilder stringBuilder = new StringBuilder();
                        if (set.getBoolean("isGroup")) {
                            stringBuilder.append("群:\t");
                        } else {
                            stringBuilder.append("个人:\t");
                        }
                        stringBuilder.append("状态: 曾拉黑被解除 录入时间:").append(set.getString("inputTime")).append(" 解除时间: ").append(set.getString("removeTime")).append(" 负责人: ").append(set.getString("botId")).append(" 原因: ").append(set.getString("reason"));
                        banHistorys.add(stringBuilder.toString());
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return banHistorys;
    }
}
