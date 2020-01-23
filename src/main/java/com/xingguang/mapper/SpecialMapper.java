package com.xingguang.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 陈瑞扬
 * @date 2020年01月23日 1:47
 * @description
 */
@Mapper
public interface SpecialMapper {

    Integer fengjiu(@Param("strQQ")String strQQ,@Param("strGroup")String strGroup,@Param("strContent")String strContent,@Param("strNow")String now);

    Integer selectFengjiu(@Param("strQQ")String strQQ,@Param("strNow")String strNow);

    Integer insertGroupBan(@Param("strQQ")String strQQ,@Param("strGroup")String strGroup,@Param("strNow")String strNow);

    Integer selectGroupBan(@Param("strQQ")String strQQ);
}
