package com.xingguang.sinanya.db.properties.game;

import com.xingguang.sinanya.entity.EntityGame;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.db.tools.DbUtil;
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

    public void insertProperties(EntityGame entityGame) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "replace into test.gameProperties(botId,jrrpSwitch,jrrpInfo,welcomeSwitch,botList,deck,nameSwitch,ob,simple)  VALUES (?,?,?,?,?,?,?,?,?);";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                ps.setBoolean(2, entityGame.isJrrpSwitch());
                ps.setString(3, entityGame.getJrrpInfo());
                ps.setBoolean(4, entityGame.isWelcomeSwitch());
                ps.setBoolean(5, entityGame.isBotList());
                ps.setBoolean(6, entityGame.isDeck());
                ps.setBoolean(7, entityGame.isNameSwitch());
                ps.setBoolean(8, entityGame.isOb());
                ps.setBoolean(9, entityGame.isSimple());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}