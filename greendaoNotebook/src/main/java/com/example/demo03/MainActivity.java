package com.example.demo03;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo03.adapter.MyAdapter;
import com.example.demo03.bean.Diary;
import com.example.demo03.util.DBUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.btn_add)
    Button btnAdd;
    @BindView(R.id.list_view)
    ListView listView;


    private List<Diary> diaryList = new ArrayList<>();
    private List<Diary> mQueryList;

    private MyAdapter adapter;
    private MyAdapter mQueryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //初始化视图
        initView();
        //初始化数据
        initData();

//        handleIntent(getIntent());

        //初始化监听
        initListener();
    }

    private void initData() {
        diaryList.clear();
        List<Diary> diaries = DBUtils.getDao(MainActivity.this).loadAll();
//        List<Diary> list = DBUtils.getDao(this).queryBuilder().build().list();
        List<Diary> list = DBUtils.getDao(this).queryBuilder().list();

        Log.d(TAG, "读取数据库:  >>>" + "loadAll():   " + new Gson().toJson(diaries));
        Log.d(TAG, "读取数据库:  >>>" + "queryBuilder():   " + new Gson().toJson(list));

        diaryList.addAll(diaries);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        //初始化适配器
        adapter = new MyAdapter(this, diaryList);
        //绑定适配器
        listView.setAdapter(adapter);
        // 这个方法的作用是用来过滤选项的.
        // 例如在软键盘上打出一个a,则会过滤掉除了a开头的所有选项.
        listView.setTextFilterEnabled(true);
    }

    @OnClick(R.id.btn_add)
    public void onViewClicked(View view) {
        //跳转到新增空界面
        Intent intent = new Intent(this, WriteActivity.class);
        startActivityForResult(intent, 1);
    }

    private void initListener() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到编辑页面（传入数据）
                Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                Diary diary = diaryList.get(position);
                intent.putExtra("id", diary.getId());
                System.out.println("====> id:" + diary.getId());
                startActivityForResult(intent, 2);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Diary diary = diaryList.get(position);
                System.out.println("====> id: " + diary.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("提示");
                builder.setMessage("确定要删除这条记录吗？");
                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //根据id删除数据库中当前数据
                        DBUtils.getDao(MainActivity.this).deleteByKey(diary.getId());
                        //重新加载数据库
                        initData();

                        Toast.makeText(MainActivity.this, "删除数据成功！", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();

                return true;
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            //重新加载数据库
            initData();
        }
    }

}
