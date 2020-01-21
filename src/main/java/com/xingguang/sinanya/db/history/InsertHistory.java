package com.xingguang.sinanya.db.history;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityHistory;
import com.xingguang.sinanya.exceptions.CantFindQqIdException;
import com.xingguang.sinanya.system.MessagesHistory;
import com.xingguang.sinanya.db.imal.CheckQqId;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;


/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 骰点历史录入类，包含插入、清空、更新、删除。只会被com.xingguang.sinanya.listener.InputHistoryToDatabase类中被定时启用，而不是每次骰点都会入库
 */
public class InsertHistory implements CheckQqId {
    private static final Logger log = LoggerFactory.getLogger(InsertHistory.class.getName());


    /**
     * 将历史骰点信息插入数据库，不过这个语句是由定时器每分钟调用一次的
     * 如果觉得卡的话可以去调整com.xingguang.sinanya.listener.InputHistoryToDatabase里的间隔时间
     * 先查询是否存在条目，不存在则插入，存在则更新
     */
    public void insertHistory() throws CantFindQqIdException {
        String botId = checkQqId();
        try (Connection conn = DbUtil.getConnection()) {
            for (Map.Entry<String, EntityHistory> mapEntry : MessagesHistory.HISTORY_LIST.entrySet()) {
                int num = 0;
                EntityHistory entityHistory = mapEntry.getValue();
                String sql = "select * from test.history where qqId=? and botId=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, entityHistory.getQqId());
                    ps.setString(2, botId);
                    try (ResultSet set = ps.executeQuery()) {
                        while (set.next()) {
                            num++;
                        }
                    }
                }
                if (num == 0) {
                    sql = "INSERT INTO test.history(botId,qqId,Fumble,CriticalSuccess,ExtremeSuccess,HardSuccess,Success,Failure,times,mean) VALUES(?,?,?,?,?,?,?,?,?,?)";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setString(1, botId);
                        ps.setString(2, entityHistory.getQqId());
                        ps.setInt(3, entityHistory.getFumble());
                        ps.setInt(4, entityHistory.getCriticalSuccess());
                        ps.setInt(5, entityHistory.getExtremeSuccess());
                        ps.setInt(6, entityHistory.getHardSuccess());
                        ps.setInt(7, entityHistory.getSuccess());
                        ps.setInt(8, entityHistory.getFailure());
                        ps.setInt(9, entityHistory.getTimes());
                        ps.setInt(10, entityHistory.getMean());

                        ps.executeUpdate();
                    }
                } else {
                    sql = "update test.history set Fumble=?,CriticalSuccess=?,ExtremeSuccess=?,HardSuccess=?,Success=?,Failure=?,times=?,mean=? where qqId=? and botId=?";
                    try (PreparedStatement ps = conn.prepareStatement(sql)) {
                        ps.setInt(1, entityHistory.getFumble());
                        ps.setInt(2, entityHistory.getCriticalSuccess());
                        ps.setInt(3, entityHistory.getExtremeSuccess());
                        ps.setInt(4, entityHistory.getHardSuccess());
                        ps.setInt(5, entityHistory.getSuccess());
                        ps.setInt(6, entityHistory.getFailure());
                        ps.setInt(7, entityHistory.getTimes());
                        ps.setInt(8, entityHistory.getMean());
                        ps.setString(9, entityHistory.getQqId());
                        ps.setString(10, botId);

                        ps.executeUpdate();
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
