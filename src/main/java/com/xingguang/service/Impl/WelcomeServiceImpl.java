package com.xingguang.service.Impl;

import com.xingguang.mapper.WelcomeMapper;
import com.xingguang.model.WelcomeModel;
import com.xingguang.service.WelcomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月02日 21:54
 * @description
 */
@Service
public class WelcomeServiceImpl implements WelcomeService {

    @Autowired
    private WelcomeMapper welcomeMapper;

    @Override
    public void setWelcome(WelcomeModel welcomeModel) {
        welcomeMapper.setWelcome(welcomeModel);

    }
}
