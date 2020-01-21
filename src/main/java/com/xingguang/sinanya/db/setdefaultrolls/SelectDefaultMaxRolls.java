package com.xingguang.sinanya.db.setdefaultrolls;

import com.xingguang.sinanya.system.MessagesRollMaxValue;
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
 * 类说明: 默认骰点上限查询类
 * <p>
 * 直接刷新到静态变量
 */
public class SelectDefaultMaxRolls {
    private static final Logger log = LoggerFactory.getLogger(SelectDefaultMaxRolls.class.getName());


    /**
     * 从数据库刷写默认骰最大值到静态变量
     */
    public void flushMaxRollsFromDatabase() {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.maxRolls";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        MessagesRollMaxValue.ROLL_MAX_VALUE.put(set.getString("groupId"), set.getInt("maxRolls"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
