package com.xingguang.sinanya.tools;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.util.ArrayList;

public class getNLP {
    public static ArrayList<String> getNlp(String key) {
        ArrayList<String> result = new ArrayList<>();
        for (Term term : BaseAnalysis.parse(key)) {
            result.add(term.getName());
        }
        return result;
    }
}
