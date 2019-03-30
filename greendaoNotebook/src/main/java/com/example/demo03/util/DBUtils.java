package com.example.demo03.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.demo03.gen.DaoMaster;
import com.example.demo03.gen.DaoSession;
import com.example.demo03.gen.DiaryDao;

/**
 * 创建日期：2018/1/23 on 下午6:16
 * 描述:
 * 作者:yangliang
 */
public class DBUtils {

    private static DiaryDao diaryDao;

    public static DiaryDao getDao(Context context) {
        /*
        这里建议使用DaoMaster.OpenHelper ,不要使用DaoMaster.DevOpenHelper,
        因为使用DaoMaster.DevOpenHelper每次升级数据库都会把表删除重建，推荐开发时用。
        正式使用时还是用DaoMaster.OpenHelper。
         */
        if (diaryDao == null) {
            //创建数据库diary.db"
            DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(
                    context, "diary.db", null);
            //获取可写数据库
            SQLiteDatabase db = helper.getWritableDatabase();
            //获取数据库对象
            DaoMaster daoMaster = new DaoMaster(db);
            //获取Dao对象管理者
            DaoSession daoSession = daoMaster.newSession();
            diaryDao = daoSession.getDiaryDao();
        }
        return diaryDao;
    }
}
