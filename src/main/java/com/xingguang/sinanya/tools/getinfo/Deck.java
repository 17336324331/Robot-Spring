package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.entity.EntityDeckBack;
import com.xingguang.sinanya.entity.EntityDeckList;
import com.xingguang.sinanya.entity.EntityStrManyRolls;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.ManyRollsTimesTooMoreException;
import com.xingguang.sinanya.exceptions.RollCantInZeroException;
import com.xingguang.sinanya.tools.makedata.GetRollResultAndStr;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.db.deck.SelectDeckList;
import com.xingguang.utils.StringUtil;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.GetMessagesProperties.entitySystemProperties;

/**
 * @author SitaNya
 * 日期: 2019-08-25
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明:
 */
public class Deck {

    private static final Logger log = LoggerFactory.getLogger(Deck.class.getName());

    static Pattern functionString = Pattern.compile("\\{\\$(.*?)\\}");
    static Pattern functionStringBack = Pattern.compile("\\{%(.*?)\\}");
    static Pattern functionDice = Pattern.compile("\\[([dk\\d+*/\\-]+)\\]");
    static HashMap<EntityDeckBack, ArrayList<Integer>> deckBack = new HashMap<>();
    private static Pattern plus = Pattern.compile("[+*/\\-]");

    /**
     * 尝试从指定的配置文件中获取类型
     *
     * @param deckType 指定的类型
     * @return 获取的语句，或报错骰主未添加此类型
     */
    private static HashMap<String, ArrayList<String>> getDeckMap(String deckType) {
        File deckTypeFile = new File(entitySystemProperties.getSystemDir() + File.separator + "deck" + File.separator + deckType);

        System.out.println("=====>"+entitySystemProperties.getSystemDir() + File.separator + "deck" + File.separator + deckType);
        System.out.println("AbsolutePath()===>"+deckTypeFile.getAbsolutePath());
//        File deckTypeFile = new File(deckType);
        HashMap<String, ArrayList<String>> deckList = new HashMap<>();
        System.out.println("exists:"+deckTypeFile.exists() );
        System.out.println("files:"+deckTypeFile.isFile());
        System.out.println("ret:"+(!deckTypeFile.exists() || !deckTypeFile.isFile()));
        if (!deckTypeFile.exists() || !deckTypeFile.isFile()) {
            return deckList;
        }
        Yaml yaml = new Yaml();
        ArrayList<String> valueList;
        try {
            // 加载配置文件
            Map map = yaml.load(new FileInputStream(deckTypeFile));
            //System.out.println(map.toString());
            for (Object key : map.keySet()) {
                if (!key.equals("name") && !key.equals("command") && !key.equals("author") && !key.equals("version") && !key.equals("desc")) {
                    String value = map.get(key).toString();
                    valueList = new ArrayList<>(Arrays.asList(value.substring(1, value.length() - 1).split(",")));
                    deckList.put(String.valueOf(key), valueList);
                }
            }
        } catch (IOException e) {
//            CQ.logError(e.getMessage(),e);
        }
        return deckList;
    }

    /**
     * 尝试从指定的配置文件中获取类型
     *
     * @param deckType 指定的类型
     * @return 获取的语句，或报错骰主未添加此类型
     */
    public static HashMap<String, ArrayList<String>> getDeckMapTest(String deckType) {
        File deckTypeFile = new File("src/main/resources/" + deckType);
//        File deckTypeFile = new File(deckType);
        HashMap<String, ArrayList<String>> deckList = new HashMap<>();
        if (!deckTypeFile.exists() || !deckTypeFile.isFile()) {
            return deckList;
        }
        Yaml yaml = new Yaml();
        ArrayList<String> valueList;
        try {
            // 加载配置文件
            Map map = yaml.load(new FileInputStream(deckTypeFile));
            for (Object key : map.keySet()) {
                if (!key.equals("name") && !key.equals("command") && !key.equals("author") && !key.equals("version") && !key.equals("desc")) {
                    ArrayList<String> value = (ArrayList<String>) map.get(key);
                    deckList.put(String.valueOf(key), value);
                }
            }
        } catch (IOException e) {
//            coolQ.logError(e.getMessage(),e);
        }
        return deckList;
    }

