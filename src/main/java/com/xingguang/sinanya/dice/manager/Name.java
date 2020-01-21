package com.xingguang.sinanya.dice.manager;

import com.forte.qqrobot.beans.messages.msgget.MsgGet;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.getinfo.GetName;
import com.xingguang.sinanya.tools.makedata.MakeMessages;

/**
 * @author SitaNya
 * @date 2019/11/25
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class Name {
    private EntityTypeMessages entityTypeMessages;

    public Name(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void nn() {
        String tag = MessagesTag.TAG_ST_NAME;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        if (msg.equals(MessagesSystem.NONE)) {
            MsgGet msgGet = new MsgGet() {
                @Override
                public String getId() {
                    return entityTypeMessages.getMsgGet().getId();
                }

                @Override
                public String getMsg() {
                    return ".st" + GetName.getRandomName();
                }

                @Override
                public String getFont() {
                    return entityTypeMessages.getMsgGet().getFont();
                }

                @Override
                public Long getTime() {
                    return entityTypeMessages.getMsgGet().getTime();
                }

                @Override
                public String getOriginalData() {
                    return entityTypeMessages.getMsgGet().getOriginalData();
                }
            };
            EntityTypeMessages entityTypeMessages1;
            switch (entityTypeMessages.getMsgGetTypes()) {
                case privateMsg:
                    entityTypeMessages1 = new EntityTypeMessages(entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getMsgSender(), msgGet, entityTypeMessages.getMsgPrivate());
                    break;
                case discussMsg:
                    entityTypeMessages1 = new EntityTypeMessages(entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getMsgSender(), msgGet, entityTypeMessages.getMsgDisGroup());
                    break;
                default:
                    entityTypeMessages1 = new EntityTypeMessages(entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getMsgSender(), msgGet, entityTypeMessages.getMsgGroup());
            }
            new Roles(entityTypeMessages1).set();
        } else {
            MsgGet msgGet = new MsgGet() {
                @Override
                public String getId() {
                    return entityTypeMessages.getMsgGet().getId();
                }

                @Override
                public String getMsg() {
                    return ".st" + msg;
                }

                @Override
                public String getFont() {
                    return entityTypeMessages.getMsgGet().getFont();
                }

                @Override
                public Long getTime() {
                    return entityTypeMessages.getMsgGet().getTime();
                }

                @Override
                public String getOriginalData() {
                    return entityTypeMessages.getMsgGet().getOriginalData();
                }
            };
            EntityTypeMessages entityTypeMessages1;
            switch (entityTypeMessages.getMsgGetTypes()) {
                case privateMsg:
                    entityTypeMessages1 = new EntityTypeMessages(entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getMsgSender(), msgGet, entityTypeMessages.getMsgPrivate());
                    break;
                case discussMsg:
                    entityTypeMessages1 = new EntityTypeMessages(entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getMsgSender(), msgGet, entityTypeMessages.getMsgDisGroup());
                    break;
                default:
                    entityTypeMessages1 = new EntityTypeMessages(entityTypeMessages.getMsgGetTypes(), entityTypeMessages.getMsgSender(), msgGet, entityTypeMessages.getMsgGroup());
            }
            new Roles(entityTypeMessages1).set();
        }
    }
}
