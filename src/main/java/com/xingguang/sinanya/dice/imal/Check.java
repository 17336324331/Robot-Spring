package com.xingguang.sinanya.dice.imal;

import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.beans.messages.types.MsgGetTypes;
import com.forte.qqrobot.beans.messages.types.PowerType;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.OnlyManagerException;

public interface Check {
    default void checkAudit(EntityTypeMessages entityTypeMessages) throws OnlyManagerException {
        GroupMemberInfo member = entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), entityTypeMessages.getFromQqString());
        if (member != null) {
            PowerType power = member.getPowerType();

            boolean boolIsAdmin = power.isAdmin() || power.isOwner();
            boolean boolIsAdminOrInDiscuss = boolIsAdmin
                    || entityTypeMessages.getMsgGetTypes() == MsgGetTypes.discussMsg;
            if (!boolIsAdminOrInDiscuss) {
                throw new OnlyManagerException(entityTypeMessages);
            }
        }
    }
}
