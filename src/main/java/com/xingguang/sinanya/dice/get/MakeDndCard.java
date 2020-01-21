package com.xingguang.sinanya.dice.get;

import com.xingguang.sinanya.dice.get.imal.MakeCard;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.MakeDndCardInfo;
import com.xingguang.sinanya.tools.makedata.MakeMessages;
import com.xingguang.sinanya.tools.makedata.Sender;
import com.xingguang.sinanya.dice.MakeNickToSender;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;

/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: DND车卡
 */
public class MakeDndCard implements MakeCard, MakeNickToSender {

    private EntityTypeMessages entityTypeMessages;

    public MakeDndCard(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    /**
     * DND简易车卡，可以根据参数生成多张
     */
    public void dnd() {
        String tag = MessagesTag.TAG_DND;
        String msg = MakeMessages.deleteTag(entityTypeMessages.getMsgGet().getMsg(), tag.substring(0, tag.length() - 11));

        int times = getTime(msg);

        String nick = GetNickName.getNickName(entityTypeMessages);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(makeNickToSender(nick))
                .append("的DND英雄做成:");

        ArrayList<String> results = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            results.add("");
        }

        results = (ArrayList<String>) results.stream().parallel().map(s -> MakeDndCardInfo.makeDndCardInfo()).collect(Collectors.toList());
        for (String dndText : results) {
            stringBuilder.append("\n")
                    .append(dndText);
        }
        Sender.sender(entityTypeMessages, stringBuilder.toString());


    }

}
