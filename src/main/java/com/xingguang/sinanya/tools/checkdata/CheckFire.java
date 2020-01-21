package com.xingguang.sinanya.tools.checkdata;

import com.xingguang.sinanya.entity.EntityFireRd;
import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.FireRdException;
import com.xingguang.sinanya.tools.checkdata.imal.CheckTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xingguang.sinanya.tools.checkdata.CheckIsNumbers.isNumeric;

public class CheckFire {
    private static Pattern checkP = Pattern.compile("check([0-9]+)");
    private static Pattern rdP = Pattern.compile("RD(.*?)-([0-9]+)-([0-9]+)");

    public static EntityFireRd checkRD(String choose, EntityTypeMessages entityTypeMessages) throws FireRdException {
        Matcher rdM = rdP.matcher(choose);
        if (rdM.find()) {
            String skill = rdM.group(1);
            int success = Integer.parseInt(rdM.group(2));
            int failed = Integer.parseInt(rdM.group(3));
            return new EntityFireRd(skill, success, failed);
        }
        throw new FireRdException(entityTypeMessages);
    }

    public static int checkCheck(String choose) {
        Matcher checkM = checkP.matcher(choose);
        int check = -1;
        if (checkM.find()) {
            check = Integer.parseInt(checkM.group(1));
        }
        return check;
    }

    public static ArrayList<Integer> checkChoose(String choose) {
        String[] chooseStringList = choose.split(",");
        ArrayList<Integer> chooseList = new ArrayList<>();
        for (String chooseStringEvery : chooseStringList) {
            chooseList.add(Integer.parseInt(chooseStringEvery));
        }
        return chooseList;
    }

    public static CheckTypes checkFire(String choose) {
        Matcher checkM = checkP.matcher(choose);
        Matcher rdM = rdP.matcher(choose);
        if (checkM.find()) {
            return CheckTypes.CHECK;
        }
        if (rdM.find()) {
            return CheckTypes.RD;
        }
        if (choose.contains(",") || isNumeric(choose)) {
            return CheckTypes.CHOOSE;
        }
        return CheckTypes.ERROR;
    }
}
