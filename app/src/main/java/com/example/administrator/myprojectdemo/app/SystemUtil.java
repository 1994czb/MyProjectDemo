package com.example.administrator.myprojectdemo.app;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.NotificationCompat;
import android.util.DisplayMetrics;
import android.view.WindowManager;


/**
 * Created by nh on 2017/2/15.
 */

public class SystemUtil {

    public static int getWindowsPixelWidth(Application context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        // 取得窗口属性
        manager.getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
       int viewWidth = dm.widthPixels;
        return viewWidth;
    }
    public static int getWindowsPixelHeight(Application context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 取得窗口属性
        manager.getDefaultDisplay().getMetrics(dm);
        // 窗口的宽度
        int screenHeight = dm.heightPixels;
        return screenHeight;
    }


    //判断网络是否连接
    public static boolean isNetworkAvailable() {
        ConnectivityManager connManager = (ConnectivityManager) BaseApplication.getAppliaction().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connManager != null) {
/**
 * 获取网络信息实体
 * 由于从系统服务中获取数据属于进程间通信，基本类型外的数据必须实现Parcelable接口，
 * NetworkInfo实现了Parcelable，获取到的activeNetInfo相当于服务中网络信息实体对象的一个副本（拷贝），
 * 所以，不管系统网络服务中的实体对象是否置为了null，此处获得的activeNetInfo均不会发生变化
 */
            NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
            if (activeNetInfo != null) {
                return activeNetInfo.isAvailable();
            }
        }
        return false;
    }

    public static void notification(Context context, String title,
                                    int icon, String content, int id,
                                    PendingIntent intent){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(content).
                        setAutoCancel(true).
                        setContentIntent(intent);
        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.notify(id, notification);
      //  boolean isAboveLollipop = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;

       // builder.setSmallIcon(isAboveLollipop ? R.drawable.small_icon : R.drawable.white_small_icon);
    }

    public static void cancleNotificationAll(Context context){
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        mNotifyMgr.cancelAll();
    }


    public static void  recyleBitmep(Bitmap bitmap){
        if(bitmap != null && !bitmap.isRecycled()){
            // 回收并且置为null
            bitmap.recycle();
            bitmap = null;
        }
        System.gc();
    }


    //获取状态栏高度
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }



}
