package com.gzyyu.weather;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class LaunchActivity extends AppCompatActivity {

    private static final long SPLASH_DELAY_MILLIS = 3000;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //启动页全屏
        full(true);

        setContentView(R.layout.activity_launch);

        SharedPreferences setting = getSharedPreferences("com.gzyyu.weather", 0);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {// 第一次则跳转到欢迎页面
            setting.edit().putBoolean("FIRST", false).commit();
            firstLoad();
        } else {//如果是第二次启动则直接跳转到主页面
            noFirstLoad();
        }


    }

    private void noFirstLoad() {
        // 使用Handler的postDelayed方法，3秒后执行跳转到MainActivity
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY_MILLIS);

    }

    private void firstLoad() {
        handler.postDelayed(new Runnable() {  //使用handler的postDelayed实现延时跳转

            public void run() {
                Intent intent = new Intent(LaunchActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY_MILLIS);//2秒后跳转至应用主界面MainActivity
    }

    /*启动页全屏*/
    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(lp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams attr = getWindow().getAttributes();
            attr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }
}
