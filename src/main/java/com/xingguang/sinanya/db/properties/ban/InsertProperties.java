package com.xingguang.sinanya.db.properties.ban;

import com.xingguang.sinanya.entity.EntityBanProperties;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.nlpcn.commons.lang.util.StringUtil;

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

    public void insertProperties(EntityBanProperties entityBanProperties) {
        try (Connection conn = DbUtil.getConnection()) {
            //language=MySQL
            String sql = "replace into test.banProperties(" +
                    "cloudBan," +
                    "Prometheus," +
                    "heap," +
                    "`master`," +
                    "notMaster," +
                    "banListInputNotId," +
                    "doSomthingForBanUserInGroup," +
                    "ignoreBanUser," +
                    "leaveByBanUser," +
                    "leaveGroupByBan," +
                    "banGroupBecauseBan," +
                    "banGroupBecauseReduce," +
                    "banUserBecauseReduce," +
                    "addGroup," +
                    "addFriend," +
                    "refuseGroupByBan," +
                    "refuseFriendByBan," +
                    "whiteGroup," +
                    "whiteUser," +
                    "notBanListInput," +
                    "botId," +
                    "clearGroupByOff," +
                    "clearGroup," +
                    "alterFrequentness," +
                    "banFrequentness," +
                    "clearGroupByOffInfo," +
                    "clearGroupInfo," +
                    "frequentnessAlterInfo," +
                    "frequentnessBanInfo," +
                    "prometheusPort,banGroupAndUserByFre,banUserByFre,autoInputGroup,autoAddFriends,reviewed,managerGroup)  VALUES (?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?," +
                    "?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setBoolean(1, entityBanProperties.isCloudBan());
                ps.setBoolean(2, entityBanProperties.isPrometheus());
                ps.setBoolean(3, entityBanProperties.isHeap());
                ps.setString(4, StringUtil.joiner(entityBanProperties.getMaster(), ","));
                ps.setString(5, entityBanProperties.getNotMaster());
                ps.setString(6, entityBanProperties.getBanListInputNotId());
                ps.setBoolean(7, entityBanProperties.isDoSomthingForBanUserInGroup());
                ps.setBoolean(8, entityBanProperties.isIgnoreBanUser());
                ps.setBoolean(9, entityBanProperties.isLeaveByBanUser());
                ps.setBoolean(10, entityBanProperties.isLeaveGroupByBan());
                ps.setBoolean(11, entityBanProperties.isBanGroupBecauseBan());
                ps.setBoolean(12, entityBanProperties.isBanGroupBecauseReduce());
                ps.setBoolean(13, entityBanProperties.isBanUserBecauseReduce());
                ps.setString(14, entityBanProperties.getAddGroup());
                ps.setString(15, entityBanProperties.getAddFriend());
                ps.setString(16, entityBanProperties.getRefuseGroupByBan());
                ps.setString(17, entityBanProperties.getRefuseFriendByBan());
                ps.setBoolean(18, entityBanProperties.isWhiteGroup());
                ps.setBoolean(19, entityBanProperties.isWhiteUser());
                ps.setString(20, entityBanProperties.getNotBanListInput());
                ps.setString(21, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                ps.setInt(22, entityBanProperties.getClearGroupByOff());
                ps.setInt(23, entityBanProperties.getClearGroup());
                ps.setInt(24, entityBanProperties.getAlterFrequentness());
                ps.setInt(25, entityBanProperties.getBanFrequentness());
                ps.setString(26, entityBanProperties.getClearGroupByOffInfo());
                ps.setString(27, entityBanProperties.getClearGroupInfo());
                ps.setString(28, entityBanProperties.getFrequentnessAlterInfo());
                ps.setString(29, entityBanProperties.getFrequentnessBanInfo());
                ps.setInt(30, entityBanProperties.getPrometheusPort());
                ps.setBoolean(31, entityBanProperties.isBanGroupAndUserByFre());
                ps.setBoolean(32, entityBanProperties.isBanUserByFre());
                ps.setBoolean(33, entityBanProperties.isAutoInputGroup());
                ps.setBoolean(34, entityBanProperties.isAutoAddFriends());
                ps.setBoolean(35, entityBanProperties.isReviewed());
                ps.setString(36,entityBanProperties.getManagerGroup());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}