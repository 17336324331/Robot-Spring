package com.xingguang.service.Impl;

import com.xingguang.mapper.BotMapper;
import com.xingguang.model.BotModel;
import com.xingguang.service.BotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 22:07
 * @description 开关Service实现类
 */
@Service
public class BotServiceImpl implements BotService {

    @Autowired
    private BotMapper botMapper;

    /**
     * @return
     * @date 2020/1/1 22:05
     * @author 陈瑞扬
     * @description 获取处于开启状态的骰娘QQ和QQ群的key-value键值对
     */
    @Override
    public List<BotModel> getBotList() {

        return botMapper.getBotList();
    }
}
