package com.example.administrator.myprojectdemo.app;

import android.app.Activity;
import android.app.Application;

import com.example.administrator.myprojectdemo.R;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by nh on 2017/2/15.
 */

public class BaseApplication extends Application {

    public static String PICTURE_DIR = "sdcard/bbc/pictures/";
    public static String PICTURE_SAVE = "sdcard/bbc/save/";//操作剪切图片时生成的临时文件夹
    public static HashMap<String, String> positioningmap;



    public Set<Activity> getAllActivities() {
        return allActivities;
    }

    private Set<Activity> allActivities;//activity管理栈
    private Activity currentActivity;

    private static BaseApplication instance;
    public static BaseApplication getAppliaction() {
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;
        //获取城市id map
        positioningmap=new HashMap<String, String>();
        //获取并存储手机的宽度和高度
        AppConfig.WINDOWS_WIDTH= SystemUtil.getWindowsPixelWidth(instance);
        AppConfig.WINDOWS_HEIGHT =SystemUtil.getWindowsPixelHeight(instance);
        AppConfig.TAB_HEIGHT= (int) ResourceUtil.getDimension(this, R.dimen.tab_height);
        AppConfig.STATU_BAR_HEIGHT=SystemUtil.getStatusBarHeight(this);
        AppConfig.BOTTOM_TAB_HEIGHT=0;

    }


    public void addActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        if (!allActivities.contains(activity))
            allActivities.add(activity);
    }
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }


    //退出APP
    public void exitApp() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (Activity act : allActivities) {
                    act.finish();
                }
            }
        }
//        android.os.Process.killProcess(android.os.Process.myPid());
//        System.exit(0);
    }





}
