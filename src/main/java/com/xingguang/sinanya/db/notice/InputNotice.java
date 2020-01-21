package com.xingguang.sinanya.db.notice;

import com.xingguang.sinanya.entity.EntityNotice;
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
public class InputNotice {
    private static final Logger log = LoggerFactory.getLogger(InputNotice.class.getName());


    /**
     * 将QQ黑名单列表入库
     */
    public void insertNotice(EntityNotice entityNotice) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "INSERT INTO test.notice(groupId,qqId,title,info) VALUES(?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, entityNotice.getGroupId());
                ps.setLong(2, entityNotice.getQqId());
                ps.setString(3, entityNotice.getTitle());
                ps.setString(4, entityNotice.getInfo());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 移除QQ群黑名单
     */
    public void removeGroupBanList(String title) {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "delete from test.notice where title=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, title);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
