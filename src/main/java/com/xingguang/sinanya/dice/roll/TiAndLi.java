package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesBanList;
import com.xingguang.sinanya.tools.getinfo.GetMessagesProperties;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.tools.makedata.Sender;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 临时疯狂与总结疯狂
 */
public class TiAndLi {

    String tagD10 = "1D10=";
    private EntityTypeMessages entityTypeMessages;

    public TiAndLi(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * 临时疯狂
     */
    public void ti() {
        StringBuilder stringBuilder = new StringBuilder();
        int indexTi = RandomInt.random(0, com.xingguang.sinanya.system.TiAndLi.TI.size() - 1);
        String strTi = com.xingguang.sinanya.system.TiAndLi.TI.get(indexTi);
        stringBuilder
                .append("[")
                .append(GetNickName.getNickName(entityTypeMessages))
                .append("]")
                .append("的疯狂发作-临时症状:\n")
                .append(tagD10)
                .append(indexTi + 1)
                .append("\n");
        symptomFormatAndSend(stringBuilder, strTi, indexTi);
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    /**
     * 总结疯狂
     */
    public void li() {
        StringBuilder stringBuilder = new StringBuilder();
        int indexLi = RandomInt.random(0, com.xingguang.sinanya.system.TiAndLi.LI.size() - 1);
        String strLi = com.xingguang.sinanya.system.TiAndLi.LI.get(indexLi);
        stringBuilder
                .append("[")
                .append(GetNickName.getNickName(entityTypeMessages))
                .append("]")
                .append("的疯狂发作-总结症状:\n")
                .append(tagD10)
                .append(indexLi + 1)
                .append("\n");

        symptomFormatAndSend(stringBuilder, strLi, indexLi);
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }


    /**
     * 将疯狂症状格式化后返回
     *
     * @param stringBuilder 传入的StringBuilder对象，由于java特性，这个对象在append后无需return
     * @param strSymptom    疯狂症状字符串，因为临时和总结的字符串不同，这里当做参数传入
     * @param index         具体疯狂的下标，9为狂躁症，8为恐惧症需要做额外信息补充
     */
    private void symptomFormatAndSend(StringBuilder stringBuilder, String strSymptom, int index) {
        switch (index) {
            case 9:
                int indexPanic = RandomInt.random(0, com.xingguang.sinanya.system.TiAndLi.STR_PANIC.size() - 1);
                strSymptom = String.format(strSymptom, tagD10 + RandomInt.random(1, 10), "1D" + com.xingguang.sinanya.system.TiAndLi.STR_PANIC.size() + "=" + (indexPanic + 1), com.xingguang.sinanya.system.TiAndLi.STR_PANIC.get(indexPanic));
                break;
            case 8:
                int indexFear = RandomInt.random(0, com.xingguang.sinanya.system.TiAndLi.STR_FEAR.size() - 1);
                strSymptom = String.format(strSymptom, tagD10 + RandomInt.random(1, 10), "1D" + com.xingguang.sinanya.system.TiAndLi.STR_FEAR.size() + "=" + (indexFear + 1), com.xingguang.sinanya.system.TiAndLi.STR_FEAR.get(indexFear));
                break;
            default:
                strSymptom = String.format(strSymptom, tagD10 + RandomInt.random(1, 10));
                break;
        }
        stringBuilder.append(strSymptom);
        boolean groupHasSwitch = MessagesBanList.groupSwitchHashMap.containsKey(entityTypeMessages.getFromGroup());
        if (!groupHasSwitch || !MessagesBanList.groupSwitchHashMap.get(entityTypeMessages.getFromGroup()).isSimple()) {
            stringBuilder.append(GetMessagesProperties.entitySystemProperties.getSymptom());
        }

    }
}
