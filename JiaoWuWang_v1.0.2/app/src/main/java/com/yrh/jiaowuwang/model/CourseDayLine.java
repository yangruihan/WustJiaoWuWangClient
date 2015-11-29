package com.yrh.jiaowuwang.model;

/**
 * Created by Yrh on 2015/10/27.
 * 一天的课程
 */
public class CourseDayLine {
    private CourseDetail[] courses;//一天六节大课(0-5)

    public CourseDayLine() {
        courses = new CourseDetail[6];
    }

    public CourseDetail[] getCourses() {
        return courses;
    }

    public void setCourses(CourseDetail[] courses) {
        this.courses = courses;
    }
}
