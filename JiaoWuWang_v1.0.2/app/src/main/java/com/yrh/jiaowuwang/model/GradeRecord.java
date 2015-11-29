package com.yrh.jiaowuwang.model;

/**
 * Created by Yrh on 2015/10/21.
 * 成绩记录类
 */
public class GradeRecord {

    private int lineNumber;//编号
    private long studentId;//学号
    private String studentName;//学生姓名
    private String courseDate;//开课时间
    private String courseName;//课程名称
    private String score;//成绩
    private String scoreTag;//成绩标志
    private String courseCharacter;//课程性质
    private String courseType;//课程类别
    private int courseHour;//学时
    private float courseCredit;//学分
    private String examCharacter;//考试性质
    private String resitDate;//补考，重修时间
    private float coursePoint;//课程绩点

    /**
     * 返回特征字符串，用于查找匹配
     */
    public String getFeatureString() {
        return courseDate + ";"
                + courseName + ";"
                + score + ";"
//                + courseCharacter + ";"
                + courseType + ";"
                + courseCredit + ";"
                + examCharacter + ";"
                + coursePoint;
    }

    /**
     * Get Set Methods
     **/
    public float getCoursePoint() {
        return coursePoint;
    }

    public void setCoursePoint(float coursePoint) {
        this.coursePoint = coursePoint;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getCourseDate() {
        return courseDate;
    }

    public void setCourseDate(String courseDate) {
        this.courseDate = courseDate;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScoreTag() {
        return scoreTag;
    }

    public void setScoreTag(String scoreTag) {
        this.scoreTag = scoreTag;
    }

    public String getCourseCharacter() {
        return courseCharacter;
    }

    public void setCourseCharacter(String courseCharacter) {
        this.courseCharacter = courseCharacter;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public int getCourseHour() {
        return courseHour;
    }

    public void setCourseHour(int courseHour) {
        this.courseHour = courseHour;
    }

    public float getCourseCredit() {
        return courseCredit;
    }

    public void setCourseCredit(float courseCredit) {
        this.courseCredit = courseCredit;
    }

    public String getExamCharacter() {
        return examCharacter;
    }

    public void setExamCharacter(String exameCharacter) {
        this.examCharacter = exameCharacter;
    }

    public String getResitDate() {
        return resitDate;
    }

    public void setResitDate(String resitDate) {
        this.resitDate = resitDate;
    }
}
