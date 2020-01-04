package com.xingguang.service.Impl;

import com.xingguang.mapper.BugMapper;
import com.xingguang.model.BugModel;
import com.xingguang.service.BugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 20:19
 * @description
 */
@Service
public class BugServiceImpl implements BugService {

    @Autowired
    private BugMapper bugMapper;

    @Override
    public String getGengXin() {
        return bugMapper.getGengXin();
    }

    @Override
    public void recordBug(BugModel bugModel) {
        bugMapper.recordBug(bugModel);
    }
}
