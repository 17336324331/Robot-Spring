package com.xingguang.sinanya.db.properties.ban;

import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 查询KP主群类，刷写到静态变量中，只在静态变量中找不到时才需要使用
 */
public class SelectBanProperties {
    private static final Logger log = LoggerFactory.getLogger(SelectBanProperties.class.getName());

    public SelectBanProperties() {
        //        初始化时无需逻辑
    }

    /**
     * 刷新kp主群设定到静态变量中，只有静态变量中找不到某人的kp主群记录时才会使用
     */
    public void flushProperties() {
        try (Connection conn = DbUtil.getConnection()) {
            int i = 0;
            //language=MySQL
            String sql = "select * from test.banProperties where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        GetMessagesProperties.entityBanProperties.setCloudBan(set.getBoolean("cloudBan"));
                        GetMessagesProperties.entityBanProperties.setPrometheus(set.getBoolean("Prometheus"));
                        GetMessagesProperties.entityBanProperties.setHeap(set.getBoolean("heap"));
                        GetMessagesProperties.entityBanProperties.setMaster(new ArrayList<>(Arrays.asList(set.getString("master").split(","))));
                        GetMessagesProperties.entityBanProperties.setBanListInputNotId(set.getString("banListInputNotId"));
                        GetMessagesProperties.entityBanProperties.setNotMaster(set.getString("notMaster"));
                        GetMessagesProperties.entityBanProperties.setDoSomthingForBanUserInGroup(set.getBoolean("doSomthingForBanUserInGroup"));
                        GetMessagesProperties.entityBanProperties.setIgnoreBanUser(set.getBoolean("ignoreBanUser"));
                        GetMessagesProperties.entityBanProperties.setLeaveByBanUser(set.getBoolean("leaveByBanUser"));
                        GetMessagesProperties.entityBanProperties.setLeaveGroupByBan(set.getBoolean("leaveGroupByBan"));
                        GetMessagesProperties.entityBanProperties.setBanGroupBecauseBan(set.getBoolean("banGroupBecauseBan"));
                        GetMessagesProperties.entityBanProperties.setBanGroupBecauseReduce(set.getBoolean("banGroupBecauseReduce"));
                        GetMessagesProperties.entityBanProperties.setBanUserBecauseReduce(set.getBoolean("banUserBecauseReduce"));
                        GetMessagesProperties.entityBanProperties.setAddGroup(set.getString("addGroup"));
                        GetMessagesProperties.entityBanProperties.setAddFriend(set.getString("addFriend"));
                        GetMessagesProperties.entityBanProperties.setRefuseGroupByBan(set.getString("refuseGroupByBan"));
                        GetMessagesProperties.entityBanProperties.setRefuseFriendByBan(set.getString("refuseFriendByBan"));
                        GetMessagesProperties.entityBanProperties.setWhiteGroup(set.getBoolean("whiteGroup"));
                        GetMessagesProperties.entityBanProperties.setWhiteUser(set.getBoolean("whiteUser"));
                        GetMessagesProperties.entityBanProperties.setNotBanListInput(set.getString("notBanListInput"));
                        GetMessagesProperties.entityBanProperties.setClearGroupByOff(set.getInt("clearGroupByOff"));
                        GetMessagesProperties.entityBanProperties.setClearGroup(set.getInt("clearGroup"));
                        GetMessagesProperties.entityBanProperties.setAlterFrequentness(set.getInt("alterFrequentness"));
                        GetMessagesProperties.entityBanProperties.setBanFrequentness(set.getInt("banFrequentness"));
                        GetMessagesProperties.entityBanProperties.setClearGroupByOffInfo(set.getString("clearGroupByOffInfo"));
                        GetMessagesProperties.entityBanProperties.setClearGroupInfo(set.getString("clearGroupInfo"));
                        GetMessagesProperties.entityBanProperties.setFrequentnessAlterInfo(set.getString("frequentnessAlterInfo"));
                        GetMessagesProperties.entityBanProperties.setFrequentnessBanInfo(set.getString("frequentnessBanInfo"));
                        GetMessagesProperties.entityBanProperties.setPrometheusPort(set.getInt("prometheusPort"));
                        GetMessagesProperties.entityBanProperties.setBanGroupAndUserByFre(set.getBoolean("banGroupAndUserByFre"));
                        GetMessagesProperties.entityBanProperties.setBanUserByFre(set.getBoolean("banUserByFre"));
                        GetMessagesProperties.entityBanProperties.setAutoInputGroup(set.getBoolean("autoInputGroup"));
                        GetMessagesProperties.entityBanProperties.setAutoAddFriends(set.getBoolean("autoAddFriends"));
                        GetMessagesProperties.entityBanProperties.setReviewed(set.getBoolean("reviewed"));
                        GetMessagesProperties.entityBanProperties.setManagerGroup(set.getString("managerGroup"));
                        i++;
                    }
                }
                if (i == 0) {
                    new InsertProperties().insertProperties(GetMessagesProperties.entityBanProperties);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
