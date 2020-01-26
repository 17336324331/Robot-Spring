package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityAntagonize;
import com.xingguang.sinanya.entity.EntityHistory;
import com.xingguang.sinanya.entity.EntityNickAndRandomAndSkill;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.*;
import com.xingguang.sinanya.system.MessagesAntagonize;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckResultLevel;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.getinfo.Kp;
import com.xingguang.sinanya.tools.makedata.*;
import com.xingguang.sinanya.dice.MakeNickToSender;
import com.xingguang.sinanya.tools.makedata.MakeRal;
import com.xingguang.sinanya.tools.makedata.MakeRcl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.xingguang.sinanya.tools.checkdata.CheckIsNumbers.isNumeric;
import static com.xingguang.sinanya.tools.getinfo.History.changeHistory;
import static com.xingguang.sinanya.tools.makedata.GetNickAndRandomAndSkill.getNickAndRandomAndSkill;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: ra、rc判定，ral、rcl多次骰点，rav、rcv对抗
 */
public class RollAndCheck implements En, MakeNickToSender {
    private static final Logger log = LoggerFactory.getLogger(RollAndCheck.class.getName());
    private Pattern times = Pattern.compile(".*?([0-9]+#).*");


    private String defaultGroupId = "0";

    private EntityTypeMessages entityTypeMessages;

    public RollAndCheck(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 房规判定//这里是正则表达式^[.。][ ]*ra.*
     */
    public void ra() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        String tag = MessagesTag.TAG_RA;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2)).replaceAll(" +", "");
        String result = check(msg, false);
        Sender.sender(entityTypeMessages, result);
    }

    /**
     * 规则书判定
     */
    public void rc() throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        String tag = MessagesTag.TAG_RC;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2)).replaceAll(" +", "").replaceAll(" +", "");
        String result = check(msg, true);
        Sender.sender(entityTypeMessages, result);
    }


    /**
     * 房规对抗
     *
     * @throws NotSetKpGroupException 如果私聊未设置kp主群，则会报此错误
     */
    public void rav() throws NotSetKpGroupException, ManyRollsTimesTooMoreException, RollCantInZeroException, NotFoundSkillException {
        String tag = MessagesTag.TAG_RAV;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2)).replaceAll(" +", "");
        EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = getNickAndRandomAndSkill(entityTypeMessages, msg);
//        checkSkillInput(entityNickAndRandomAndSkill);
        CheckResultLevel checkResultLevel = new CheckResultLevel(entityNickAndRandomAndSkill.getRandom(), entityNickAndRandomAndSkill.getSkill(), false);
        //        使用房规进行判定结果
        String nick;
        if (msg.contains(":")) {
            nick = msg.split(":")[0];
            msg = msg.split(":")[1];
        } else {
            nick = entityNickAndRandomAndSkill.getNick();
        }
        StringBuilder stringBuilder = new StringBuilder()
                .append(makeNickToSender(nick))
                .append("进行鉴定: D100=")
                .append(entityNickAndRandomAndSkill.getRandom())
                .append("/")
                .append(entityNickAndRandomAndSkill.getSkill())
                .append(checkResultLevel.getLevelResultStr(entityTypeMessages.getFromGroupString()));
        String groupId = makeVforGroupId(msg, checkResultLevel);
        if (MessagesAntagonize.ANTAGONIZE.containsKey(groupId) && !groupId.equals(defaultGroupId)) {
            SendRVResult(stringBuilder, groupId, checkResultLevel);
        } else if (!groupId.equals(defaultGroupId)) {
            Sender.sender(entityTypeMessages, stringBuilder.toString());
            MessagesAntagonize.ANTAGONIZE.put(groupId, checkResultLevel.getAntagonize());
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, makeNickToSender(GetNickName.getNickName(entityTypeMessages)) + "发起一次对抗");
        } else {
            throw new NotSetKpGroupException(entityTypeMessages);
        }
    }

    /**
     * 规则书对抗
     *
     * @throws NotSetKpGroupException 如果私聊未设置kp主群，则会报此错误
     */
    public void rcv() throws NotSetKpGroupException, ManyRollsTimesTooMoreException, RollCantInZeroException, NotFoundSkillException {
        String tag = MessagesTag.TAG_RCV;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2)).replaceAll(" +", "");
        EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = getNickAndRandomAndSkill(entityTypeMessages, msg);
        CheckResultLevel checkResultLevel = new CheckResultLevel(entityNickAndRandomAndSkill.getRandom(), entityNickAndRandomAndSkill.getSkill(), true);
