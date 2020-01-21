package com.xingguang.sinanya.db.fire;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.tools.getinfo.GetTime;
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
public class InputFireTemp {
    private static final Logger log = LoggerFactory.getLogger(InputFireTemp.class.getName());


    /**
     * 将QQ黑名单列表入库
     */
    public static void insertFireTemp(long qqId, int id) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "replace into coc.fireTemp(qqId, Id)  VALUES (?,?);";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, qqId);
                ps.setLong(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 将QQ黑名单列表入库
     */
    public static void updateFireSwitch(long qqId, boolean enabled) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "replace into coc.fireSwitch(qqId, switch,time)  VALUES (?,?,?);";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, qqId);
                ps.setBoolean(2, enabled);
                ps.setTimestamp(3, GetTime.getTime(GetTime.getNowString()));
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 将QQ黑名单列表入库
     */
    public static void deleteFireTemp(long qqId) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "delete from coc.fireTemp where qqId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, qqId);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
