package com.xingguang.sinanya.tools.getinfo;

import com.xingguang.sinanya.entity.EntityHistory;
import com.xingguang.sinanya.exceptions.CantFindQqIdException;
import com.xingguang.sinanya.system.MessagesHistory;
import com.xingguang.sinanya.db.history.InsertHistory;
import com.xingguang.sinanya.db.history.SelectHistory;


/**
 * @author SitaNya
 * 日期: 2019-06-15
 * 电子邮箱: sitanya@qq.com
 * 维护群(QQ): 162279609
 * 有任何问题欢迎咨询
 * 类说明: 骰点历史信息的数据库交互类
 */
public class History {

    private static SelectHistory selectHistory = new SelectHistory();
    private static InsertHistory insertHistory = new InsertHistory();

    private History() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 刷新骰点历史到静态变量，只在程序启动时刷新一次
     */
    public static void flushHistory() {
        selectHistory.flushHistoryFromDatabase();
    }

    /**
     * 将当前静态变量中的骰点历史入库，这个方法被com.xingguang.sinanya.listener.InputHistoryToDataBase定时器调用
     */
    public static void setHistory() throws CantFindQqIdException {
        insertHistory.insertHistory();
    }

    /**
     * 获取EntityHistory对象，如果不存在则更新一个对象
     *
     * @param qqId QQ号
     * @return 封装后的EntityHistory对象，包含骰点人，骰娘QQ，各种成功失败次数，骰点次数，均值
     */
    public static EntityHistory changeHistory(String qqId) {
        if (MessagesHistory.HISTORY_LIST.containsKey(qqId)) {
            return MessagesHistory.HISTORY_LIST.get(qqId);
        } else {
            MessagesHistory.HISTORY_LIST.put(qqId, new EntityHistory(qqId));
            return MessagesHistory.HISTORY_LIST.get(qqId);
        }
    }
}
