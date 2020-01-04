package com.xingguang.mapper;

import com.xingguang.model.VoImageModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈瑞扬
 * @date 2020年01月04日 11:08
 * @description
 */
@Mapper
public interface ImageMapper {

    /**
     * @date 2020/1/4 11:08
     * @author 陈瑞扬
     * @description 根据图片id查找对应回复
     * @param
     * @return
     */
    VoImageModel selectRetByImageId(@Param("imageId") String imageId);

    /**
     * @date 2020/1/4 15:56
     * @author 陈瑞扬
     * @description 保存新图片
     * @param strImgId
     * @return
     */
    void saveImage(@Param("strImgId") String strImgId);

}