    /**
     * 尝试从指定的配置文件中获取类型
     *
     * @return 获取的语句，或报错骰主未添加此类型
     */
    public static HashMap<String, ArrayList<String>> getDeckHelp() {
        File deckTypeDir = new File(entitySystemProperties.getSystemDir() + File.separator + "deck");
//        File deckTypeDir = new File("src/main/resources/");
        HashMap<String, ArrayList<String>> deckList = new HashMap<>();
        if (deckTypeDir.exists() && deckTypeDir.isDirectory()) {
            Yaml yaml = new Yaml();
            for (File file : deckTypeDir.listFiles()) {
                ArrayList<String> keyList = new ArrayList<>();
                try {
//                     加载配置文件
                    Map map = yaml.load(new FileInputStream(file));
//                    Map map = yaml.load(new FileInputStream(new File("src/main/resources/test.yaml")));
                    String[] includes = new String[]{};
                    if (map.containsKey("includes")) {
                        String tmp = map.get("includes").toString().substring(1, map.get("includes").toString().length() - 1).replaceAll(" ", "");
                        includes = tmp.split(",");
                    }
                    for (Object key : map.keySet()) {
                        String keyValue = String.valueOf(key);
                        if (!"name".equals(keyValue) && !"command".equals(keyValue) && !"author".equals(keyValue) && !"version".equals(keyValue) && !"desc".equals(keyValue) && !"default".equals(keyValue) && !"includes".equals(keyValue)) {
                            for (String include : includes) {
                                if (include.equals(keyValue)) {
                                    keyList.add(map.get("command") + " " + keyValue);
                                }
                            }
                        }
                    }
                    if (map.containsKey("default")) {
                        keyList.add(String.valueOf(map.get("command")));
                    }
                    deckList.put(String.valueOf(map.get("name")), keyList);
                } catch (IOException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return deckList;
    }

    public static String getDeck(EntityTypeMessages entityTypeMessages, String deckType, String type) {
        long qqId = entityTypeMessages.getFromQq();
        HashMap<String, ArrayList<String>> deck = getDeckMap(deckType);
        ArrayList<String> randomList = new ArrayList<>();
        randomList.add("未找到");
        if (deck.containsKey(type)) {
            randomList = deck.get(type);
//            CQ.logDebug("deck", deck.toString());
        }
        int random = RandomInt.random(0, randomList.size() - 1);
        String result = randomList.get(random);
        Matcher functionStringFind = functionString.matcher(result);
        while (functionStringFind.find()) {
            String tmp = functionStringFind.group(1);
            result = result.replaceFirst(functionString.toString(), getDeck(entityTypeMessages, deckType, tmp));
        }
        Matcher functionStringBackFind = functionStringBack.matcher(result);
        while (functionStringBackFind.find()) {
            String tmp = functionStringBackFind.group(1);
            randomList = deck.get(tmp);
            EntityDeckBack entityDeckBack = new EntityDeckBack(qqId, deckType, tmp);
            if (deckBack.containsKey(entityDeckBack) && deckBack.get(entityDeckBack).size() != randomList.size()) {
                ArrayList<Integer> backList = deckBack.get(entityDeckBack);
                do {
                    random = RandomInt.random(0, randomList.size() - 1);
                } while (backList.contains(random));
                result = result.replaceFirst(functionStringBack.toString(), randomList.get(random));
                backList.add(random);
                deckBack.put(entityDeckBack, backList);
            } else if (!deckBack.containsKey(entityDeckBack)) {
                ArrayList<Integer> backList = new ArrayList<>();
                random = RandomInt.random(0, randomList.size() - 1);
                result = result.replaceFirst(functionStringBack.toString(), randomList.get(random));
                backList.add(random);
                deckBack.put(entityDeckBack, backList);
            } else {
//                sender(entityTypeMessages, "牌堆已抽干，重新生成");
//                System.out.println("牌堆已抽干，重新生成");
                deckBack.remove(entityDeckBack);
                ArrayList<Integer> backList = new ArrayList<>();
                random = RandomInt.random(0, randomList.size() - 1);
                result = result.replaceFirst(functionStringBack.toString(), randomList.get(random));
                backList.add(random);
                deckBack.put(entityDeckBack, backList);
            }
        }
        Matcher functionDiceFind = functionDice.matcher(result);
        while (functionDiceFind.find()) {
            String tmp = functionDiceFind.group(1);
            String[] everyFunction = tmp.split(plus.toString());
            try {
                EntityStrManyRolls entityStrManyRolls = GetRollResultAndStr.getResFunctionAndResultInt(entityTypeMessages, tmp, everyFunction);
                result = result.replaceFirst(functionDice.toString(), String.valueOf(entityStrManyRolls.getResult()));
            } catch (ManyRollsTimesTooMoreException | RollCantInZeroException e) {
                log.error(e.getMessage(), e);
            }
        }

        return result;
    }

    public static ArrayList<EntityDeckList> getHasDeckList() {
        ArrayList<EntityDeckList> hasDeckList = new ArrayList();
        File deckTypeDir = new File(entitySystemProperties.getSystemDir() + File.separator + "deck");
//        File deckTypeDir = new File("src/main/resources/");
        if (deckTypeDir.isDirectory()) {
            for (File deckType : deckTypeDir.listFiles()) {
                if (deckType.isFile()) {
                    EntityDeckList entityDeckList = new EntityDeckList();
                    String deckName;
                    Yaml yaml = new Yaml();
                    try {
                        // 加载配置文件
                        Map map = yaml.load(new FileInputStream(deckType));
                        deckName = String.valueOf(map.getOrDefault("name", "未找到"));
                        entityDeckList.setName(deckName);
                        entityDeckList.setCommand(deckType.getName());
                        entityDeckList.setAuthor(String.valueOf(map.get("author")));
                        entityDeckList.setVersion((int) map.get("version"));
                        entityDeckList.setDesc(String.valueOf(map.get("desc")));
                        hasDeckList.add(entityDeckList);
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
        return hasDeckList;
    }


    /**
     * 获取全部的deck列表，包括名字、分支指令（已拥有的不显示），每次删除或新增后会刷新
     *
     * @return
     */
    public static ArrayList<EntityDeckList> getInternetDeck() {
        ArrayList<EntityDeckList> result = new ArrayList<>();
        ArrayList<EntityDeckList> hasDeckList = getHasDeckList();
        ArrayList<EntityDeckList> internetDeckList = new SelectDeckList().selectDeckList();
        for (EntityDeckList entityDeckList : internetDeckList) {
            if (!hasDeckList.contains(entityDeckList)) {
                result.add(entityDeckList);
            }
        }
        return result;
    }
}
