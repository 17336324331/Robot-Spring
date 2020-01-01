package com.xingguang.model;

import lombok.Data;

/**
 * @author 陈瑞扬
 * @date 2019年12月31日 23:48
 * @description 随机姓名生成器Model
 */
@Data
public class RandomNameModel {

    /**
     * 外文名称
     */
    private String strForeignName;

    /**
     * 中文名称
     */
    private String strChineseName;

}
