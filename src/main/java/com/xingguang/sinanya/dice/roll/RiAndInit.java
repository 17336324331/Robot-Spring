package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CantInPrivateException;
import com.xingguang.sinanya.exceptions.InitSetFormatException;
import com.xingguang.sinanya.system.MessagesInit;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.checkdata.CheckCantInPrivate;
import com.xingguang.sinanya.tools.checkdata.CheckIsNumbers;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.Calculator;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;
import static java.lang.Math.ceil;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: DND先攻骰掷及列表
 */
public class RiAndInit implements MakeNickToSender {
    private static Pattern getName = Pattern.compile("\\[(.*)]");
    private static Pattern numAndName = Pattern.compile("([+*/-]?\\d+)([^\\d].+)");
    private static Pattern plus = Pattern.compile("([+*/\\-]\\d)");

    private Pattern nickNameRegex = Pattern.compile("([\\u4e00-\\u9fa5]+)");

    private EntityTypeMessages entityTypeMessages;

    public RiAndInit(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 根据value从大到小排序先攻列表
     *
     * @param map 先攻列表，key为骰点人+骰点过程，value为骰点结果
     * @return 排序后的map
     */
    private static HashMap<String, String> sortHashMap(HashMap<String, String> map) {
        //從HashMap中恢復entry集合，得到全部的鍵值對集合
        Set<Map.Entry<String, String>> entey = map.entrySet();
        //將Set集合轉為List集合，為了實用工具類的排序方法
        List<Map.Entry<String, String>> list = new ArrayList<>(entey);
        //使用Collections工具類對list進行排序
        list.sort((o1, o2) -> {
            //按照age倒敘排列
            String[] o1Value = o1.getValue().split("=");
            String[] o2Value = o2.getValue().split("=");
            return Integer.parseInt(o2Value[o2Value.length - 1].trim()) - Integer.parseInt(o1Value[o1Value.length - 1].trim());
        });
        //創建一個HashMap的子類LinkedHashMap集合
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        //將list中的數據存入LinkedHashMap中
        for (Map.Entry<String, String> entry : list) {
            linkedHashMap.put(entry.getKey(), entry.getValue());
        }
        return linkedHashMap;
    }

    /**
     * 先攻骰掷，这里支持加值和减值计算，并且如果信息中包含汉字，则和对方昵称放在一起（通常kp使用）
     * 返回结果后还会把结果插入先攻列表记录，用于打印先攻列表
     */
    public void ri() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_RI;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        String msgBefore = msg;
        int result = 0;
        boolean add = false;
        int random = RandomInt.random(1, 20);
        String nick = GetNickName.getNickName(entityTypeMessages);

        Matcher mNumAndName = numAndName.matcher(msg);
        while (mNumAndName.find()) {
            msg = mNumAndName.group(1);
            nick = mNumAndName.group(2).trim() + "(" + GetNickName.getNickName(entityTypeMessages) + ")";
        }

        Matcher mPlus = plus.matcher(msg);
        while (mPlus.find()) {
            result = (int) ceil(Calculator.conversion(random + msg));
            add = msg.contains("+");
        }

        if (!msg.contains("-") && !msg.equals(MessagesSystem.NONE) && msg.matches("\\d+")) {
            result = random + Integer.parseInt(msg);
            add = true;
        }

        Matcher mNickNameRegex = nickNameRegex.matcher(msg);
        if (result == 0) {
            result = random;
            if (mNickNameRegex.find()) {
                nick = msg;
                msg = MessagesSystem.NONE;
            } else {
                nick = GetNickName.getNickName(entityTypeMessages);
            }
        }

        nick = makeNickToSender(nick);
        String tagInitText = "的先攻骰掷,掷出了: D20=\t";
        if (msg.equals(MessagesSystem.NONE)) {
            Sender.sender(entityTypeMessages, nick + tagInitText + result);
        } else {
            if (add) {
                Sender.sender(entityTypeMessages, nick + tagInitText + random + "+" + msg.replace("+", "") + "=" + result);
                msgBefore = random + "+" + msg.replace("+", "").replace("-", "") + "=";
            } else {
                Sender.sender(entityTypeMessages, nick + tagInitText + random + msg + "=" + result);
                msgBefore = random + "-" + msg.replace("+", "").replace("-", "") + "=";
            }
        }
        if (MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            HashMap<String, String> sort = sortHashMap(MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()));
            for (Map.Entry<String, String> mapEntry2 : sort.entrySet()) {
                Matcher matcher = getName.matcher(mapEntry2.getKey());
                if (matcher.find() && matcher.group(1).equals(nick)) {
                    MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()).remove(mapEntry2.getKey());
                }
            }
        }
        Matcher mMsgBefore = nickNameRegex.matcher(msgBefore);
        if (MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            Map<String, String> riList = MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString());
            MessagesInit.INIT_LIST.put(entityTypeMessages.getFromGroupString(), (HashMap<String, String>) putInitList(nick, riList, mMsgBefore, msgBefore, result));
        } else {
            Map<String, String> riList = new HashMap<>(30);
            MessagesInit.INIT_LIST.put(entityTypeMessages.getFromGroupString(), (HashMap<String, String>) putInitList(nick, riList, mMsgBefore, msgBefore, result));
        }
    }

    /**
     * 打印先攻列表，根据群号确定列表后，根据骰点最终结果排序后顺序打印
     */
    public void init() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        StringBuilder stringBuffer = new StringBuilder();
        if (!MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getDndInitIsEmtpy());
            return;
        }
        stringBuffer.append("先攻列表为:\n");
        int i = 1;
        if (MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            HashMap<String, String> sort = sortHashMap(MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()));
            for (Map.Entry<String, String> mapEntry2 : sort.entrySet()) {
                stringBuffer.append(i)
                        .append(".\t")
                        .append(mapEntry2.getKey())
                        .append(mapEntry2.getValue())
                        .append("\n");
                i++;
            }
        }

        Sender.sender(entityTypeMessages, stringBuffer.substring(0, stringBuffer.length() - 1));
    }

    /**
     * 打印先攻列表，根据群号确定列表后，根据骰点最终结果排序后顺序打印
     */
    public void rm() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_INIT_RM;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        StringBuilder stringBuffer = new StringBuilder();
        if (!MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getDndInitIsEmtpy());
            return;
        }
        stringBuffer.append("已删除:\n");
        int i = 1;
        if (MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            HashMap<String, String> sort = sortHashMap(MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()));
            for (Map.Entry<String, String> mapEntry2 : sort.entrySet()) {
                Matcher matcher = getName.matcher(mapEntry2.getKey());
                if (matcher.find() && matcher.group(1).toLowerCase().equals(msg)) {
                    stringBuffer.append(i).append(".\t").append(mapEntry2.getKey()).append(mapEntry2.getValue());
                    MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()).remove(mapEntry2.getKey());
                }
                i++;
            }
        }

        Sender.sender(entityTypeMessages, stringBuffer.toString());
    }

    /**
     * 打印先攻列表，根据群号确定列表后，根据骰点最终结果排序后顺序打印
     */
    public void set() throws CantInPrivateException, InitSetFormatException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        String tag = MessagesTag.TAG_INIT_SET;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 2));
        if (!msg.contains(MessagesSystem.SPACE) || msg.split(MessagesSystem.SPACE)[1].equals(MessagesSystem.NONE) || !CheckIsNumbers.isNumeric(msg.split(MessagesSystem.SPACE)[1])) {
            throw new InitSetFormatException(entityTypeMessages);
        }
        String key = msg.split(MessagesSystem.SPACE)[0];
        String value = msg.split(MessagesSystem.SPACE)[1];
        if (MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            HashMap<String, String> sort = sortHashMap(MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()));
            for (Map.Entry<String, String> mapEntry2 : sort.entrySet()) {
                Matcher matcher = getName.matcher(mapEntry2.getKey());
                if (matcher.find() && matcher.group(1).toLowerCase().equals(key)) {
                    MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()).remove(mapEntry2.getKey());
                }
            }
        }

        if (MessagesInit.INIT_LIST.containsKey(entityTypeMessages.getFromGroupString())) {
            MessagesInit.INIT_LIST.get(entityTypeMessages.getFromGroupString()).put(makeNickToSender(key) + "由" + entityTypeMessages.getFromQq() + "指定\t", value);
        } else {
            HashMap<String, String> riList = new HashMap<>(30);
            riList.put(makeNickToSender(key) + "由" + entityTypeMessages.getFromQq() + "指定\t", value);
            MessagesInit.INIT_LIST.put(entityTypeMessages.getFromGroupString(), riList);
        }

        Sender.sender(entityTypeMessages, "已添加:\n" + makeNickToSender(key) + "的记录进入先攻列表，并强制设定其先攻结果为: " + value);
    }

    /**
     * 清空先攻列表
     */
    public void clr() throws CantInPrivateException {
        CheckCantInPrivate.checkCantInPrivate(entityTypeMessages);
        MessagesInit.INIT_LIST.remove(entityTypeMessages.getFromGroupString());
        Sender.sender(entityTypeMessages, GetMessagesProperties.entitySystemProperties.getClrDndInit());
    }

    private Map<String, String> putInitList(String nick, Map<String, String> initList, Matcher mMsgBefore, String msgBefore, int result) {
        String tagD20 = ": D20=";
        if (mMsgBefore.find()) {
            initList.put(nick, tagD20 + "\t" + result);
        } else {
            initList.put(nick, tagD20 + msgBefore + "\t" + result);
        }
        return initList;
    }
}
