package com.example.administrator.myprojectdemo.utils;

import android.text.TextUtils;

/**
 * Created by ouyangyu on 2017-05-05 .
 */
//// TODO: 2018/3/11 fragment向activity传值
public class ConvertUtils {
    private static  ConvertUtils convertUtils;
    public static ConvertUtils getInstance(){
         if(convertUtils==null){
             convertUtils=new ConvertUtils();
         }
        return convertUtils;
    }
    public double  ConvertDouble(Object value, int defaultvalue){
        if(value==null){
            return defaultvalue;
        }else{
            if(TextUtils.isEmpty(value.toString())){
                return defaultvalue;
            }
            try{
                return Double.valueOf(value.toString());
            }catch (Exception e){
                    return  defaultvalue;
            }

        }
    }
    public  int  ConvertInt(Object value, int defaultvalue){
        if(TextUtils.isEmpty(value.toString())){
            return defaultvalue;
        }else{
            try{
                return Double.valueOf(value.toString()).intValue();
            }catch (Exception e){
                return  defaultvalue;
            }
        }
    }
}
