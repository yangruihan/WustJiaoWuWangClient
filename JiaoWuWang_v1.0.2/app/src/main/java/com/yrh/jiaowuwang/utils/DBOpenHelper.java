package com.yrh.jiaowuwang.utils;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yrh.jiaowuwang.model.CourseDayLine;
import com.yrh.jiaowuwang.model.CourseDetail;
import com.yrh.jiaowuwang.model.CourseWeekTable;
import com.yrh.jiaowuwang.model.GradeRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yrh on 2015/10/27.
 * 数据库辅助类
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /**
         * 创建成绩信息表
         */
        String createGradeRecordsTable = "CREATE TABLE IF NOT EXISTS " + Constant.SQLiteTableConfig.TABLE_GRADE_RECORDS + " ("
                + " lineNumber INTEGER PRIMARY KEY, "   // 编号
                + " studentId INTEGER(8), "             // 学号
                + " studentName VARCHAR(20), "          // 学生姓名
                + " courseDate VARCHAR(11), "           // 开课时间
                + " courseName VARCHAR(80), "           // 课程名称
                + " score VARCHAR(4), "                 // 成绩
                + " scoreTag VARCHAR(10), "             // 成绩标志
                + " courseCharacter VARCHAR(20), "      // 课程性质
                + " courseType VARCHAR(4), "            // 课程类别
                + " courseHour INTEGER, "               // 学时
                + " courseCredit FLOAT, "               // 学分
                + " examCharacter VARCHAR(20), "        // 考试性质
                + " resitDate VARCHAR(11), "            // 补考，重修时间
                + " coursePoint FLOAT " + ");";          // 课程绩点
        db.execSQL(createGradeRecordsTable);

        /**
         * 创建课程表
         */
        String createSyllabusTable = "CREATE TABLE IF NOT EXISTS " + Constant.SQLiteTableConfig.TABLE_SYLABUS + " ("
                + " lineNumber INTEGER PRIMARY KEY AUTOINCREMENT, " // 编号
                + " isEmpty INTEGER, "                              // 是否为空
                + " studentId INTEGER(8), "                         // 学号
                + " term VARCHAR(11), "                             // 学期
                + " week INTEGER, "                                 // 周次
                + " remarks TEXT, "                                 // 备注
                + " courseName VARCHAR(80), "                       // 课程名称
                + " teacherName VARCHAR(20), "                      // 授课老师名
                + " classesName TEXT, "                             // 上课班级名
                + " address VARCHAR(40), "                          // 上课地点
                + " classWeekLine VARCHAR(10) " + ");";              // 课程周期
        db.execSQL(createSyllabusTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 删除已存在的所有表
        db.execSQL("DROP TABLE IF EXISTS " + Constant.SQLiteTableConfig.TABLE_SYLABUS);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.SQLiteTableConfig.TABLE_GRADE_RECORDS);
        // 重新创建表
        onCreate(db);
    }

    /**
     * 清空所有本地数据
     */
    public void clearAllTables() {
        // 获得一个可以写的数据库
        SQLiteDatabase db = this.getWritableDatabase();
        // 删除已存在的所有表
        db.execSQL("DROP TABLE IF EXISTS " + Constant.SQLiteTableConfig.TABLE_SYLABUS);
        db.execSQL("DROP TABLE IF EXISTS " + Constant.SQLiteTableConfig.TABLE_GRADE_RECORDS);
        // 重新创建表
        onCreate(db);
    }

    /**
     * 保存成绩信息
     */
    public void saveGradeRecords(List<GradeRecord> datas) {
        // 获得一个可以写的数据库
        SQLiteDatabase db = this.getWritableDatabase();

        // 清空之前存入的数据
        db.execSQL("DELETE FROM " + Constant.SQLiteTableConfig.TABLE_GRADE_RECORDS);

        for (GradeRecord gr : datas) {
            db.execSQL("INSERT INTO " + Constant.SQLiteTableConfig.TABLE_GRADE_RECORDS +
                            " VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
                    new Object[]{gr.getLineNumber(), gr.getStudentId(), gr.getStudentName(),
                            gr.getCourseDate(), gr.getCourseName(), gr.getScore(), gr.getScoreTag(),
                            gr.getCourseCharacter(), gr.getCourseType(), gr.getCourseHour(), gr.getCourseCredit(),
                            gr.getExamCharacter(), gr.getResitDate(), gr.getCoursePoint()});
        }

        db.close();
    }

    /**
     * 得到成绩信息
     */
    public ArrayList<GradeRecord> getGradeRecords() {
        ArrayList<GradeRecord> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constant.SQLiteTableConfig.TABLE_GRADE_RECORDS + " ;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                GradeRecord gr = new GradeRecord();
                gr.setLineNumber(cursor.getInt(0));
                gr.setStudentId(cursor.getLong(1));
                gr.setStudentName(cursor.getString(2));
                gr.setCourseDate(cursor.getString(3));
                gr.setCourseName(cursor.getString(4));
                gr.setScore(cursor.getString(5));
                gr.setScoreTag(cursor.getString(6));
                gr.setCourseCharacter(cursor.getString(7));
                gr.setCourseType(cursor.getString(8));
                gr.setCourseHour(cursor.getInt(9));
                gr.setCourseCredit(cursor.getFloat(10));
                gr.setExamCharacter(cursor.getString(11));
                gr.setResitDate(cursor.getString(12));
                gr.setCoursePoint(cursor.getFloat(13));

                result.add(gr);
            } while (cursor.moveToNext());
        } else {
            db.close();
            return null;
        }
        db.close();
        return result;
    }

    /**
     * 通过课程名找到该课程的成绩信息
     */
    public GradeRecord getGradeRecordByName(String name) {
        GradeRecord gr = new GradeRecord();
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constant.SQLiteTableConfig.TABLE_GRADE_RECORDS +
                " WHERE courseName=\"" + name + "\"" + " ;";
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                gr.setLineNumber(cursor.getInt(0));
                gr.setStudentId(cursor.getLong(1));
                gr.setStudentName(cursor.getString(2));
                gr.setCourseDate(cursor.getString(3));
                gr.setCourseName(cursor.getString(4));
                gr.setScore(cursor.getString(5));
                gr.setScoreTag(cursor.getString(6));
                gr.setCourseCharacter(cursor.getString(7));
                gr.setCourseType(cursor.getString(8));
                gr.setCourseHour(cursor.getInt(9));
                gr.setCourseCredit(cursor.getFloat(10));
                gr.setExamCharacter(cursor.getString(11));
                gr.setResitDate(cursor.getString(12));
                gr.setCoursePoint(cursor.getFloat(13));
            } while (cursor.moveToNext());
        } else {
            db.close();
            return null;
        }
        db.close();
        return gr;
    }

    /**
     * 保存课表信息
     */
    public void saveSyllabus(CourseWeekTable courseWeekTable) {
        // 获得一个可以写的数据库
        SQLiteDatabase db = this.getWritableDatabase();

        // 清空之前存入的数据
        db.execSQL("DELETE FROM " + Constant.SQLiteTableConfig.TABLE_SYLABUS + " WHERE studentId=" + GlobalVar.IDNumber
                + " AND " + " term=\"" + courseWeekTable.getTerm() + "\";");

        for (CourseDayLine courseDayLine : courseWeekTable.getCourseDayLines()) {
            for (CourseDetail courseDetail : courseDayLine.getCourses()) {
                if (courseDetail != null) {
                    db.execSQL("INSERT INTO " + Constant.SQLiteTableConfig.TABLE_SYLABUS +
                            " VALUES(null,?,?,?,?,?,?,?,?,?,?);", new Object[]{
                            1, GlobalVar.IDNumber, courseWeekTable.getTerm(), courseWeekTable.getWeek(),
                            courseWeekTable.getRemarks(), courseDetail.getCourseName(), courseDetail.getTeacherName(),
                            courseDetail.getClassesName(), courseDetail.getAddress(), courseDetail.getClassWeekLine()
                    });
                } else {
                    db.execSQL("INSERT INTO " + Constant.SQLiteTableConfig.TABLE_SYLABUS +
                            " VALUES(null,?,?,?,?,?,?,?,?,?,?);", new Object[]{
                            0, 0, "", 0, "", "", "", "", "", ""
                    });
                }
            }
        }
        db.close();
    }

    /**
     * 读取课程表信息
     */
    public CourseWeekTable getSyllabus(String IDNumber, String term, String week) {
        // 避免没有初始值出错情况
        if (IDNumber.equals("")) {
            return null;
        }
        if (term.equals("")) {
            return null;
        }
        if (week.equals("")) {
            return null;
        }

        CourseWeekTable courseWeekTable = new CourseWeekTable();

        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + Constant.SQLiteTableConfig.TABLE_SYLABUS + " WHERE "
                + "studentId=" + Long.valueOf(IDNumber)
                + " AND " + "term=\"" + term + "\"" + " ;";
        Cursor cursor = db.rawQuery(sql, null);

        CourseDetail[] courseDetails = new CourseDetail[42];
        int position = 0;
        if (cursor.moveToFirst()) {
            courseWeekTable.setTerm(term);
            courseWeekTable.setWeek(Integer.valueOf(week));
            courseWeekTable.setRemarks(cursor.getString(5));
            do {
                // 如果 week 为0，则全部显示
                courseDetails[position] = new CourseDetail();
                if (week.equals("0") || cursor.getString(6) == null) {
                    courseDetails[position].setCourseName(cursor.getString(6));
                    courseDetails[position].setTeacherName(cursor.getString(7));
                    courseDetails[position].setClassesName(cursor.getString(8));
                    courseDetails[position].setAddress(cursor.getString(9));
                    courseDetails[position].setClassWeekLine(cursor.getString(10));
                } else {
                    String courseName = "";
                    String teacherName = "";
                    String classesName = "";
                    String address = "";
                    String classWeekLine = "";

                    ArrayList<Integer> result = includeWeek(week, cursor.getString(10));
                    String[] courseNames = cursor.getString(6).split("@");
                    String[] teacherNames = cursor.getString(7).split("@");
                    String[] classesNames = cursor.getString(8).split("@");
                    String[] addresses = cursor.getString(9).split("@");
                    String[] classWeekLines = cursor.getString(10).split("@");

                    for (int i = 0; i < result.size(); i++) {
                        int j = result.get(i);
                        courseName += courseNames[j] + "@";
                        teacherName += teacherNames[j] + "@";
                        classesName += classesNames[j] + "@";
                        address += addresses[j] + "@";
                        classWeekLine += classWeekLines[j] + "@";
                    }

                    if (courseName.endsWith("@")) {
                        courseName = courseName.substring(0, courseName.length() - 1);
                    }
                    if (teacherName.endsWith("@")) {
                        teacherName = teacherName.substring(0, teacherName.length() - 1);
                    }
                    if (classesName.endsWith("@")) {
                        classesName = classesName.substring(0, classesName.length() - 1);
                    }
                    if (address.endsWith("@")) {
                        address = address.substring(0, address.length() - 1);
                    }
                    if (classWeekLine.endsWith("@")) {
                        classWeekLine = classWeekLine.substring(0, classWeekLine.length() - 1);
                    }

                    if (!courseName.equals("")) {
                        courseDetails[position].setCourseName(courseName.trim());
                        courseDetails[position].setTeacherName(teacherName.trim());
                        courseDetails[position].setClassesName(classesName.trim());
                        courseDetails[position].setAddress(address.trim());
                        courseDetails[position].setClassWeekLine(classWeekLine.trim());
                    } else {
                        courseDetails[position].setCourseName(null);
                        courseDetails[position].setTeacherName(null);
                        courseDetails[position].setClassesName(null);
                        courseDetails[position].setAddress(null);
                        courseDetails[position].setClassWeekLine(null);
                    }
                }
                position++;
            } while (cursor.moveToNext());
        } else {
            db.close();
            return null;
        }
        db.close();

        CourseDayLine[] courseDayLines = new CourseDayLine[7];
        for (int i = 0; i < 7; i++) {
            CourseDayLine courseDayLine = new CourseDayLine();
            CourseDetail[] courseDetails1 = new CourseDetail[6];
            for (int j = 0; j < 6; j++) {
                courseDetails1[j] = courseDetails[i * 6 + j];
            }
            courseDayLine.setCourses(courseDetails1);
            courseDayLines[i] = courseDayLine;
        }
        courseWeekTable.setCourseDayLines(courseDayLines);
        return courseWeekTable;
    }

    // 如果不传参数默认获得当前学期的所有课表
    public CourseWeekTable getSyllabus() {
        return getSyllabus(GlobalVar.IDNumber, GlobalVar.currentTerm, GlobalVar.defaultWeek);
    }

    /**
     * 判断当前课程的周期是否在当前周内
     *
     * @param week     当前周次
     * @param weekLine 课程周期
     * @return 如果一个课程位置有两节课，则返回符合要求的课程的位置
     */
    public static ArrayList<Integer> includeWeek(String week, String weekLine) {
        weekLine = weekLine.trim();
        ArrayList<Integer> result = new ArrayList<>();
        String[] ss = weekLine.split("@");
        for (int i = 0; i < ss.length; i++) {

            String[] s1 = ss[i].split("-");

            if (s1.length == 1 && s1[0].trim().substring(0, s1[0].trim().length() - 1).trim().equals(week)) {
                result.add(i);
            } else if (s1.length == 2 && Integer.valueOf(s1[0].trim()) <= Integer.valueOf(week.trim())
                    && Integer.valueOf(s1[1].substring(0, s1[1].length() - 1).trim()) >= Integer.valueOf(week.trim())) {
                result.add(i);
            }
        }
        return result;
    }
}
