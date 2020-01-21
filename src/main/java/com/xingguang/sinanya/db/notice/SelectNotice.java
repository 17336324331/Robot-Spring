package com.xingguang.sinanya.db.notice;

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
public class SelectNotice {
    private static final Logger log = LoggerFactory.getLogger(SelectNotice.class.getName());


    /**
     * 读取数据库中的骰点历史信息到缓存
     */
    public boolean FindNoticeByGroupId(long groupId, String title) {
        boolean has = false;
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select count(*) as has from test.notice where title=? and groupId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.setLong(2, groupId);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        has = set.getInt("has") != 0;
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return has;
    }
}
