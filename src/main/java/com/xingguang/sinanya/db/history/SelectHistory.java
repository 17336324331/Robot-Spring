package com.xingguang.sinanya.db.history;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityHistory;
import com.xingguang.sinanya.system.MessagesHistory;
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
 * 类说明: 查询骰点历史类，这里不会返回对象而是直接刷写到静态变量中，只有在静态变量中获取不到时才会使用
 */
public class SelectHistory {
    private static final Logger log = LoggerFactory.getLogger(SelectHistory.class.getName());

    public SelectHistory() {
        //        初始化时无需逻辑
    }

    /**
     * 读取数据库中的骰点历史信息到缓存
     */
    public void flushHistoryFromDatabase() {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.history where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        EntityHistory history = new EntityHistory(set.getString("qqId"));
                        history.setFumble(set.getInt("Fumble"));
                        history.setCriticalSuccess(set.getInt("CriticalSuccess"));
                        history.setExtremeSuccess(set.getInt("ExtremeSuccess"));
                        history.setHardSuccess(set.getInt("HardSuccess"));
                        history.setSuccess(set.getInt("Success"));
                        history.setFailure(set.getInt("Failure"));
                        history.setTimes(set.getInt("times"));
                        history.setMean(set.getInt("mean"));
                        MessagesHistory.HISTORY_LIST.put(set.getString("qqId"), history);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
