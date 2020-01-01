package com.xingguang.model;

import lombok.Data;

/**
 * @author 陈瑞扬
 * @date 2019年12月01日 11:28
 * @description 自定义的规则实体,用于召唤怪物
 */
@Data
public class RuleModel {

    // 规则名称
    private String strName;

    // 力量
    private String strSTR;

    // 体质
    private String strCON;

    // 体型
    private String strSIZ;

    // 敏捷
    private String strDEX;

    // 智力
    private String strINT;

    // 意志
    private String strPOW;

    // 血量
    private String strHP;

    // 魔法值
    private String strMANGIC;

    // 伤害加成
    private String strHURT;

    // 特殊能力
    private  String strSpical;

    // 内容
    private String strContent;

    // 备注
    private String strRemark;

    // 规则类型
    private String strType;

    // 是否删除
    private Integer IS_DELETED;

    // 闪避
    private String shanbi;

    // 移动
    private String yidong;

    // 武器
    private String  wuqi;

    // 攻击方式
    private String  gongjifangshi;

    // 咒文
    private String zhouwen;

    @Override
    public String toString() {
        return "" +
                "物种: " + strName + '\n' +
                "力量: " + strSTR + "\n" +
                "体质: " + strCON + '\n' +
                "体型: " + strSIZ + '\n' +
                "智力: " + strINT + '\n' +
                "意志: " + strPOW + '\n' +
                "敏捷: " + strDEX + '\n' +
                "基础攻击力: 未知 \n"   +
                "攻击力加成: " + strHURT + '\n' +
                "闪避: " + shanbi + '\n' +
                "移动: " + yidong + '\n' +
                "武器: " + wuqi + '\n' +
                "攻击方式: " + gongjifangshi + '\n' +
                "特殊能力: " + strSpical + '\n' +
                "hp: " + strHP + '\n' +
                "咒文: " + zhouwen ;
    }
}
