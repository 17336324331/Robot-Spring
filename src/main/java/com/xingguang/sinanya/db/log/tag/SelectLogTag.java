package com.xingguang.sinanya.db.log.tag;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityLogTag;
import com.xingguang.sinanya.system.MessagesLog;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 查询log开启状况，包含当前群内是否有log开启，是哪个log，目前某个log是否处于开启状态
 */
public class SelectLogTag {
    private static final Logger log = LoggerFactory.getLogger(SelectLogTag.class.getName());


    public SelectLogTag() {
        //        初始化时无需逻辑
    }

    /**
     * 刷新日志标记位对应的开关值到静态变量中
     */
    public void flushLogTagFromDatabase() {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.tagLog";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        MessagesLog.LOG_NAME_SWITCH.put(new EntityLogTag(set.getString("groupId"), set.getString("logName")), set.getBoolean("logSwitch"));
                        MessagesLog.LOG_SWITCH_FOR_GROUP.put(set.getString("groupId"), set.getBoolean("logSwitch"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 获取某个群中所有的日志信息列表
     *
     * @param groupId 群号
     * @return 日志名称列表
     */
    public List<String> getTagList(String groupId) {
        ArrayList<String> tagList = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select logName from test.tagLog where groupId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, groupId);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        tagList.add(set.getString("logName"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return tagList;
    }

    /**
     * 获取当前已开启的log日志的名称
     *
     * @param groupId 群号
     * @return 当前已开启的log日志的名称
     */
    public String getOthorLogTrue(String groupId) {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select logSwitch,logName from test.tagLog where groupId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, groupId);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        if (set.getBoolean("logSwitch")) {
                            return set.getString("logName");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return "未找到";
    }
}
