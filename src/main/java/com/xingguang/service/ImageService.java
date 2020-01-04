package com.xingguang.service;

import org.springframework.stereotype.Service;

/**
 * @author 陈瑞扬
 * @date 2020年01月04日 10:57
 * @description
 */
public interface ImageService {

    /**
     * @date 2020/1/4 10:58
     * @author 陈瑞扬
     * @description 处理带有image的消息
     * @param
     * @return
     */
    String dealImageMsg(String strImageMsg,String strQQ);

    /**
     * @date 2020/1/4 15:52
     * @author 陈瑞扬
     * @description 保存新图片
     * @param
     * @return
     */
    void saveImage(String strImageMsg);


}
