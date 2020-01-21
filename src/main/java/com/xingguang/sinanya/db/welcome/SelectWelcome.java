package com.xingguang.sinanya.db.welcome;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityWelcome;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesWelcome;
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
 * 类说明: 查询KP主群类，刷写到静态变量中，只在静态变量中找不到时才需要使用
 */
public class SelectWelcome {
    private static final Logger log = LoggerFactory.getLogger(SelectWelcome.class.getName());


    public SelectWelcome() {
        //        初始化时无需逻辑
    }

    /**
     * 刷新kp主群设定到静态变量中，只有静态变量中找不到某人的kp主群记录时才会使用
     */
    public void flushProperties() {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.welcome where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        EntityWelcome entityWelcome = new EntityWelcome();
                        entityWelcome.setText(set.getString("text"));
                        MessagesWelcome.welcomes.put(set.getLong("groupId"), entityWelcome);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
