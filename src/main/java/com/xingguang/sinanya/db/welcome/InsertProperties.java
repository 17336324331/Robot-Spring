package com.xingguang.sinanya.db.welcome;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityWelcome;
import com.xingguang.sinanya.system.MessagesSystem;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 录入KP主群类
 */
public class InsertProperties {
    private static final Logger log = LoggerFactory.getLogger(InsertProperties.class.getName());

    public void insertProperties(long groupId, EntityWelcome entityWelcome) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "replace into test.welcome(botId,groupId,text)  VALUES (?,?,?);";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                ps.setLong(2, groupId);
                ps.setString(3, entityWelcome.getText());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}