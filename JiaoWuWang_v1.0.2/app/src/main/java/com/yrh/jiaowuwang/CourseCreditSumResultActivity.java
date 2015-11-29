package com.yrh.jiaowuwang;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Yrh on 2015/10/30.
 * 绩点计算结果 Activity
 */
public class CourseCreditSumResultActivity extends AppCompatActivity {

    private TextView mTvBack;
    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coursecredit_sum_result);

        initViews();

        Intent intent = getIntent();
        float result = intent.getFloatExtra("courseCreditSumResult", 0f);
        mTvResult.setText(result + "");

        mTvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initViews() {
        mTvBack = (TextView) findViewById(R.id.id_courseCreditSum_tvBack);
        mTvResult = (TextView) findViewById(R.id.id_courseCreditSum_tvResult);
    }
}
