package com.yrh.jiaowuwang;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Yrh on 2015/10/29.
 * 课程信息详细情况 Activity
 */
public class CourseDetailActivity extends AppCompatActivity {

    private TextView mTvBack;
    private TextView mTvCourseNameTop;      // 顶部课程名字
    private TextView mTvCourseName;         // 课程名称
    private TextView mTvTeacherName;        // 授课老师
    private TextView mTvAddress;            // 教室
    private TextView mTvPosition;           // 节数
    private TextView mTvClassWeekLine;      // 周数
    private TextView mTvClassesName;        // 上课班级

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        initViews();

        setCourseDetail();

        initEvents();
    }

    private void initEvents() {
        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setCourseDetail() {
        String courseName = "";
        String teacherName = "";
        String classesName = "";
        String address = "";
        String classWeekLine = "";
        int position = -1;

        try {
            Bundle bundle = getIntent().getExtras();
            courseName = bundle.getString("courseName");
            teacherName = bundle.getString("teacherName");
            classesName = bundle.getString("classesName");
            address = bundle.getString("address");
            classWeekLine = bundle.getString("classWeekLine");
            position = bundle.getInt("position");
        } catch (Exception e) {

        }

        mTvCourseName.setText(courseName);
        mTvCourseNameTop.setText(courseName);
        mTvTeacherName.setText(teacherName);
        mTvAddress.setText(address);
        mTvClassWeekLine.setText(classWeekLine);
        mTvPosition.setText(getPosition(position));

        String[] ss = classesName.split(",");
        String s = "";
        for (int i = 0; i < ss.length - 1; i++) {
            s += ss[i] + "\n";
        }
        s += ss[ss.length - 1];
        mTvClassesName.setText(s);
    }

    private void initViews() {
        mTvBack = (TextView) findViewById(R.id.id_courseDetail_tvBack);
        mTvCourseNameTop = (TextView) findViewById(R.id.id_courseDetail_tvCourseNameTop);
        mTvCourseName = (TextView) findViewById(R.id.id_courseDetail_tvCourseName);
        mTvTeacherName = (TextView) findViewById(R.id.id_courseDetail_tvTeacherName);
        mTvPosition = (TextView) findViewById(R.id.id_courseDetail_tvPosition);
        mTvClassWeekLine = (TextView) findViewById(R.id.id_courseDetail_tvClassWeekLine);
        mTvClassesName = (TextView) findViewById(R.id.id_courseDetail_tvClassesName);
        mTvAddress = (TextView) findViewById(R.id.id_courseDetail_tvAddress);
    }

    private String getPosition(int pos) {
        if (pos == -1) {
            return "";
        }

        String weekStr = "";
        String numStr = "";

        switch (pos / 6) {
            case 0: {
                weekStr = "星期天";
                break;
            }
            case 1: {
                weekStr = "星期一";
                break;
            }
            case 2: {
                weekStr = "星期二";
                break;
            }
            case 3: {
                weekStr = "星期三";
                break;
            }
            case 4: {
                weekStr = "星期四";
                break;
            }
            case 5: {
                weekStr = "星期五";
                break;
            }
            case 6: {
                weekStr = "星期六";
                break;
            }
            default: {
                break;
            }
        }

        switch (pos % 6) {
            case 0: {
                numStr = "1-2节";
                break;
            }
            case 1: {
                numStr = "3-4节";
                break;
            }
            case 2: {
                numStr = "5-6节";
                break;
            }
            case 3: {
                numStr = "7-8节";
                break;
            }
            case 4: {
                numStr = "9-10节";
                break;
            }
            case 5: {
                numStr = "11-12节";
                break;
            }
        }

        return weekStr + " " + numStr;
    }
}
