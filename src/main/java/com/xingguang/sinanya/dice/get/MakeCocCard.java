package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.dice.get.imal.MakeCard;
import com.xingguang.sinanya.entity.EntityCoc6CardInfo;
import com.xingguang.sinanya.entity.EntityCoc7CardExcel;
import com.xingguang.sinanya.entity.EntityCoc7CardInfo;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.CardTooMoreException;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: COC车卡
 */
public class MakeCocCard implements MakeCard, MakeNickToSender {

    private EntityTypeMessages entityTypeMessages;

    public MakeCocCard(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * COC7版详细车卡
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    public void coc7d() {
        String nick = GetNickName.getNickName(entityTypeMessages);

        EntityCoc7CardInfo cocCardInfo = new EntityCoc7CardInfo();

        String stringBuilder = makeNickToSender(nick) +
                "的人物作成:" +
                "\n" +
                "力量STR=3D6*5=" +
                cocCardInfo.getStr() + "/" +
                cocCardInfo.getStr() / 2 + "/" +
                cocCardInfo.getStr() / 5 + "\n" +
                "体质CON=3D6*5=" +
                cocCardInfo.getCon() + "/" +
                cocCardInfo.getCon() / 2 + "/" +
                cocCardInfo.getCon() / 5 + "\n" +
                "体型SIZ=(2D6+6)*5=" +
                cocCardInfo.getSiz() + "/" +
                cocCardInfo.getSiz() / 2 + "/" +
                cocCardInfo.getSiz() / 5 + "\n" +
                "敏捷DEX=3D6*5=" +
                cocCardInfo.getDex() + "/" +
                cocCardInfo.getDex() / 2 + "/" +
                cocCardInfo.getDex() / 5 + "\n" +
                "外貌APP=3D6*5=" +
                cocCardInfo.getApp() + "/" +
                cocCardInfo.getApp() / 2 + "/" +
                cocCardInfo.getApp() / 5 + "\n" +
                "智力INT=(2D6+6)*5=" +
                cocCardInfo.getInt() + "/" +
                cocCardInfo.getInt() / 2 + "/" +
                cocCardInfo.getInt() / 5 + "\n" +
                "意志POW=3D6*5=" +
                cocCardInfo.getPow() + "/" +
                cocCardInfo.getPow() / 2 + "/" +
                cocCardInfo.getPow() / 5 + "\n" +
                "教育EDU=(2D6+6)*5=" +
                cocCardInfo.getEdu() + "/" +
                cocCardInfo.getEdu() / 2 + "/" +
                cocCardInfo.getEdu() / 5 + "\n" +
                "幸运LUCK=3D6*5=" +
                cocCardInfo.getLuck() + "/" +
                cocCardInfo.getLuck() / 2 + "/" +
                cocCardInfo.getLuck() / 5 + "\n" + "\n" +
                "共计:\n" +
                "不带幸运为:\t" +
                cocCardInfo.getNotLuck() + "\t大约为 " + String.valueOf((cocCardInfo.getNotLuck() * 1.0 / 540) * 100).substring(0, 5) + "% 强度(越高越好)\n" +
                "带幸运为:\t" +
                cocCardInfo.getHasLuck() + "\t大约为 " + String.valueOf((cocCardInfo.getHasLuck() * 1.0 / 630) * 100).substring(0, 5) + "% 强度(越高越好)" +
                "\n" + "\n" +
                "生命值HP=(SIZ+CON)/10(向下取整)=" +
                cocCardInfo.getHp() +
                "\n" +
                "理智SAN=POW=" +
                cocCardInfo.getSan() +
                "\n" +
                "魔法值MP=POW/5=" +
                cocCardInfo.getMp() +
                "\n" +
                "伤害奖励DB=" +
                cocCardInfo.getDb() +
                "\n" +
                "体格BUILD=" +
                cocCardInfo.getBuild() +
                "\n" +
                "移动力MOV=" +
                cocCardInfo.getMov();
        Sender.sender(entityTypeMessages, stringBuilder);
    }

    /**
     * COC7版带导入车卡
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    public void coc7x() throws CardTooMoreException {
        String tag = MessagesTag.TAG_COC7X;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 11));
        msg = MakeMessages.deleteTag(msg, ".coc");


        String nick = GetNickName.getNickName(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(makeNickToSender(nick))
                .append("的7版带导入人物做成:\n");


        ArrayList<String> results = makeTimes(msg);

        ArrayList<EntityCoc7CardExcel> coc7CardExcels = (ArrayList<EntityCoc7CardExcel>) results.stream().parallel().map(s -> getCoc7CardInfo()).collect(Collectors.toList());
        ArrayList<String> infos = new ArrayList<>();
        ArrayList<String> excels = new ArrayList<>();
        int i = 1;
        for (EntityCoc7CardExcel coc7CardExcel : coc7CardExcels) {
            infos.add(coc7CardExcel.getInfo());
            excels.add(i + "-" + StringUtil.joiner(coc7CardExcel.getExcel(), "-"));
            i++;
        }
        stringBuilder.append(StringUtil.joiner(infos, "\n\n"));
        Sender.sender(entityTypeMessages, stringBuilder.toString() + "\n导入信息请查看私聊");
        entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), "将以下信息粘贴到此excel的《人物卡（骰点）》右侧的链接中。识别码039N");
        entityTypeMessages.getMsgSender().SENDER.sendPrivateMsg(entityTypeMessages.getFromQqString(), StringUtil.joiner(excels, "\n"));
    }

    /**
     * COC6版详细车卡
     */
    @SuppressWarnings("AlibabaMethodTooLong")
    public void coc6d() {
        String nick = GetNickName.getNickName(entityTypeMessages);

        EntityCoc6CardInfo cocCardInfo = new EntityCoc6CardInfo();

        String stringBuilder = makeNickToSender(nick) +
                "的人物作成:" +
                "\n" +
                "力量STR=3D6=" +
                cocCardInfo.getStr() + "/" +
                cocCardInfo.getStr() / 2 + "/" +
                cocCardInfo.getStr() / 5 + "\n" +
                "体质CON=3D6=" +
                cocCardInfo.getCon() + "/" +
                cocCardInfo.getCon() / 2 + "/" +
                cocCardInfo.getCon() / 5 + "\n" +
                "体型SIZ=2D6+6=" +
                cocCardInfo.getSiz() + "/" +
                cocCardInfo.getSiz() / 2 + "/" +
                cocCardInfo.getSiz() / 5 + "\n" +
                "敏捷DEX=3D6=" +
                cocCardInfo.getDex() + "/" +
                cocCardInfo.getDex() / 2 + "/" +
                cocCardInfo.getDex() / 5 + "\n" +
                "外貌APP=3D6=" +
                cocCardInfo.getApp() + "/" +
                cocCardInfo.getApp() / 2 + "/" +
                cocCardInfo.getApp() / 5 + "\n" +
                "智力INT=2D6+6=" +
                cocCardInfo.getInt() + "/" +
                cocCardInfo.getInt() / 2 + "/" +
                cocCardInfo.getInt() / 5 + "\n" +
                "意志POW=3D6=" +
                cocCardInfo.getPow() + "/" +
                cocCardInfo.getPow() / 2 + "/" +
                cocCardInfo.getPow() / 5 + "\n" +
                "教育EDU=3D6+3=" +
                cocCardInfo.getEdu() + "/" +
                cocCardInfo.getEdu() / 2 + "/" +
                cocCardInfo.getEdu() / 5 + "\n" +
                "共计:\n" +
                cocCardInfo.getNotLuck() + "为 " + String.valueOf((cocCardInfo.getNotLuck() * 1.0 / 147) * 100).substring(0, 5) + "% 强度(越高越好)\n" +
                "\n" + "\n" +
                "生命值HP=(SIZ+CON)/2(向上取整)=" +
                cocCardInfo.getHp() +
                "\n" +
                "理智SAN=POW*5=" +
                cocCardInfo.getSan() +
                "\n" +
                "灵感IDEA=INT*5=" +
                cocCardInfo.getIdea() +
                "\n" +
                "幸运LUCK=POW*5=" +
                cocCardInfo.getLuck() +
                "\n" +
                "知识KNOW=EDU*5=" +
                cocCardInfo.getKnow() +
                "\n" +
                "伤害奖励DB=" +
                cocCardInfo.getDb();
        Sender.sender(entityTypeMessages, stringBuilder);
    }

    /**
     * COC7版简易车卡，可以根据参数生成多张
     */
    public void coc7() throws CardTooMoreException {
        String tag = MessagesTag.TAG_COC7;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 11));
        msg = MakeMessages.deleteTag(msg, ".coc");

