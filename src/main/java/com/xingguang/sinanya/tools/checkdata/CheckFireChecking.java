package com.xingguang.sinanya.tools.checkdata;

import com.xingguang.sinanya.entity.EntityTypeMessages;
import com.xingguang.sinanya.exceptions.FireAlreadyInPageException;
import com.xingguang.sinanya.exceptions.FireRdException;
import com.xingguang.sinanya.db.fire.SelectFire;

import java.util.ArrayList;

public class CheckFireChecking {
    public static int checkFireChecking(int id, int input, EntityTypeMessages entityTypeMessages) throws FireRdException, FireAlreadyInPageException {
        SelectFire selectFire = new SelectFire(entityTypeMessages);
        ArrayList<Integer> chooseList = new ArrayList<>();
        for (String every : selectFire.selectCheckChoose(id).split(",")) {
            chooseList.add(Integer.parseInt(every));
        }
        if (chooseList.contains(input)) {
            return input;
        } else {
            throw new FireRdException(entityTypeMessages);
        }
    }
}
