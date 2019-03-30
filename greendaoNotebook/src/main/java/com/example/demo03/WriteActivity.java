package com.example.demo03;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo03.bean.Diary;
import com.example.demo03.util.DBUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WriteActivity extends AppCompatActivity {

    @BindView(R.id.add_title_edt)
    EditText addTitleEdt;
    @BindView(R.id.add_content_edt)
    EditText addContentEdt;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    private long id;
    private Diary diary;
    public static final String TAG = WriteActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        ButterKnife.bind(this);
        //获取数据
        initData();
    }

    private void initData() {
        id = getIntent().getLongExtra("id", 0);
        Log.d(TAG, "initData: 当前id:   " + id);
        if (id > 0) {
            //已有数据，点击进入编辑视图
            diary = DBUtils.getDao(WriteActivity.this).load(id);
            addTitleEdt.setText(diary.getTitle());
            addContentEdt.setText(diary.getContent());
        } else {
            //没有数据，第一次编辑进入
            //新增到数据库
            diary = new Diary();
        }
    }

    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        String title = addTitleEdt.getText().toString().trim();
        String content = addContentEdt.getText().toString().trim();
        if ("".equals(title)){
            Toast.makeText(this, "标题不能为空~", Toast.LENGTH_SHORT).show();
            return;
        }

        //新增到数据库
        diary.setTitle(title);
        diary.setContent(content);
        //替换
        DBUtils.getDao(WriteActivity.this).insertOrReplace(diary);

        //数据回传
        setResult(RESULT_OK);

        Toast.makeText(this, "提交成功！", Toast.LENGTH_SHORT).show();

        //关闭当前页面
        finish();
    }
}
