package com.yrh.jiaowuwang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.yrh.jiaowuwang.model.GradeRecord;
import com.yrh.jiaowuwang.utils.Constant;
import com.yrh.jiaowuwang.utils.DBOpenHelper;

/**
 * Created by Yrh on 2015/10/29.
 * 成绩信息详细情况 Activity
 */
public class GradeRecordDetailActivity extends AppCompatActivity {

    // 当前课程信息
    private GradeRecord gradeRecord;

    private TextView mTvBack;
    private TextView mTvCouserNameTop;      // 顶部课程名称
    private TextView mTvCourseName;         // 课程名称
    private TextView mTvScore;              // 课程得分
    private TextView mTvCourseCredit;       // 学分
    private TextView mTvCoursePoint;        // 绩点
    private TextView mTvCourseCharacter;    // 课程性质
    private TextView mTvCourseType;         // 课程类型
    private TextView mTvCourseDate;         // 课程日期
    private TextView mTvCourseHour;         // 课时
    private TextView mTvExamCharacter;      // 考试性质

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_record_detail);
        
        initViews();

        // 设置成绩信息
        setGradeRecordInfo();

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

    /**
     * 设置成绩信息
     */
    private void setGradeRecordInfo() {
        // 得到成绩详细信息
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        try {
            String gradeRecordName = bundle.getString("gradeRecordName");

            // 通过课程名找到信息
            DBOpenHelper mDBOpenHelper = new DBOpenHelper(getApplicationContext(), "db", null, Constant.SQLiteTableConfig.DB_VERSION);

            gradeRecord = mDBOpenHelper.getGradeRecordByName(gradeRecordName);

            // 设置属性
            mTvCourseName.setText(gradeRecord.getCourseName());
            mTvCouserNameTop.setText(gradeRecord.getCourseName());
            mTvScore.setText(gradeRecord.getScore());
            mTvCourseCredit.setText(gradeRecord.getCourseCredit() + "");
            mTvCoursePoint.setText(gradeRecord.getCoursePoint() + "");
            mTvCourseType.setText(gradeRecord.getCourseType());
            mTvCourseCharacter.setText(gradeRecord.getCourseCharacter());
            mTvCourseDate.setText(gradeRecord.getCourseDate());
            mTvCourseHour.setText(gradeRecord.getCourseHour() + "");
            mTvExamCharacter.setText(gradeRecord.getExamCharacter());
        } catch (Exception e) {
            // 如果得到数据出现异常，则全部初始化
            mTvCourseName.setText("");
            mTvCouserNameTop.setText(gradeRecord.getCourseName());
            mTvScore.setText("");
            mTvCourseCredit.setText("");
            mTvCoursePoint.setText("");
            mTvCourseType.setText("");
            mTvCourseCharacter.setText("");
            mTvCourseDate.setText("");
            mTvCourseHour.setText("");
            mTvExamCharacter.setText("");
        }
    }

    /**
     * 初始化 Views
     */
    private void initViews() {
        mTvBack = (TextView) findViewById(R.id.id_gradeRecordDetail_tvBack);
        mTvCourseName = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCourseName);
        mTvScore = (TextView) findViewById(R.id.id_gradeRecordDetail_tvScore);
        mTvCourseCredit = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCourseCredit);
        mTvCoursePoint = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCoursePoint);
        mTvCourseCharacter = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCourseCharacter);
        mTvCourseType = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCourseType);
        mTvCourseDate = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCourseDate);
        mTvCourseHour = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCourseHour);
        mTvExamCharacter = (TextView) findViewById(R.id.id_gradeRecordDetail_tvExamCharacter);
        mTvCouserNameTop = (TextView) findViewById(R.id.id_gradeRecordDetail_tvCourseNameTop);
    }
}
