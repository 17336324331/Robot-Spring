package com.xingguang.sinanya.db.setdefaultrolls;

import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SitaNya
 * 日期: 2019-06-17
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 默认骰点上限录入类
 * <p>
 * 这里不再特地录入默认值100了
 */
public class InsertDefaultMaxRolls {
    private static final Logger log = LoggerFactory.getLogger(InsertDefaultMaxRolls.class.getName());


    /**
     * 将设定的最大骰点值记录进数据库
     *
     * @param groupId  群号
     * @param maxRolls 最大骰点值
     */
    public void insert(String groupId, int maxRolls) {
        int num = 0;
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.maxRolls where groupId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, groupId);
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        num++;
                    }
                }
            }

            if (num == 0) {
                sql = "INSERT INTO test.maxRolls(" +
                        "groupId," +
                        "maxRolls" +
                        ") VALUES(?,?)";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, groupId);
                    ps.setInt(2, maxRolls);
                    ps.executeUpdate();
                }
            } else {
                sql = "update test.maxRolls set " +
                        "maxRolls=? where groupId=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, maxRolls);
                    ps.setString(2, groupId);
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
