package com.xingguang.model;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Objects;

/**
 * @author 陈瑞扬
 * @date 2020年01月01日 21:58
 * @description
 */
@Data
public class BotModel {

    private String strQQ;

    private String strGroup;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BotModel botModel = (BotModel) o;
        return Objects.equals(strQQ, botModel.strQQ) &&
                Objects.equals(strGroup, botModel.strGroup);
    }

}
