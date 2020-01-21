package com.xingguang.sinanya.db.rules;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.tools.getNLP;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.nlpcn.commons.lang.util.StringUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author SitaNya
 * @date 2019/11/1
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class Rule {
    private static final Logger log = LoggerFactory.getLogger(Rule.class.getName());

    public Rule() {
        //        初始化时无需逻辑
    }

    /**
     * 刷新当前角色信息到缓存
     */
    public static String selectRule(String database, String table, String key) {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = String.format("select * from %s.%s where `key`='%s'", database, table, key);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getString("value");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        ArrayList<String> keyList = makeArray(getNLP.getNlp(key));
        ArrayList<String> result = new ArrayList<>();
        try (Connection conn = DbUtil.getConnection()) {
            for (String singleKey : keyList) {
                String sql = String.format("select * from %s.%s where `key` like '%%%s%%'", database, table, singleKey);
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    try (ResultSet set = ps.executeQuery()) {
                        while (set.next()) {
                            String skill = set.getString("key");
                            if (!skill.contains("术") && !skill.contains("之")) {
                                result.add(set.getString("key"));
                            }
                        }
                    }
                }
            }
            return "不存在此条目:\t" + key + "\n猜测是以下条目:\n" + StringUtil.joiner(result, "\n");
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return "未找到";
    }

    /**
     * 刷新当前角色信息到缓存
     */
    public static String selectRandom(String database, String table, String value) {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from " + database + "." + table + " order by rand() LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    if (set.next()) {
                        return set.getString(value);
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return "未找到";
    }

    /**
     * 刷新当前角色信息到缓存
     */
    public static String selectEnglishName() {
        StringBuilder english = new StringBuilder();
        StringBuilder chinese = new StringBuilder();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select * from coc.englishFirstName order by rand() LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        english.append(set.getString("english"));
                        chinese.append(set.getString("chinese"));
                    }
                }
            }
            sql = "select * from coc.englishLastName order by rand() LIMIT 1";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        english.append("·").append(set.getString("english"));
                        chinese.append("·").append(set.getString("chinese"));
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        return english.toString() + "(" + chinese.toString() + ")";
    }


    private static ArrayList<String> makeArray(ArrayList<String> arrayList) {
        ArrayList<String> temp = new ArrayList<>();
        for (String str : arrayList) {
            if (!temp.contains(str)) {
                temp.add(str);
            }
        }
        return temp;
    }
}
