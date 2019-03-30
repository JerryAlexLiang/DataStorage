package com.liang.sharedpreferences02;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 首先创建一个ActivityCollector类用于管理所有的活动(活动管理器)
 * Created by Jerry on 2016/12/19.
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity : activities) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
