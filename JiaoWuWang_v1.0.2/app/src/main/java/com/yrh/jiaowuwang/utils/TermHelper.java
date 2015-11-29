package com.yrh.jiaowuwang.utils;

/**
 * Created by Yrh on 2015/10/29.
 * 学期计算类
 */
public class TermHelper {
    /**
     * 获得传入学期的上一学期
     */
    public static String getLastTerm(String term) {
        String[] ss = term.split("-");
        try {
            if (ss[2].equals("2")) {
                return ss[0] + "-" + ss[1] + "-1";
            } else {
                return (Integer.valueOf(ss[0]) - 1) + "-" + (Integer.valueOf(ss[1]) - 1) + "-1";
            }
        } catch (Exception e) {
            return "";
        }
    }
}
