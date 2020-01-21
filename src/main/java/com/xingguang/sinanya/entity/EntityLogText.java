package com.xingguang.sinanya.entity;

import com.xingguang.sinanya.entity.imal.LogType;

import java.util.Objects;

/**
 * @author SitaNya
 * @date 2019/9/12
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class EntityLogText {
    String nick;
    String text;
    LogType logType;

    public EntityLogText() {
    }

    public EntityLogText(String nick, String text, LogType logType) {
        this.nick = nick;
        this.text = text;
        this.logType = logType;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LogType getLogType() {
        return logType;
    }

    public void setLogType(LogType logType) {
        this.logType = logType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EntityLogText that = (EntityLogText) o;
        return Objects.equals(nick, that.nick) &&
                Objects.equals(text, that.text) &&
                logType == that.logType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nick, text, logType);
    }
}
