package com.xingguang.sinanya.listener;

import com.forte.qqrobot.anno.Filter;
import com.forte.qqrobot.anno.Listen;
import com.forte.qqrobot.beans.messages.msgget.*;
import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.beans.messages.result.inner.Group;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.messages.types.PowerType;
import com.forte.qqrobot.beans.types.KeywordMatchType;
import com.forte.qqrobot.listener.result.ListenResult;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.sinanya.exceptions.NotEnableException;
import com.xingguang.sinanya.exceptions.NotEnableInGroupException;
import com.xingguang.sinanya.exceptions.OnlyManagerException;
import com.xingguang.sinanya.dice.MakeNickToSender;
import com.xingguang.sinanya.dice.game.Welcome;
import com.xingguang.sinanya.dice.system.Bot;
import com.xingguang.sinanya.entity.EntityLogTag;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.flow.Flow;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.regex.Pattern;

import static com.forte.qqrobot.beans.messages.types.MsgGetTypes.discussMsg;
import static com.xingguang.sinanya.db.reviewed.SelectReviewed.checkReviewEd;
import static com.xingguang.sinanya.db.system.InsertBot.deleteBot;
import static com.xingguang.sinanya.system.MessagesBanList.groupSwitchHashMap;
import static com.xingguang.sinanya.system.MessagesListenResult.MSG_IGNORE;
import static com.xingguang.sinanya.system.MessagesListenResult.MSG_INTERCEPT;
import static com.xingguang.sinanya.system.MessagesSystem.loginInfo;
import static com.xingguang.sinanya.system.MessagesTag.TAG_WELCOME;
import static com.xingguang.sinanya.system.MessagesWelcome.welcomes;
import static com.xingguang.sinanya.tools.checkdata.CheckIsNumbers.isNumeric;
import static com.xingguang.sinanya.tools.getinfo.BanList.*;
import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.*;
import static com.xingguang.sinanya.tools.getinfo.GetNickName.getGroupName;
import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickNameByQq;
import static com.xingguang.sinanya.tools.getinfo.LogTag.checkOthorLogTrue;
import static com.xingguang.sinanya.tools.getinfo.LogTag.getOtherLogTrue;
import static com.xingguang.sinanya.tools.getinfo.LogText.setLogText;
import static com.xingguang.sinanya.tools.getinfo.SwitchBot.getBot;
import static com.xingguang.sinanya.tools.getinfo.WhiteList.*;
import static com.xingguang.sinanya.tools.makedata.FullWidth2halfWidth.fullWidth2halfWidth;
import static com.xingguang.sinanya.tools.makedata.Sender.sender;

public class Listener implements MakeNickToSender {
    private static final Logger Log = LoggerFactory.getLogger(Listener.class.getName());

    private Pattern commandHeader = Pattern.compile("(?s)^[ ]*[.。][ ]*.+");
    private Pattern chineseRegex = Pattern.compile("([\\u4e00-\\u9fa5]+)");
    private String tagMe = String.format("[cq:at,qq=%s]", String.valueOf(loginInfo.getLoginId()));


    @Listen(MsgGetTypes.privateMsg)
    public ListenResult privateMsg(MsgGet msgGet, MsgGetTypes msgGetTypes, MsgSender msgSender, PrivateMsg msgPrivate) {
        String msg = fullWidth2halfWidth(msgPrivate.getMsg());
        if (!entitySystemProperties.isRunning()) {
            msgSender.SENDER.sendPrivateMsg(msgPrivate.getQQCode(), "目前本骰休假中，不提供服务");
            return MSG_INTERCEPT;
        }
        if (!msg.matches(commandHeader.toString()) && !isNumeric(msg)) {
            return MSG_IGNORE;
        }
        EntityTypeMessages entityTypeMessages = new EntityTypeMessages(msgGetTypes, msgSender, msgGet, msgPrivate);
        if (checkQqInBanList(entityTypeMessages.getFromQqString())) {
            return MSG_INTERCEPT;
        }
        if (msg.contains("bot") && !msg.contains("update")) {
            new Bot(entityTypeMessages).info();
        } else if (msg.contains("bot update")) {
            new Bot(entityTypeMessages).update();
        } else {
            try {
                new Flow(entityTypeMessages).toGroup();
            } catch (Exception e) {
                Log.error(e.getMessage(), e);
            }
        }
        return MSG_IGNORE;
    }

