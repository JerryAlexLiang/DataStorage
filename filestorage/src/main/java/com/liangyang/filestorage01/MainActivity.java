package com.liangyang.filestorage01;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Button mSimulateLogin;
    private Button mSimulateDiary;
    private Button mModeSwitchBtn;
    private int flage = 0;
    private WindowManager mWindowManager;
    private SharedPreferences skinSp;
    private final static String DAY = "day";
    private final static String NIGHT = "night";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化视图
        mModeSwitchBtn = (Button) findViewById(R.id.btn_mode_switch);//模式切换按钮
        mSimulateLogin = (Button) findViewById(R.id.simulate_login);//模拟登陆
        mSimulateDiary = (Button) findViewById(R.id.simulate_diary);
        //设置切换模式Button点击监听事件
        mModeSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flage % 2 == 0) {
                    //点击切换夜间模式
                    night();
                    mModeSwitchBtn.setText("点击切换白天模式");
                    mModeSwitchBtn.setTextColor(Color.WHITE);
                    flage++;
                } else {
                    //点击一次切换白天模式
                    day();
                    mModeSwitchBtn.setText("点击切换夜间模式");
                    mModeSwitchBtn.setTextColor(Color.BLACK);
                    flage++;
                }
            }
        });
        mSimulateLogin.setOnClickListener(this);
        mSimulateDiary.setOnClickListener(this);

    }


    /**
     * 设置Button的点击监听事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        //跳转页面
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.simulate_login:
                //跳转模拟登陆页面
                intent.setClass(this, SimulateLoginActivity.class);
                break;

            case R.id.simulate_diary:
                //跳转简易模拟记事本页面
                intent.setClass(this, SimulateDiaryActivity.class);
                break;
        }
        startActivity(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        String mode = skinSp.getString("skin", "");
        if (mode.equals(NIGHT)) {
            removeSkin();
        }
    }

    private void removeSkin() {
        if (myView != null) {
            mWindowManager.removeView(myView);
            SharedPreferences.Editor edit = skinSp.edit();
            edit.putString("skin", DAY);
            edit.commit();
        }
    }


    /**
     * 返回键退出应用(连按两次)
     * @param keyCode
     * @param event
     * @return
     */
    private long waitTime = 2000;
    private long touchTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN && KeyEvent.KEYCODE_BACK == keyCode){
            long currentTime = System.currentTimeMillis();
            if ((currentTime - touchTime ) >= waitTime){
                Toast.makeText(MainActivity.this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                touchTime = currentTime;
            }else {
                finish();
                day();
//                System.exit(0);
            }
            return true;
        }else if (KeyEvent.KEYCODE_HOME == keyCode){
            day();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
