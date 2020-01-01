package com.xingguang.service.Impl;

import com.xingguang.mapper.NameMapper;
import com.xingguang.mapper.RandomNameMapper;
import com.xingguang.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 17:08
 * @description
 */
@Service
public class NameServiceImpl implements NameService {

    @Autowired
    private RandomNameMapper nameMapper;

    @Override
    public String getRandomName() {
        return null;
    }

    @Override
    public void saveName() {

    }
}
