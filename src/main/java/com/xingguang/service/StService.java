package com.xingguang.service;

import com.xingguang.model.STModel;

/**
 * @author 陈瑞扬
 * @date 2020年01月05日 12:42
 * @description 人物属性service
 */
public interface StService {

    /**
     * @date 2020/1/5 12:45
     * @author 陈瑞扬
     * @description 属性设置
     * @param msg
     * @return
     */
    void stsave(String msg);

    /**
     * @date 2020/1/5 12:45
     * @author 陈瑞扬
     * @description 属性查看
     * @param stModel
     * @return
     */
    String stshow(STModel stModel);

}
