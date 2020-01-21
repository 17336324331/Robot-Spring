package com.xingguang.sinanya.tools.checkdata;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.InBigGroupException;

import java.util.ArrayList;

/**
 * @author SitaNya
 * @date 2019/10/12
 * @email sitanya@qq.com
 * @qqGroup 162279609
 * 有任何问题欢迎咨询
 * <p>
 * 类说明:
 */
public class CheckInBigGroup {
    static ArrayList<String> bigGroups = new ArrayList<>();

    static {
        bigGroups.add("571103547");
        bigGroups.add("815245928");
    }

    public static void checkInBigGroup(EntityTypeMessages entityTypeMessages) throws InBigGroupException {
        if (bigGroups.contains(entityTypeMessages.getFromGroupString())) {
            throw new InBigGroupException(entityTypeMessages);
        }
    }
}
