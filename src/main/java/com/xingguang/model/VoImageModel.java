package com.xingguang.model;

import lombok.Data;

/**
 * @author 陈瑞扬
 * @date 2020年01月04日 11:52
 * @description
 */
@Data
public class VoImageModel {

    /**
     * 自增主键
     */
    private Integer intId;

    /**
     * 图片返回的内容
     */
    private String strRet;

    /**
     * 图片返回的类型
     */
    private int intRetType;
}
