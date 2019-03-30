package com.example.demo02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private String[] mStrings = {"苹果", "小苹果", "青苹果", "黄苹果"};
    private SearchView searchView;
    private ListView listView;
    private ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        searchView = (SearchView) findViewById(R.id.sv);
        listView = (ListView) findViewById(R.id.lv);

        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, mStrings);
        listView.setAdapter(adapter);

        // 这个方法的作用是用来过滤选项的.
        // 例如在软键盘上打出一个a,则会过滤掉除了a开头的所有选项.
        listView.setTextFilterEnabled(true);

        // 设置该SearchView默认是否自动缩小为图标
        searchView.setIconifiedByDefault(true);
        // 为该搜索组件设置监听事件
        //sv.setOnQueryTextListener(this);// 查询的监听
        searchView.setOnQueryTextListener(this);
        // 设置该SearchView显示搜索按钮
        searchView.setSubmitButtonEnabled(true);// 同意的按钮
        // 设置该SearchView内默认显示的提示文本
        searchView.setQueryHint("查找");// 查询默认的额字
    }

    /**
     * 实际应用中应该在该方法内执行实际查询
     * 此处仅使用Toast显示用户输入的查询内容
     *
     * @param query
     * @return
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(this, "你选择的是" + query, Toast.LENGTH_SHORT).show();
        return false;
    }

    /**
     * 用户输入字符时激发该方法
     *
     * @param newText
     * @return
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        //这个是传入一个新的文字
        if (TextUtils.isEmpty(newText)) {
            //如果这个文字等于空
            //清除listView的过滤
            listView.clearTextFilter();
        } else {
            // 使用用户输入的内容对ListView的列表项进行过滤
            //会出现悬浮窗
//            listView.setFilterText(newText);

            //不会出现浮框
            adapter.getFilter().filter(newText);
        }
        return true;
    }
}
