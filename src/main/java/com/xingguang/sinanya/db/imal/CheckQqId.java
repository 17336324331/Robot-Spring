package com.xingguang.sinanya.db.imal;

import com.xingguang.sinanya.exceptions.CantFindQqIdException;
import com.xingguang.sinanya.system.MessagesSystem;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public interface CheckQqId {
    Logger log = LoggerFactory.getLogger(CheckQqId.class.getName());

    default String checkQqId() throws CantFindQqIdException {
        String botId = String.valueOf(MessagesSystem.loginInfo.getLoginId());
        int i = 0;
        while (botId.contains("-")) {
            botId = String.valueOf(MessagesSystem.loginInfo.getLoginId());
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                log.error(e.getMessage(), e);
            }
            if (i == 500) {
                log.error("无法取到QQ号");
                throw new CantFindQqIdException();
            }
        }
        return botId;
    }
}
