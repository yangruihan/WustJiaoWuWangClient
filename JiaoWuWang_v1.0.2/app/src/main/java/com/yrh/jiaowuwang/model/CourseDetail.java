package com.yrh.jiaowuwang.model;

/**
 * Created by Yrh on 2015/10/27.
 * 课程细节类
 */
public class CourseDetail {
    private String courseName;//课程名称
    private String teacherName;//授课老师名
    private String classesName;//上课班级名(复数)
    private String address;//上课地点
    private String classWeekLine;//课程周期属性

    public CourseDetail() {
        courseName = "";
        teacherName = "";
        classesName = "";
        address = "";
        classWeekLine = "";
    }

    @Override
    public String toString() {
        return courseName + "@" + teacherName + "@" + classesName + "@" + address + "@" + classWeekLine;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassesName() {
        return classesName;
    }

    public void setClassesName(String classesName) {
        this.classesName = classesName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getClassWeekLine() {
        return classWeekLine;
    }

    public void setClassWeekLine(String classWeekLine) {
        this.classWeekLine = classWeekLine;
    }
}
