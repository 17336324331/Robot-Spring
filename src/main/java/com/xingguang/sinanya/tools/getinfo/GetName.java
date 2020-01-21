package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.tools.makedata.RandomInt;

import java.util.ArrayList;

import static com.xingguang.sinanya.db.rules.Rule.selectEnglishName;
import static com.xingguang.sinanya.db.rules.Rule.selectRandom;

/**
 * @author SitaNya
 * 日期: 2019-06-14
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明: 获取人名，不是指玩家，而是NPC名称
 */
public class GetName {

    private GetName() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @return 中文人名
     */
    public static String getChineseName() {
        return selectRandom("coc", "chineseSurname", "text") + selectRandom("coc", "chineseFirstName", "text");
    }

    /**
     * @return 英文人名（含有中文翻译）
     */
    public static String getEnglishName() {
        return selectEnglishName();
    }

    /**
     * @return 日本人名（中间带有空格）
     */
    public static String getJapaneseName() {
        return selectRandom("coc", "japaneseSurname", "text") + " " + selectRandom("coc", "japaneseFirstName", "text");
    }

    /**
     * @return 随机人名
     */
    public static String getRandomName() {
        int random = RandomInt.random(1, 3);
        switch (random) {
            case 1:
                return getChineseName();
            case 3:
                return getJapaneseName();
            default:
                return getEnglishName();
        }
    }

    /**
     * 随机返回字符串列表中的一个元素，其实和GetRandomList接口的方法一样，但毕竟这里是静态的
     *
     * @param list 输入的字符串列表
     * @return 随机返回字符串列表中的一个元素
     */
    private static String getFromList(ArrayList<String> list) {
        return list.get(RandomInt.random(0, list.size() - 1));
    }
}
