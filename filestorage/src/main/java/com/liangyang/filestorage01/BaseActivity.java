package com.liangyang.filestorage01;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * 切换主题BaseActivity
 */
public class BaseActivity extends AppCompatActivity {

    private WindowManager mWindowManager;
    private SharedPreferences skinSp;
    private final static String DAY = "day";
    private final static String NIGHT = "night";
    public TextView myView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWindowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        //初始化视图
        intView();
    }

    /**
     * 初始化视图
     */
    private void intView() {
        skinSp = this.getSharedPreferences("skinchange", Context.MODE_PRIVATE);//SharedPreferences
        changeViewMode();
    }

    public void changeViewMode() {
        String mode = skinSp.getString("skin", "");
        if (mode != null || !mode.equals("")) {
            if (mode.equals(NIGHT)) {
                night();
            } else {
                day();
            }
        }
    }

    /**
     * 白天模式
     */
    public void day() {
        if (myView != null) {
            mWindowManager.removeView(myView);
            SharedPreferences.Editor edit = skinSp.edit();
            edit.putString("skin", DAY);
            edit.commit();
        }

    }

    /**
     * 夜间模式
     */
    public void night() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                        | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );
        params.gravity = Gravity.BOTTOM;
        params.y = 10;
        if (myView == null) {
            myView = new TextView(this);
            myView.setBackgroundColor(0x80000000);
        }
        mWindowManager.addView(myView, params);
        SharedPreferences.Editor edit = skinSp.edit();
        edit.putString("skin", NIGHT);
        edit.commit();

    }


//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        String mode = skinSp.getString("skin", "");
//        if (mode.equals(NIGHT)) {
//            removeSkin();
//        }
//    }
//
//    private void removeSkin() {
//        if (myView != null) {
//            mWindowManager.removeView(myView);
//            SharedPreferences.Editor edit = skinSp.edit();
//            edit.putString("skin", DAY);
//            edit.commit();
//        }
//    }
}
