package com.xingguang.sinanya.db.kp;

import com.xingguang.sinanya.db.tools.DbUtil;
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
 * 类说明: 录入KP主群类
 */
public class InsertKp {
    private static final Logger log = LoggerFactory.getLogger(InsertKp.class.getName());


    /**
     * 将kp主群设定插入或更新到数据库中
     *
     * @param qqId    QQ号
     * @param groupId 群号
     */
    public void insertKp(String qqId, String groupId) {
        int num = 0;
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.kp where qqId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, qqId);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        num++;
                    }
                }
            }


            if (num == 0) {
                sql = "INSERT INTO test.kp(qqId,groupId) VALUES(?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, qqId);
                    ps.setString(2, groupId);

                    ps.executeUpdate();
                }
            } else {
                sql = "update test.kp set groupId=? where qqId=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setString(1, groupId);
                    ps.setString(2, qqId);

                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}