package com.crazypudding.greendaodemo;

import android.app.Application;

import com.crazypudding.greendaodemo.greendao.dao.DaoMaster;
import com.crazypudding.greendaodemo.greendao.dao.DaoMaster.DevOpenHelper;
import com.crazypudding.greendaodemo.greendao.dao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Crazypudding on 2017/1/4.
 */

public class MyApplication extends Application {

    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        DevOpenHelper helper = new DevOpenHelper(this, "company.db");
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
