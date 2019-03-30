package com.liang.sharedpreferences02;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 登录界面LoginActivity继承BaseActivity
 */
public class LoginActivity extends BaseActivity {

private EditText mEtName;
    private EditText mEtPassword;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private TextView mTvReadName;
    private TextView mTvReadPassword;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化视图
        mEtName = (EditText) findViewById(R.id.et_name);
        mEtPassword = (EditText) findViewById(R.id.et_password);
        mTvReadName = (TextView) findViewById(R.id.tv_content_name);
        mTvReadPassword = (TextView) findViewById(R.id.tv_content_password);
        //初始化SharedPreferences对象
        /**
         * 参数1: fileName, 文件名，不要带后缀
         * 参数2: mode, 值有：MODE_PRIVATE  文件的读写是私有的，只能本应用程序使用。
         * MODE_WORLD_READABLE  全局可读
         * MODE_WORLD_WRITABLE  全局可写
         */
        sp = getSharedPreferences("person", Context.MODE_PRIVATE);
        //获取Editor对象
        editor = sp.edit();

    }

    /**
     * 保存数据
     */
    public void saveData(View view) {
        //获取输入的用户名和密码
        String name = mEtName.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();

        //判断是否为空
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "用户名和密码不能为空！", Toast.LENGTH_SHORT).show();
            return;
        }

        //保存数据
        editor.putString("name", name);
        editor.putString("password", password);

        //提交数据
        editor.commit();

        //清空EditText
        clearEditText();

    }

    /**
     * 读取数据
     */
    public void readData(View view) {

        String userName = sp.getString("name", "admin");
        String userPassword = sp.getString("password", "123456");

        if (TextUtils.isEmpty(userName) && TextUtils.isEmpty(userPassword)) {
            Toast.makeText(LoginActivity.this, "数据为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //显示数据
        mTvReadName.setText(userName);
        mTvReadPassword.setText(userPassword);


    }

    /**
     * 清除数据
     */
    public void clearData(View view) {
        //判断弹出框是否已弹出
        if (popupWindow != null && popupWindow.isShowing()) {
            //关闭弹出框
            popupWindow.dismiss();
        } else {
            //初始化弹出框
            initPopupWindow();
        }
    }

    public void goToLogin(View view) {

        String name = mEtName.getText().toString().trim();
        String password = mEtPassword.getText().toString().trim();
        String userName = sp.getString("name", "admin");
        String userPassword = sp.getString("password", "123456");

        if (userName.equals(name) && userPassword.equals(password)) {
            Intent intent = new Intent(LoginActivity.this, SuccessActivity.class);
            intent.putExtra("name", userName);
            intent.putExtra("password", userPassword);
            startActivity(intent);
        }else {
            Toast.makeText(LoginActivity.this, "用户名或密码不正确，请重新输入...", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 清理EditText
     */
    private void clearEditText() {
        mEtName.setText(null);
        mEtPassword.setText(null);
        mTvReadName.setText(null);
        mTvReadPassword.setText(null);
        //重新聚焦
        mEtName.requestFocus();
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        //创建popupWindow弹出框
        popupWindow = new PopupWindow(LoginActivity.this);
        //弹出窗口的布局
        View contentView = getLayoutInflater().inflate(R.layout.popup_window, null);
        //设置popupWindow里面显示的内容
        popupWindow.setContentView(contentView);
        //设置高宽
        popupWindow.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置外部触摸消失，true有效果，false无效果
        popupWindow.setOutsideTouchable(false);
        //控件在指定位置显示
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    /**
     * 弹出框的点击监听事件
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                //清除缓存数据
                editor.clear().commit();
                //清空EditText
                clearEditText();
                popupWindow.dismiss();
                break;

            case R.id.btn_cancel:
                //退出popupWindow
                popupWindow.dismiss();
                break;
        }
    }
}
