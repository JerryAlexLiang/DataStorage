package com.liangyang.filestorage01;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件存储---模拟登陆
 */
public class SimulateLoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText mEtName;
    private EditText mEtPassword;
    private Button mBtnSave;
    private Button mBtnRead;
    private EditText mEtContent;
    private String mName;
    private String mPassword;
    private Button mBtnClear;
    private String content;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulatelogin_activity);
        //初始化视图
        initView();
        //初始化Button点击监听事件
        mBtnSave.setOnClickListener(this);//保存数据
        mBtnRead.setOnClickListener(this);//读取数据
        mBtnClear.setOnClickListener(this);//清除数据
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        //创建弹出窗口
        popupWindow = new PopupWindow(SimulateLoginActivity.this);
        //弹出窗口的布局
        View contentView = getLayoutInflater().inflate(R.layout.popup_window, null);
        //设置popupWindow里面显示的内容
        popupWindow.setContentView(contentView);
        //设置高宽
        popupWindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        //设置外部触摸消失，true有效果，false无效果
        popupWindow.setOutsideTouchable(false);
        //控件在指定位置显示
        popupWindow.showAtLocation(contentView, Gravity.CENTER, 0, 0);
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mEtName = (EditText) findViewById(R.id.et_name);//用户名
        mEtPassword = (EditText) findViewById(R.id.et_password);//用户名密码
        mBtnSave = (Button) findViewById(R.id.save_btn);//保存数据按钮
        mBtnRead = (Button) findViewById(R.id.read_btn);//读取数据按钮
        mBtnClear = (Button) findViewById(R.id.clear_btn);//清除数据按钮
        mEtContent = (EditText) findViewById(R.id.et_content);//读取保存的数据

    }

    /**
     * 初始化Button点击监听事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        mName = mEtName.getText().toString();
        mPassword = mEtPassword.getText().toString();
        content = "用户名是：" + mName + "   " + "密码是：" + mPassword;
        switch (view.getId()) {
            case R.id.save_btn:
                //保存数据
                if (mName.isEmpty() || mPassword.isEmpty()) {
                    Toast.makeText(SimulateLoginActivity.this, "提示:用户名和密码不能为空!", Toast.LENGTH_SHORT).show();
                }
                save(content);
                break;

            case R.id.read_btn:
                //读取数据
                if (!TextUtils.isEmpty(content)) {
                    read();
                }
                break;

            case R.id.clear_btn:
                //clear();
                if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mPassword)) {
                    Toast.makeText(SimulateLoginActivity.this, "没有缓存数据", Toast.LENGTH_SHORT).show();
                    return;
                }
                //调出提示弹出框
                if (popupWindow != null && popupWindow.isShowing()) {
                    //关闭popupWindow弹出框
                    popupWindow.dismiss();
                } else {
                    //初始化popupWindow弹出框
                    initPopupWindow();
                }
                break;

            /**
             * popupWindow弹出框button点击监听事件
             */
            case R.id.btn_ok:
                //删除文件
                clear();
                //关闭popupWindow
                popupWindow.dismiss();
                break;

            case R.id.btn_cancel:
                //退出popupWindow
                popupWindow.dismiss();
                break;
        }

    }

    /**
     * 清除数据
     */
    private void clear() {

        final String path = getFilesDir().getParent();

        //清空配置文件目录shared_prefs；
        File file_xml = new File(path + "/shared_prefs");

        if (!file_xml.exists()) {
            Toast.makeText(SimulateLoginActivity.this, "没有缓存数据", Toast.LENGTH_SHORT).show();
        }

        if (file_xml.exists()) {
            File[] files = file_xml.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }

        //清空缓存目录；
        File file_cache = getCacheDir();
        if (file_cache.exists()) {
            File[] files = file_cache.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }

        //清空file目录；
        File file_file = new File(path + "/files");

        if (file_file.exists()) {
            File[] files = file_file.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }

        //清空数据库目录；
        File file_db = new File(path + "/databases");
        if (file_db.exists()) {
            File[] files = file_db.listFiles();
            for (int i = 0; i < files.length; i++) {
                files[i].delete();
            }
        }

        Toast.makeText(SimulateLoginActivity.this, "数据已清除!", Toast.LENGTH_SHORT).show();

        //清空EditText内容
        mEtName.setText(null);
        mEtPassword.setText(null);
        mEtContent.setText(null);

        //失去指针焦点
        mEtName.clearFocus();
        mEtPassword.clearFocus();
        mEtContent.clearFocus();


    }

    /**
     * 读取数据
     *
     * @param content
     */
    private void read() {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            //创建文件输入流，读取数据
            inputStream = openFileInput("data");
            //创建BufferedReader对象
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                //mEtContent.append(line);
                mEtContent.setText(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 保存数据
     *
     * @param content
     */
    private void save(String content) {
        FileOutputStream os = null;
        BufferedWriter writer = null;
        try {
            //创建数据输出流，写入数据到磁盘
            os = openFileOutput("data", MODE_PRIVATE);
            //创建BufferedWriter对象
            writer = new BufferedWriter(new OutputStreamWriter(os));
            //写入数据
            writer.write(content);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
