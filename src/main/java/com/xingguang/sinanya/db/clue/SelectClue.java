package com.xingguang.sinanya.db.clue;

import com.xingguang.sinanya.db.tools.DbUtil;
import com.xingguang.sinanya.entity.EntityClue;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.getinfo.RoleChoose;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.xingguang.sinanya.tools.getinfo.RoleChoose.checkRoleChooseExistByQQ;
import static com.xingguang.sinanya.tools.getinfo.RoleChoose.getRoleChooseByQQ;


/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 线索获取类，查询并包装成对象
 */
public class SelectClue {
    private static final Logger log = LoggerFactory.getLogger(SelectClue.class.getName());


    public SelectClue() {
//        初始化时无需逻辑
    }

    /**
     * 基于线索对象中的群号为线索查询每个人在每个时间点的线索信息
     *
     * @param entityClue 线索对象
     * @return 格式化好的字符串
     */
    public String selectClue(EntityClue entityClue) {
        StringBuilder stringBuffer = new StringBuilder();
        try (Connection conn = DbUtil.getConnection()) {
            String sql = "select date_format(createTime,'%Y-%m-%d %H:%i:%s') as createTime,qqId,info from test.clue where groupId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, entityClue.getGroupId());
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        stringBuffer.append("于 ")
                                .append(set.getString("createTime"))
                                .append(" 由 ");
                        if (RoleChoose.checkRoleChooseExistByQQ(set.getString("qqId"))) {
                            stringBuffer.append(RoleChoose.getRoleChooseByQQ(set.getString("qqId")));
                        } else {
                            stringBuffer.append(set.getString("qqId"));
                        }
                        stringBuffer.append(" 记录的线索:\t")
                                .append(set.getString("info"))
                                .append("\n");
                    }
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
        if (stringBuffer.toString().equals(MessagesSystem.NONE)) {
            return stringBuffer.toString();
        } else {
            return stringBuffer.substring(0, stringBuffer.length() - 1);
        }
    }


}