        String nick = GetNickName.getNickName(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(makeNickToSender(nick))
                .append("的7版人物做成:\n");

        ArrayList<String> results = makeTimes(msg);

        ArrayList<EntityCoc7CardExcel> coc7CardExcels = (ArrayList<EntityCoc7CardExcel>) results.stream().parallel().map(s -> getCoc7CardInfo()).collect(Collectors.toList());
        ArrayList<String> infos = new ArrayList<>();
        for (EntityCoc7CardExcel coc7CardExcel : coc7CardExcels) {
            infos.add(coc7CardExcel.getInfo());
        }
        stringBuilder.append(StringUtil.joiner(infos, "\n\n"));
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    /**
     * COC6版简易车卡，可以根据参数生成多张
     */
    public void coc6() {
        String tag = MessagesTag.TAG_COC6;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 11));

        int times = getTime(msg);

        String nick = GetNickName.getNickName(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(makeNickToSender(nick))
                .append("的6版人物做成:");

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            results.add("");
        }

        results = (ArrayList<String>) results.stream().parallel().map(s -> getCoc6CardInfo()).collect(Collectors.toList());
        for (String cocText : results) {
            stringBuilder.append("\n")
                    .append(cocText);
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());
    }

    private ArrayList<String> makeTimes(String msg) throws CardTooMoreException {
        int times = getTime(msg);

        if (times > 10) {
            throw new CardTooMoreException(entityTypeMessages);
        }
        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            results.add("");
        }
        return results;
    }

}
