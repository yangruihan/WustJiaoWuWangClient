package com.yrh.jiaowuwang.utils;

import com.yrh.jiaowuwang.model.GradeRecord;

import java.util.ArrayList;

/**
 * Created by Yrh on 2015/10/30.
 * 计算绩点类
 */
public class InfoCalculator {

    public static float getGPA(ArrayList<GradeRecord> gradeRecords) {
        // 学分和
        float courseCreditSum = 0;
        // 绩点和
        float coursePointSum = 0;
        for (GradeRecord gr : gradeRecords) {
            // 如果没有绩点，即成绩是补考的，则跳过
            if (gr.getCoursePoint() > 0) {
                courseCreditSum += gr.getCourseCredit();
                coursePointSum += gr.getCoursePoint() * gr.getCourseCredit();
            }
        }

        return coursePointSum / courseCreditSum;
    }

    public static float getSumCourseCredit(ArrayList<GradeRecord> gradeRecords) {
        // 学分和
        float courseCreditSum = 0;
        for (GradeRecord gr : gradeRecords) {
            // 如果没有绩点，即成绩是补考的，则跳过
            if (gr.getCoursePoint() > 0) {
                courseCreditSum += gr.getCourseCredit();
            }
        }
        return courseCreditSum;
    }
}
