package com.yrh.jiaowuwang.utils;

import java.util.Date;

/**
 * Created by Yrh on 2015/10/26.
 * 全局变量类
 */
public class GlobalVar {
    public static String cookie = "";                               // cookie
    public static String username = Constant.Tag.UNLOGIN_TAG;       // 学生姓名
    public static String IDNumber = "";                             // 学号
    public static String password = "";                             // 密码
    public static String currentTerm = "";                          // 学期数
    public static String defaultWeek = "0";                         // 默认周数
    public static String currentWeek = "0";                         // 当前周数
    public static boolean isNextWeek = false;                       // 是否要下一个星期
    public static int currentDay = 0;                               // 当前的日期
    public static int currentMonth = 0;                             // 当前月份
    public static int currentDayOfWeek = 0;                         // 当前日期在星期中第几天
    static {
        Date curDate = new Date(System.currentTimeMillis());
        int year = curDate.getYear() + 1900;
        int month = curDate.getMonth() + 1;
        if (month >= 9) {
            currentTerm = year + "-" + (year + 1) + "-" + "1";
        } else if (month < 3) {
            currentTerm = (year - 1) + "-" + year + "-" + "1";
        } else {
            currentTerm = (year - 1) + "-" + year + "-" + "2";
        }
    }
}