    @Listen(MsgGetTypes.groupMsg)
    public ListenResult groupMsg(MsgGet msgGet, MsgGetTypes msgGetTypes, MsgSender msgSender, GroupMsg msgGroup) {
        String msg = fullWidth2halfWidth(msgGroup.getMsg());
        if (!entitySystemProperties.isRunning()) {
            return MSG_INTERCEPT;
        }
        EntityTypeMessages entityTypeMessages = new EntityTypeMessages(msgGetTypes, msgSender, msgGet, msgGroup);
        if ((checkBeBanOrInBan(entityTypeMessages) == MSG_INTERCEPT)) {
            return MSG_INTERCEPT;
        }

        try {
            if (checkBotSwitch(entityTypeMessages) == MSG_INTERCEPT) {
                return MSG_INTERCEPT;
            }
        } catch (OnlyManagerException e) {
            Log.error(e.getMessage(), e);
        }

        try {
            if (msg.contains("welcome")) {
                Pattern pattern = Pattern.compile(TAG_WELCOME, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                if (pattern.matcher(msgGroup.getMsg().trim().toLowerCase()).find()) {
                    Welcome welcome = new Welcome(entityTypeMessages);
                    welcome.set();
                }
            }
        } catch (NotEnableException | OnlyManagerException | NotEnableInGroupException e) {
            Log.error(e.getMessage(), e);
        }

        if (!getBot(entityTypeMessages.getFromGroup())) {
            return MSG_INTERCEPT;
        }

        if (msg.matches(commandHeader.toString()) && !msg.contains("exit")) {
            if (entityBanProperties.isReviewed() && !checkReviewEd(entityTypeMessages.getFromGroup()) && !msg.contains("bot exit")) {
                msgSender.SENDER.sendGroupMsg(entityTypeMessages.getFromGroupString(), "本群未通过审核，因此除.bot exit退群功能外，其余无法使用。请前往群162279609进行审核");
                return MSG_INTERCEPT;
            }
            try {
                new Flow(entityTypeMessages).toGroup();
            } catch (Exception e) {
                Log.error(e.getMessage(), e);
            }
        }
        setLogsForText(entityTypeMessages);
        return MSG_IGNORE;
    }

    @Listen(discussMsg)
    @Filter(value = "(?s)^[ ]*[.。][ ]*.*", keywordMatchType = KeywordMatchType.TRIM_REGEX)
    public ListenResult discussMsg(MsgGet msgGet, MsgGetTypes msgGetTypes, MsgSender msgSender, DiscussMsg msgDisGroup) {
        String msg = fullWidth2halfWidth(msgDisGroup.getMsg());
        if (!entitySystemProperties.isRunning()) {
            return MSG_INTERCEPT;
        }
        EntityTypeMessages entityTypeMessages = new EntityTypeMessages(msgGetTypes, msgSender, msgGet, msgDisGroup);
        if (checkBeBanOrInBan(entityTypeMessages) == MSG_INTERCEPT) {
            return MSG_INTERCEPT;
        }

        try {
            if (checkBotSwitch(entityTypeMessages) == MSG_INTERCEPT) {
                return MSG_INTERCEPT;
            }
        } catch (OnlyManagerException e) {
            Log.error(e.getMessage(), e);
        }

        if (!getBot(entityTypeMessages.getFromGroup())) {
            return MSG_INTERCEPT;
        }

        if (msg.matches(commandHeader.toString()) && !msg.contains("exit")) {
            if (entityBanProperties.isReviewed() && !checkReviewEd(entityTypeMessages.getFromGroup()) && !msg.contains("bot exit")) {
                msgSender.SENDER.sendDiscussMsg(entityTypeMessages.getFromGroupString(), "本群未通过审核，因此除.bot exit退群功能外，其余无法使用。请前往群162279609进行审核");
                return MSG_INTERCEPT;
            }
            try {
                new Flow(entityTypeMessages).toDisGroup();
            } catch (Exception e) {
                Log.error(e.getMessage(), e);
            }
        }
        setLogsForText(entityTypeMessages);
        return MSG_IGNORE;
    }

    @Listen(MsgGetTypes.groupMemberReduce)
    public ListenResult groupMemberDecrease(MsgSender msgSender, GroupMemberReduce groupMemberReduce) {
        if (groupMemberReduce.getType().isKickOut() && groupMemberReduce.getBeOperatedQQ().equals(msgSender.getLoginInfo().getQQ()) && entityBanProperties.isBanGroupBecauseReduce()) {
            if (entityBanProperties.isBanUserBecauseReduce()) {
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "已被移出群" + groupMemberReduce.getGroupCode() + "中，将群和操作者"
                        + msgSender.GETTER.getStrangerInfo(groupMemberReduce.getOperatorQQ()).getName() + "(" + groupMemberReduce.getOperatorQQ() + ")拉黑");
                if (checkQqInWhiteList(groupMemberReduce.getQQCodeNumber())) {
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), groupMemberReduce.getOperatorQQ() + "为白名单用户，放弃拉黑");
                } else {
                    insertQqBanList(groupMemberReduce.getOperatorQQ(), "被踢出群" + groupMemberReduce.getGroupCode());

                    Group[] groupList = msgSender.GETTER.getGroupList().getList();
                    for (Group group : groupList) {
                        GroupMemberInfo member = msgSender.GETTER.getGroupMemberInfo(group.getCode(), groupMemberReduce.getOperatorQQ());
                        if (member == null) {
                            continue;
                        }
                        PowerType powerType = member.getPowerType();
                        PowerType myPowerType = msgSender.GETTER.getGroupMemberInfo(group.getCode(), msgSender.GETTER.getLoginQQInfo().getQQ()).getPowerType();
                        boolean powerThanMe = powerType.TYPE > myPowerType.TYPE;
                        if (powerThanMe) {
                            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "发现在群" + group.getName() + "(" + group.getCode() + ")中有黑名单成员" + member.getNickName() + "(" + member.getQQ() + ")且权限更高,将预防性退群");
                            msgSender.SENDER.sendGroupMsg(group.getCode(), "发现在群" + group.getName() + "(" + group.getCode() + ")中有黑名单成员" + member.getNickName() + "(" + member.getQQ() + ")且权限更高,将预防性退群");
                            leave(msgSender, group.getCode());
                            deleteBot(Long.parseLong(group.getCode()));
                        }
                    }
                }
                if (checkGroupInWhiteList(groupMemberReduce.getGroupCodeNumber())) {
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), groupMemberReduce.getGroupCode() + "为白名单群，放弃拉黑");
                } else {
                    insertGroupBanList(groupMemberReduce.getGroupCode(), "被" + groupMemberReduce.getOperatorQQ() + "踢出");
                }
            } else {
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "已被移出群" + "(" + groupMemberReduce.getGroupCode() + ")中，将群拉黑");
                if (checkGroupInWhiteList(groupMemberReduce.getGroupCodeNumber())) {
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), groupMemberReduce.getGroupCode() + "为白名单群，放弃拉黑");
                } else {
                    insertGroupBanList(groupMemberReduce.getGroupCode(), "被" + groupMemberReduce.getOperatorQQ() + "踢出");
                }
            }
        }
        return MSG_IGNORE;
    }

    @Listen(MsgGetTypes.groupMemberIncrease)
    public ListenResult groupMemberIncrease(MsgSender msgSender, GroupMemberIncrease groupMemberIncrease) {
        if (groupMemberIncrease.getType().isAgree() || (groupMemberIncrease.getType().isInvite() && Long.parseLong(groupMemberIncrease.getBeOperatedQQ()) != loginInfo.getLoginId())) {
            long fromGroup = groupMemberIncrease.getGroupCodeNumber();
            try {
                Thread.sleep(1000);
                if (entityGame.isWelcomeSwitch() && welcomes.containsKey(fromGroup)) {
                    if (!groupSwitchHashMap.containsKey(fromGroup) || groupSwitchHashMap.get(fromGroup).isWelcome()) {
                        msgSender.SENDER.sendGroupMsg(groupMemberIncrease.getGroupCode(), String.format(welcomes.get(fromGroup).getText(), "[CQ:at,qq=" + groupMemberIncrease.getBeOperatedQQ() + "]"));
                    }
                }
            } catch (InterruptedException e) {
                Log.error(e.getMessage(), e);
            }
        } else if (groupMemberIncrease.getType().isInvite() && Long.parseLong(groupMemberIncrease.getBeOperatedQQ()) == loginInfo.getLoginId()) {
            if (checkGroupInBanList(groupMemberIncrease.getGroupCode())) {
                msgSender.SENDER.sendGroupMsg(groupMemberIncrease.getGroupCode(), "检测到加入黑名单群，即将退群");
                leave(msgSender, groupMemberIncrease.getGroupCode());
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                        "被强制邀请进黑名单群: " + makeGroupNickToSender(getGroupName(msgSender, groupMemberIncrease.getGroup())) + "(" + groupMemberIncrease.getGroupCode() + ")，邀请人: "
                                + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupMemberIncrease.getGroupCode(), groupMemberIncrease.getOperatorQQ()).getNickOrName()) + "(" + groupMemberIncrease.getOperatorQQ() + ")");
                return MSG_INTERCEPT;
            } else {
                if (!entitySystemProperties.isPublicMode() && checkQqInWhiteList(Long.parseLong(groupMemberIncrease.getOperatorQQ()))) {
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                            "已加入白名单群: " + makeGroupNickToSender(getGroupName(msgSender, groupMemberIncrease.getGroup())) + "(" + groupMemberIncrease.getGroupCode() + ")，邀请人: "
                                    + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupMemberIncrease.getGroupCode(), groupMemberIncrease.getOperatorQQ()).getNickOrName()) + "(" + groupMemberIncrease.getOperatorQQ() + ")");
                    msgSender.SENDER.sendGroupMsg(groupMemberIncrease.getGroupCode(), entityBanProperties.getAddGroup());
                    return MSG_INTERCEPT;
                }
                if (!entitySystemProperties.isPublicMode() && checkGroupInWhiteList(groupMemberIncrease.getGroupCodeNumber())) {
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                            "已加入白名单群: " + makeGroupNickToSender(getGroupName(msgSender, groupMemberIncrease.getGroup())) + "(" + groupMemberIncrease.getGroupCode() + ")，邀请人: "
                                    + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupMemberIncrease.getGroupCode(), groupMemberIncrease.getOperatorQQ()).getNickOrName()) + "(" + groupMemberIncrease.getOperatorQQ() + ")");
                    msgSender.SENDER.sendGroupMsg(groupMemberIncrease.getGroupCode(), entityBanProperties.getAddGroup());
                    return MSG_INTERCEPT;
                }
