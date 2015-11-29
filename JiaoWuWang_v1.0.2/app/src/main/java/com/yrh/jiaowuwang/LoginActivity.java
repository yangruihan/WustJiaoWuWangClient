package com.yrh.jiaowuwang;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.yrh.jiaowuwang.utils.Constant;
import com.yrh.jiaowuwang.utils.DBOpenHelper;
import com.yrh.jiaowuwang.utils.GlobalVar;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yrh on 2015/10/27.
 * 用户登录 Activity
 */
public class LoginActivity extends AppCompatActivity {

    private EditText mEtIDNumber;       // 输入学号
    private EditText mEtPassword;       // 输入密码
    private EditText mEtVerifyCode;     // 输入验证码
    private ImageView mImgVerifyCode;   // 验证码
    private Button mBtnLogin;           // 登录按钮
    private TextView mTvLoginBack;      // 返回按钮
    private CheckBox mCbRememberPsd;    // 记住密码
    private TextView mTvRememberPsd;    // 记住密码
    private TextView mTvRememberIDNumber;// 记住账号
    private CheckBox mCbRememberIDNumber;// 记住账号

    DBOpenHelper mDBOpenHelper;

    private String verifyCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mDBOpenHelper = new DBOpenHelper(getApplicationContext(), "db", null, Constant.SQLiteTableConfig.DB_VERSION);

        // 初始化 Views
        initViews();

        // 获得验证码图片
        getVerifyCodeImg();

