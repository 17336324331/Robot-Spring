package com.xingguang.service.Impl;

import com.xingguang.mapper.SystemParamMapper;
import com.xingguang.service.SystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 21:50
 * @description
 */
@Service
public class SystemParamServiceImpl implements SystemParamService {

    @Autowired
    private SystemParamMapper systemParamMapper;

    @Override
    public String getSytemParam(String sysCode) {

        return systemParamMapper.selectSystemCode(sysCode);
    }
}
