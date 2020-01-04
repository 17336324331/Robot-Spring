package com.xingguang.service;

import com.xingguang.model.NameModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 16:58
 * @description
 */

public interface NameService {

    /**
     * @date 2020/1/3 23:16
     * @author 陈瑞扬
     * @description 根据语言类型和数量 获取 随机名称
     * @param strForgignType
     * @param intNum
     * @return
     */
    String getRandomName(String strForgignType,int intNum);

    /**
     * @date 2020/1/3 23:17
     * @author 陈瑞扬
     * @description 保存自定义名称
     * @param
     * @return
     */
    void saveName(NameModel nameModel);
}
