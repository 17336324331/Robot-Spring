package com.xingguang.sinanya.db.properties.system;

import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.db.tools.DbUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 查询KP主群类，刷写到静态变量中，只在静态变量中找不到时才需要使用
 */
public class SelectSystemProperties {
    private static final Logger log = LoggerFactory.getLogger(SelectSystemProperties.class.getName());


    public SelectSystemProperties() {
        //        初始化时无需逻辑
    }

    /**
     * 刷新kp主群设定到静态变量中，只有静态变量中找不到某人的kp主群记录时才会使用
     */
    public void flushProperties() {
        try (Connection conn = DbUtil.getConnection()) {
            int i = 0;
            //language=MySQL
            String sql = "select * from test.systemProperties where botId=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, String.valueOf(MessagesSystem.loginInfo.getLoginId()));
                try (ResultSet set = ps.executeQuery()) {
                    while (set.next()) {
                        GetMessagesProperties.entitySystemProperties.setBotStart(set.getString("botStart"));
                        GetMessagesProperties.entitySystemProperties.setBotAlreadyStart(set.getString("botAlreadyStart"));
                        GetMessagesProperties.entitySystemProperties.setBotStop(set.getString("botStop"));
                        GetMessagesProperties.entitySystemProperties.setBotAlreadyStop(set.getString("botAlreadyStop"));
                        GetMessagesProperties.entitySystemProperties.setBotExit(set.getString("botExit"));
                        GetMessagesProperties.entitySystemProperties.setBotInfo(set.getString("botInfo"));
                        GetMessagesProperties.entitySystemProperties.setBookCard(set.getString("bookCard"));
                        GetMessagesProperties.entitySystemProperties.setBookRp(set.getString("bookRp"));
                        GetMessagesProperties.entitySystemProperties.setBookKp(set.getString("bookKp"));
                        GetMessagesProperties.entitySystemProperties.setBookMake(set.getString("bookMake"));
                        GetMessagesProperties.entitySystemProperties.setManyRollsFormat(set.getString("manyRollsFormat"));
                        GetMessagesProperties.entitySystemProperties.setDiceTimesTooBig(set.getString("diceTimesTooBig"));
                        GetMessagesProperties.entitySystemProperties.setSetPropFormat(set.getString("setPropFormat"));
                        GetMessagesProperties.entitySystemProperties.setSetHelp(set.getString("setHelp"));
                        GetMessagesProperties.entitySystemProperties.setNotFoundSkill(set.getString("NotFoundSkill"));
                        GetMessagesProperties.entitySystemProperties.setSetPropSuccess(set.getString("setPropSuccess"));
                        GetMessagesProperties.entitySystemProperties.setDndInitIsEmtpy(set.getString("dndInitIsEmtpy"));
                        GetMessagesProperties.entitySystemProperties.setClrDndInit(set.getString("clrDndInit"));
                        GetMessagesProperties.entitySystemProperties.setNeedKpGroup(set.getString("needKpGroup"));
                        GetMessagesProperties.entitySystemProperties.setCantInPrivate(set.getString("cantInPrivate"));
                        GetMessagesProperties.entitySystemProperties.setOnlyManager(set.getString("onlyManager"));
                        GetMessagesProperties.entitySystemProperties.setAlreadyOpen(set.getString("alreadyOpen"));
                        GetMessagesProperties.entitySystemProperties.setAlreadyClose(set.getString("alreadyClose"));
                        GetMessagesProperties.entitySystemProperties.setNotFoundLog(set.getString("notFoundLog"));
                        GetMessagesProperties.entitySystemProperties.setReadLock(set.getString("readLock"));
                        GetMessagesProperties.entitySystemProperties.setDeleteOpenLog(set.getString("deleteOpenLog"));
                        GetMessagesProperties.entitySystemProperties.setSanCheck(set.getString("sanCheck"));
                        GetMessagesProperties.entitySystemProperties.setAntagonizeOver(set.getString("antagonizeOver"));
                        GetMessagesProperties.entitySystemProperties.setAntagonizeFirstSuccess(set.getString("antagonizeFirstSuccess"));
                        GetMessagesProperties.entitySystemProperties.setAntagonizeSecondSuccess(set.getString("antagonizeSecondSuccess"));
                        GetMessagesProperties.entitySystemProperties.setAntagonizeAllFailed(set.getString("antagonizeAllFailed"));
                        GetMessagesProperties.entitySystemProperties.setAntagonizeDraw(set.getString("antagonizeDraw"));
                        GetMessagesProperties.entitySystemProperties.setSymptom(set.getString("symptom"));
                        GetMessagesProperties.entitySystemProperties.setEnSuccess(set.getString("enSuccess"));
                        GetMessagesProperties.entitySystemProperties.setEnFailed(set.getString("enFailed"));
                        GetMessagesProperties.entitySystemProperties.setHiddenDice(set.getString("hiddenDice"));
                        GetMessagesProperties.entitySystemProperties.setTeamIsEmpty(set.getString("teamIsEmpty"));
                        GetMessagesProperties.entitySystemProperties.setTeamMemberEnIsEmpty(set.getString("teamMemberEnIsEmpty"));
                        GetMessagesProperties.entitySystemProperties.setAppendLog(set.getString("appendLog"));
                        GetMessagesProperties.entitySystemProperties.setCreateLog(set.getString("createLog"));
                        GetMessagesProperties.entitySystemProperties.setCantEmptyLogName(set.getString("CantEmptyLogName"));
                        GetMessagesProperties.entitySystemProperties.setSanCheckFumble(set.getString("sanCheckFumble"));
                        GetMessagesProperties.entitySystemProperties.setSanCheckCriticalSuccess(set.getString("sanCheckCriticalSuccess"));
                        GetMessagesProperties.entitySystemProperties.setSanCheckSuccess(set.getString("sanCheckSuccess"));
                        GetMessagesProperties.entitySystemProperties.setSanCheckFailure(set.getString("sanCheckFailure"));
                        GetMessagesProperties.entitySystemProperties.setCRITICAL_SUCCESS(set.getString("CRITICAL_SUCCESS"));
                        GetMessagesProperties.entitySystemProperties.setEXTREME_SUCCESS(set.getString("EXTREME_SUCCESS"));
                        GetMessagesProperties.entitySystemProperties.setHARD_SUCCESS(set.getString("HARD_SUCCESS"));
                        GetMessagesProperties.entitySystemProperties.setSUCCESS(set.getString("SUCCESS"));
                        GetMessagesProperties.entitySystemProperties.setFAILURE(set.getString("FAILURE"));
                        GetMessagesProperties.entitySystemProperties.setFUMBLE(set.getString("FUMBLE"));
                        GetMessagesProperties.entitySystemProperties.setPublicMode(set.getBoolean("public"));
                        GetMessagesProperties.entitySystemProperties.setRunning(set.getBoolean("running"));
                        GetMessagesProperties.entitySystemProperties.setHelpInfo(set.getString("helpInfo"));
                        i++;
                    }
                }
                if (i == 0) {
                    new InsertProperties().insertProperties(GetMessagesProperties.entitySystemProperties);
                }
            }
        } catch (SQLException e) {
            log.error(e.getMessage(), e);
        }
    }
}
