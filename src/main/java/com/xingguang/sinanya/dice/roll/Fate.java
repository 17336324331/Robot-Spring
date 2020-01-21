package com.xingguang.sinanya.dice.roll;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.system.MessagesSystem;
import com.xingguang.sinanya.system.MessagesTag;
import com.xingguang.sinanya.tools.getinfo.GetNickName;
import com.xingguang.sinanya.tools.makedata.Calculator;
import com.xingguang.sinanya.tools.makedata.RandomInt;
import com.xingguang.sinanya.tools.makedata.Sender;
import org.nlpcn.commons.lang.util.StringUtil;

import java.util.ArrayList;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.getinfo.GetNickName.getNickName;
import static java.lang.Math.ceil;

public class Fate {

    private Pattern hasFunction = Pattern.compile("[^-+*\0-9]+");
    private EntityTypeMessages entityTypeMessages;

    public Fate(EntityTypeMessages entityTypeMessages) {
        this.entityTypeMessages = entityTypeMessages;
    }

    public void fate() {
        String tag = MessagesTag.TAG_FATE;
        String msg = entityTypeMessages.getMsgGet().getMsg().trim().replaceFirst(tag.substring(0, tag.length() - 2), "").trim();
        ArrayList<Integer> result = new ArrayList<>();
        String function;
        for (int i = 0; i < 4; i++) {
            switch (RandomInt.random(1, 6)) {
                case 1:
                case 2:
                    result.add(-1);
                    break;
                case 3:
                case 4:
                    result.add(0);
                    break;
                case 5:
                case 6:
                    result.add(1);
                    break;
                default:
                    break;
            }
        }
        int sum = 0;
        for (Integer every : result) {
            sum += every;
        }
        String stringBuilder = GetNickName.getNickName(entityTypeMessages) +
                "骰出了:\t4DF=[" +
                StringUtil.joiner(result, " ") +
                "]=";
        if (!msg.equals(MessagesSystem.NONE) && !hasFunction.matcher(msg).find()) {
            function = sum + msg;
            sum = (int) ceil(Calculator.conversion(sum + msg));
            Sender.sender(entityTypeMessages, stringBuilder.replace("-1", "-").replace("1", "+") + function + "=" + sum);
            return;
        }
        Sender.sender(entityTypeMessages, stringBuilder.replace("-1", "-").replace("1", "+") + sum);
    }
}
