package com.xingguang.service.Impl;

import com.xingguang.mapper.SystemParamMapper;
import com.xingguang.service.BugService;
import com.xingguang.service.HelpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 20:04
 * @description
 */
@Service
public class HelpServiceImpl implements HelpService {

    @Autowired
    private SystemParamMapper mapper;

    @Autowired
    private BugService bugService;

    @Override
    public String getHelpZhiLing() {
//        String helpzhiling = mapper.selectSystemCode("helpzhiling");
//        return helpzhiling;
        return null;
    }

    @Override
    public String getHelpGengXin() {
        //BugService.getGengXin();
        return null;
    }
}
