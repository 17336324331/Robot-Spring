package com.xingguang.sinanya.dice.system;

import com.xingguang.sinanya.entity.EntityLogTag;
import com.xingguang.sinanya.entity.EntityLogText;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantEmptyLogNameException;
import com.xingguang.sinanya.exceptions.CantInPrivateException;
import com.xingguang.sinanya.system.MessagesLog;
import com.xingguang.sinanya.system.MessagesLogGetLock;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.getinfo.LogTag;
import com.xingguang.sinanya.tools.getinfo.LogText;
import com.xingguang.sinanya.tools.log.LogSave;
import com.xingguang.sinanya.tools.log.SaveDocx;
import com.xingguang.sinanya.tools.log.SendMail;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import org.docx4j.openpackaging.exceptions.Docx4JException;

import java.util.ArrayList;
import java.util.concurrent.*;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 日志记录类
 */
public class Log implements MakeNickToSender {

    private EntityTypeMessages entityTypeMessages;

    public Log(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    private static void testTask(ExecutorService exec, EntityTypeMessages entityTypeMessages, String msg, ArrayList<EntityLogText> bigResult) throws Docx4JException {
        SaveDocx saveDoc = new SaveDocx(entityTypeMessages.getFromGroupString(), msg, bigResult);
        Future<Boolean> future = exec.submit(saveDoc);
        Boolean taskResult = null;
        String failReason = null;
        try {
            // 等待计算结果，最长等待timeout秒，timeout秒后中止任务
            taskResult = future.get(600, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            failReason = "主线程在等待计算结果时被中断！";
        } catch (ExecutionException e) {
            failReason = "主线程等待计算结果，但计算抛出异常！";
        } catch (TimeoutException e) {
            failReason = "主线程等待计算结果超时，因此中断任务线程！";
            Sender.sender(entityTypeMessages, "日志过大，染色线程超过10分钟未能完成，已终止。请联系骰主取docx染色日志，或者换用染色网站");
            exec.shutdownNow();
        }

        System.out.println("\ntaskResult : " + taskResult);
        System.out.println("failReason : " + failReason);
    }

    /**
     * 记录开启，若已存在则重新开启日志进行追加，若不存在则关闭日志
     */
    public void logOn() throws CantInPrivateException, CantEmptyLogNameException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_LOG_ON;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        checkLogName(msg);
        if (LogTag.checkOthorLogTrue(entityTypeMessages.getFromGroupString())) {
            Sender.sender(entityTypeMessages, String.format(GetMessagesProperties.entitySystemProperties.getAlreadyOpen(), LogTag.getOtherLogTrue(entityTypeMessages.getFromGroupString())));
        } else {
            if (LogTag.checkLogTagExist(entityTypeMessages, msg)) {
                Sender.sender(entityTypeMessages, String.format(GetMessagesProperties.entitySystemProperties.getAppendLog(), makeLogNickToSender(msg)));
            } else {
                Sender.sender(entityTypeMessages, String.format(GetMessagesProperties.entitySystemProperties.getCreateLog(), makeLogNickToSender(msg)));
            }
            MessagesLog.LOG_NAME_FOR_GROUP.put(entityTypeMessages.getFromGroupString(), msg);
            MessagesLog.LOG_SWITCH_FOR_GROUP.put(entityTypeMessages.getFromGroupString(), true);
            LogTag.setLogTagSwitch(entityTypeMessages, msg, true);
        }
    }

    /**
     * 日志关闭
     */
    public void logOff() throws CantInPrivateException, CantEmptyLogNameException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_LOG_OFF;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        checkLogName(msg);
        if (LogTag.checkLogTagExist(entityTypeMessages, msg)) {
            if (LogTag.checkLogTagSwitch(entityTypeMessages, msg)) {
                LogTag.setLogTagSwitch(entityTypeMessages, msg, false);
                MessagesLog.LOG_NAME_FOR_GROUP.remove(entityTypeMessages.getFromGroupString());
                MessagesLog.LOG_SWITCH_FOR_GROUP.put(entityTypeMessages.getFromGroupString(), false);
                Sender.sender(entityTypeMessages, makeLogNickToSender(msg) + "已关闭，现在可以使用\".log get " + msg + "\"进行获取");
            } else {
                Sender.sender(entityTypeMessages, String.format(GetMessagesProperties.entitySystemProperties.getAlreadyClose(), makeLogNickToSender(msg)));
            }
        } else {
            Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getNotFoundLog());
        }
    }

    /**
     * 日志get，无法get开启的日志
     */
    public void get() throws CantInPrivateException, Docx4JException, CantEmptyLogNameException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_LOG_GET;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        checkLogName(msg);
        if (!LogTag.checkLogTagSwitch(entityTypeMessages, msg)) {
            if (MessagesLogGetLock.LOG_GET_LOCK.contains(msg)) {
                Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getReadLock());
            } else {
                MessagesLogGetLock.LOG_GET_LOCK.add(msg);
            }
            final ArrayList<EntityLogText> bigResult = LogText.getLogText(new EntityLogTag(entityTypeMessages.getFromGroupString(), msg));
            Sender.sender(entityTypeMessages, "正在抽取数据库为" + makeLogNickToSender(msg) + "生成文件");
            LogSave.logSave(entityTypeMessages.getFromGroupString(), msg, bigResult);
            Sender.sender(entityTypeMessages, "正在抽取数据库为" + makeLogNickToSender(msg) + "生成去括号文件");
            LogSave.logSaveNotHide(entityTypeMessages.getFromGroupString(), msg, bigResult);
            if (bigResult.size() <= 524288) {
                Sender.sender(entityTypeMessages, "正在抽取数据库为" + makeLogNickToSender(msg) + "生成染色文件");
                ExecutorService exec = Executors.newCachedThreadPool();
                testTask(exec, entityTypeMessages, msg, bigResult);
                exec.shutdown();
            }
            Sender.sender(entityTypeMessages, makeLogNickToSender(msg) + "正在发送到您的邮箱" + entityTypeMessages.getFromQq() + "@qq.com");
            SendMail.sendMail(entityTypeMessages.getFromQqString(), entityTypeMessages.getFromGroupString(), GetNickName.getGroupName(entityTypeMessages), msg);
            Sender.sender(entityTypeMessages, "[CQ:at,qq=" + entityTypeMessages.getFromQq() + "] 已发送到您的QQ邮箱，注意查收");
            MessagesLogGetLock.LOG_GET_LOCK.remove(msg);
        } else {
            Sender.sender(entityTypeMessages, makeLogNickToSender(msg) + "仍处于打开状态，请关闭后再试");
        }
    }

    /**
     * 当前群内日志列表
     */
    public void list() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        Sender.sender(entityTypeMessages, LogTag.getTagList(entityTypeMessages.getFromGroupString()));
    }

    /**
     * 删除日志
     */
    public void del() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_LOG_RM;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        if (LogTag.checkLogTagExist(entityTypeMessages, msg)) {
            if (LogTag.checkLogTagSwitch(entityTypeMessages, msg)) {
                Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getDeleteOpenLog());
            } else {
                LogTag.delLog(new EntityLogTag(entityTypeMessages.getFromGroupString(), msg));
                Sender.sender(entityTypeMessages, "已删除日志: " + makeLogNickToSender(msg));
            }
        } else {
            Sender.sender(entityTypeMessages, "不存在日志: " + makeLogNickToSender(msg));
        }
    }

    private void checkLogName(String msg) throws CantEmptyLogNameException {
        if (msg.equals(MessagesSystem.NONE)) {
            throw new CantEmptyLogNameException(entityTypeMessages);
        }
    }
}
