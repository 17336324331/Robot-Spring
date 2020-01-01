package com.xingguang.model;

import lombok.Data;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 23:24
 * @description 昵称工具类,用于存储自定义的昵称
 */
@Data
public class NameModel extends  CommonModel {

    /**
     * QQ昵称
     */
    private String strNickName;

    /**
     * QQ群昵称
     */
    private String strCard;

    /**
     * coc自定义昵称
     */
    private String strNName;

}

