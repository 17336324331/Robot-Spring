package com.xingguang.sinanya.db.deck;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityDeckList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
public class SelectDeckList {
    private static final Logger log = LoggerFactory.getLogger(SelectDeckList.class.getName());

    /**
     * 从数据库刷写默认骰最大值到静态变量
     */
    public ArrayList<EntityDeckList> selectDeckList() {
        ArrayList<EntityDeckList> deckLists = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "select * from test.deckList";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        EntityDeckList deckList = new EntityDeckList();
                        deckList.setCommand(set.getString("command"));
                        deckList.setAuthor(set.getString("author"));
                        deckList.setName(set.getString("name"));
                        deckList.setVersion(set.getInt("version"));
                        deckList.setDesc(set.getString("desc"));
                        deckLists.add(deckList);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return deckLists;
    }
}
