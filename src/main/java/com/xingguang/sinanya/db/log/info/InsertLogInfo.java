package com.xingguang.sinanya.db.log.info;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityLogTag;
import com.xingguang.sinanya.entity.EntityLogText;
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
 * 类说明: 录入log信息类，这里是实际的log内容而不是log是否开启的标签
 */
public class InsertLogInfo {
    private static final Logger log = LoggerFactory.getLogger(InsertLogInfo.class.getName());


    public InsertLogInfo() {
        //        初始化时不需要参数
    }

    /**
     * 将某个群的某个日志的某一条信息插入到数据库中
     *
     * @param entityLogTag 日志标志对象，包含群、日志名
     */
    public void insertLogTag(EntityLogTag entityLogTag, EntityLogText entityLogText) {
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "INSERT INTO test.textLog(" +
                    "groupId,logName, logInfo,nick,logType) VALUES(?,?,?,?,?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, entityLogTag.getGroupId());
                ps.setString(2, entityLogTag.getLogName());
                ps.setString(3, entityLogText.getText());
                ps.setString(4, entityLogText.getNick());
                ps.setInt(5, entityLogText.getLogType().getTypeId());
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
