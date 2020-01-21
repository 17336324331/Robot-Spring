package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.entity.EntityLogTag;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesLog;
import com.xingguang.sinanya.db.log.tag.InsertLogTag;
import com.xingguang.sinanya.db.log.tag.SelectLogTag;

import java.util.ArrayList;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 日志标签交互类
 * <p>
 * 标签是指，某个群的某个日志是否处于开启状态
 */
public class LogTag {
    private static SelectLogTag selectLogTag = new SelectLogTag();
    private static InsertLogTag insertLogTag = new InsertLogTag();

    private LogTag() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 从数据库读取日志标签数据刷写到静态变量，这个方法只在启动时调用一次
     */
    public static void flushLogTag() {
        selectLogTag.flushLogTagFromDatabase();
    }

    /**
     * 检查某个群中是否存在指定日志
     * 存在返回true
     * 不存在返回false
     *
     * @param entityTypeMessages 消息封装类
     * @param logName            日志名
     * @return 是否存在
     */
    public static boolean checkLogTagExist(EntityTypeMessages entityTypeMessages, String logName) {
        EntityLogTag entityLogTag = new EntityLogTag(entityTypeMessages.getFromGroupString(), logName);
        return MessagesLog.LOG_NAME_SWITCH.containsKey(entityLogTag);
    }

    /**
     * 检查某个群中是否存在其它日志已经开启，由于LOG_SWITCH_FOR_GROUP在刷写时，就只录入了已打开的日志。所以这里找到群号就一定是日志已打开，未找到群号就一定是日志已关闭返回false
     * 注意：
     * 存在返回true
     * 不存在返回false
     *
     * @param groupId 群号
     * @return 是否不存在其他日志开启
     */
    public static boolean checkOthorLogTrue(String groupId) {
        return MessagesLog.LOG_SWITCH_FOR_GROUP.getOrDefault(groupId, false);
    }

    /**
     * 检查群中已开启的日志名称
     *
     * @param groupId 群号
     * @return 日志名
     */
    public static String getOtherLogTrue(String groupId) {
        String tagNotFound = "未找到";
        if (checkOthorLogTrue(groupId) && MessagesLog.LOG_NAME_FOR_GROUP.get(groupId) != null) {
            return MessagesLog.LOG_NAME_FOR_GROUP.get(groupId);
        } else {
            String name = selectLogTag.getOthorLogTrue(groupId);
            if (!name.equals(tagNotFound)) {
                MessagesLog.LOG_NAME_FOR_GROUP.put(groupId, name);
                return name;
            } else {
                return name;
            }
        }
    }

    /**
     * 检查群中指定日志是否开启
     * 开启返回true
     * 关闭返回false
     *
     * @param entityTypeMessages 消息封装类
     * @param logName            日志名
     * @return 是否开启
     */
    public static boolean checkLogTagSwitch(EntityTypeMessages entityTypeMessages, String logName) {
        EntityLogTag entityLogTag = new EntityLogTag(entityTypeMessages.getFromGroupString(), logName);
        if (checkLogTagExist(entityTypeMessages, logName)) {
            return MessagesLog.LOG_NAME_SWITCH.get(entityLogTag);
        } else {
            return false;
        }
    }

    /**
     * 更改群中某个日志的开关状态
     *
     * @param entityTypeMessages 消息封装类
     * @param logName            日志名
     * @param logSwitch          开关值
     */
    public static void setLogTagSwitch(EntityTypeMessages entityTypeMessages, String logName, boolean logSwitch) {
        EntityLogTag entityLogTag = new EntityLogTag(entityTypeMessages.getFromGroupString(), logName);
        insertLogTag.insertLogTag(entityLogTag, logSwitch);
        MessagesLog.LOG_NAME_SWITCH.put(entityLogTag, logSwitch);
    }

    /**
     * 返回本群中所有日志列表，无论开关
     *
     * @param groupId 群号
     * @return 格式化后的日志列表
     */
    public static String getTagList(String groupId) {
        StringBuilder stringBuffer = new StringBuilder();
        stringBuffer.append("此群中存在以下日志\n");
        ArrayList<String> tagList = (ArrayList<String>) selectLogTag.getTagList(groupId);
        for (String tag : tagList) {
            stringBuffer.append(tag)
                    .append("\n");
        }
        return stringBuffer.substring(0, stringBuffer.length() - 1);
    }

    /**
     * 删除某个群中的某个日志
     *
     * @param entityLogTag 日志标志对象
     */
    public static void delLog(EntityLogTag entityLogTag) {
        insertLogTag.deleteLog(entityLogTag);
        MessagesLog.LOG_NAME_SWITCH.remove(entityLogTag);
    }
}
