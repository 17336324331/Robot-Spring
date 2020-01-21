package com.xingguang.sinanya.db.log.info;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityLogTag;
import com.xingguang.sinanya.entity.EntityLogText;
import com.xingguang.sinanya.entity.imal.LogType;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 查询log信息类，只在log get时使用
 */
public class SelectLogInfo {
    private static final Logger log = LoggerFactory.getLogger(SelectLogInfo.class.getName());


    public SelectLogInfo() {
        //        初始化时无需逻辑
    }

    private static <T> T getEnum(int index) {
        T[] c = ((Class<T>) LogType.class).getEnumConstants();
        return c[index - 1];
    }

    /**
     * 根据日志标志对象内的信息查询所有相关日志内容并以换行分割后返回
     *
     * @param entityLogTag 日志标志对象，包含群、日志名
     * @return 符合日志标记对象的所有信息，以换行符分割传出
     */
    public ArrayList<EntityLogText> selectLogInfo(EntityLogTag entityLogTag) {
        ArrayList<EntityLogText> logTexts = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.textLog where groupId=? and logName=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, entityLogTag.getGroupId());
                ps.setString(2, entityLogTag.getLogName());
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        EntityLogText entityLogText = new EntityLogText(set.getString("nick"), set.getString("loginfo"), getEnum(set.getInt("logType")));
                        logTexts.add(entityLogText);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return logTexts;
    }
}
