package com.liangyang.sharedpreferences;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 跳转页面
     * @param view
     */
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.btn_one:
                //SharedPreferences登录页面1
                intent.setClass(MainActivity.this,SPLoginActivity.class);
                break;
            case R.id.btn_two:
                intent.setClass(MainActivity.this,PhoneBookActivity.class);
                break;
        }
        startActivity(intent);

    }
}
