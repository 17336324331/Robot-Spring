package com.xingguang.sinanya.dice.system;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesHelp;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.makedata.Sender;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 帮助信息
 */
public class Help {
    private EntityTypeMessages entityTypeMessages;

    public Help(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 骰点帮助
     */
    public void normal() {
        Sender.sender(entityTypeMessages, MessagesHelp.NORMAL_HELP.toString());
    }

    /**
     * 车卡帮助
     */
    public void make() {
        Sender.sender(entityTypeMessages, MessagesHelp.MAKE_HELP.toString());
    }

    /**
     * 带团帮助
     */
    public void group() {
        Sender.sender(entityTypeMessages, MessagesHelp.GROUP_HELP.toString());
    }

    public void game() {
        Sender.sender(entityTypeMessages, MessagesHelp.GAME_HELP.toString());
    }

    /**
     * 获取资料帮助
     */
    public void book() {
        Sender.sender(entityTypeMessages, MessagesHelp.BOOK_HELP.toString());
    }

    /**
     * dnd帮助
     */
    public void dnd() {
        Sender.sender(entityTypeMessages, MessagesHelp.DND_HELP.toString());
    }

    /**
     * 机器人信息
     */
    public void info() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MessagesSystem.STR_BOT_INFO.toString());
        if (!GetMessagesProperties.entitySystemProperties.getHelpInfo().equals(MessagesSystem.NONE)) {
            stringBuilder.append("\n-----------------------------------------------\n")
                    .append(GetMessagesProperties.entitySystemProperties.getHelpInfo());
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    public void other() {
        Sender.sender(entityTypeMessages, MessagesHelp.OTHOR_HELP.toString());
    }

    public void master() {
        Sender.sender(entityTypeMessages, MessagesHelp.MASTER_HELP.toString());
    }

    public void manager() {
        Sender.sender(entityTypeMessages, MessagesHelp.MANAGER_HELP.toString());
    }
}
