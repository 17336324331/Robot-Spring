package com.xingguang.sinanya.tools.imal;

public interface GetSkillNameFromMuiltRole {


    /**
     * @param strSkillName 技能原值，可能包含[人物名:技能值]
     * @return 获取后的技能值
     */
    static String getSkillNameFromMuiltRole(StringBuilder strSkillName) {
        String skillName;
        if (strSkillName.toString().contains(":")) {
            skillName = strSkillName.toString().split(":")[strSkillName.toString().split(":").length - 1];
        } else if (strSkillName.toString().contains("=")) {
            skillName = strSkillName.toString().split("=")[strSkillName.toString().split("=").length - 1];
        } else {
            skillName = strSkillName.toString();
        }
        return skillName;
    }
}
