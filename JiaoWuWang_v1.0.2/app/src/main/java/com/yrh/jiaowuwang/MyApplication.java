package com.yrh.jiaowuwang;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Yrh on 2015/10/21.
 */
public class MyApplication extends Application {

    public static RequestQueue queues;

    @Override
    public void onCreate() {
        super.onCreate();

        queues = Volley.newRequestQueue(getApplicationContext());
    }

    public static RequestQueue getQueues() {
        return queues;
    }
}
