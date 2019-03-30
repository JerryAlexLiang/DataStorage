package com.liang.sharedpreferences02;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 发出强制下线广播，也就是说强制用户下线的逻辑并不是写在SuccessActivity中的，
 * 而应该写在接收这条广播的广播接收器里面，这样强制下线的功能就不会依附于任何的界面
 * 不管是在程序的任何地方，只需要发出这样一条广播，就可以完成强制下线的操作了
 */
public class SuccessActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        //接收数据
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String password = intent.getStringExtra("password");
        //初始化视图
        TextView tvName = (TextView) findViewById(R.id.name);
        TextView tvPassword = (TextView) findViewById(R.id.password);
        //赋值
        tvName.setText("用户名： " + name);
        tvPassword.setText("密 码： " + password);

        //初始化强制下线按钮
        Button forceOfflineButton = (Button) findViewById(R.id.force_offline);
        //监听事件
        forceOfflineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //发出强制下线广播
                /**
                 * 发出强制下线广播，也就是说强制用户下线的逻辑并不是写在SuccessActivity中的，
                 * 而应该写在接收这条广播的广播接收器里面，这样强制下线的功能就不会依附于任何的界面
                 * 不管是在程序的任何地方，只需要发出这样一条广播，就可以完成强制下线的操作了
                 */
                Intent intent = new Intent("com.liang.sharedpreferences02.FORCE_OFFLINE");//广播的值
                sendBroadcast(intent);
            }
        });
    }
}
