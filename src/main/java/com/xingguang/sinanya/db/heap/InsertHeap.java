package com.xingguang.sinanya.db.heap;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.exceptions.CantFindQqIdException;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetTime;
import com.xingguang.sinanya.db.imal.CheckQqId;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.nlpcn.commons.lang.util.StringUtil;

import java.sql.*;

/**
 * @author SitaNya
 * 日期: 2019-08-09
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class InsertHeap implements CheckQqId {
    private static final Logger log = LoggerFactory.getLogger(InsertHeap.class.getName());


    /**
     * 将QQ黑名单列表入库
     */
    public void updateHeap() throws CantFindQqIdException {
        String botId = checkQqId();
        int retryTimes = 0;
        try (Connection conn = DbUtil.getConnection()) {
            Timestamp timestamp = GetTime.getTime(GetTime.getNowString());
            Timestamp timestampInDatabase = GetTime.getTime("2019-09-26 15:38:00");
            while (!timestamp.equals(timestampInDatabase) && retryTimes < 30) {
                //language=MySQL
                String sql = "replace into test.heap(botId,botName,time,enable,botMaster,version)  VALUES (?,?,?,?,?,?);";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, botId);
                    ps.setString(2, MessagesSystem.loginInfo.getLoginName());
                    ps.setTimestamp(3, timestamp);
                    ps.setBoolean(4, GetMessagesProperties.entityBanProperties.isHeap());
                    ps.setString(5, StringUtil.joiner(GetMessagesProperties.entityBanProperties.getMaster(), ","));
                    ps.setString(6, MessagesSystem.VERSIONS);
                    ps.executeUpdate();
                }
                //language=MySQL
                sql = "select time from test.heap where botId=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, botId);
                    try (ResultSet set = ps.executeQuery()) {
                        while (set.next()) {
                            timestampInDatabase = set.getTimestamp("time");
                        }
                    }
                }
                retryTimes++;
                log.error("无法取到QQ号");
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
