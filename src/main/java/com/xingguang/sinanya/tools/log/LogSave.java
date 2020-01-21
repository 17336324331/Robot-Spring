package com.xingguang.sinanya.tools.log;

import com.xingguang.sinanya.entity.EntityLogText;
import com.xingguang.sinanya.entity.imal.LogType;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.*;
import java.util.ArrayList;

import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entitySystemProperties;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 日志存储落地类
 */
public class LogSave {
    private static final Logger log = LoggerFactory.getLogger(LogSave.class.getName());


    private LogSave() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 将格式化后的全部日志信息存储到相对路径"bin/../saveLogs/${groupId}/${logName}"下
     *
     * @param groupId 群号
     * @param logName 日志名
     * @param info    格式化后的全部日志信息
     */
    public static void logSave(String groupId, String logName, ArrayList<EntityLogText> info) {
        // 1：利用File类找到要操作的对象
        File file = new File(entitySystemProperties.getSystemDir() + "/saveLogs/" + groupId + "/" + logName + ".txt");

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            log.error("日志目录错误");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (EntityLogText entityLogText : info) {
            stringBuilder.append(entityLogText.getText()).append("\n");
        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK"));) {
            out.write(stringBuilder.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 将格式化后的全部日志信息存储到相对路径"bin/../saveLogs/${groupId}/${logName}"下
     *
     * @param groupId 群号
     * @param logName 日志名
     * @param info    格式化后的全部日志信息
     */
    public static void logSaveNotHide(String groupId, String logName, ArrayList<EntityLogText> info) {
        // 1：利用File类找到要操作的对象
        File file = new File(entitySystemProperties.getSystemDir() + "/saveLogs/" + groupId + "/" + logName + "-NotHide.txt");

        if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
            log.error("日志目录错误");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (EntityLogText entityLogText : info) {
            if (entityLogText.getLogType() != LogType.HIDE) {
                stringBuilder.append(entityLogText.getText()).append("\n");
            }
        }

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true), "GBK"));) {
            out.write(stringBuilder.toString());
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
