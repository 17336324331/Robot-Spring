package com.xingguang.sinanya.db.reviewed;

import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class SelectReviewed {
    private static final Logger log = LoggerFactory.getLogger(SelectReviewed.class.getName());


    /**
     * 读取数据库中的骰点历史信息到缓存
     */
    public static boolean checkReviewEd(long groupId) {
        ArrayList<Long> revieweds = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.reviewed";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        revieweds.add(set.getLong("groupId"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return revieweds.contains(groupId);
    }
}
