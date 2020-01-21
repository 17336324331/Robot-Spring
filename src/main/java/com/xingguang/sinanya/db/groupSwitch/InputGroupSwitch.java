package com.xingguang.sinanya.db.groupSwitch;

import com.xingguang.sinanya.entity.EntityGroupSwitch;
import com.xingguang.sinanya.system.MessagesSystem;
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
public class InputGroupSwitch {
    private static final Logger log = LoggerFactory.getLogger(InputGroupSwitch.class.getName());


    /**
     * 将QQ黑名单列表入库
     */
    public static void insertGroupSwitch(EntityGroupSwitch entityGroupSwitch, long groupId) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "replace into test.groupSwitch(botId,groupId,jrrp,npc,welcome,gas,bg,tz,simple,ob,deck)  VALUES (?,?,?,?,?,?,?,?,?,?,?);";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                long qqId = MessagesSystem.loginInfo.getLoginId();
                ps.setLong(1, qqId);
                ps.setLong(2, groupId);
                ps.setBoolean(3, entityGroupSwitch.isJrrp());
                ps.setBoolean(4, entityGroupSwitch.isNpc());
                ps.setBoolean(5, entityGroupSwitch.isWelcome());
                ps.setBoolean(6, entityGroupSwitch.isGas());
                ps.setBoolean(7, entityGroupSwitch.isBg());
                ps.setBoolean(8, entityGroupSwitch.isTz());
                ps.setBoolean(9, entityGroupSwitch.isSimple());
                ps.setBoolean(10, entityGroupSwitch.isOb());
                ps.setBoolean(11, entityGroupSwitch.isDeck());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
