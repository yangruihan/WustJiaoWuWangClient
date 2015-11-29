package com.yrh.jiaowuwang.utils;

import com.yrh.jiaowuwang.model.GradeRecord;

import java.util.Comparator;

/**
 * Created by Yrh on 2015/10/28.
 * 成绩排序类
 */
public class GradeRecordsSort {
    /**
     * 按名称降序排列
     */
    public static class SortByNameDes implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            return -(gr1.getCourseName().compareTo(gr2.getCourseName()));
        }
    }

    /**
     * 按名称升序排列
     */
    public static class SortByNameAsc implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            return gr1.getCourseName().compareTo(gr2.getCourseName());
        }
    }

    /**
     * 按得分降序排列
     */
    public static class SortByScoreDes implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            return -(gr1.getScore().compareTo(gr2.getScore()));
        }
    }


    /**
     * 按得分升序排列
     */
    public static class SortByScoreAsc implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            return gr1.getScore().compareTo(gr2.getScore());
        }
    }

    /**
     * 按学分降序排列
     */
    public static class SortByCourseCreditDes implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            if (gr1.getCourseCredit() > gr2.getCourseCredit()) {
                return -1;
            } else if (gr1.getCourseCredit() == gr2.getCourseCredit()) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * 按学分升序排列
     */
    public static class SortByCourseCreditAsc implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            if (gr1.getCourseCredit() > gr2.getCourseCredit()) {
                return 1;
            } else if (gr1.getCourseCredit() == gr2.getCourseCredit()) {
                return 0;
            }
            return -1;
        }
    }

    /**
     * 按绩点降序排列
     */
    public static class SortByCoursePointDes implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            if (gr1.getCoursePoint() > gr2.getCoursePoint()) {
                return -1;
            } else if (gr1.getCoursePoint() == gr2.getCoursePoint()) {
                return 0;
            }
            return 1;
        }
    }

    /**
     * 按绩点升序排列
     */
    public static class SortByCoursePointAsc implements Comparator {
        @Override
        public int compare(Object lhs, Object rhs) {
            GradeRecord gr1 = (GradeRecord)lhs;
            GradeRecord gr2 = (GradeRecord)rhs;
            if (gr1.getCoursePoint() > gr2.getCoursePoint()) {
                return 1;
            } else if (gr1.getCoursePoint() == gr2.getCoursePoint()) {
                return 0;
            }
            return -1;
        }
    }
}
