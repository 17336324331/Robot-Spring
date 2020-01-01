package com.xingguang.model;

import lombok.Data;

import java.util.Date;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 23:30
 * @description 公共Model
 */
@Data
public class CommonModel {

    /**
     * QQ号码
     */
    private String strQQ;

    /**
     * QQ群号码
     */
    private String strGroup;

    /**
     * 是否删除
     */
    private Integer isDeleted;

    /**
     * 创建时间
     */
    private Date dtCreatetime;


}
