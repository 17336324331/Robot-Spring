package com.xingguang.sinanya.tools.getinfo;

import com.forte.qqrobot.beans.messages.result.GroupMemberInfo;
import com.forte.qqrobot.sender.MsgSender;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.RoleInfoCache;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 获取昵称
 */
public class GetNickName {

    private GetNickName() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 如果已经设定了人物卡则默认给人物卡名字，没设定的话给QQ昵称
     *
     * @param entityTypeMessages 消息包装类
     * @return 昵称
     */
    public static String getNickName(EntityTypeMessages entityTypeMessages) {
        if (RoleChoose.checkRoleChooseExistByFromQQ(entityTypeMessages)) {
            if ("自定义".equals(RoleChoose.getRoleChooseByFromQQ(entityTypeMessages))) {
                return RoleInfoCache.ROLE_TMP_NAME.getOrDefault(entityTypeMessages.getFromQq(), entityTypeMessages.getMsgSender().getPersonInfoByCode(entityTypeMessages.getFromQqString()).getName());
            }
            return RoleChoose.getRoleChooseByFromQQ(entityTypeMessages);
        }
        switch (entityTypeMessages.getMsgGetTypes()) {
            case groupMsg:
                return entityTypeMessages.getMsgGroup().getOtherParam("sender.nickname", String.class);
            case discussMsg:
                return entityTypeMessages.getMsgDisGroup().getOtherParam("sender.nickname", String.class);
            default:
                return entityTypeMessages.getMsgSender().getPersonInfoByCode(entityTypeMessages.getFromQqString()).getName();
        }
    }

    /**
     * 如果已经设定了人物卡则默认给人物卡名字，没设定的话给QQ昵称
     *
     * @return 昵称
     */
    public static String getNickName(long qqId, EntityTypeMessages entityTypeMessages) {
        if (RoleChoose.checkRoleChooseExistByQQ(qqId) && !"自定义".equals(RoleChoose.getRoleChooseByQQ(qqId))) {
            return RoleChoose.getRoleChooseByQQ(qqId);
        }
        switch (entityTypeMessages.getMsgGetTypes()) {
            case groupMsg:
            case discussMsg:
                GroupMemberInfo member = entityTypeMessages.getMsgSender().GETTER.getGroupMemberInfo(entityTypeMessages.getFromGroupString(), String.valueOf(qqId));
                if (member != null) {
                    return member.getCard();
                }
            default:
                return entityTypeMessages.getMsgSender().getPersonInfoByCode(entityTypeMessages.getFromQqString()).getName();
        }
    }

    public static String getTrueNickName(EntityTypeMessages entityTypeMessages) {
        switch (entityTypeMessages.getMsgGetTypes()) {
            case groupMsg:
                return entityTypeMessages.getMsgGroup().getOtherParam("sender.nickname", String.class);
            case discussMsg:
                return entityTypeMessages.getMsgDisGroup().getOtherParam("sender.nickname", String.class);
            default:
                return entityTypeMessages.getMsgSender().getPersonInfoByCode(entityTypeMessages.getFromQqString()).getName();
        }
    }

    /**
     * 如果已经设定了人物卡则默认给人物卡名字，没设定的话给QQ昵称
     *
     * @return 昵称
     */
    public static String getNickNameByQq(MsgSender msgSender, String qqId) {
        return msgSender.GETTER.getStrangerInfo(String.valueOf(qqId)).getName();
    }


//    /**
//     * 返回群或讨论组名
//     *
//     * @param entityTypeMessages 消息包装类
//     * @return 昵称
//     */
//    public static String getGroupName(EntityTypeMessages entityTypeMessages) {
//        return getGroupName(entityTypeMessages.getFromGroup());
//    }
//
//    /**
//     * 返回群或讨论组名
//     *
//     * @return 昵称
//     */
//    public static String getGroupName(long groupId) {
//        return coolQ.getGroupInfo(groupId).getName();
//    }
//
//
//    /**
//     * 返回群或讨论组名
//     *
//     * @return 昵称
//     */
//    public static String getGroupName(String groupId) {
//        return getGroupInfo(Long.parseLong(groupId)).getName();
//    }

    /**
     * 返回群或讨论组名
     *
     * @param entityTypeMessages 消息包装类
     * @return 昵称
     */
    public static String getGroupName(EntityTypeMessages entityTypeMessages) {
        return entityTypeMessages.getMsgSender().GETTER.getGroupInfo(entityTypeMessages.getFromGroupString()).getName();
    }

    /**
     * 返回群或讨论组名
     *
     * @return 昵称
     */
    public static String getGroupName(MsgSender msgSender, String groupId) {
        return msgSender.GETTER.getGroupInfo(groupId).getName();
    }


}
