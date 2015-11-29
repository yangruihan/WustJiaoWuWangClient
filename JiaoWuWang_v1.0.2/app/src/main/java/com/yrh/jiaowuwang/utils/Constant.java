package com.yrh.jiaowuwang.utils;

import com.yrh.jiaowuwang.R;

/**
 * Created by Yrh on 2015/10/23.
 */
public class Constant {
    public class TabLog {
        public static final int TAB_SYLLABUS = 0;
        public static final int TAB_GRADE = 1;
        public static final int TAB_OTHER = 2;
    }

    public class RequestCode {
        public static final int LOGIN_CODE = 101;
    }

    public class RequestURL {
        public static final String VERIFY_URL = "http://23.99.110.60:8080/SpringMVC/image/";             // 获取图片action
        public static final String LOGIN_URL = "http://23.99.110.60:8080/SpringMVC/user/login";          // 登陆action
        public static final String USERNAME = "http://23.99.110.60:8080/SpringMVC/user/username";        // 获取用户名
        public static final String CREDIT_URL = "http://23.99.110.60:8080/SpringMVC/user/info";          // 获取个人信息action
        public static final String GRADES_URL = "http://23.99.110.60:8080/SpringMVC/user/grades";        // 个人成绩action
        public static final String COURSE_URL = "http://23.99.110.60:8080/SpringMVC/user/courseTable";   // 个人课表action
        public static final String CURRENT_WEEK_URL = "http://23.99.110.60:8080/SpringMVC/currentWeek";  // 当前星期数
    }

    public class SQLiteTableConfig {
        public static final int DB_VERSION = 1;
        public static final String TABLE_GRADE_RECORDS = "t_gradeRecords";
        public static final String TABLE_SYLABUS = "t_syllabus";
    }

    public class Tag {
        public static final String UNLOGIN_TAG = "登录";
    }
}
