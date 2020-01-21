package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.tools.imal.GetSkillNameFromMuiltRole;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;

/**
 * @author SitaNya
 * 日期: 2019-06-14
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:将传入的msg人物属性信息整理包装为HashMap格式
 */
public class GetMessagesToValue implements GetSkillNameFromMuiltRole {

    private static final Logger log = LoggerFactory.getLogger(GetMessagesToValue.class.getName());


    private GetMessagesToValue() {
        throw new IllegalStateException("Utility class");
    }


    /**
     * 将输入的属性值字符串转变为HashMap形式
     *
     * @param properties 属性值的HashMap列表，包含了所有技能和技能值。如果是新卡则所有技能都是默认值
     * @param msg        属性值字符串
     * @return 新生成的HashMap属性值列表
     */
    static HashMap<String, Integer> getMessagesToValue(HashMap<String, Integer> properties, String msg) {
        StringBuilder strSkillValue = new StringBuilder();
        StringBuilder strSkillName = new StringBuilder();
        int i = 0;
        while (i < msg.length()) {
            if (msg.charAt(i) == '|') {
                i++;
                continue;
            }
            int tmp = i;
            i = findSkillName(i, msg, strSkillName);
            i = findSkillValue(i, msg, strSkillValue);
            try {
                if (!strSkillValue.toString().equals(MessagesSystem.NONE)) {
                    String skillName = GetSkillNameFromMuiltRole.getSkillNameFromMuiltRole(strSkillName);
                    properties.put(MakeSkillName.makeSkillName(skillName), Integer.parseInt(strSkillValue.toString()));
                    strSkillName = new StringBuilder();
                    strSkillValue = new StringBuilder();
                }
            } catch (NumberFormatException e) {
                log.error(e.getMessage(), e);
            }
            if (tmp == i) {
                i++;
            }
        }
        return properties;
    }

    /**
     * 将输入的属性值字符串转变为HashMap形式
     *
     * @param msg 属性值字符串
     * @return 新生成的HashMap属性值列表
     */
    public static HashMap<String, Integer> getMessagesToValue(String msg) {
        HashMap<String, Integer> properties = new HashMap<>();
        return getMessagesToValue(properties, msg);
    }

    private static int findSkillName(int i, String msg, StringBuilder strSkillName) {
        while (i < msg.length() && !Character.isSpaceChar(msg.charAt(i)) &&
                !Character.isDigit(msg.charAt(i))) {
            strSkillName.append(msg.charAt(i));
            i++;
        }
        return i;
    }

    private static int findSkillValue(int i, String msg, StringBuilder strSkillValue) {
        while (i < msg.length() && Character.isDigit(msg.charAt(i))) {
            strSkillValue.append(msg.charAt(i));
            i++;
        }
        return i;
    }
}
