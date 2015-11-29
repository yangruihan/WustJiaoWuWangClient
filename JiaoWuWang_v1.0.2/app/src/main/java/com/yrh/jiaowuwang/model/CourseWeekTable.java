package com.yrh.jiaowuwang.model;

/**
 * Created by Yrh on 2015/10/27.
 * 周课表类
 */
public class CourseWeekTable {

    private String term;//学期
    private int week;//周次
    private CourseDayLine[] courseDayLines;//每天课程（0-6），0代表星期天
    private String remarks;//备注

    public CourseWeekTable() {
        courseDayLines = new CourseDayLine[7];
        term = "";
        week = 0;
        remarks = "";
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public CourseDayLine[] getCourseDayLines() {
        return courseDayLines;
    }

    public void setCourseDayLines(CourseDayLine[] courseDayLines) {
        this.courseDayLines = courseDayLines;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