        // 初始化事件
        initEvents();
    }

    /**
     * 初始化事件
     */
    private void initEvents() {
        // 当点击验证码时，刷新验证码
        mImgVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "正在刷新验证码，请稍等...", Toast.LENGTH_SHORT).show();
                // 重新获得验证码
                getVerifyCodeImg();
            }
        });

        // 当点击返回时
        mTvLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

        // 当点击登录按钮时，提交表单数据
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果记住密码勾选，则将密码保存下来
                SharedPreferences mSharedPreferences = getSharedPreferences("data", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString("IDNumber", mEtIDNumber.getText().toString().trim());
                if (mCbRememberIDNumber.isChecked()) {
                    editor.putBoolean("rememberIDNumber", true);
                } else {
                    editor.putBoolean("rememberIDNumber", false);
                }
                if (mCbRememberPsd.isChecked()) {
                    editor.putString("password", mEtPassword.getText().toString().trim());
                    editor.putBoolean("rememberPsd", true);
                } else {
                    editor.putBoolean("rememberPsd", false);
                }
                editor.apply();

                Toast.makeText(LoginActivity.this, "正在登录，请稍等...", Toast.LENGTH_SHORT).show();

                // 新建一个 Post 请求
                StringRequest request = new StringRequest(Request.Method.POST, Constant.RequestURL.LOGIN_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Log.i("Login----Info", s);

                        // 如果登录成功
                        if (s.equals("success")) {
                            // 登录成功提示
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();

                            // 登录成功时清除数据库里有的数据
                            mDBOpenHelper.clearAllTables();

                            setResult(RESULT_OK);
                            finish();
                        } else {
                            // 登录失败提示
                            Toast.makeText(LoginActivity.this, "登录失败，请检查用户名、密码以及验证码是否输入正确", Toast.LENGTH_SHORT).show();
                            // 刷新验证码
                            getVerifyCodeImg();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        // 登录失败提示
                        Toast.makeText(LoginActivity.this, "网络连接中断，请检查网络设置", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    // 覆写父类方法，设置 POST 参数
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        GlobalVar.IDNumber = mEtIDNumber.getText().toString().trim();
                        GlobalVar.password = mEtPassword.getText().toString().trim();
                        verifyCode = mEtVerifyCode.getText().toString().trim();

                        Map<String, String> map = new HashMap<>();
                        map.put("username", GlobalVar.IDNumber);
                        map.put("password", GlobalVar.password);
                        map.put("verifyCode", verifyCode);
                        return map;
                    }

                    // 覆写父类方法，设置 Cookies
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Cookie", GlobalVar.cookie);
                        return headers;
                    }
                };

                request.setTag("Login request");
                MyApplication.getQueues().add(request);
            }
        });

        // 当点击记住账号文字时
        mTvRememberIDNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCbRememberIDNumber.isChecked()) {
                    // 将记住密码和记住账号一起取消
                    mCbRememberIDNumber.setChecked(false);
                    mCbRememberPsd.setChecked(false);
                } else {
                    mCbRememberIDNumber.setChecked(true);
                }
            }
        });

        // 当点击记住账号 checkbox 时
        mCbRememberIDNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCbRememberIDNumber.isChecked()) {
                    // 将记住密码和记住账号一起取消
                    mCbRememberPsd.setChecked(false);
                }
            }
        });

        // 当点击记住密码文字时
        mTvRememberPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCbRememberPsd.isChecked()) {
                    mCbRememberPsd.setChecked(false);
                } else {
                    // 将记住账号和记住密码一起打勾
                    mCbRememberIDNumber.setChecked(true);
                    mCbRememberPsd.setChecked(true);
                }
            }
        });

        // 当点击记住密码 checkbox 时
        mCbRememberPsd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCbRememberPsd.isChecked()) {
                    mCbRememberIDNumber.setChecked(true);
                }
            }
        });
    }

    /**
     * 获得验证码图片
     */
    private void getVerifyCodeImg() {
        ImageRequest request = new ImageRequest(Constant.RequestURL.VERIFY_URL, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap bitmap) {

                // 缩放 bitmap
                Matrix matrix = new Matrix();
                matrix.postScale(3f, 3f);
                Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                // 成功获得验证码图片后显示
                mImgVerifyCode.setImageBitmap(newBitmap);
            }
        }, 0, 0, Bitmap.Config.RGB_565, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                // 图片获取失败提示
                Toast.makeText(LoginActivity.this, "验证码获取失败，请检查网络", Toast.LENGTH_SHORT).show();
            }
        }) {
            // 获得 Cookies
            @Override
            protected Response<Bitmap> parseNetworkResponse(NetworkResponse response) {
                Map<String, String> responseHeaders = response.headers;
                GlobalVar.cookie = responseHeaders.get("Set-Cookie");
                return super.parseNetworkResponse(response);
            }
        };
        request.setTag("VerifyCodeImage Request");
        MyApplication.getQueues().add(request);
    }

    /**
     * 初始化 Views
     */
    private void initViews() {
        mEtIDNumber = (EditText) findViewById(R.id.id_EtIDNumber);
        mEtPassword = (EditText) findViewById(R.id.id_EtPassword);
        mEtVerifyCode = (EditText) findViewById(R.id.id_EtVerifyCode);
        mImgVerifyCode = (ImageView) findViewById(R.id.id_ImgVerifyCode);
        mBtnLogin = (Button) findViewById(R.id.id_BtnLogin);
        mTvLoginBack = (TextView) findViewById(R.id.id_tvLoginBack);
        mCbRememberPsd = (CheckBox) findViewById(R.id.id_cBRememberPsd);
        mTvRememberPsd = (TextView) findViewById(R.id.id_tvRememberPsd);
        mCbRememberIDNumber = (CheckBox) findViewById(R.id.id_cBRememberIDNumber);
        mTvRememberIDNumber = (TextView) findViewById(R.id.id_tvRememberIDNumber);

        // 读取保存的密码
        SharedPreferences mSharedPreferences = getSharedPreferences("data", Activity.MODE_PRIVATE);
        boolean rememberPsd = mSharedPreferences.getBoolean("rememberPsd", false);
        boolean rememberIDNumber = mSharedPreferences.getBoolean("rememberIDNumber", false);
        if (rememberPsd) {
            String IDNumber = mSharedPreferences.getString("IDNumber", "");
            String password = mSharedPreferences.getString("password", "");
            mEtIDNumber.setText(IDNumber);
            mEtPassword.setText(password);
            // 将焦点置于填写验证码
            mEtVerifyCode.requestFocus();
            // 并将记住账号和记住密码默认打勾
            mCbRememberIDNumber.setChecked(true);
            mCbRememberPsd.setChecked(true);
        } else if (rememberIDNumber) {
            String IDNumber = mSharedPreferences.getString("IDNumber", "");
            mEtIDNumber.setText(IDNumber);
            // 将焦点置于填写验证码
            mEtPassword.requestFocus();
            // 并将记住账号默认打勾
            mCbRememberIDNumber.setChecked(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
