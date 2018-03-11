package com.example.administrator.myprojectdemo.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myprojectdemo.R;
import com.example.administrator.myprojectdemo.app.AppConfig;
import com.example.administrator.myprojectdemo.app.BaseApplication;
import com.example.administrator.myprojectdemo.utils.KeyboardHelper;

import butterknife.ButterKnife;


/**
 * Created by nh on 2017/2/14.
 */

public abstract  class BaseActivity extends FragmentActivity {


    public void showToast(Context context,String str){
      Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }
    //设置标题栏
    public void setTab(final BaseActivity context, String str, boolean isback) {
        TextView textView = (TextView) context.findViewById(R.id.tab_text);
        ImageView imageView = (ImageView) context.findViewById(R.id.tab_image_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                context.finish();
            }
        });
        if(str!=null){
            textView.setText(str);
        }

        if (isback) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }

    }

    /*设置点击区域隐藏小键盘*/
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (KeyboardHelper.isShouldHideKeyboard(v, ev)) {
                KeyboardHelper.hideKeyboard(v.getWindowToken(),this);
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /** 设置标题文字和左侧的图片*/
    public void setrightText(BaseActivity ac, int id, String text, View.OnClickListener oc){
        TextView tv= (TextView) ac.findViewById(R.id.tab_next);
        ImageView iv_left_top= (ImageView) ac.findViewById(R.id.iv_left_top);
        ImageView iv_right_top= (ImageView) ac.findViewById(R.id.iv_right_top);
        tv.setText(text);
        iv_right_top.setVisibility(View.GONE);
        tv.setOnClickListener(oc);
        iv_left_top.setImageResource(id);
        iv_left_top.setVisibility(View.VISIBLE);
    }
    public TextView setrightText(BaseActivity ac, int id, String text){
        TextView tv= (TextView) ac.findViewById(R.id.tab_next);
        ImageView iv_left_top= (ImageView) ac.findViewById(R.id.iv_left_top);
        ImageView iv_right_top= (ImageView) ac.findViewById(R.id.iv_right_top);
        tv.setText(text);
        iv_right_top.setVisibility(View.GONE);
        iv_left_top.setImageResource(id);
        iv_left_top.setVisibility(View.VISIBLE);

        return tv;
    }
    /** 设置右侧title 图片 */
    public void setRightImageAndClick(BaseActivity ac, int id, View.OnClickListener oc){
       ImageView iv= (ImageView) ac.findViewById(R.id.iv_right_top);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
        iv.setOnClickListener(oc);
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        BaseApplication.getAppliaction().addActivity(this);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.andly.bro");
        registerReceiver(receiver, intentFilter);
        getIntentData();
        init();
        requestData();

        setStatuBarVisible();
    }
    /**设置tab背景*/
    public void setBgColor(BaseActivity ac,int color){
        View v_status = ac.findViewById(R.id.v_status);
        RelativeLayout bg_rel = ac.findViewById(R.id.bg_rel);
        v_status.setBackgroundColor(getResources().getColor(color));
        bg_rel.setBackgroundColor(getResources().getColor(color));
    }
    private void setStatuBarVisible() {
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            View view = findViewById(R.id.v_status);
            if (view != null) {
                if(view.getLayoutParams() instanceof LinearLayout.LayoutParams){
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view.getLayoutParams();
                    params.height = AppConfig.STATU_BAR_HEIGHT;
                }else if(view.getLayoutParams() instanceof RelativeLayout.LayoutParams){
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    params.height = AppConfig.STATU_BAR_HEIGHT;
                }
                view.setVisibility(View.VISIBLE);
            }
        }
    }

    public abstract int getContentViewId();
    public  abstract void  init();
    public  abstract  void getIntentData();
    public abstract  void requestData();
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        BaseApplication.getAppliaction().removeActivity(this);
    }


    public <T> T getIntentData(String key, T t){
        Intent intent=getIntent();
        T a=null;
        Object obj=null;
        if(t instanceof String){
            obj=intent.getStringExtra(key);
        }else if(t instanceof Integer){
            obj=intent.getIntExtra(key, 0);

        }else if(t instanceof Boolean){
            Boolean b=intent.getBooleanExtra(key,false);
            obj=b;
        }

        if(obj!=null){
            a= (T)obj;
        }else{
            a=t;
        }
        return a;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //跳转activity携带数据
    public void toActivity(Class cs, String key, Object value){
        Intent intent=new Intent();
        intent.setClass(this,cs);

        if(value instanceof String){
            String str= (String) value;
            intent.putExtra(key,str);
        }else if(value instanceof Integer){
            int a= (int) value;
            intent.putExtra(key,a);
        }
        startActivity(intent);
    }

    public void toActivity(Class cs, String[]valuel, String...keyl){
        Intent intent=new Intent();
        intent.setClass(this,cs);

       for(int i=0;i<keyl.length;i++){
           intent.putExtra(keyl[i],valuel[i]);
       }
        startActivity(intent);
    }

    public void toActivity(Class ac){
        Intent it=new Intent(this,ac);
        startActivity(it);
    }

    public void toActivityForResult(Class<?> cs, int requeseCode){
        Intent intent=new Intent(this,cs);
        startActivityForResult(intent,requeseCode);
    }

    public Intent intent(Class cs){
        Intent intent=new Intent();
        intent.setClass(this,cs);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

    }






}
