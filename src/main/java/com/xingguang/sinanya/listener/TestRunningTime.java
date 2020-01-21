package com.xingguang.sinanya.listener;

import com.forte.component.forcoolqhttpapi.CoolQHttpInteractionException;
import com.forte.qqrobot.anno.timetask.CronTask;
import com.forte.qqrobot.beans.messages.result.inner.Group;
import com.forte.qqrobot.exception.TimeTaskException;
import com.forte.qqrobot.sender.MsgSender;
import com.forte.qqrobot.timetask.TimeJob;
import com.forte.qqrobot.timetask.TimeTaskContext;
import com.forte.qqrobot.utils.CQCodeUtil;
import com.xingguang.sinanya.db.heap.InsertHeap;
import com.xingguang.sinanya.dice.MakeNickToSender;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.quartz.JobExecutionContext;

import java.util.ArrayList;

import static com.xingguang.sinanya.db.system.InsertBot.deleteBot;
import static com.xingguang.sinanya.db.system.SelectBot.*;
import static com.xingguang.sinanya.system.MessagesBanList.groupWhiteList;
import static com.xingguang.sinanya.system.MessagesSystem.loginInfo;
import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entityBanProperties;
import static com.xingguang.sinanya.tools.getinfo.GetNickName.getGroupName;
import static com.xingguang.sinanya.tools.makedata.RandomInt.random;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 定时任务类，会根据设定的时间每隔多久自动执行一次
 * <p>
 * 这里的“0 * * * * ? *”表示每分钟执行一次，具体怎么写请去查crontab的使用
 * 前5位应该是:分 时 日 月 周
 */

@CronTask("0 0/5 * * * ? *")
public class TestRunningTime implements TimeJob, MakeNickToSender {
    private static Logger log = LoggerFactory.getLogger(TestRunningTime.class.getName());
    private MsgSender msgSender;

    public TestRunningTime() {

    }

