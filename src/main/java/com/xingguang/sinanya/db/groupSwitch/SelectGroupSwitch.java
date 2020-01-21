package com.xingguang.sinanya.db.groupSwitch;

import com.xingguang.sinanya.entity.EntityGroupSwitch;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class SelectGroupSwitch {
    private static final Logger log = LoggerFactory.getLogger(SelectGroupSwitch.class.getName());


    /**
     * 读取数据库中的骰点历史信息到缓存
     */
    public void flushGroupSwitchFromDataBase() {
        MessagesBanList.groupSwitchHashMap.clear();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from test.groupSwitch where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, MessagesSystem.loginInfo.getLoginId());
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                        entityGroupSwitch.setJrrp(set.getBoolean("jrrp"));
                        entityGroupSwitch.setNpc(set.getBoolean("npc"));
                        entityGroupSwitch.setWelcome(set.getBoolean("welcome"));
                        entityGroupSwitch.setGas(set.getBoolean("gas"));
                        entityGroupSwitch.setBg(set.getBoolean("bg"));
                        entityGroupSwitch.setTz(set.getBoolean("tz"));
                        entityGroupSwitch.setSimple(set.getBoolean("simple"));
                        entityGroupSwitch.setOb(set.getBoolean("ob"));
                        entityGroupSwitch.setDeck(set.getBoolean("deck"));
                        MessagesBanList.groupSwitchHashMap.put(set.getLong("groupId"), entityGroupSwitch);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
