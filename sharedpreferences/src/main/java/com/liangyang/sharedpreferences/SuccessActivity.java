package com.liangyang.sharedpreferences;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SuccessActivity extends AppCompatActivity {

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

    }
}