//                使用规则书进行判定结果
        String nick;
        if (msg.contains(":")) {
            nick = msg.split(":")[0];
            msg = msg.split(":")[1];
        } else {
            nick = entityNickAndRandomAndSkill.getNick();
        }
        StringBuilder stringBuilder = new StringBuilder()
                .append(makeNickToSender(nick))
                .append("进行鉴定: D100=")
                .append(entityNickAndRandomAndSkill.getRandom())
                .append("/")
                .append(entityNickAndRandomAndSkill.getSkill())
                .append(checkResultLevel.getLevelResultStr(entityTypeMessages.getFromGroupString()));
        String groupId = makeVforGroupId(msg, checkResultLevel);
        if (MessagesAntagonize.ANTAGONIZE.containsKey(groupId) && !groupId.equals(defaultGroupId)) {
            SendRVResult(stringBuilder, groupId, checkResultLevel);
        } else if (!groupId.equals(defaultGroupId)) {
//            静态对象Antagonize中包含了以群号为key的EntityAntagonize对象，如果不包含的话，那么就说明这次是发起对抗，直接插入进去
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, makeNickToSender(GetNickName.getNickName(entityTypeMessages)) + "发起一次对抗");
            Sender.sender(entityTypeMessages, stringBuilder.toString());
            MessagesAntagonize.ANTAGONIZE.put(groupId, checkResultLevel.getAntagonize());
        } else {
            throw new NotSetKpGroupException(entityTypeMessages);
        }
    }

    private void SendRVResult(StringBuilder stringBuilder, String groupId, CheckResultLevel checkResultLevel) {
        //            静态对象Antagonize中包含了以群号为key的EntityAntagonize对象，如果包含的话，那么就说明上一次对抗已经发起了，这次直接给结果
        EntityAntagonize entityAntagonize = MessagesAntagonize.ANTAGONIZE.get(groupId);
        EntityAntagonize thisEntityAntagonize = checkResultLevel.getAntagonize();
        Sender.sender(entityTypeMessages, stringBuilder.toString());
        checkAntagonize(entityTypeMessages, thisEntityAntagonize, entityAntagonize, groupId);
        MessagesAntagonize.ANTAGONIZE.remove(groupId);
        entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, GetMessagesProperties.entitySystemProperties.getAntagonizeOver());
    }

    /**
     * 房规多次骰点
     */
    public void ral() {
        String tag = MessagesTag.TAG_RAL;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        try {
            ArrayList<Integer> rollsList = initRalAndRcl(msg);
            EntityHistory entityHistory = new EntityHistory("0");
            rollsList = (ArrayList<Integer>) rollsList.stream().parallel().map(s -> new MakeRal(entityTypeMessages, msg.split(" ")[0]).call()).collect(Collectors.toList());
            updateHistory(entityHistory, rollsList);
            formatRxlAndSend(entityHistory);
        } catch (ManyRollsFormatException | ManyRollsTimesTooMoreException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 规则书多次骰点
     */
    public void rcl() {
        String tag = MessagesTag.TAG_RCL;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        try {
            ArrayList<Integer> rollsList = initRalAndRcl(msg);
            EntityHistory entityHistory = new EntityHistory("0");

            rollsList = (ArrayList<Integer>) rollsList.stream().parallel().map(s -> new MakeRcl(entityTypeMessages, msg.split(" ")[0]).call()).collect(Collectors.toList());
            updateHistory(entityHistory, rollsList);
            formatRxlAndSend(entityHistory);
        } catch (ManyRollsFormatException | ManyRollsTimesTooMoreException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * @param msg      输入信息，可能包含骰点表达式、技能等
     * @param ruleBook 是否使用规则书
     * @return 包装后的骰点结果字符串
     */
    private String check(String msg, Boolean ruleBook) throws ManyRollsTimesTooMoreException, RollCantInZeroException {
        int timesNum = 1;
        Matcher timesFind = times.matcher(msg);
        if (timesFind.find() && isNumeric(timesFind.group(1).replace("#", ""))) {
            timesNum = Integer.parseInt(timesFind.group(1).replace("#", ""));
            msg = msg.replaceFirst("[0-9]+#", "");
        }
        StringBuilder result = new StringBuilder();
        for (int i = 1; i <= timesNum; i++) {
            EntityNickAndRandomAndSkill entityNickAndRandomAndSkill = getNickAndRandomAndSkill(entityTypeMessages, msg);
            CheckResultLevel checkResultLevel = new CheckResultLevel(entityNickAndRandomAndSkill.getRandom(), entityNickAndRandomAndSkill.getSkill(), ruleBook);
            String nick;
            if (msg.contains(":")) {
                nick = msg.split(":")[0];
                msg = msg.split(":")[1];
            } else {
                nick = entityNickAndRandomAndSkill.getNick();
            }
            result.append(makeNickToSender(nick)).append("进行").append(msg).append("鉴定: D100=").append(entityNickAndRandomAndSkill.getRandom()).append("/").append(entityNickAndRandomAndSkill.getSkill()).append(checkResultLevel.getLevelResultStr(entityTypeMessages.getFromGroupString()));
            checkEn(checkResultLevel.getLevel(), msg, entityTypeMessages.getFromQqString(), entityTypeMessages.getFromGroupString());
            changeHistory(entityTypeMessages.getFromQqString()).update(checkResultLevel.getLevelAndRandom());
            if (i != timesNum) {
                result.append("\n");
            }
        }
        return result.toString();
    }

    /**
     * 根据本次和上次对抗结果，分别对比成功等级->骰点大小->技能上限。返回包装后的对抗结果
     *
     * @param entityTypeMessages 包装信息类，包含发送消息用的方法
     * @param thisAntagonize     这次对抗的骰点结果
     * @param lastAntagonize     已存储的对抗骰点结果
     * @param groupId            群号
     */
    private void checkAntagonize(EntityTypeMessages entityTypeMessages, EntityAntagonize thisAntagonize, EntityAntagonize lastAntagonize, String groupId) {
        int successMinLevel = 2;
        if (lastAntagonize.getLevel() > thisAntagonize.getLevel()) {
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, GetMessagesProperties.entitySystemProperties.getAntagonizeFirstSuccess());
        } else if (lastAntagonize.getLevel() == thisAntagonize.getLevel()) {
            if (lastAntagonize.getLevel() < successMinLevel && thisAntagonize.getLevel() < successMinLevel) {
                entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, GetMessagesProperties.entitySystemProperties.getAntagonizeAllFailed());
            } else if (lastAntagonize.getRandom() < thisAntagonize.getRandom()) {
                entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, GetMessagesProperties.entitySystemProperties.getAntagonizeFirstSuccess());
            } else if (lastAntagonize.getSkill() > thisAntagonize.getSkill()) {
                entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, GetMessagesProperties.entitySystemProperties.getAntagonizeFirstSuccess());
            } else if (lastAntagonize.getSkill() == thisAntagonize.getSkill()) {
                entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, GetMessagesProperties.entitySystemProperties.getAntagonizeDraw());
            }
        } else {
            entityTypeMessages.getMsgSender().SENDER.sendGroupMsg(groupId, GetMessagesProperties.entitySystemProperties.getAntagonizeSecondSuccess());
        }
    }

    /**
     * @param msg 检查骰点次数是否超过限制
     * @throws ManyRollsTimesTooMoreException 骰点次数太多
     * @throws ManyRollsFormatException       骰点表达式不合规范
     */
    private void checkManyRollsError(String msg) throws ManyRollsTimesTooMoreException, ManyRollsFormatException {
        int numParams = 2;
        int maxTimes = 1000;
        if (!msg.contains(MessagesSystem.SPACE) || msg.split(MessagesSystem.SPACE).length != numParams || !isNumeric(msg.split(MessagesSystem.SPACE)[0]) || !isNumeric(msg.split(MessagesSystem.SPACE)[1])) {
            throw new ManyRollsFormatException(entityTypeMessages);
        }

        if (Integer.parseInt(msg.split(MessagesSystem.SPACE)[1]) > maxTimes) {
            throw new ManyRollsTimesTooMoreException(entityTypeMessages);
        }
    }

    /**
     * 等待多线程骰点的结果被标记位isDone后，将结果更新到一个临时的骰点信息中
     *
     * @param entityHistory 临时的骰点信息，用于计算本次的各种成功次数
     * @param results       多线程骰点的对象列表
     */
    private void updateHistory(EntityHistory entityHistory, ArrayList<Integer> results) {
        for (int result : results) {
            entityHistory.update(result);
        }
    }

    /**
     * 将临时骰点信息包装成回复语发送
     *
     * @param entityHistory 临时的骰点信息，用于计算本次的各种成功次数
     */
    private void formatRxlAndSend(EntityHistory entityHistory) {
        String stringBuilder = "大成功:\t" +
                entityHistory.getCriticalSuccess() +
                "次" +
                "\n" +
                "极难成功:\t" +
                entityHistory.getExtremeSuccess() +
                "次" +
                "\n" +
                "困难成功:\t" +
                entityHistory.getHardSuccess() +
                "次" +
                "\n" +
                "成功:\t" +
                entityHistory.getSuccess() +
                "次" +
                "\n" +
                "失败:\t" +
                entityHistory.getFailure() +
                "次" +
                "\n" +
                "大失败:\t" +
                entityHistory.getFumble() +
                "次";
        Sender.sender(entityTypeMessages, stringBuilder);
    }

    private String makeVforGroupId(String msg, CheckResultLevel checkResultLevel) {
        checkEn(checkResultLevel.getLevel(), msg, entityTypeMessages.getFromQqString(), entityTypeMessages.getFromGroupString());
        changeHistory(entityTypeMessages.getFromQqString()).update(checkResultLevel.getLevelAndRandom());
        String groupId;
        if (entityTypeMessages.getFromGroupString().equals(defaultGroupId)) {
            try {
                groupId = Kp.getKpGroup(entityTypeMessages);
                Sender.sender(entityTypeMessages, "本次对抗将用于群" + makeGroupNickToSender(GetNickName.getGroupName(entityTypeMessages.getMsgSender(), String.valueOf(groupId))) + "(" + groupId + ")");
            } catch (NotSetKpGroupException e) {
                log.error(e.getMessage(), e);
                groupId = "0";
            }
        } else {
            groupId = entityTypeMessages.getFromGroupString();
        }
        return groupId;
    }

    private ArrayList<Integer> initRalAndRcl(String msg) throws ManyRollsFormatException, ManyRollsTimesTooMoreException {
        checkManyRollsError(msg);

        ArrayList<Integer> rollsList = new ArrayList<>();
        for (int i = 0; i < Integer.parseInt(msg.split(MessagesSystem.SPACE)[1]); i++) {
            rollsList.add(0);
        }
        return rollsList;
    }

    private void checkSkillInput(EntityNickAndRandomAndSkill entityNickAndRandomAndSkill) throws NotFoundSkillException {
        if (entityNickAndRandomAndSkill.getSkill() == 0) {
            throw new NotFoundSkillException(entityTypeMessages);
        }
    }

}
