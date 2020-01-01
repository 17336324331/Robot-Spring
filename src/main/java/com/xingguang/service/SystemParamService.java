package com.xingguang.service;

import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 21:50
 * @description 系统参数Service
 */
public interface SystemParamService {

    /**
     * @date 2020/1/1 22:01
     * @author 陈瑞扬
     * @description 获取系统参数
     * @param sysCode
     * @return sysValue
     */
    String getSytemParam(String sysCode);

}
