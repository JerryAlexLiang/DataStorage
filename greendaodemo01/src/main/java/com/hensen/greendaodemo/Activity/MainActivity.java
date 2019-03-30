package com.hensen.greendaodemo.Activity;

import android.app.Application;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hensen.greendaodemo.Adapter.ShopListAdapter;
import com.hensen.greendaodemo.Base.BaseApplication;
import com.hensen.greendaodemo.Bean.Shop;
import com.hensen.greendaodemo.Dao.LoveDao;
import com.hensen.greendaodemo.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button bt_add, bt_delete, bt_update, bt_query;
    //    private ListView lv_content;
    private PullToRefreshListView lv_content;
    private ShopListAdapter adapter;
    private List<Shop> shops;
    private static int i = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_add = (Button) findViewById(R.id.bt_add);
        bt_delete = (Button) findViewById(R.id.bt_delete);
        bt_update = (Button) findViewById(R.id.bt_update);
        bt_query = (Button) findViewById(R.id.bt_query);
//        lv_content = (ListView) findViewById(R.id.lv_content);
        lv_content = (PullToRefreshListView) findViewById(R.id.lv_content);
        bt_add.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_query.setOnClickListener(this);

        //设置可上拉刷新和下拉刷新
//        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        queryDate();

        initListener();

    }

    private void initListener() {
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

                //开启一个线程处理逻辑，然后在线程中在开启一个UI线程，当子线程中的逻辑完成之后，
                // 就会执行UI线程中的操作，将结果反馈到UI界面。
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            // 模拟耗时的操作，在子线程中进行。
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // 更新主线程UI，跑在主线程
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                queryDate();
                                Toast.makeText(MainActivity.this, "刷新完成！", Toast.LENGTH_SHORT).show();
                                //刷新完成
                                lv_content.onRefreshComplete();
                            }
                        });


                    }
                }).start();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

            }
        });

        lv_content.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shop shop = shops.get(position - 1);
                Toast.makeText(MainActivity.this, "当前id:  " + shop.getId(), Toast.LENGTH_SHORT).show();
            }
        });

//        lv_content.getRefreshableView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                final Shop shop = shops.get(position);
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setTitle("警告");
//                builder.setMessage("温馨提示：您确实不是因为手贱点击的删除？");
//                builder.setNegativeButton("删除", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        BaseApplication.getDaoInstant().getShopDao().deleteByKey(shop.getId());
//                        queryDate();
//                    }
//                });
//                builder.create().show();
//                return true;
//            }
//        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_add:
                addDate();
                break;
            case R.id.bt_delete:
                deleteDate();
                break;
            case R.id.bt_update:
                updateDate();
                break;
            case R.id.bt_query:
                queryDate();
                break;
        }
    }

    private void deleteDate() {
        if (!shops.isEmpty()) {
            LoveDao.deleteLove(shops.get(0).getId());
            queryDate();
        }
    }

    private void queryDate() {
        shops = new ArrayList<>();
        shops = LoveDao.queryLove();
//        shops = LoveDao.queryAll();
        adapter = new ShopListAdapter(this, shops);
        lv_content.setAdapter(adapter);
    }

    private void addDate() {
        Shop shop = new Shop();
        shop.setType(Shop.TYPE_LOVE);
        shop.setAddress("广东深圳");
        shop.setImage_url("http://i3.meishichina.com/attachment/recipe/201011/201011221633537.JPG@!p320");
        shop.setPrice("19.40");
        shop.setSell_num(15263);
        shop.setName("正宗梅菜扣肉 聪厨梅干菜扣肉 家宴常备方便菜虎皮红烧肉 2盒包邮" + i++);
        LoveDao.insertLove(shop);
        queryDate();
    }

    private void updateDate() {
        if (!shops.isEmpty()) {
            Shop shop = shops.get(0);
            shop.setName("我是修改的名字");
            LoveDao.updateLove(shop);
            queryDate();
        }
    }
}
