package com.xingguang.service;

import com.xingguang.model.BugModel;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 20:18
 * @description
 */
public interface BugService {

    String getGengXin();

    void recordBug(BugModel bugModel);
}
