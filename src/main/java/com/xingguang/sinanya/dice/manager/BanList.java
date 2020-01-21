package com.xingguang.sinanya.dice.manager;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.BanListInputNotIdException;
import com.xingguang.sinanya.exceptions.NotBanListInputException;
import com.xingguang.sinanya.exceptions.NotEnableBanException;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.db.ban.SelectBanList;
import com.xingguang.sinanya.exceptions.NotMasterException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author SitaNya
 * 日期: 2019-08-07
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class BanList {
    private static final Logger log = LoggerFactory.getLogger(BanList.class.getName());

    private Pattern id = Pattern.compile("(\\d{4,15})[^\\d]*");
    private EntityTypeMessages entityTypeMessages;
    private String Id;
    private String reason;

    public BanList(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     *
     */
    public void inputQqBanList() throws NotEnableBanException {
        checkEnableBan();
        String tag = MessagesTag.TAG_BAN_USER;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));

        makeId(msg);
        try {
            checkMaster();
            isQqOrGroup(Id);
            if (MessagesBanList.qqBanList.containsKey(Id)) {
                Sender.sender(entityTypeMessages, "用户:\t" + Id + "已在云黑名单中");
            } else {
                com.xingguang.sinanya.tools.getinfo.BanList.insertQqBanList(Id, reason);
                Sender.sender(entityTypeMessages, "已将用户:\t" + Id + "加入云黑名单,理由为: " + reason);
            }
        } catch (BanListInputNotIdException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void inputGroupBanList() throws NotEnableBanException {
        checkEnableBan();
        String tag = MessagesTag.TAG_BAN_GROUP;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        makeId(msg);
        try {
            checkMaster();
            isQqOrGroup(Id);
            if (MessagesBanList.groupBanList.containsKey(Id)) {
                Sender.sender(entityTypeMessages, "群:\t" + Id + "已在云黑名单中");
            } else {
                com.xingguang.sinanya.tools.getinfo.BanList.insertGroupBanList(Id, reason);
                Sender.sender(entityTypeMessages, "已将群:\t" + Id + "加入云黑名单,理由为: " + reason);
            }
        } catch (BanListInputNotIdException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void rmQqBanList() throws NotEnableBanException {
        checkEnableBan();
        String tag = MessagesTag.TAG_RM_BAN_USER;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        try {
            checkMaster();
            isQqOrGroup(msg);
            if (MessagesBanList.qqBanList.containsKey(msg)) {
                com.xingguang.sinanya.tools.getinfo.BanList.removeQqBanList(msg, entityTypeMessages);
            } else {
                Sender.sender(entityTypeMessages, "用户:\t" + msg + "不在云黑名单中");
            }
        } catch (BanListInputNotIdException | NotBanListInputException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void rmGroupBanList() throws NotEnableBanException {
        checkEnableBan();
        String tag = MessagesTag.TAG_RM_BAN_GROUP;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        try {
            checkMaster();
            isQqOrGroup(msg);
            if (MessagesBanList.groupBanList.containsKey(msg)) {
                com.xingguang.sinanya.tools.getinfo.BanList.removeGroupBanList(msg, entityTypeMessages);
            } else {
                Sender.sender(entityTypeMessages, "群:\t" + msg + "不在云黑名单中");
            }
        } catch (BanListInputNotIdException | NotBanListInputException | NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     *
     */
    public void getQqBanList() throws NotEnableBanException {
        checkEnableBan();
        try {
            checkMaster();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("当前云黑内记录的黑名单用户列表如下，黑名单的刷新周期为15分钟，其他人新添加的黑名单可能暂时未同步");
            for (Map.Entry<String, String> mapEntry : MessagesBanList.qqBanList.entrySet()) {
                stringBuilder.append("\n").append(mapEntry.getKey());
            }
            Sender.sender(entityTypeMessages, stringBuilder.toString());
        } catch (NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     *
     */
    public void getGroupBanList() throws NotEnableBanException {
        checkEnableBan();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("当前云黑内记录的黑名单群列表如下，黑名单的刷新周期为15分钟，其他人新添加的黑名单可能暂时未同步");
        for (Map.Entry<String, String> mapEntry : MessagesBanList.groupBanList.entrySet()) {
            stringBuilder.append("\n").append(mapEntry.getKey());
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    public void isBan() throws BanListInputNotIdException, NotEnableBanException {
        checkEnableBan();
        String tag = MessagesTag.TAG_BAN_CHECK;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        if (!CheckIsNumbers.isNumeric(msg)) {
            throw new BanListInputNotIdException(entityTypeMessages);
        }
        SelectBanList selectBanList = new SelectBanList();
        try {
            checkMaster();
            ArrayList<String> banHistoryLists = new ArrayList<>();
            long id = Long.parseLong(msg);
            if (MessagesBanList.qqBanList.containsKey(msg)) {
                String qqHistory = selectBanList.selectQqBanInfo(id);
                if (qqHistory != null) {
                    banHistoryLists.add(qqHistory);
                }
            }
            if (MessagesBanList.groupBanList.containsKey(msg)) {
                String groupHistory = selectBanList.selectGroupBanInfo(id);
                if (groupHistory != null) {
                    banHistoryLists.add(groupHistory);
                }
            }
            ArrayList<String> historyList = selectBanList.selectBanHistory(msg);
            banHistoryLists.addAll(historyList);
            if (banHistoryLists.size() == 0) {
                Sender.sender(entityTypeMessages, "未找到与 " + msg + " 的有关记录");
            } else {
                Sender.sender(entityTypeMessages, "此记录下黑名单历史为:\n" + StringUtil.joiner(banHistoryLists, "\n"));
            }
        } catch (NotMasterException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void isQqOrGroup(String input) throws BanListInputNotIdException {
        if (!CheckIsNumbers.isNumeric(input) || input.length() > 15 || input.length() < 4) {
            throw new BanListInputNotIdException(entityTypeMessages);
        }
    }

    private void checkMaster() throws NotMasterException {
        if (!GetMessagesProperties.entityBanProperties.getMaster().contains(String.valueOf(entityTypeMessages.getFromQqString()))) {
            Sender.sender(entityTypeMessages, "非master在使用命令，当前master为\n" + GetMessagesProperties.entityBanProperties.getMaster());
            throw new NotMasterException(entityTypeMessages);
        }
    }

    private void checkEnableBan() throws NotEnableBanException {
        if (!GetMessagesProperties.entityBanProperties.isCloudBan()) {
            throw new NotEnableBanException(entityTypeMessages);
        }
    }

    private void makeId(String msg) {
        Matcher qqMatcher = id.matcher(msg);
        if (qqMatcher.find()) {
            Id = qqMatcher.group(1);
            reason = msg.replace(Id, "").trim();
        } else if (CheckIsNumbers.isNumeric(msg)) {
            Id = msg;
            reason = "手工录入";
        } else {
            try {
                throw new BanListInputNotIdException(entityTypeMessages);
            } catch (BanListInputNotIdException e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
