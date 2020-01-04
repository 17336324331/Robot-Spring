package com.xingguang.mapper;

import com.xingguang.model.NameModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 陈瑞扬
 * @date 2019年12月13日 23:27
 * @description
 */
@Mapper
public interface NameMapper {

    /**
     * @date 2020/1/3 23:17
     * @author 陈瑞扬
     * @description 保存自定义名称
     * @param
     * @return
     */
    void saveName(NameModel nameModel);

    void updateName(NameModel nameModel);

    String selectNameByQQGroup(@Param("strQQ") String strQQ, @Param("strGroup") String strGroup);

    NameModel selectByQQGroup();

    /**
     * @date 2020/1/4 0:03
     * @author 陈瑞扬
     * @description 获取随机名称
     * @param
     * @return
     */
    String getJapanName();

    /**
     * @date 2020/1/4 0:03
     * @author 陈瑞扬
     * @description 获取中文随机名称
     * @param
     * @return
     */
    String getChineseName();

    /**
     * @date 2020/1/4 0:03
     * @author 陈瑞扬
     * @description 获取英文随机名称
     * @param
     * @return
     */
    NameModel getEnglishName();

}
