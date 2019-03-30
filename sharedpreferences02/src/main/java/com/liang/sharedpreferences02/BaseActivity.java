package com.liang.sharedpreferences02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 创建BaseActivity类作为所有活动的父类
 * 不管你想在什么地方退出程序，只需要调用ActivityCollector.finishAll()方法就可以了
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //表明将当前正在创建的活动添加到活动管理器里面去
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //表明将一个马上要销毁的活动从活动管理器里移除
        ActivityCollector.removeActivity(this);
    }
}