//                if (!"0".equals(groupMemberIncrease.getOperatorQQ())) {
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                        "已加入群（若无处理邀请提示则为讨论组）: " + makeGroupNickToSender(getGroupName(msgSender, groupMemberIncrease.getGroup())) + "(" + groupMemberIncrease.getGroupCode() + ")");
                msgSender.SENDER.sendGroupMsg(groupMemberIncrease.getGroupCode(), entityBanProperties.getAddGroup());
//                } else {
//                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
//                            "已加入讨论组: " + makeGroupNickToSender(getGroupName(msgSender, groupMemberIncrease.getGroup())) + "(" + groupMemberIncrease.getGroupCode() + ")");
//                    msgSender.SENDER.sendGroupMsg(groupMemberIncrease.getGroupCode(), entityBanProperties.getAddGroup());
//                }
                return MSG_INTERCEPT;
            }
        }
        return MSG_IGNORE;
    }

    @Listen(MsgGetTypes.groupAddRequest)
    public ListenResult groupAddRequest(MsgSender msgSender, GroupAddRequest groupAddRequest) {
        if (groupAddRequest.getRequestType().isAdd()) {
            return MSG_IGNORE;
        }
        if (!entitySystemProperties.isRunning()) {
            msgSender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), groupAddRequest.getRequestType(), false, "本骰休假中，不提供服务");
            msgSender.SENDER.sendPrivateMsg(groupAddRequest.getQQCode(),
                    "本骰休假中，不提供服务");
            return MSG_INTERCEPT;
        }

        if (!entitySystemProperties.isPublicMode() && checkQqInWhiteList(groupAddRequest.getQQCodeNumber())) {
            msgSender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), groupAddRequest.getRequestType(), true, "白名单用户");
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                    "收到白名单群: " + makeGroupNickToSender(getGroupName(msgSender, groupAddRequest.getGroup())) + "(" + groupAddRequest.getGroupCode() + ")，邀请人: "
                            + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupAddRequest.getGroupCode(), groupAddRequest.getQQCode()).getNickOrName()) + "(" + groupAddRequest.getQQCode() + ")");
            insertGroupWhiteList(groupAddRequest.getGroupCodeNumber());
            return MSG_INTERCEPT;
        }

        if (!entitySystemProperties.isPublicMode() && checkGroupInWhiteList(groupAddRequest.getGroupCodeNumber())) {
            msgSender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), groupAddRequest.getRequestType(), true, "白名单群");
            return MSG_INTERCEPT;
        } else if (!entitySystemProperties.isPublicMode()) {
            msgSender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), groupAddRequest.getRequestType(), false, "非白名单群");
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                    makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupAddRequest.getGroupCode(), groupAddRequest.getQQCode()).getNickOrName()) + "(" + groupAddRequest.getQQCode() + ")邀请进群: " + makeGroupNickToSender(getGroupName(msgSender, groupAddRequest.getGroup()) + "(" + groupAddRequest.getGroupCode() + ")，群和邀请人均不在白名单内，已拒绝"));
            msgSender.SENDER.sendGroupMsg(groupAddRequest.getGroupCode(), entityBanProperties.getAddGroup());
            return MSG_INTERCEPT;
        }

        if (entityBanProperties.isLeaveGroupByBan() && checkGroupInBanList(groupAddRequest.getGroupCode())) {
            msgSender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), groupAddRequest.getRequestType(), false, "黑名单群");
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                    makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupAddRequest.getGroupCode(), groupAddRequest.getQQCode()).getNickOrName()) + "(" + groupAddRequest.getQQCode() + ")邀请进入黑名单群: " + makeGroupNickToSender(getGroupName(msgSender, groupAddRequest.getGroup()) + "(" + groupAddRequest.getGroupCode() + ")已拒绝"));
            msgSender.SENDER.sendGroupMsg(groupAddRequest.getGroupCode(), entityBanProperties.getAddGroup());
            return MSG_INTERCEPT;
        }
        if (checkQqInBanList(groupAddRequest.getQQCode())) {
            msgSender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), groupAddRequest.getRequestType(), false, "黑名单用户");
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                    "黑名单用户: " + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupAddRequest.getGroupCode(), groupAddRequest.getQQCode()).getNickName()) + "(" + groupAddRequest.getQQCode() + ")邀请进入群: " + makeGroupNickToSender(getGroupName(msgSender, groupAddRequest.getGroup())) + "(" + groupAddRequest.getGroupCode() + ")已拒绝");
            msgSender.SENDER.sendGroupMsg(groupAddRequest.getGroupCode(), entityBanProperties.getAddGroup());
            return MSG_INTERCEPT;
        }


        if (entityBanProperties.isAutoInputGroup()) {
            msgSender.SETTER.setGroupAddRequest(groupAddRequest.getFlag(), groupAddRequest.getRequestType(), true, "正常加入");
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(),
                    "收到群: " + makeGroupNickToSender(getGroupName(msgSender, groupAddRequest.getGroup())) + "(" + groupAddRequest.getGroupCode() + ")，邀请人: "
                            + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupAddRequest.getGroupCode(), groupAddRequest.getQQCode()).getNickOrName()) + "(" + groupAddRequest.getQQCode() + ")的邀请，已通过");
            return MSG_INTERCEPT;
        } else {
            return MSG_IGNORE;
        }
    }


    @Listen.ByName("groupBan")
    public ListenResult groupBan(GroupBan groupBan, MsgSender msgSender) {
        if (groupBan.getBanType().isBan() && entityBanProperties.isBanGroupBecauseBan() && groupBan.getBeOperatedQQ().equals(msgSender.getLoginInfo().getQQ())) {
            long fromGroup = Long.parseLong(groupBan.getGroup());
            long fromQq = Long.parseLong(groupBan.getOperatorQQ());
            if (checkQqInWhiteList(fromQq)) {
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "在群" + makeGroupNickToSender(getGroupName(msgSender, groupBan.getGroup())) + "("
                        + fromGroup + ")中被禁言，操作者为: " + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupBan.getGroup(), groupBan.getOperatorQQ()).getNickName()) + "(" + fromQq + ")处于本骰子白名单中，因此不做额外操作");
                return MSG_IGNORE;
            } else {
                insertQqBanList(String.valueOf(fromQq), "在群" + makeGroupNickToSender(getGroupName(msgSender, groupBan.getGroup())) + "("
                        + fromGroup + ")中被禁言，操作者为: " + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupBan.getGroup(), groupBan.getOperatorQQ()).getNickName()) + "(" + fromQq + ")");
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "在群" + makeGroupNickToSender(getGroupName(msgSender, groupBan.getGroup())) + "("
                        + fromGroup + ")中被禁言，操作者为: " + makeNickToSender(msgSender.GETTER.getGroupMemberInfo(groupBan.getGroup(), groupBan.getOperatorQQ()).getNickName()) + "(" + fromQq + ")");
            }
            if (checkGroupInWhiteList(fromGroup)) {
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "于群" + makeGroupNickToSender(getGroupName(msgSender, groupBan.getGroup())) + "("
                        + fromGroup + ")中被禁言，但由于同时处于本骰子白名单中，因此不做额外操作");
                return MSG_IGNORE;
            } else {
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "于群" + makeGroupNickToSender(getGroupName(msgSender, groupBan.getGroup())) + "("
                        + fromGroup + ")中被禁言，已退出并拉黑");
                leave(msgSender, String.valueOf(fromGroup));
                deleteBot(fromGroup);
                insertGroupBanList(String.valueOf(fromGroup), "被禁言");
            }
        }
        return MSG_INTERCEPT;
    }


    @Listen(MsgGetTypes.friendAdd)
    public ListenResult friendAdd(FriendAdd friendAdd, MsgSender msgSender) {
        msgSender.SENDER.sendPrivateMsg(friendAdd.getQQCode(), entityBanProperties.getAddFriend());
        return MSG_IGNORE;
    }

    @Listen(MsgGetTypes.friendAddRequest)
    public ListenResult friendAddRequest(FriendAddRequest friendAddRequest, MsgSender msgSender) {
        if (!entitySystemProperties.isRunning()) {
            msgSender.SETTER.setFriendAddRequest(friendAddRequest.getFlag(), "", false);
            msgSender.SENDER.sendPrivateMsg(friendAddRequest.getQQCode(), "本骰休假中，不提供服务");
            return MSG_INTERCEPT;
        }

        if (!entitySystemProperties.isPublicMode() && checkQqInWhiteList(friendAddRequest.getQQCodeNumber())) {
            msgSender.SETTER.setFriendAddRequest(friendAddRequest.getFlag(), "", true);
            msgSender.SENDER.sendPrivateMsg(friendAddRequest.getQQCode(), entityBanProperties.getAddFriend());
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Log.error(e.getMessage(),e);
            }
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "白名单已开启: " + makeNickToSender(getNickNameByQq(msgSender, friendAddRequest.getQQ())) + "(" + friendAddRequest.getQQCode() + ") 的好友邀请，处于白名单中已同意");
            return MSG_INTERCEPT;
        } else if (!entitySystemProperties.isPublicMode()) {
            msgSender.SETTER.setFriendAddRequest(friendAddRequest.getFlag(), "", false);
            msgSender.SENDER.sendPrivateMsg(friendAddRequest.getQQCode(), "很抱歉，您不在白名单内，请联系: " + entityBanProperties.getMaster());
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Log.error(e.getMessage(),e);
            }
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "白名单已开启: " + makeNickToSender(getNickNameByQq(msgSender, friendAddRequest.getQQ())) + "(" + friendAddRequest.getQQCode() + ") 的好友邀请，不在白名单中已拒绝");
            return MSG_INTERCEPT;
        }

        if (checkQqInBanList(friendAddRequest.getQQ())) {
            msgSender.SETTER.setFriendAddRequest(friendAddRequest.getFlag(), "", false);
            msgSender.SENDER.sendPrivateMsg(friendAddRequest.getQQCode(), entityBanProperties.getRefuseFriendByBan());
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Log.error(e.getMessage(),e);
            }
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), makeNickToSender(getNickNameByQq(msgSender, friendAddRequest.getQQ())) + "(" + friendAddRequest.getQQCode() + ") 的好友邀请，处于黑名单中已拒绝");
            return MSG_INTERCEPT;
        }

        if (entityBanProperties.isAutoAddFriends()) {
            msgSender.SETTER.setFriendAddRequest(friendAddRequest.getFlag(), "", true);
            msgSender.SENDER.sendPrivateMsg(friendAddRequest.getQQCode(), entityBanProperties.getAddFriend());
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                Log.error(e.getMessage(),e);
            }
            msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), makeNickToSender(getNickNameByQq(msgSender, friendAddRequest.getQQ())) + "(" + friendAddRequest.getQQCode() + ") 的好友邀请，已自动同意");
            return MSG_INTERCEPT;
        } else {
            return MSG_IGNORE;
        }
    }

    private ListenResult checkBeBanOrInBan(EntityTypeMessages entityTypeMessages) {
        if (checkQqInBanList(entityTypeMessages.getFromQqString()) && entityBanProperties.isIgnoreBanUser() && !checkQqInWhiteList(entityTypeMessages.getFromQq())) {
            return MSG_INTERCEPT;
        }

        if (checkGroupInBanList(entityTypeMessages.getFromGroupString()) && entityBanProperties.isLeaveGroupByBan()) {
            if (checkGroupInWhiteList(entityTypeMessages.getFromGroup())) {
                return MSG_IGNORE;
            }
            sender(entityTypeMessages, "检测到处于黑名单群中，正在退群");
            entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityBanProperties.getManagerGroup(), "检测到处于黑名单群" + makeGroupNickToSender(getGroupName(entityTypeMessages)) + "("
                    + entityTypeMessages.getFromGroup() + ")中，正在退群");
            leave(entityTypeMessages.getMsgSender(), entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getFromGroupString());
            return MSG_INTERCEPT;
        }
        return MSG_IGNORE;
    }

    private void leave(MsgSender msgSender, MsgGetTypes msgGetTypes, String groupId) {
        switch (msgGetTypes) {
            case groupMsg:
                msgSender.SETTER.setGroupLeave(groupId);
                break;
            case discussMsg:
                msgSender.SETTER.setDiscussLeave(groupId);
                break;
            default:
                break;
        }
    }

    private void leave(MsgSender msgSender, String groupId) {
        try {
            msgSender.SETTER.setGroupLeave(groupId);
        } catch (Exception e) {
            msgSender.SETTER.setDiscussLeave(groupId);
        }
    }

    private ListenResult checkBotSwitch(EntityTypeMessages entityTypeMessages) throws OnlyManagerException {
        String messages = entityTypeMessages.getMsgGet().getMsg().toLowerCase();

        if (!messages.contains("bot")) {
            return MSG_IGNORE;
        }

        String tagBotOff = ".*[.。][ ]*bot[ ]*off.*";
        String tagBotInfo = ".*[.。][ ]*bot.*";
        String tagBotExit = ".*[.。][ ]*bot[ ]*exit.*";
        String tagBotUpdate = ".*[.。][ ]*bot[ ]*update.*";

        String tagBotOn = ".*[.。][ ]*bot[ ]*on.*";
        boolean botOn = messagesContainsAtMe(messages, tagBotOn, tagMe) || messagesBotForAll(messages, tagBotOn)
                || messagesContainsQqId(entityTypeMessages, messages, tagBotOn);
        boolean botOff = messagesContainsAtMe(messages, tagBotOff, tagMe) || messagesBotForAll(messages, tagBotOff)
                || messagesContainsQqId(entityTypeMessages, messages, tagBotOff);
        boolean botExit = messagesContainsAtMe(messages, tagBotExit, tagMe) || messagesBotForAll(messages, tagBotExit)
                || messagesContainsQqId(entityTypeMessages, messages, tagBotExit);
        boolean botUpdate = (messagesContainsAtMe(messages, tagBotUpdate, tagMe)
                || messagesBotForAll(messages, tagBotUpdate) || messagesContainsQqId(entityTypeMessages, messages, tagBotUpdate));
        boolean botInfo = (messagesContainsAtMe(messages, tagBotInfo, tagMe) || messagesBotForAll(messages, tagBotInfo)
                || messagesContainsQqId(entityTypeMessages, messages, tagBotInfo)) && !botOn && !botOff && !botExit && !botUpdate;

        if (botOn) {
            checkAudit(entityTypeMessages);
            new Bot(entityTypeMessages).on();
        } else if (botOff) {
            checkAudit(entityTypeMessages);
            new Bot(entityTypeMessages).off();
        } else if (botExit) {
            checkAudit(entityTypeMessages);
            new Bot(entityTypeMessages).exit();
        } else if (botUpdate) {
            new Bot(entityTypeMessages).update();
        } else if (botInfo) {
            new Bot(entityTypeMessages).info();
        }
        return MSG_INTERCEPT;
    }

    private boolean messagesContainsAtMe(String messages, String tagBotSwitch, String tagMe) {
        return messages.trim().matches(tagBotSwitch) && messages.trim().contains(tagMe);
    }

    private boolean messagesBotForAll(String messages, String tagBotSwitch) {
        return messages.trim().matches(tagBotSwitch) && !messages.trim().contains("[cq:at")
                && !messages.matches(".*[0-9]+.*") && messages.matches("^[ ]*" + tagBotSwitch.substring(2, tagBotSwitch.length() - 2) + "[ ]*$");
    }

    private boolean messagesContainsQqId(EntityTypeMessages entityTypeMessages, String messages, String tagBotSwitch) {
        String qqId = entityTypeMessages.getMsgSender().getLoginInfo().getQQ();
        return messages.trim().matches(tagBotSwitch)
                && (messages.trim().contains(qqId) || messages.trim().contains(qqId.substring(qqId.length() - 5)));
    }

    private void checkAudit(EntityTypeMessages entityTypeMessages) throws OnlyManagerException {
        if (entityTypeMessages.getMsgGetTypes() == discussMsg) {
            return;
        }
        GroupMemberInfo member = entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString());

        boolean boolIsAdmin = member.getPowerType() != PowerType.MEMBER;
        boolean boolIsAdminOrInDiscuss = boolIsAdmin
                || entityTypeMessages.getMsgGetTypes() == discussMsg;
        if (!boolIsAdminOrInDiscuss) {
            throw new OnlyManagerException(entityTypeMessages);
        }
    }

    private void setLogsForText(EntityTypeMessages entityTypeMessages) {
        if (checkOthorLogTrue(entityTypeMessages.getFromGroupString())) {
            if ("s".equals(entityTypeMessages.getMsgGet().getMsg().toLowerCase())){
                sender(entityTypeMessages,"kp开始描述");
                return;
            }
            if ("o".equals(entityTypeMessages.getMsgGet().getMsg().toLowerCase())){
                sender(entityTypeMessages,"kp描述结束");
                return;
            }
            setLogText(entityTypeMessages, new EntityLogTag(entityTypeMessages.getFromGroupString(),
                    getOtherLogTrue(entityTypeMessages.getFromGroupString())), entityTypeMessages.getMsgGet().getMsg());
        }
    }
}
