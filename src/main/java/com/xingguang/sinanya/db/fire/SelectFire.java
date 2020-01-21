package com.xingguang.sinanya.db.fire;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.FireRdException;
import com.xingguang.sinanya.system.MessagesFire;
import com.xingguang.sinanya.system.MessagesSystem;
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
public class SelectFire {
    private static final Logger log = LoggerFactory.getLogger(SelectFire.class.getName());

    private EntityTypeMessages entityTypeMessages;

    public SelectFire(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public SelectFire() {
    }

    public String selectFireCheck(int id) throws FireRdException {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select choose from coc.fire where id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getString("choose");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new FireRdException(entityTypeMessages);
    }

    public String selectFireText(int id) throws FireRdException {
        String checkText = selectCheckInfo(id);
        if (!MessagesSystem.NONE.equals(checkText)) {
            return checkText;
        }
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select info from coc.fire where id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getString("info");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new FireRdException(entityTypeMessages);
    }

    public int selectFireTempExsit(long qqId) throws FireRdException {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select count(1) as isCount from coc.fireTemp where qqId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, qqId);
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getInt("isCount");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new FireRdException(entityTypeMessages);
    }

    public int selectFireTemp(long qqId) throws FireRdException {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select Id from coc.fireTemp where qqId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, qqId);
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getInt("Id");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new FireRdException(entityTypeMessages);
    }

    private String selectCheckInfo(int id) {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select info,other from coc.fireCheckInfo where Id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getString("info") + "\n\n\n" + set.getString("other");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return MessagesSystem.NONE;
    }

    public String selectCheckChoose(int id) throws FireRdException {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select choose from coc.fireCheckInfo where Id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setLong(1, id);
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getString("choose");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        throw new FireRdException(entityTypeMessages);
    }

    public void flushFireSwitch() {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select qqId from coc.fireSwitch where switch=1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        MessagesFire.fireSwitch.add(set.getLong("qqId"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
