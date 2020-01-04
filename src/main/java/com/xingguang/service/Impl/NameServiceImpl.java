package com.xingguang.service.Impl;

import com.xingguang.mapper.NameMapper;
import com.xingguang.model.NameModel;
import com.xingguang.service.NameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 17:08
 * @description
 */
@Service
public class NameServiceImpl implements NameService {

    @Autowired
    private NameMapper nameMapper;


    /**
     * @param strForgignType
     * @param intNum
     * @return
     * @date 2020/1/3 23:16
     * @author 陈瑞扬
     * @description 根据语言类型和数量 获取 随机名称
     */
    @Override
    public String getRandomName(String strForgignType, int intNum) {

        String ret = "";

        // 英文
        if ("en".equals(strForgignType)){
            for (int i = 0; i < intNum; i++) {
                NameModel nameModel= nameMapper.getEnglishName();
                ret += (","+nameModel.getStrForeignName()+"("+nameModel.getStrChineseName()+")");
            }
        }
        // 中文
        else if ("cn".equals(strForgignType)){
            for (int i = 0; i < intNum; i++) {
                ret += ("," +nameMapper.getChineseName());
            }
        }
        // 日文
        else if ("jp".equals(strForgignType)){
            for (int i = 0; i < intNum; i++) {
                ret += (","+ nameMapper.getJapanName());
            }
        }

        return ret.substring(1);
    }

    /**
     * @param nameModel
     * @return
     * @date 2020/1/3 23:17
     * @author 陈瑞扬
     * @description 保存自定义名称
     */
    @Override
    public void saveName(NameModel nameModel) {

    }
}
