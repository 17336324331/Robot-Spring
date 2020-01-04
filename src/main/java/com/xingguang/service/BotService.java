package com.xingguang.service;

import com.xingguang.model.BotModel;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 22:05
 * @description  开关Service
 */
public interface BotService {

    /**
     * @date 2020/1/1 22:05
     * @author 陈瑞扬
     * @description 获取处于开启状态的骰娘QQ和QQ群的key-value键值对
     * @param
     * @return
     */
    List<BotModel> getBotList();

    /**
     * @date 2020/1/4 15:16
     * @author 陈瑞扬
     * @description 更新骰子的开关状态
     * @param strQQ
     * @param strGroup
     * @param intBotstatus
     * @return
     */
    void updateBotStatus(String strQQ,String strGroup,int intBotstatus);
}