    @Override
    public void execute(MsgSender msgSender, CQCodeUtil cqCodeUtil) {
        flushBotList(msgSender);
        this.msgSender = msgSender;
        if (checkHasGroup(Long.parseLong(entityBanProperties.getManagerGroup()))) {
            autoClean();
            try {
                Thread.sleep(random(2000, 12000));
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            autoCleanNotPlay();
        }
        try {
            if (loginInfo.getLoginId() != -10006) {
                new InsertHeap().updateHeap();
            } else {
                log.error("当前识别QQ号错误");
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            log.error("获取群列表失败，停止心跳");
        }
    }

    @Override
    public void execute(JobExecutionContext context) {
        try {
            CQCodeUtil cqCodeUtil = TimeTaskContext.getCQCodeUtil(context);
            MsgSender msgSender = TimeTaskContext.getMsgSender(context);
            execute(msgSender, cqCodeUtil);
        } catch (Exception e) {
            throw new TimeTaskException(e);
        }
    }


    private void autoClean() {
        ArrayList<String> offBotList = selectOffBotList();
        ArrayList<Long> botList = selectBotList();

        Group[] groupList = msgSender.GETTER.getGroupList().getList();
        ArrayList<Long> groupListCode = new ArrayList<>();
        for (Group group : groupList) {
            groupListCode.add(Long.valueOf(group.getCode()));
        }

        for (long botGroupIdString : botList) {
            if (!groupListCode.contains(botGroupIdString)) {
                deleteBot(botGroupIdString);
                msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "删除已不存在群： " + botGroupIdString);
            }
        }

        for (String offBotGroupIdString : offBotList) {
            long offBotGroupId = Long.parseLong(offBotGroupIdString);
            if (groupWhiteList.contains(offBotGroupId)) {
                continue;
            }
            long lastMsgForNow = System.currentTimeMillis() - msgSender.GETTER.getGroupMemberInfo(String.valueOf(offBotGroupId), String.valueOf(loginInfo.getLoginId())).getLastTime()*1000;
            if (lastMsgForNow / 1000 > entityBanProperties.getClearGroupByOff() * 60 * 60 * 24) {
                try {
                    msgSender.SENDER.sendGroupMsg(offBotGroupIdString, String.format(entityBanProperties.getClearGroupByOffInfo(), makeGroupNickToSender(getGroupName(msgSender, offBotGroupIdString)) + "(" + offBotGroupId + ")", lastMsgForNow / 1000 / 60 / 60 / 24));
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "已清理" + lastMsgForNow / 1000 / 60 / 60 / 24 + "日未使用，且已关闭本骰的群: " + makeGroupNickToSender(getGroupName(msgSender, offBotGroupIdString)) + offBotGroupId);
                    msgSender.SETTER.setGroupLeave(String.valueOf(offBotGroupId));
                } catch (Exception e) {
                    msgSender.SENDER.sendPrivateMsg(String.valueOf(offBotGroupId), "已在讨论组: " + makeGroupNickToSender(getGroupName(msgSender, offBotGroupIdString)) + offBotGroupId + "中超过" + lastMsgForNow / 1000 / 60 / 60 / 24 + "日未响应且处于关闭状态，即将退群。\n此次退群不会记录黑名单，如遇到问题请至群162279609进行反馈或使用退群命令缓解问题");
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "已清理" + lastMsgForNow / 1000 / 60 / 60 / 24 + "日未使用，且已关闭本骰的讨论组: " + offBotGroupId);
                    msgSender.SETTER.setDiscussLeave(String.valueOf(offBotGroupId));
                }
                deleteBot(offBotGroupId);
                return;
            }
        }
    }

    private void autoCleanNotPlay() {
        Group[] groupLists = msgSender.GETTER.getGroupList().getList();
        for (Group group : groupLists) {
            long notPlayBotGroupId = Long.parseLong(group.getCode());
            if (groupWhiteList.contains(notPlayBotGroupId)) {
                continue;
            }

            long lastMsgForNow;

            try {
                lastMsgForNow=System.currentTimeMillis() - msgSender.GETTER.getGroupMemberInfo(String.valueOf(notPlayBotGroupId), String.valueOf(loginInfo.getLoginId())).getLastTime()*1000;
            }catch (CoolQHttpInteractionException e){
                continue;
            }
            if (lastMsgForNow / 1000 > entityBanProperties.getClearGroup() * 60 * 60 * 24) {
                try {
                    msgSender.SENDER.sendGroupMsg(String.valueOf(notPlayBotGroupId), String.format(entityBanProperties.getClearGroupInfo(), makeGroupNickToSender(getGroupName(msgSender, String.valueOf(notPlayBotGroupId))) + "(" + notPlayBotGroupId + ")", lastMsgForNow / 1000 / 60 / 60 / 24));
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "已清理" + lastMsgForNow / 1000 / 60 / 60 / 24 + "日未使用的群: " + makeGroupNickToSender(getGroupName(msgSender, String.valueOf(notPlayBotGroupId))) + notPlayBotGroupId);
                    msgSender.SETTER.setGroupLeave(String.valueOf(notPlayBotGroupId));
                } catch (Exception e) {
                    msgSender.SENDER.sendDiscussMsg(String.valueOf(notPlayBotGroupId), "已在讨论组: " + makeGroupNickToSender(getGroupName(msgSender, String.valueOf(notPlayBotGroupId))) + notPlayBotGroupId + "中超过" + lastMsgForNow / 1000 / 60 / 60 / 24 + "日未响应，即将退群。\n此次退群不会记录黑名单，如遇到问题请至群162279609进行反馈或使用退群命令缓解问题");
                    msgSender.SENDER.sendGroupMsg(entityBanProperties.getManagerGroup(), "已清理" + lastMsgForNow / 1000 / 60 / 60 / 24 + "日未使用的讨论组: " + notPlayBotGroupId);
                    msgSender.SETTER.setDiscussLeave(String.valueOf(notPlayBotGroupId));
                }
                deleteBot(notPlayBotGroupId);
                return;
            }
        }
    }

    private boolean checkHasGroup(long groupId) {
        Group[] groupList = msgSender.GETTER.getGroupList().getList();
        for (Group group : groupList) {
            if (groupId == Long.parseLong(group.getCode())) {
                return true;
            }
        }
        return false;
    }
}