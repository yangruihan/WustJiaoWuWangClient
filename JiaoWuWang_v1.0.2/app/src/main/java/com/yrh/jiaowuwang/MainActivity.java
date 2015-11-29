package com.yrh.jiaowuwang;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.yrh.jiaowuwang.model.CourseDayLine;
import com.yrh.jiaowuwang.model.CourseDetail;
import com.yrh.jiaowuwang.model.CourseWeekTable;
import com.yrh.jiaowuwang.model.GradeRecord;
import com.yrh.jiaowuwang.utils.Constant;
import com.yrh.jiaowuwang.utils.InfoCalculator;
import com.yrh.jiaowuwang.utils.DBOpenHelper;
import com.yrh.jiaowuwang.utils.GlobalVar;
import com.yrh.jiaowuwang.utils.GradeRecordsSort;
import com.yrh.jiaowuwang.utils.TermHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Yrh on 2015/10/22.
 * 主界面 Activity
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private boolean isLogin = false; // 是否已经登录

    private ViewPager mViewPager;
    private List<View> mViewList = new ArrayList<>();

    // private ImageButton mImgBtnLogin;
    private TextView mTvLoginInfo;

    // View
    private View tab_syllabus;
    private View tab_grade;
    private View tab_other;

    // Tab
    private LinearLayout mLLTabSyllabus;
    private LinearLayout mLLTabGrade;
    private LinearLayout mLLTabOther;

    // TextView
    private TextView mTvTextSyllabus;
    private TextView mTvShowSyllabus;
    private TextView mTvTextGrade;
    private TextView mTvShowGrade;
    private TextView mTvTextOther;
    private TextView mTvShowOther;

    // Top View
    private LinearLayout m_top_loginInfoLl;

    private FrameLayout m_top_syllabusTabFl;
    private LinearLayout m_top_syllabusTabLl;
    private TextView m_top_tvSpinnerShow;
    private DropdownSpinner m_top_spinnerWeekNumSelect;

    private LinearLayout m_top_gradeRecordTabLl;
    private SearchView m_top_searchViewGradeRecordSearch;
    // 搜索中的特殊关键字
    private HashMap<String, String> mSearchKeys = new HashMap<>();
    private TextView m_top_tvGradeRecordTabMore;

    // Syllabus Tab View
    private TextView m_tabSyl_tvHint;
    private LinearLayout m_tabSyl_linearLayoutShowInfo;

    private TextView[] m_tabSyl_tvCourses = new TextView[42];
    private int[] tvCoursesIds = new int[]{
            R.id.id_tabSyl_course0, R.id.id_tabSyl_course1, R.id.id_tabSyl_course2, R.id.id_tabSyl_course3, R.id.id_tabSyl_course4,
            R.id.id_tabSyl_course5, R.id.id_tabSyl_course6, R.id.id_tabSyl_course7, R.id.id_tabSyl_course8, R.id.id_tabSyl_course9,
            R.id.id_tabSyl_course10, R.id.id_tabSyl_course11, R.id.id_tabSyl_course12, R.id.id_tabSyl_course13, R.id.id_tabSyl_course14,
            R.id.id_tabSyl_course15, R.id.id_tabSyl_course16, R.id.id_tabSyl_course17, R.id.id_tabSyl_course18, R.id.id_tabSyl_course19,
            R.id.id_tabSyl_course20, R.id.id_tabSyl_course21, R.id.id_tabSyl_course22, R.id.id_tabSyl_course23, R.id.id_tabSyl_course24,
            R.id.id_tabSyl_course25, R.id.id_tabSyl_course26, R.id.id_tabSyl_course27, R.id.id_tabSyl_course28, R.id.id_tabSyl_course29,
            R.id.id_tabSyl_course30, R.id.id_tabSyl_course31, R.id.id_tabSyl_course32, R.id.id_tabSyl_course33, R.id.id_tabSyl_course34,
            R.id.id_tabSyl_course35, R.id.id_tabSyl_course36, R.id.id_tabSyl_course37, R.id.id_tabSyl_course38, R.id.id_tabSyl_course39,
            R.id.id_tabSyl_course40, R.id.id_tabSyl_course41};
    private int[] courseTextViewBoder = new int[]{
            R.drawable.course_textview_boder1, R.drawable.course_textview_boder2, R.drawable.course_textview_boder3,
            R.drawable.course_textview_boder4, R.drawable.course_textview_boder9, R.drawable.course_textview_boder6,
            R.drawable.course_textview_boder7, R.drawable.course_textview_boder8, R.drawable.course_textview_boder5,
            R.drawable.course_textview_boder10, R.drawable.course_textview_boder11, R.drawable.course_textview_boder12,
            R.drawable.course_textview_boder13, R.drawable.course_textview_boder14, R.drawable.course_textview_boder15,
            R.drawable.course_textview_boder16
    };

    private GradeRecordsAdapter mGradeRecordsAdapter;
    private TextView m_tabGrade_tvHint;
    private LinearLayout m_tabGrade_linearLayoutShowInfo;
    private SwipeRefreshLayout m_tabGrade_swipeRefreshLayout;
    private TextView m_tabGrade_tvCourseName;
    private TextView m_tabGrade_tvGradeScore;
    private TextView m_tabGrade_tvCourseCredit;
    private TextView m_tabGrade_tvCoursePoint;
