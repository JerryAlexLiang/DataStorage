package com.liangyang.filestorage01;

import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

/**
 * 文件存储---简易记事本
 */
public class SimulateDiaryActivity extends BaseActivity {

    private EditText mEtFileName;
    private EditText mEtFileContent;
    private ArrayAdapter<String> adapter;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulate_diary);
        //初始化视图
        initView();
    }

    /**
     * 初始化视图
     */
    private void initView() {
        mEtFileName = (EditText) findViewById(R.id.et_file_name);//文件名称
        mEtFileContent = (EditText) findViewById(R.id.et_content);//文件内容

    }

    /**
     * 保存文件
     *
     * @param view
     */
    public void saveFile(View view) {

        String mName = mEtFileName.getText().toString();
        String mContent = mEtFileContent.getText().toString();

        //判断是否为空
        if (TextUtils.isEmpty(mEtFileName.getText()) || TextUtils.isEmpty(mEtFileContent.getText())) {
            Toast.makeText(SimulateDiaryActivity.this, "标题或文件内容为空，请检查重新输入...", Toast.LENGTH_SHORT).show();
            return;
        }
        FileOutputStream outputStream = null;
        BufferedWriter writer = null;
        try {
            //创建数据输出流，写入数据到磁盘
            outputStream = openFileOutput(mName, MODE_PRIVATE);
            outputStream.write(mContent.getBytes());
            //提示信息
            Toast.makeText(SimulateDiaryActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();
            //清空EditText内容
            clearEditText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            closeStream(outputStream);
            closeStream(writer);
        }
    }

    /**
     * 清理EditText
     */
    private void clearEditText() {
        mEtFileName.setText(null);
        mEtFileContent.setText(null);
        //重新指针聚焦
        mEtFileName.requestFocus();
    }

    /**
     * 读取保存的文件内容
     *
     * @param view
     */
    public void openFile(View view) {

        String mName = mEtFileName.getText().toString();

        if (TextUtils.isEmpty(mEtFileName.getText())) {
            Toast.makeText(SimulateDiaryActivity.this, "没有输入要打开的文本标题，请输入...", Toast.LENGTH_SHORT).show();
            return;
        }
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        try {
            //创建文件输入流，读取数据内容
            inputStream = openFileInput(mName);
            // 获取流中的字节数
            byte[] buffer = new byte[inputStream.available()];
            //读取到buffer
            inputStream.read(buffer);
            //显示内容
            //mEtFileContent.append(new String(buffer));
            mEtFileContent.setText(new String(buffer));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //清空流
            closeStream(reader);
            closeStream(inputStream);
        }

    }


    /**
     * 删除当前日记内容
     */
    public void deleteFile(View view) {
        // 获取文件名
        String mName = mEtFileName.getText().toString();
        if (TextUtils.isEmpty(mName)) {
            Toast.makeText(SimulateDiaryActivity.this, "标题为空，请重新输入...", Toast.LENGTH_SHORT).show();
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
    }

    /**
     * 初始化popupWindow
     */
    private void initPopupWindow() {
        //创建弹出窗口
        popupWindow = new PopupWindow(SimulateDiaryActivity.this);
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
     * popupWindow的button点击监听事件
     */
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                //确认
                String mName = mEtFileName.getText().toString();// 获取文件名
                //根据指定的文件名，删除文件
                boolean flag = deleteFile(mName);
                if (flag) {
                    Toast.makeText(SimulateDiaryActivity.this, "删除文件成功", Toast.LENGTH_SHORT).show();
                    //清理EditText
                    clearEditText();
                    //关闭popupWindow
                    popupWindow.dismiss();
                }
                break;

            case R.id.btn_cancel:
                //取消
                //退出popupWindow
                popupWindow.dismiss();
                break;
        }
    }

    /**
     * 创建menu
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * 创建menu的点击监听事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_new_diary:
                //新建日记
                clearEditText();
                break;

            case R.id.action_open_file:
                //打开日记目录---打开弹出框
                openDialog();
                break;

            case R.id.action_clear_all_files:
                final String path = getFilesDir().getParent();
                //清空配置文件目录shared_prefs；
                File file_xml = new File(path + "/shared_prefs");
                if (file_xml.exists()) {
                    clearAllFiles();
                }
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    /**
     * 清空所有缓存
     */
    private void clearAllFiles() {

        final String path = getFilesDir().getParent();

        //清空配置文件目录shared_prefs；
        File file_xml = new File(path + "/shared_prefs");

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

        Toast.makeText(SimulateDiaryActivity.this, "数据已清除!", Toast.LENGTH_SHORT).show();

        //清空EditText内容
        mEtFileName.setText(null);
        mEtFileContent.setText(null);

        //失去指针焦点
        mEtFileName.clearFocus();
        mEtFileContent.clearFocus();
    }

    /**
     * 点击menu的item打开弹出框
     */
    private void openDialog() {
        //创建适配器
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        //fileList()获取内部存储的所有文件,添加到adapter
        adapter.addAll(Arrays.asList(fileList()));
        System.out.println("======> " + Arrays.asList(fileList()));
        //创建AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(SimulateDiaryActivity.this);
        builder.setTitle("日记标题目录")
                .setIcon(R.mipmap.android)
                .setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //在dialog中添加listView
                        String item = adapter.getItem(which);
                        openDiaryFile(item);
                    }
                })
                .create()
                .show();

    }


    /**
     * 点击menu弹出的dialog中的item跳转到详情页面
     *
     * @param item
     */
    private void openDiaryFile(String item) {
        if (TextUtils.isEmpty(item)) {
            return;
        }
        FileInputStream inputStream = null;
        try {
            //根据指定的文件名，打开文件输入流
            inputStream = openFileInput(item);
            //获取流中的字节数
            byte[] buffer = new byte[inputStream.available()];
            //读取buffer
            inputStream.read(buffer);
            //显示文件标题
            mEtFileName.setText(item);
            //显示内容详情
            mEtFileContent.setText(new String(buffer));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            closeStream(inputStream);
        }

    }

    /**
     * 自定义关闭流
     */
    public void closeStream(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
