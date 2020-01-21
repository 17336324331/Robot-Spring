package com.xingguang.sinanya.db.heap;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityOtherBotInfo;
import com.xingguang.sinanya.tools.getinfo.GetTime;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-08-14
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class SelectOnlineBotList {
    private static final Logger log = LoggerFactory.getLogger(SelectOnlineBotList.class.getName());

    public ArrayList<EntityOtherBotInfo> selectBotList() {
        ArrayList<EntityOtherBotInfo> otherBotInfos = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.heap";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        EntityOtherBotInfo otherBotInfo = new EntityOtherBotInfo();
                        otherBotInfo.setBotId(set.getString("botId"));
                        otherBotInfo.setBotName(set.getString("botName"));
                        otherBotInfos.add(otherBotInfo);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return otherBotInfos;
    }

    public ArrayList<EntityOtherBotInfo> selectOnlineBotList() {
        ArrayList<EntityOtherBotInfo> otherBotInfos = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            long notTimeFor20Min = System.currentTimeMillis() - 60 * 60 * 1000;
            String sql = "select * from test.heap where time>=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, GetTime.secondToDate(notTimeFor20Min));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        EntityOtherBotInfo otherBotInfo = new EntityOtherBotInfo();
                        otherBotInfo.setBotId(set.getString("botId"));
                        otherBotInfo.setBotName(set.getString("botName"));
                        otherBotInfos.add(otherBotInfo);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return otherBotInfos;
    }

}