//    private int lastVisibleItem;

    // Other Tab View
    private Button m_tabOther_btnLogout;
    private Button m_tabOther_btnClearLocalData;
    private Button m_tabOther_btnUser;

    // 用于按两次返回键退出
    private long exitTime;

    // 成绩信息
    private ArrayList<GradeRecord> mGradeRecords = new ArrayList<>();
    // 成绩信息备份
    private ArrayList<GradeRecord> mGradeRecordsBackUp = new ArrayList<>();
    // 查询成绩结果
    private ArrayList<GradeRecord> mGradeRecordsQueryResult;

    // 当前周次课表信息
    private CourseWeekTable mCurrentCourseWeekTable;
    // 全部课表信息
    private CourseWeekTable mAllCourseWeekTable;

    // 数据库辅助类
    private DBOpenHelper mDBOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 提前初始化
        preInit();

        // 初始化数据库
        initDatabase();

        // 初始化View
        initMainViews();

        // 初始化事件
        initMainEvents();

        // 初始化 Top View
        initTopViews();

        // 初始化 Top View Event
        initTopEvents();

        // 初始化 Syllabus Tab View
        initSyllabusTabViews();

        // 初始化 Syllabus Tab Events
        initSyllabusTabEvents();

        // 初始化 Grade Tab View
        initGradeTabViews();

        // 初始化 Grade Tab Events
        initGradeTabEvents();

        // 初始化 Other Tab View
        initOtherTabViews();

        // 初始化 Other Tab Events
        initOtherTabEvents();
    }

    private void preInit() {
        // 获得当前日期
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        GlobalVar.currentMonth = c.get(Calendar.MONTH) + 1;// 获取当前月份
        GlobalVar.currentDay = c.get(Calendar.DAY_OF_MONTH);    // 获取当前月份的日期号码
        GlobalVar.currentDayOfWeek = c.get(Calendar.DAY_OF_WEEK);     // 获得当前星期数

        // 读取保存的账号
        SharedPreferences mSharedPreferences = getSharedPreferences("data", Activity.MODE_PRIVATE);
        GlobalVar.IDNumber = mSharedPreferences.getString("IDNumber", "0");

        // 每一天同步一下周次数
        if (GlobalVar.currentDayOfWeek != mSharedPreferences.getInt("currentDayOfWeek", -1)) {
            setCurrentWeekFromNet(mSharedPreferences);
        }

        // 将新的日期保存下来
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt("currentDayOfWeek", GlobalVar.currentDayOfWeek);
    }

    /**
     * 初始化 Other Tab Events
     */
    private void initOtherTabEvents() {
        m_tabOther_btnClearLocalData.setOnClickListener(this);
        m_tabOther_btnLogout.setOnClickListener(this);
        m_tabOther_btnUser.setOnClickListener(this);
    }

    /**
     * 初始化 Other Tab View
     */
    private void initOtherTabViews() {
        m_tabOther_btnClearLocalData = (Button) tab_other.findViewById(R.id.id_tabOther_btnClearLocalData);
        m_tabOther_btnLogout = (Button) tab_other.findViewById(R.id.id_tabOther_btnLogout);
        m_tabOther_btnUser = (Button) tab_other.findViewById(R.id.id_tabOther_btnUser);
        m_tabOther_btnUser.setText(GlobalVar.username);
    }

    /**
     * 初始化 Syllabus Tab Events
     */
    private void initSyllabusTabEvents() {
        for (int i = 0; i < m_tabSyl_tvCourses.length; i++) {
            final int position = i;
            m_tabSyl_tvCourses[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 如果当前没有显示课程
                    if (!((TextView) v).getText().equals("")) {
                        // 通过位置得到课程详细信息
                        final CourseDetail courseDetail = getCourseDetailByPosition(position, mAllCourseWeekTable);
                        if (courseDetail.getCourseName().contains("@")) {
                            // 对话框数据项
                            final String[] courseNames = courseDetail.getCourseName().split("@");

                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setItems(courseNames, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    bundle.putString("courseName", courseDetail.getCourseName().trim().split("@")[which].trim());
                                    bundle.putString("teacherName", courseDetail.getTeacherName().trim().split("@")[which].trim());
                                    bundle.putString("classesName", courseDetail.getClassesName().trim().split("@")[which].trim());
                                    bundle.putString("address", courseDetail.getAddress().trim().split("@")[which].trim());
                                    bundle.putString("classWeekLine", courseDetail.getClassWeekLine().trim().split("@")[which].trim());
                                    bundle.putInt("position", position);
                                    intent.putExtras(bundle);
                                    intent.setClass(MainActivity.this, CourseDetailActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.create().show();
                        } else {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("courseName", courseDetail.getCourseName().trim());
                            bundle.putString("teacherName", courseDetail.getTeacherName().trim());
                            bundle.putString("classesName", courseDetail.getClassesName().trim());
                            bundle.putString("address", courseDetail.getAddress().trim());
                            bundle.putString("classWeekLine", courseDetail.getClassWeekLine().trim());
                            bundle.putInt("position", position);
                            intent.putExtras(bundle);
                            intent.setClass(MainActivity.this, CourseDetailActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            });
        }
    }

    /**
     * 通过位置获得课程相信信息
     */
    public CourseDetail getCourseDetailByPosition(int pos, CourseWeekTable courseWeekTable) {
        int i = 0;
        for (CourseDayLine courseDayLine : courseWeekTable.getCourseDayLines()) {
            for (CourseDetail courseDetail : courseDayLine.getCourses()) {
                if (i == pos) {
                    return courseDetail;
                }
                i++;
            }
        }
        return null;
    }

    /**
     * 初始化 Top View Event
     */
    private void initTopEvents() {
        // 当周次选中时
        m_top_spinnerWeekNumSelect.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!((position + 1) + "").equals(GlobalVar.currentWeek)) {
                    m_top_tvSpinnerShow.setText("第" + (position + 1) + "周(非本周)▼");
                    m_top_tvSpinnerShow.setTextColor(Color.parseColor("#F5C8B1"));
                } else {
                    m_top_tvSpinnerShow.setText("第" + (position + 1) + "周▼");
                    m_top_tvSpinnerShow.setTextColor(Color.parseColor("#ffffff"));
                }
                setSyllabusByWeekNumFromDB((position + 1) + "");
                setSyllabusData(mCurrentCourseWeekTable);
            }
        });

        // 搜索栏事件
        m_top_searchViewGradeRecordSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当搜索栏提交的时候
            @Override
            public boolean onQueryTextSubmit(String query) {
                // 当前查找字符串是否在关键词中
                if (mSearchKeys.containsKey(query)) {
                    query = mSearchKeys.get(query);
                }

                // 遍历特征字符串查找
                mGradeRecordsQueryResult = new ArrayList<>();
                for (GradeRecord gr : mGradeRecordsBackUp) {
                    boolean isResult = true;
                    for (int i = 0; i < query.length(); i++) {
                        if (!gr.getFeatureString().contains(query.charAt(i) + "")) {
                            isResult = false;
                            break;
                        }
                    }
                    if (isResult) {
                        mGradeRecordsQueryResult.add(gr);
                    }
                }
                mGradeRecords = mGradeRecordsQueryResult;
                // 设置显示结果
                mGradeRecordsAdapter.changeData(mGradeRecords);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // 如果搜索栏什么都没有
                if (TextUtils.isEmpty(newText)) {
                    // 设置显示全部结果
                    mGradeRecords = mGradeRecordsBackUp;
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                    mGradeRecordsQueryResult.clear();
                    return true;
                }

                // 当前查找字符串是否在关键词中
                if (mSearchKeys.containsKey(newText)) {
                    newText = mSearchKeys.get(newText);
                }

                // 遍历特征字符串查找
                mGradeRecordsQueryResult = new ArrayList<>();
                for (GradeRecord gr : mGradeRecordsBackUp) {
                    boolean isResult = true;
                    for (int i = 0; i < newText.length(); i++) {
                        if (!gr.getFeatureString().contains(newText.charAt(i) + "")) {
                            isResult = false;
                            break;
                        }
                    }
                    if (isResult) {
                        mGradeRecordsQueryResult.add(gr);
                    }
                }
                mGradeRecords = mGradeRecordsQueryResult;
                // 设置显示结果
                mGradeRecordsAdapter.changeData(mGradeRecords);
                return true;
            }
        });

        // 成绩界面点击更多时，弹出一个菜单
        m_top_tvGradeRecordTabMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(MainActivity.this, m_top_tvGradeRecordTabMore);
                popupMenu.getMenuInflater().inflate(R.menu.menu_graderecords_more, popupMenu.getMenu());
                // 为弹出菜单设置点击回调事件
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.id_menu_item_calculateCoursePoint: {
                                Intent intent = new Intent();
                                intent.putExtra("coursePointResult", InfoCalculator.getGPA(mGradeRecords));
                                intent.setClass(MainActivity.this, CoursePointResultActivity.class);
                                startActivity(intent);
                                break;
                            }
                            case R.id.id_menu_item_sumCourseCredit: {
                                Intent intent = new Intent();
                                intent.putExtra("courseCreditSumResult", InfoCalculator.getSumCourseCredit(mGradeRecords));
                                intent.setClass(MainActivity.this, CourseCreditSumResultActivity.class);
                                startActivity(intent);
                                break;
                            }
                            default: {
                                break;
                            }
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
    }

    /**
     * 初始化 Top View
     */
    private void initTopViews() {
        m_top_syllabusTabFl = (FrameLayout) findViewById(R.id.id_top_syllabusTab_ll1);
        m_top_syllabusTabLl = (LinearLayout) findViewById(R.id.id_top_syllabusTab_ll2);

        // 初始化第几周下拉列表
        initWeekNumSpinner();

        m_top_loginInfoLl = (LinearLayout) findViewById(R.id.id_top_loginInfoLl);
        m_top_gradeRecordTabLl = (LinearLayout) findViewById(R.id.id_top_gradeRecordsTab_ll);

        m_top_searchViewGradeRecordSearch = (SearchView) findViewById(R.id.id_top_searchView);
        // 设置自动缩小成图标
        m_top_searchViewGradeRecordSearch.setIconifiedByDefault(true);
        // 设置该SearchView内默认显示的提示文本
        m_top_searchViewGradeRecordSearch.setQueryHint("查找成绩");

        // 初始化搜索关键字
        mSearchKeys.put("本学期", GlobalVar.currentTerm);
        mSearchKeys.put("本学期成", GlobalVar.currentTerm);
        mSearchKeys.put("本学期成绩", GlobalVar.currentTerm);
        mSearchKeys.put("当前学期", GlobalVar.currentTerm);
        mSearchKeys.put("当前学期成", GlobalVar.currentTerm);
        mSearchKeys.put("当前学期成绩", GlobalVar.currentTerm);
        mSearchKeys.put("上学期", TermHelper.getLastTerm(GlobalVar.currentTerm));
        mSearchKeys.put("上学期成", TermHelper.getLastTerm(GlobalVar.currentTerm));
        mSearchKeys.put("上学期成绩", TermHelper.getLastTerm(GlobalVar.currentTerm));
        mSearchKeys.put("上上学期", TermHelper.getLastTerm(TermHelper.getLastTerm(GlobalVar.currentTerm)));
        mSearchKeys.put("上上学期成", TermHelper.getLastTerm(TermHelper.getLastTerm(GlobalVar.currentTerm)));
        mSearchKeys.put("上上学期成绩", TermHelper.getLastTerm(TermHelper.getLastTerm(GlobalVar.currentTerm)));

        m_top_tvGradeRecordTabMore = (TextView) findViewById(R.id.id_top_gradeRecordsTab_more);
    }

    /**
     * 初始化第几周下拉列表
     */
    private void initWeekNumSpinner() {
        m_top_tvSpinnerShow = (TextView) findViewById(R.id.id_top_tvSpinnerShow);

        // 读取保存的星期数
        final SharedPreferences mSharedPreferences = getSharedPreferences("data", Activity.MODE_PRIVATE);
        GlobalVar.currentWeek = mSharedPreferences.getString("currentWeek", "-1");

        // 如果还未得到过当前星期数 或者 当前周数超过25
        if (GlobalVar.currentWeek.equals("-1") || Integer.valueOf(GlobalVar.currentWeek) >= 25) {
            GlobalVar.currentWeek = "0";
            // 从网上获得当前周期数
            setCurrentWeekFromNet(mSharedPreferences);
        } else {
            // 读取是否要下一个星期
            GlobalVar.isNextWeek = mSharedPreferences.getBoolean("isNextWeek", false);

            // 如果是星期天，则周数要加1
            if (GlobalVar.isNextWeek && GlobalVar.currentDayOfWeek == 0) {
                GlobalVar.currentWeek = (Integer.valueOf(GlobalVar.currentWeek) + 1) + "";
                GlobalVar.isNextWeek = false;
                // 将数据保存下来
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean("isNextWeek", false);
                editor.putString("currentWeek", GlobalVar.currentWeek);
                editor.apply();
            } else if (!GlobalVar.isNextWeek && GlobalVar.currentDayOfWeek == 6) {
                // 如果是星期六则马上周数要加1
                // 将数据保存下来
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putBoolean("isNextWeek", true);
                editor.apply();
            }
        }

        m_top_spinnerWeekNumSelect = (DropdownSpinner) findViewById(R.id.id_top_spinnerWeekSelect);
        // 为 Spinner 初始化数据
        for (int i = 1; i <= 25; i++) {
            if (GlobalVar.currentWeek.equals(i + "")) {
                m_top_spinnerWeekNumSelect.addItem("第" + i + "周(本周)");
            } else {
                m_top_spinnerWeekNumSelect.addItem("第" + i + "周");
            }
        }
        m_top_tvSpinnerShow.setText("第" + GlobalVar.currentWeek + "周▼");
        // 设置最小宽度
        m_top_spinnerWeekNumSelect.setMinWidth(this.getWindowManager().getDefaultDisplay().getWidth() / 2);
        m_top_spinnerWeekNumSelect.setVisibleItemNo(6);
        m_top_spinnerWeekNumSelect.setItemTextSize(20);
        m_top_spinnerWeekNumSelect.setItemPadding(25, 6, 25, 6);
        m_top_spinnerWeekNumSelect.setItemTextColor(getResources().getColor(R.color.transparent_blue));
        m_top_spinnerWeekNumSelect.setItemBackgroundColor(getResources().getColor(R.color.transparent_white));

        // 根据当前周数获得课表
        setSyllabusByWeekNumFromDB(GlobalVar.currentWeek);
    }

    /**
     * 从网上获得当前周期数
     * @param mSharedPreferences
     */
    private void setCurrentWeekFromNet(final SharedPreferences mSharedPreferences) {
        // 访问 URL 得到数据
        StringRequest request = new StringRequest(Request.Method.GET, Constant.RequestURL.CURRENT_WEEK_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String[] ss = s.split(":");
                GlobalVar.currentWeek = ss[ss.length - 1].substring(ss[ss.length - 1].indexOf("\"") + 1
                        , ss[ss.length - 1].lastIndexOf("\""));

                // 如果得到的数据大于25周，则归0
                if (Integer.valueOf(GlobalVar.currentWeek) >= 25) {
                    GlobalVar.currentWeek = "0";
                }

                // 将数据保存下来
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("currentWeek", GlobalVar.currentWeek);
                if (GlobalVar.currentDayOfWeek == 6) {
                    editor.putBoolean("isNextWeek", true);
                } else {
                    editor.putBoolean("isNextWeek", false);
                }
                editor.apply();

                if (m_top_tvSpinnerShow != null) {
                    m_top_tvSpinnerShow.setText("第" + GlobalVar.currentWeek + "周▼");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MainActivity.this, "当前周数获取失败，请手动选择", Toast.LENGTH_SHORT).show();
            }
        });
        request.setTag("CurrentWeek Request");
        MyApplication.getQueues().add(request);
    }

    /**
     * 初始化 Syllabus Tab View
     */
    private void initSyllabusTabViews() {
        TextView m_tabSyl_tvMonth = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvMonth);
        TextView m_tabSyl_tvSunday = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvSunday);
        TextView m_tabSyl_tvMonday = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvMonday);
        TextView m_tabSyl_tvTuesday = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvTuesday);
        TextView m_tabSyl_tvWednesday = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvWednesday);
        TextView m_tabSyl_tvThursday = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvThursday);
        TextView m_tabSyl_tvFriday = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvFriday);
        TextView m_tabSyl_tvSaturday = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvSaturday);

        m_tabSyl_tvHint = (TextView) tab_syllabus.findViewById(R.id.id_tabSyl_tvHint);
        m_tabSyl_linearLayoutShowInfo = (LinearLayout) tab_syllabus.findViewById(R.id.id_tabSyl_llShow);

        m_tabSyl_tvMonth.setText(GlobalVar.currentMonth + "月");

        int mSundayDate = GlobalVar.currentDay - GlobalVar.currentDayOfWeek + 1;
        m_tabSyl_tvSunday.setText(mSundayDate + "\n" + "星期天");
        m_tabSyl_tvMonday.setText(mSundayDate + 1 + "\n" + "星期一");
        m_tabSyl_tvTuesday.setText(mSundayDate + 2 + "\n" + "星期二");
        m_tabSyl_tvWednesday.setText(mSundayDate + 3 + "\n" + "星期三");
        m_tabSyl_tvThursday.setText(mSundayDate + 4 + "\n" + "星期四");
        m_tabSyl_tvFriday.setText(mSundayDate + 5 + "\n" + "星期五");
        m_tabSyl_tvSaturday.setText(mSundayDate + 6 + "\n" + "星期六");

        // 强调显示今天
        switch (GlobalVar.currentDayOfWeek) {
            case 1: {
                m_tabSyl_tvSunday.setBackground(getResources().getDrawable(R.drawable.textview_boder_light));
                break;
            }
            case 2: {
                m_tabSyl_tvMonday.setBackground(getResources().getDrawable(R.drawable.textview_boder_light));
                break;
            }
            case 3: {
                m_tabSyl_tvTuesday.setBackground(getResources().getDrawable(R.drawable.textview_boder_light));
                break;
            }
            case 4: {
                m_tabSyl_tvWednesday.setBackground(getResources().getDrawable(R.drawable.textview_boder_light));
                break;
            }
            case 5: {
                m_tabSyl_tvThursday.setBackground(getResources().getDrawable(R.drawable.textview_boder_light));
                break;
            }
            case 6: {
                m_tabSyl_tvFriday.setBackground(getResources().getDrawable(R.drawable.textview_boder_light));
                break;
            }
            case 7: {
                m_tabSyl_tvSaturday.setBackground(getResources().getDrawable(R.drawable.textview_boder_light));
                break;
            }
            default: {
                break;
            }
        }

        for (int i = 0; i < m_tabSyl_tvCourses.length; i++) {
            m_tabSyl_tvCourses[i] = (TextView) tab_syllabus.findViewById(tvCoursesIds[i]);
            m_tabSyl_tvCourses[i].setTextSize(12);
        }

        // 设置课程表信息
        setSyllabusData(mCurrentCourseWeekTable);

        // 如果数据库里已经有信息了，则直接显示
        if (mCurrentCourseWeekTable != null) {
            m_tabSyl_tvHint.setVisibility(View.GONE);
            m_tabSyl_linearLayoutShowInfo.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 初始化数据库
     */
    private void initDatabase() {
        mDBOpenHelper = new DBOpenHelper(getApplicationContext(), "db", null, Constant.SQLiteTableConfig.DB_VERSION);

        // 给成绩信息设置数据
        if (mDBOpenHelper.getGradeRecords() != null) {
            mGradeRecords = mDBOpenHelper.getGradeRecords();
            mGradeRecordsBackUp = mGradeRecords;
        }

        // 给课程表设置数据
        if (mDBOpenHelper.getSyllabus() != null) {
            mAllCourseWeekTable = mDBOpenHelper.getSyllabus();
        }
    }

    /**
     * 初始化 Grade Tab Events
     */
    private void initGradeTabEvents() {
        m_tabGrade_swipeRefreshLayout.setOnRefreshListener(this);
//        // 上拉刷新
//        m_tabGrade_recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mGradeRecordsAdapter.getItemCount()) {
//                    m_tabGrade_swipeRefreshLayout.setRefreshing(true);
//                    Log.i("Refresh", "refreshing");
//                    // 获得成绩信息
//                    getGradeRecords();
//                }
//            }
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
//            }
//        });

        m_tabGrade_tvCourseCredit.setOnClickListener(this);
        m_tabGrade_tvCourseName.setOnClickListener(this);
        m_tabGrade_tvCoursePoint.setOnClickListener(this);
        m_tabGrade_tvGradeScore.setOnClickListener(this);

        mGradeRecordsAdapter.setOnItemClickListener(new GradeRecordsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 跳转到成绩信息详细页
                TextView tv = (TextView) view.findViewById(R.id.id_item_tvCourseName);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("gradeRecordName", tv.getText() + "");
                intent.putExtras(bundle);
                intent.setClass(MainActivity.this, GradeRecordDetailActivity.class);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }

    /**
     * 初始化 Grade Tab View
     */
    private void initGradeTabViews() {
        m_tabGrade_tvCourseCredit = (TextView) tab_grade.findViewById(R.id.id_tabGrade_tvCourseCredit);
        m_tabGrade_tvCourseName = (TextView) tab_grade.findViewById(R.id.id_tabGrade_tvCourseName);
        m_tabGrade_tvCoursePoint = (TextView) tab_grade.findViewById(R.id.id_tabGrade_tvCoursePoint);
        m_tabGrade_tvGradeScore = (TextView) tab_grade.findViewById(R.id.id_tabGrade_tvGradeScore);

        // init Tab Grade RecyclerView
        RecyclerView m_tabGrade_recyclerView = (RecyclerView) tab_grade.findViewById(R.id.id_tabGrade_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        // 设置RecyclerView的显示方式
        m_tabGrade_recyclerView.setLayoutManager(linearLayoutManager);

        mGradeRecordsAdapter = new GradeRecordsAdapter(this, mGradeRecords);
        m_tabGrade_recyclerView.setAdapter(mGradeRecordsAdapter);

        m_tabGrade_tvHint = (TextView) tab_grade.findViewById(R.id.id_tabGrade_tvHint);
        m_tabGrade_linearLayoutShowInfo = (LinearLayout) tab_grade.findViewById(R.id.id_tabGrade_LinearLayoutShowInfo);

        m_tabGrade_recyclerView.setHasFixedSize(true);
        m_tabGrade_recyclerView.setItemAnimator(new DefaultItemAnimator());

        // 如果数据库中已经有数据了，那么则直接显示数据
        if (!mGradeRecords.isEmpty()) {
            m_tabGrade_tvHint.setVisibility(View.GONE);
            m_tabGrade_linearLayoutShowInfo.setVisibility(View.VISIBLE);
        }

        m_tabGrade_swipeRefreshLayout = (SwipeRefreshLayout) tab_grade.findViewById(R.id.id_tabGrade_SwipeRefreshWidget);
        m_tabGrade_swipeRefreshLayout.setColorSchemeColors(R.color.refresh_color1, R.color.refresh_color2, R.color.refresh_color3, R.color.refresh_color4);

        // 这句话是为了，第一次进入页面的时候显示加载进度条
        m_tabGrade_swipeRefreshLayout.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
    }

    /**
     * 初始化事件
     */
    private void initMainEvents() {

        // mImgBtnLogin.setOnClickListener(this);
        mTvLoginInfo.setOnClickListener(this);

        mLLTabSyllabus.setOnClickListener(this);
        mLLTabGrade.setOnClickListener(this);
        mLLTabOther.setOnClickListener(this);

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshBottomShow();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 刷新底部 tab 栏显示效果
     */
    private void refreshBottomShow() {
        int currentItem = mViewPager.getCurrentItem();
        resetTextColor();
        // 将一些特定的 View 隐藏起来
        resetTopViewsShow();
        switch (currentItem) {
            case Constant.TabLog.TAB_SYLLABUS: {
                m_top_syllabusTabFl.setVisibility(View.VISIBLE);
                m_top_syllabusTabLl.setVisibility(View.VISIBLE);

                mTvShowSyllabus.setBackgroundColor(getResources().getColor(R.color.tab_show_selected));
                mTvTextSyllabus.setTextColor(getResources().getColor(R.color.tab_text_selected));
                break;
            }
            case Constant.TabLog.TAB_GRADE: {
                // 将 UserInfo width 设置为 wrap_content weight 设置为 0
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, 0);
                m_top_loginInfoLl.setLayoutParams(params);

                m_top_gradeRecordTabLl.setVisibility(View.VISIBLE);
                mTvShowGrade.setBackgroundColor(getResources().getColor(R.color.tab_show_selected));
                mTvTextGrade.setTextColor(getResources().getColor(R.color.tab_text_selected));
                break;
            }
            case Constant.TabLog.TAB_OTHER: {
                mTvShowOther.setBackgroundColor(getResources().getColor(R.color.tab_show_selected));
                mTvTextOther.setTextColor(getResources().getColor(R.color.tab_text_selected));
                break;
            }
            default: {
                break;
            }
        }
    }

    /**
     * m_top_listView
     */
    private void resetTopViewsShow() {
        // 将 UserInfo width 设置为0 weight 设置为 1
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,
                ViewGroup.LayoutParams.MATCH_PARENT, 1);
        m_top_loginInfoLl.setLayoutParams(params);

        m_top_syllabusTabFl.setVisibility(View.GONE);
        m_top_syllabusTabLl.setVisibility(View.GONE);

        m_top_searchViewGradeRecordSearch.setIconified(true);
        m_top_gradeRecordTabLl.setVisibility(View.GONE);
    }

    /**
     * 初始化 Views
     */
    private void initMainViews() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewPager);

        // mImgBtnLogin = (ImageButton) findViewById(R.id.id_ImBtnLogin);
        mTvLoginInfo = (TextView) findViewById(R.id.id_tvLoginInfo);

        // Tab
        mLLTabSyllabus = (LinearLayout) findViewById(R.id.id_ll_tab_syllabus);
        mLLTabGrade = (LinearLayout) findViewById(R.id.id_ll_tab_grade);
        mLLTabOther = (LinearLayout) findViewById(R.id.id_ll_tab_other);

        // TextView
        mTvTextSyllabus = (TextView) findViewById(R.id.id_tvText_tab_syllabus);
        mTvShowSyllabus = (TextView) findViewById(R.id.id_tvShow_tab_syllabus);
        mTvTextGrade = (TextView) findViewById(R.id.id_tvText_tab_grade);
        mTvShowGrade = (TextView) findViewById(R.id.id_tvShow_tab_grade);
        mTvTextOther = (TextView) findViewById(R.id.id_tvText_tab_other);
        mTvShowOther = (TextView) findViewById(R.id.id_tvShow_tab_other);

        LayoutInflater mInflater = LayoutInflater.from(this);
        tab_syllabus = mInflater.inflate(R.layout.tab_syllabus, null);
        tab_grade = mInflater.inflate(R.layout.tab_grade, null);
        tab_other = mInflater.inflate(R.layout.tab_other, null);

        mViewList.add(tab_syllabus);
        mViewList.add(tab_grade);
        mViewList.add(tab_other);

        // 新建适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViewList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        mViewPager.setAdapter(mPagerAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // 点击登录按钮 id_ImBtnLogin
            // case R.id.id_ImBtnLogin:
            case R.id.id_tvLoginInfo: {
                // 当未登录时，才出现登录界面
                if (mTvLoginInfo.getText().toString().trim().equals(Constant.Tag.UNLOGIN_TAG)) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Constant.RequestCode.LOGIN_CODE);
                }
                break;
            }
            case R.id.id_ll_tab_syllabus: {
                resetTextColor();
                resetTopViewsShow();
                m_top_syllabusTabFl.setVisibility(View.VISIBLE);
                m_top_syllabusTabLl.setVisibility(View.VISIBLE);
                mViewPager.setCurrentItem(Constant.TabLog.TAB_SYLLABUS);
                mTvShowSyllabus.setBackgroundColor(getResources().getColor(R.color.tab_show_selected));
                mTvTextSyllabus.setTextColor(getResources().getColor(R.color.tab_text_selected));
                break;
            }
            case R.id.id_ll_tab_grade: {
                resetTextColor();
                resetTopViewsShow();
                // 登录信息 width weight
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, 0);
                m_top_loginInfoLl.setLayoutParams(params);
                m_top_searchViewGradeRecordSearch.setVisibility(View.VISIBLE);
                mViewPager.setCurrentItem(Constant.TabLog.TAB_GRADE);
                mTvShowGrade.setBackgroundColor(getResources().getColor(R.color.tab_show_selected));
                mTvTextGrade.setTextColor(getResources().getColor(R.color.tab_text_selected));
                break;
            }
            case R.id.id_ll_tab_other: {
                resetTextColor();
                resetTopViewsShow();
                mViewPager.setCurrentItem(Constant.TabLog.TAB_OTHER);
                mTvShowOther.setBackgroundColor(getResources().getColor(R.color.tab_show_selected));
                mTvTextOther.setTextColor(getResources().getColor(R.color.tab_text_selected));
                break;
            }
            // 在成绩页面点击名称
            case R.id.id_tabGrade_tvCourseName: {
                if (m_tabGrade_tvCourseName.getText().toString().trim().equals("名称")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCourseName.setText("名称↓");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByNameDes());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else if (m_tabGrade_tvCourseName.getText().toString().trim().equals("名称↓")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCourseName.setText("名称↑");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByNameAsc());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCourseName.setText("名称");
                    if (mGradeRecordsQueryResult != null && !mGradeRecordsQueryResult.isEmpty()) {
                        mGradeRecords = mGradeRecordsQueryResult;
                    } else {
                        mGradeRecords = mDBOpenHelper.getGradeRecords();
                    }
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                }
                break;
            }
            // 在成绩页面点击得分
            case R.id.id_tabGrade_tvGradeScore: {
                if (m_tabGrade_tvGradeScore.getText().toString().trim().equals("得分")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvGradeScore.setText("得分↓");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByScoreDes());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else if (m_tabGrade_tvGradeScore.getText().toString().trim().equals("得分↓")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvGradeScore.setText("得分↑");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByScoreAsc());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else {
                    restoreGradeTabTextView();
                    m_tabGrade_tvGradeScore.setText("得分");
                    if (mGradeRecordsQueryResult != null && !mGradeRecordsQueryResult.isEmpty()) {
                        mGradeRecords = mGradeRecordsQueryResult;
                    } else {
                        mGradeRecords = mDBOpenHelper.getGradeRecords();
                    }
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                }
                break;
            }
            // 在成绩页面点击学分
            case R.id.id_tabGrade_tvCourseCredit: {
                if (m_tabGrade_tvCourseCredit.getText().toString().trim().equals("学分")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCourseCredit.setText("学分↓");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByCourseCreditDes());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else if (m_tabGrade_tvCourseCredit.getText().toString().trim().equals("学分↓")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCourseCredit.setText("学分↑");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByCourseCreditAsc());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCourseCredit.setText("学分");
                    if (mGradeRecordsQueryResult != null && !mGradeRecordsQueryResult.isEmpty()) {
                        mGradeRecords = mGradeRecordsQueryResult;
                    } else {
                        mGradeRecords = mDBOpenHelper.getGradeRecords();
                    }
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                }
                break;
            }
            // 在成绩页面点击绩点
            case R.id.id_tabGrade_tvCoursePoint: {
                if (m_tabGrade_tvCoursePoint.getText().toString().trim().equals("绩点")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCoursePoint.setText("绩点↓");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByCoursePointDes());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else if (m_tabGrade_tvCoursePoint.getText().toString().trim().equals("绩点↓")) {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCoursePoint.setText("绩点↑");
                    Collections.sort(mGradeRecords, new GradeRecordsSort.SortByCoursePointAsc());
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                } else {
                    restoreGradeTabTextView();
                    m_tabGrade_tvCoursePoint.setText("绩点");
                    if (mGradeRecordsQueryResult != null && !mGradeRecordsQueryResult.isEmpty()) {
                        mGradeRecords = mGradeRecordsQueryResult;
                    } else {
                        mGradeRecords = mDBOpenHelper.getGradeRecords();
                    }
                    mGradeRecordsAdapter.changeData(mGradeRecords);
                }
                break;
            }
            // 点击退出当前登录按钮
            case R.id.id_tabOther_btnLogout: {
                if (!isLogin) {
                    Toast.makeText(MainActivity.this, "当前无已登录的账号", Toast.LENGTH_SHORT).show();
                } else {
                    userLogout();
                }
                break;
            }
            // 点击清除本地数据
            case R.id.id_tabOther_btnClearLocalData: {
                // 清除数据库中的数据
                mDBOpenHelper.clearAllTables();
                // 重新载入视图
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MainActivity.class);
                userLogout();
                startActivity(intent);
                finish();
                break;
            }
            // 点击 Other Tab 里的登录
            case R.id.id_tabOther_btnUser: {
                if (m_tabOther_btnUser.getText().toString().trim().equals(Constant.Tag.UNLOGIN_TAG)) {
                    Intent intent = new Intent();
                    intent.setClass(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Constant.RequestCode.LOGIN_CODE);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private void userLogout() {
        GlobalVar.username = Constant.Tag.UNLOGIN_TAG;
        mTvLoginInfo.setText(GlobalVar.username);
        m_tabOther_btnUser.setText(GlobalVar.username);
        Toast.makeText(MainActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
        isLogin = false;
    }

    private void restoreGradeTabTextView() {
        m_tabGrade_tvCourseName.setText("名称");
        m_tabGrade_tvGradeScore.setText("得分");
        m_tabGrade_tvCourseCredit.setText("学分");
        m_tabGrade_tvCoursePoint.setText("绩点");
    }

    /**
     * 将所有标签都换回未选中的颜色
     */
    private void resetTextColor() {
        mTvShowGrade.setBackgroundColor(getResources().getColor(R.color.tab_show_no_selected));
        mTvShowOther.setBackgroundColor(getResources().getColor(R.color.tab_show_no_selected));
        mTvShowSyllabus.setBackgroundColor(getResources().getColor(R.color.tab_show_no_selected));

        mTvTextGrade.setTextColor(getResources().getColor(R.color.tab_text_no_selected));
        mTvTextOther.setTextColor(getResources().getColor(R.color.tab_text_no_selected));
        mTvTextSyllabus.setTextColor(getResources().getColor(R.color.tab_text_no_selected));
    }

    /**
     * 获取并设置用户名
     */
    private void setUserName() {
        StringRequest request = new StringRequest(Request.Method.GET, Constant.RequestURL.USERNAME, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                String temp = s.split(":")[1];
                GlobalVar.username = temp.substring(temp.indexOf("\"") + 1, temp.lastIndexOf("\""));
                mTvLoginInfo.setText(GlobalVar.username);
                m_tabOther_btnUser.setText(GlobalVar.username);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                GlobalVar.username = "已登录";
                mTvLoginInfo.setText(GlobalVar.username);
                m_tabOther_btnUser.setText(GlobalVar.username);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", GlobalVar.cookie);
                return headers;
            }
        };
        request.setTag("Username request");
        MyApplication.getQueues().add(request);
    }

    /**
     * 获得当前学期课表信息并转换成 CourseWeekTable 类
     */
    private void getSyllabusDefault() {
        String url = Constant.RequestURL.COURSE_URL + "?term=" + GlobalVar.currentTerm + "&week=" + GlobalVar.defaultWeek;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement el = parser.parse(jsonObject.toString());
                mAllCourseWeekTable = gson.fromJson(el, CourseWeekTable.class);

                // 存入数据库
                mDBOpenHelper.saveSyllabus(mAllCourseWeekTable);

                // 设置课程表信息
                if (mDBOpenHelper.getSyllabus(GlobalVar.IDNumber, GlobalVar.currentTerm, GlobalVar.currentWeek) != null) {
                    mCurrentCourseWeekTable = mDBOpenHelper.getSyllabus(GlobalVar.IDNumber, GlobalVar.currentTerm, GlobalVar.currentWeek);
                    setSyllabusData(mCurrentCourseWeekTable);
                } else {
                    setSyllabusData(mAllCourseWeekTable);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // 错误提示
                requestErrorHint();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", GlobalVar.cookie);
                return headers;
            }
        };

        request.setTag("SyllabusDefault Request");
        MyApplication.getQueues().add(request);
    }

    /**
     * 获取成绩信息并转换成 GradeRecord 类
     */
    private void getGradeRecords() {
        JsonArrayRequest request = new JsonArrayRequest(Constant.RequestURL.GRADES_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {

                // 如果没得到则提示用户刷新
                if (jsonArray.toString().equals("[]")) {
                    Toast.makeText(MainActivity.this, "获取课程信息失败，请下拉刷新尝试重新获取", Toast.LENGTH_SHORT).show();
                    // 停止刷新显示
                    if (m_tabGrade_swipeRefreshLayout.isRefreshing()) {
                        m_tabGrade_swipeRefreshLayout.setRefreshing(false);
                    }
                    return;
                }

                // 清空内存中的数据
                mGradeRecords.clear();

                // 将 JSONArray 转换成 对象
                Gson gson = new Gson();
                JsonParser parser = new JsonParser();
                JsonElement el = parser.parse(jsonArray.toString());
                JsonArray ja;
                if (el.isJsonArray()) {
                    ja = el.getAsJsonArray();

                    Iterator it = ja.iterator();
                    while (it.hasNext()) {
                        GradeRecord gr;
                        JsonElement e = (JsonElement) it.next();
                        gr = gson.fromJson(e, GradeRecord.class);
                        // 将对象添加到成绩列表中
                        mGradeRecords.add(gr);
                    }
                    // 刷新 GradeTab 显示内容
                    refreshGradeTabShow();
                    // 将内容备份
                    mGradeRecordsBackUp = mGradeRecords;
                    // 将内容写入数据库中
                    mDBOpenHelper.saveGradeRecords(mGradeRecords);
                }

                // 停止刷新显示
                if (m_tabGrade_swipeRefreshLayout.isRefreshing()) {
                    m_tabGrade_swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                }

                // 将搜索内容清空
                m_top_searchViewGradeRecordSearch.setIconified(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // 错误提示
                requestErrorHint();

                // 停止刷新显示
                if (m_tabGrade_swipeRefreshLayout.isRefreshing()) {
                    m_tabGrade_swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "刷新成功", Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", GlobalVar.cookie);
                return headers;
            }
        };

        request.setTag("GradeRecord request");
        MyApplication.getQueues().add(request);
    }

    /**
     * 刷新 GradeTab 显示内容
     */
    private void refreshGradeTabShow() {
        // 得到成绩数据后刷新 recyclerView 数据
        mGradeRecordsAdapter.changeData(mGradeRecords);
        // 如果提示还在显示
        if (m_tabGrade_tvHint.getVisibility() == View.VISIBLE) {
            m_tabGrade_tvHint.setVisibility(View.GONE);
        }
        // 如果数据没有显示的话
        if (m_tabGrade_linearLayoutShowInfo.getVisibility() == View.GONE) {
            m_tabGrade_linearLayoutShowInfo.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constant.RequestCode.LOGIN_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(MainActivity.this, "正在刷新课程表和成绩信息，请稍等...", Toast.LENGTH_SHORT).show();
                // 已经登录
                isLogin = true;
                // 设置用户名
                setUserName();
                // 清除课程表信息
                clearSyllabusTvShow();
                // 清除成绩信息
                clearGradeRecords();
                // 得到课程表信息
                getSyllabusDefault();
                // 得到成绩信息
                getGradeRecords();

            }
        }
    }

    /**
     * 清除成绩信息
     */
    private void clearGradeRecords() {
        mGradeRecords.clear();
        mGradeRecordsAdapter.changeData(mGradeRecords);
    }

    /**
     * 清除课程表信息
     */
    private void clearSyllabusTvShow() {
        for (int i = 0; i < m_tabSyl_tvCourses.length; i++) {
            m_tabSyl_tvCourses[i].setText("");
            m_tabSyl_tvCourses[i].setBackgroundResource(R.drawable.course_textview_default);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 刷新底部 tab 栏显示效果
        refreshBottomShow();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        if (isLogin) {
            // 获得教务处成绩
            getGradeRecords();
        } else {
            m_tabGrade_swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 给课程表设置数据
     */
    private void setSyllabusData(CourseWeekTable courseWeekTable) {
        if (courseWeekTable == null) {
            return;
        }

        /**
         * 第一遍所有课程
         */
        int position = 0;
        for (CourseDayLine courseDayLine : mAllCourseWeekTable.getCourseDayLines()) {
            for (CourseDetail courseDetail : courseDayLine.getCourses()) {
                if (courseDetail != null) {
                    if (courseDetail.getCourseName() != null) {
                        // 如果一个位置有多节课
                        if (courseDetail.getCourseName().contains("@")) {
                            String show = "(...)\n" + courseDetail.getCourseName().split("@")[0].trim()
                                    + "@" + courseDetail.getAddress().split("@")[0].trim();
                            m_tabSyl_tvCourses[position].setText(show.trim());
                        } else {
                            m_tabSyl_tvCourses[position].setText(courseDetail.getCourseName() + "@"
                                    + courseDetail.getAddress());
                        }
                        m_tabSyl_tvCourses[position].setBackgroundResource(R.drawable.course_textview_empty);
                        m_tabSyl_tvCourses[position].setTextColor(Color.parseColor("#e092989B"));
                        // 使其可见
                        m_tabSyl_tvCourses[position].setVisibility(View.VISIBLE);
                    } else {
                        Log.i("Course Detail", "COURSE NAME NULL");
                    }
                    position++;
                } else {
                    Log.i("Course Detail", "NULL");
                }
            }
            // 如果数据中一天只有五节大课，那么则要手动加一，使其成为一天六节大课
            if (position % 6 != 0) {
                position++;
            }
        }

        /**
         * 第二遍绘制本周课程
         */
        HashMap<String, Integer> styleMap = new HashMap<>();
        int i = 0; // 用来记录当前使用到第几个style

        // 记录当前课程的在课程表中的位置
        position = 0;
        for (CourseDayLine courseDayLine : courseWeekTable.getCourseDayLines()) {
            for (CourseDetail courseDetail : courseDayLine.getCourses()) {
                if (courseDetail != null) {
                    if (courseDetail.getCourseName() != null) {
                        // 如果一个位置有多节课
                        if (courseDetail.getCourseName().contains("@")) {
                            String show = "(...)\n" + courseDetail.getCourseName().split("@")[0].trim()
                                    + "@" + courseDetail.getAddress().split("@")[0].trim();
                            m_tabSyl_tvCourses[position].setText(show.trim());
                        } else {
                            m_tabSyl_tvCourses[position].setText(courseDetail.getCourseName() + "@"
                                    + courseDetail.getAddress());
                        }
                        // 相同课程设置相同样式，不同课程设置不同样式
                        if (!styleMap.containsKey(courseDetail.getCourseName().split("@")[0])) {
                            m_tabSyl_tvCourses[position].setBackgroundResource(courseTextViewBoder[i]);
                            m_tabSyl_tvCourses[position].setTextColor(Color.parseColor("#ffffff"));
                            styleMap.put(courseDetail.getCourseName().split("@")[0], courseTextViewBoder[i]);
                            if (++i >= courseTextViewBoder.length) {
                                i = 0;
                            }
                        } else {
                            m_tabSyl_tvCourses[position].setBackgroundResource(
                                    styleMap.get(courseDetail.getCourseName().split("@")[0]));
                            m_tabSyl_tvCourses[position].setTextColor(Color.parseColor("#ffffff"));
                        }
                        // 使其可见
                        m_tabSyl_tvCourses[position].setVisibility(View.VISIBLE);
                    } else {
                        Log.i("Course Detail", "COURSE NAME NULL");
                    }
                    position++;
                } else {
                    Log.i("Course Detail", "NULL");
                }
            }
            // 如果数据中一天只有五节大课，那么则要手动加一，使其成为一天六节大课
            if (position % 6 != 0) {
                position++;
            }
        }
        if (m_tabSyl_tvHint.getVisibility() == View.VISIBLE) {
            m_tabSyl_tvHint.setVisibility(View.GONE);
        }
        if (m_tabSyl_linearLayoutShowInfo.getVisibility() == View.GONE) {
            m_tabSyl_linearLayoutShowInfo.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 请求错误，提示
     */
    private void requestErrorHint() {
        Toast.makeText(MainActivity.this, "网络连接错误，请检查网络连接", Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据周数获得课表信息
     */
    public void setSyllabusByWeekNumFromDB(String currentWeek) {
        if (mDBOpenHelper.getSyllabus(GlobalVar.IDNumber, GlobalVar.currentTerm, currentWeek) != null) {
            mCurrentCourseWeekTable = mDBOpenHelper.getSyllabus(GlobalVar.IDNumber, GlobalVar.currentTerm, currentWeek);
        } else if (mDBOpenHelper.getSyllabus() != null) {
            mCurrentCourseWeekTable = mDBOpenHelper.getSyllabus();
        }
    }
}
