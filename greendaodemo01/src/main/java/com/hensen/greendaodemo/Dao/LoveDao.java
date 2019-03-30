package com.hensen.greendaodemo.Dao;

import com.hensen.greendaodemo.Base.BaseApplication;
import com.hensen.greendaodemo.Bean.Shop;
import com.hensen.greendaodemo.Bean.ShopDao;

import java.util.List;

/**
 * 创建日期：2018/1/23 on 上午9:37
 * 描述: 数据库的增删改查
 * 数据库的表名、字段、数据库都建好了，下面就通过GreenDao对数据库的操作
 * 1、增加单个数据
 * getShopDao().insert(shop);
 * getShopDao().insertOrReplace(shop);
 * 2、增加多个数据
 * getShopDao().insertInTx(shopList);
 * getShopDao().insertOrReplaceInTx(shopList);
 * 3、查询全部
 * List<Shop> list = getShopDao().loadAll();
 * List<Shop> list = getShopDao().queryBuilder().list();
 * 4、查询附加单个条件
 * .where()
 * .whereOr()
 * 5、查询附加多个条件
 * .where(, , ,)
 * .whereOr(, , ,)
 * 6、查询附加排序
 * .orderDesc()
 * .orderAsc()
 * 7、查询限制当页个数
 * .limit()
 * 8、查询总个数
 * .count()
 * 9、修改单个数据
 * getShopDao().update(shop);
 * 10、修改多个数据
 * getShopDao().updateInTx(shopList);
 * 11、删除单个数据
 * getTABUserDao().delete(user);
 * 12、删除多个数据
 * getUserDao().deleteInTx(userList);
 * 13、删除数据ByKey
 * getTABUserDao().deleteByKey();
 * <p>
 * 作者: liangyang
 */
public class LoveDao {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param shop
     */
    public static void insertLove(Shop shop) {
        BaseApplication.getDaoInstant().getShopDao().insertOrReplace(shop);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteLove(long id) {
        BaseApplication.getDaoInstant().getShopDao().deleteByKey(id);
    }

    /**
     * 更新数据
     *
     * @param shop
     */
    public static void updateLove(Shop shop) {
        BaseApplication.getDaoInstant().getShopDao().update(shop);
    }

    /**
     * 查询条件为Type=TYPE_LOVE的数据
     *
     * @return
     */
    public static List<Shop> queryLove() {
        return BaseApplication.getDaoInstant().getShopDao().queryBuilder()
                .where(ShopDao.Properties.Type.eq(Shop.TYPE_LOVE)).list();
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    public static List<Shop> queryAll() {
        return BaseApplication.getDaoInstant().getShopDao().loadAll();
    }
}
