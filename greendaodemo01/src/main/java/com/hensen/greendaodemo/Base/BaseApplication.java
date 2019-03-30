package com.hensen.greendaodemo.Base;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.hensen.greendaodemo.Bean.DaoMaster;
import com.hensen.greendaodemo.Bean.DaoSession;

/**
 * 创建日期：2018/1/23 on 上午9:14
 * 描述: 数据库配置,创建数据库（数据库名）
 * 1、DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
 * 2、DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
 * 3、DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
 * 4、由于我们已经创建好了DaoSession和Shop的Bean对象，编译后会自动生成我们的ShopDao对象，
 * 可通过DaoSession获得：ShopDao dao = daoSession.getShopDao();
 * 这里的Dao（Data Access Object）是指数据访问接口，即提供了数据库操作一些API接口，可通过dao进行增删改查操作
 * 作者: liangyang
 */
public class BaseApplication extends Application {

    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置数据库
        setupDatabase();
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        /*
        这里建议使用DaoMaster.OpenHelper ,不要使用DaoMaster.DevOpenHelper,
        因为使用DaoMaster.DevOpenHelper每次升级数据库都会把表删除重建，推荐开发时用。
        正式使用时还是用DaoMaster.OpenHelper。
         */
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
