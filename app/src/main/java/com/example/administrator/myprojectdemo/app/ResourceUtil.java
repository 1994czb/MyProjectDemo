package com.example.administrator.myprojectdemo.app;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by nh on 2017/2/22.
 */

public class ResourceUtil {

    public static float  getDimension(Context context, int resource){
        Resources resources=context.getResources();
        float rs=resources.getDimension(resource);
        return rs;
    }

    public static int  getColor(Context context, int resource){
        Resources resources=context.getResources();

        return  resources.getColor(resource);
    }

    public static int dp2px(int dp,Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
    }



}
