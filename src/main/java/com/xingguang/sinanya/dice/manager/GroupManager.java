package com.xingguang.sinanya.dice.manager;

import com.forte.qqrobot.beans.messages.result.inner.Group;
import com.forte.qqrobot.beans.messages.result.inner.GroupMember;
import com.xingguang.sinanya.entity.EntityGroupSwitch;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.*;
import com.xingguang.sinanya.exceptions.*;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.system.MessagesWelcome;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetGroupInfo;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.getinfo.SwitchBot;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import com.xingguang.sinanya.dice.imal.Check;
import com.xingguang.sinanya.exceptions.*;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.xingguang.sinanya.db.groupSwitch.InputGroupSwitch.insertGroupSwitch;
import static com.xingguang.sinanya.db.system.InsertBot.deleteBot;
import static com.xingguang.sinanya.db.system.SelectBot.flushBotList;
import static com.xingguang.sinanya.db.system.SelectBot.selectBotList;
import static com.xingguang.sinanya.tools.getinfo.GetNickName.getGroupName;
import static java.lang.Math.ceil;

/**
 * @author SitaNya
 * @date 2019/9/11
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class GroupManager implements MakeNickToSender, Check {
    private EntityTypeMessages entityTypeMessages;

    public GroupManager(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void jrrp() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_JRRP;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean jrrp = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setJrrp(jrrp);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (jrrp) {
                    Sender.sender(entityTypeMessages, "jrrp已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "jrrp已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setJrrp(jrrp);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (jrrp) {
                    Sender.sender(entityTypeMessages, "jrrp已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "jrrp已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void npc() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_NPC;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean npc = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setNpc(npc);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (npc) {
                    Sender.sender(entityTypeMessages, "npc已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "npc已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setNpc(npc);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (npc) {
                    Sender.sender(entityTypeMessages, "npc已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "npc已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void welcome() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_WELCOME;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean welcome = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setSimple(welcome);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (welcome) {
                    Sender.sender(entityTypeMessages, "welcome已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "welcome已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setWelcome(welcome);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (welcome) {
                    Sender.sender(entityTypeMessages, "welcome已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "welcome已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void gas() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_GAS;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean gas = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setGas(gas);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (gas) {
                    Sender.sender(entityTypeMessages, "gas已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "gas已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setGas(gas);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (gas) {
                    Sender.sender(entityTypeMessages, "gas已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "gas已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void bg() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_BG;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean bg = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setBg(bg);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (bg) {
                    Sender.sender(entityTypeMessages, "bg已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "bg已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setBg(bg);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (bg) {
                    Sender.sender(entityTypeMessages, "bg已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "bg已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void tz() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_TZ;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean tz = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setTz(tz);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (tz) {
                    Sender.sender(entityTypeMessages, "tz已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "tz已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setTz(tz);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (tz) {
                    Sender.sender(entityTypeMessages, "tz已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "tz已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void simple() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_SIMPLE;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean simple = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setSimple(simple);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (simple) {
                    Sender.sender(entityTypeMessages, "simple已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "simple已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setSimple(simple);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (simple) {
                    Sender.sender(entityTypeMessages, "simple已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "simple已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void ob() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_OB;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean ob = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setOb(ob);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (ob) {
                    Sender.sender(entityTypeMessages, "ob已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "ob已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setOb(ob);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (ob) {
                    Sender.sender(entityTypeMessages, "ob已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "ob已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }

    /**
     * 设定kp主群，由于基本不造成影响，所以不做删除了
     */
    public void deck() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_DECK;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 5));
        long groupId = entityTypeMessages.getFromGroup();
        if ("1".equals(msg) || "0".equals(msg)) {
            boolean deck = "1".equals(msg);
            if (MessagesBanList.groupSwitchHashMap.containsKey(groupId)) {
                EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(groupId);
                entityGroupSwitch.setDeck(deck);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (deck) {
                    Sender.sender(entityTypeMessages, "deck已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "deck已在本群关闭");
                }
            } else {
                EntityGroupSwitch entityGroupSwitch = new EntityGroupSwitch();
                entityGroupSwitch.setDeck(deck);
                MessagesBanList.groupSwitchHashMap.put(groupId, entityGroupSwitch);
                insertGroupSwitch(entityGroupSwitch, groupId);
                if (deck) {
                    Sender.sender(entityTypeMessages, "deck已在本群开启");
                } else {
                    Sender.sender(entityTypeMessages, "deck已在本群关闭");
                }
            }
        } else {
            Sender.sender(entityTypeMessages, "请在结尾输入1开启，0关闭");
        }
    }


    public void get() throws OnlyManagerException, CantInPrivateException {
        checkAudit(entityTypeMessages);
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String jrrpEnable = "禁用";
        String welcomeEnable = "禁用";
        String welcomeSet = "未设置";
        String deckEnable = "禁用";
        String botListEnable = "禁用";
        if (GetMessagesProperties.entityGame.isJrrpSwitch()) {
            jrrpEnable = "启用";
        }
        if (GetMessagesProperties.entityGame.isWelcomeSwitch()) {
            welcomeEnable = "启用";
        }

        if (MessagesWelcome.welcomes.containsKey(entityTypeMessages.getFromGroup()) && !MessagesWelcome.welcomes.get(entityTypeMessages.getFromGroup()).getText().equals(MessagesSystem.NONE)) {
            welcomeSet = "已设置";
        }

        if (GetMessagesProperties.entityGame.isDeck()) {
            deckEnable = "启用";
        }

        if (GetMessagesProperties.entityGame.isBotList()) {
            botListEnable = "启用";
        }

        String jrrpGroup = "启用";
        String npcGroup = "启用";
        String welcomeGroup = "启用";
        String gasGroup = "启用";
        String bgGroup = "启用";
        String tzGroup = "启用";
        String simpleGroup = "禁用";
        String obGroup = "启用";
        String deckGroup = "启用";

        if (MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup())) {
            EntityGroupSwitch entityGroupSwitch = MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup());
            if (!entityGroupSwitch.isJrrp()) {
                jrrpGroup = "禁用";
            }

            if (!entityGroupSwitch.isNpc()) {
                npcGroup = "禁用";
            }

            if (!entityGroupSwitch.isWelcome()) {
                welcomeGroup = "禁用";
            }

            if (!entityGroupSwitch.isGas()) {
                gasGroup = "禁用";
            }

            if (!entityGroupSwitch.isBg()) {
                bgGroup = "禁用";
            }

            if (!entityGroupSwitch.isTz()) {
                tzGroup = "禁用";
            }

            if (!entityGroupSwitch.isSimple()) {
                simpleGroup = "禁用";
            } else {
                simpleGroup = "启用";
                jrrpGroup = "因simple模式而禁用";
                npcGroup = "因simple模式而禁用";
                gasGroup = "因simple模式而禁用";
                bgGroup = "因simple模式而禁用";
                tzGroup = "因simple模式而禁用";
                deckGroup = "因simple模式而禁用";
            }

            if (!entityGroupSwitch.isOb()) {
                obGroup = "禁用";
            }

            if (!entityGroupSwitch.isDeck()) {
                deckGroup = "禁用";
            }
        }

        GroupMember[] members = entityTypeMessages.getMsgSender().GETTER.getGroupMemberList(entityTypeMessages.getFromGroupString()).getList();
        int fishMembers = 0;
        StringBuilder banInGroup = new StringBuilder();
        for (GroupMember member : members) {
            long lastMsgForNow = System.currentTimeMillis() - member.getLastTime();
            if (lastMsgForNow / 1000 > 30 * 60 * 60 * 24) {
                fishMembers++;
            }
            if (MessagesBanList.qqBanList.containsKey(String.valueOf(member.getQQ()))) {
                banInGroup.append("\n").append(makeNickToSender(String.valueOf(member.getQQ()))).append("(").append(member.getNickName()).append(")");
            }
        }
        String info = "群名:\t" +
                makeGroupNickToSender(GetNickName.getGroupName(entityTypeMessages)) +
                "\n" +
                "群号:\t" +
                entityTypeMessages.getFromGroupString() +
                "\n" +
                MessagesSystem.loginInfo.getLoginName() + "在本群状态:\t" +
                SwitchBot.getBot(entityTypeMessages.getFromGroup()) +
                "\n" +
                "jrrp全局开关:\t" +
                jrrpEnable +
                "\n" +
                "jrrp本群开关:\t" +
                jrrpGroup +
                "\n" +
                "welcome全局开关:\t" +
                welcomeEnable +
                "\n" +
                "welcome本群开关:\t" +
                welcomeGroup +
                "\n" +
                "welcome信息:\t" +
                welcomeSet +
                "\n" +
                "deck全局开关:\t" +
                deckEnable +
                "\n" +
                "deck本群开关:\t" +
                deckGroup +
                "\n" +
                "dicelist全局开关:\t" +
                botListEnable +
                "\n" +
                "npc本群开关:\t" +
                npcGroup +
                "\n" +
                "gas本群开关:\t" +
                gasGroup +
                "\n" +
                "bj本群开关:\t" +
                bgGroup +
                "\n" +
                "tz本群开关:\t" +
                tzGroup +
                "\n" +
                "simple本群开关:\t" +
                simpleGroup +
                "\n" +
                "ob本群开关:\t" +
                obGroup +
                "\n" +
                "30天不活跃群员数:\t" +
                fishMembers +
                "\n" +
                "群内黑名单成员:\t" +
                banInGroup;
        Sender.sender(entityTypeMessages, info);
    }

    public void groupList() throws NotMasterException {
        if (!GetMessagesProperties.entityBanProperties.getMaster().contains(entityTypeMessages.getFromQqString())) {
            throw new NotMasterException(entityTypeMessages);
        }
        String tag = MessagesTag.TAG_GROUP_LIST;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        Sender.sender(entityTypeMessages, "此命令可能需要较长时间才能回复，通常在10分钟以内，期间不会影响其他功能使用，请耐心等待");
        int index = 1;
        if (CheckIsNumbers.isNumeric(msg)) {
            index = Integer.parseInt(msg);
        }
        Group[] groups = entityTypeMessages.getMsgSender().GETTER.getGroupList().getList();
        ArrayList<String> collect = (ArrayList<String>) Arrays.stream(groups).parallel().map(s -> new GetGroupInfo(entityTypeMessages.getMsgSender().GETTER.getGroupInfo(s.getCode())).call()).collect(Collectors.toList());
        int pages = (int) ceil(collect.size() * 1.0 / 10);
        if (index > pages) {
            Sender.sender(entityTypeMessages, "您指定的页码数大于总页数");
            return;
        }
        if (pages == 1) {
            Sender.sender(entityTypeMessages, "本骰共加入以下群组:\n" + StringUtil.joiner(collect, "\n"));
        } else {
            ArrayList<String> result = new ArrayList<>();
            int a = 1;
            for (int i = (index - 1) * 10; i < collect.size(); i++) {
                result.add(collect.get(i));
                a++;
                if (a == 10) {
                    break;
                }
            }
            Sender.sender(entityTypeMessages, "本骰共加入以下群组:(共有" + collect.size() + "项，共计" + pages + "页" + "当前显示为第" + index + "页)\n" + StringUtil.joiner(result, "\n"));
        }
        flushBotList(entityTypeMessages.getMsgSender());
    }

    public void groupSearch() throws NotFoundGroupException, BanListInputNotIdException, NotMasterException, CantInPrivateException {
        if (!GetMessagesProperties.entityBanProperties.getMaster().contains(entityTypeMessages.getFromQqString())) {
            throw new NotMasterException(entityTypeMessages);
        }
        //CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_GROUP_SEARCH;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 4));
        if (!CheckIsNumbers.isNumeric(msg)) {
            throw new BanListInputNotIdException(entityTypeMessages);
        }
        long groupId = Long.parseLong(msg);
        String name = GetNickName.getGroupName(entityTypeMessages.getMsgSender(), String.valueOf(groupId));
//        int memberSize = coolQ.getGroupInfo(groupId, false).getCount();
        if ("".equals(name)) {
            throw new NotFoundGroupException(entityTypeMessages);
        }
        Sender.sender(entityTypeMessages, "群号: " + groupId + "\t群名: ");
//        + name + "\t共有成员: " + memberSize + "名"
    }

    public void groupClean() throws NotMasterException {
        if (!GetMessagesProperties.entityBanProperties.getMaster().contains(entityTypeMessages.getFromQqString())) {
            throw new NotMasterException(entityTypeMessages);
        }
        ArrayList<Long> botList = selectBotList();

        Group[] groupList = entityTypeMessages.getMsgSender().GETTER.getGroupList().getList();
        ArrayList<Long> groupListCode = new ArrayList<>();
        for (Group group : groupList) {
            groupListCode.add(Long.valueOf(group.getCode()));
        }

        for (long botGroupIdString : botList) {
            if (!groupListCode.contains(botGroupIdString)) {
                deleteBot(botGroupIdString);
                for (String masterId : GetMessagesProperties.entityBanProperties.getMaster()) {
                    entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(masterId, "删除已不存在群： " + botGroupIdString);
                }
            }
        }
    }
}
