package com.example.administrator.myprojectdemo.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myprojectdemo.R;
import com.example.administrator.myprojectdemo.app.AppConfig;
import com.example.administrator.myprojectdemo.utils.ConvertUtils;

import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    public BaseFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }
    public View getMyRootView(LayoutInflater inflater,
                              int layoutid, ViewGroup container , String tabText, boolean isBack, boolean ishowTitlbar){
        View v= inflater.inflate(layoutid, container, false);
        if(ishowTitlbar)
            setTab(v,tabText,isBack);
        ButterKnife.bind(this,v);

        return v;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
        request();
        setStatuBarVisible(getView());
            }

    private void setStatuBarVisible(View v) {
        if (Build.VERSION.SDK_INT >= 19) {
            View view = v.findViewById(R.id.v_status);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //设置标题栏
    public void setTab(View v, String str, boolean isback) {
        if(str!=null){
            TextView textView = (TextView) v.findViewById(R.id.tab_text);
            textView.setText(str);
        }
        ImageView imageView = (ImageView)v
                .findViewById(R.id.tab_image_back);
        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        if (isback) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }



    }
    /** 设置标题文字和左侧的图片*/
    public void setrightText(View v, int id, String text, View.OnClickListener oc){
        TextView tv= (TextView) v.findViewById(R.id.tab_next);
        ImageView iv_left_top= (ImageView) v.findViewById(R.id.iv_left_top);
        ImageView iv_right_top= (ImageView) v.findViewById(R.id.iv_right_top);
        tv.setText(text);
        iv_right_top.setVisibility(View.GONE);
        tv.setOnClickListener(oc);
        iv_left_top.setImageResource(id);
        iv_left_top.setVisibility(View.VISIBLE);
    }
    public TextView setrightText(View v, int id, String text){
        TextView tv= (TextView) v.findViewById(R.id.tab_next);
        ImageView iv_left_top= (ImageView) v.findViewById(R.id.iv_left_top);
        ImageView iv_right_top= (ImageView) v.findViewById(R.id.iv_right_top);
        tv.setText(text);
        iv_right_top.setVisibility(View.GONE);
        iv_left_top.setImageResource(id);
        iv_left_top.setVisibility(View.VISIBLE);

        return tv;
    }
    /** 设置右侧title 图片 */
    public void setRightImageAndClick(View v, int id, View.OnClickListener oc){
        ImageView iv= (ImageView) v.findViewById(R.id.iv_right_top);
        iv.setVisibility(View.VISIBLE);
        iv.setImageResource(id);
        iv.setOnClickListener(oc);
    }
    public abstract void  init();
    public abstract  void  request();
    public void toActivity(Class activity){
        Intent it=new Intent(getActivity(),activity);
        startActivity(it);
    }
    //跳转Activity携带数据
    public void toActivity(Class cs, String key, Object value){
        Intent intent=new Intent();
        intent.setClass(getActivity(),cs);

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
        intent.setClass(getActivity(),cs);

        for(int i=0;i<keyl.length;i++){
            intent.putExtra(keyl[i],valuel[i]);
        }
        startActivity(intent);
    }

    public void showToast(Activity activity,String str){
        Toast.makeText(activity,str,Toast.LENGTH_SHORT).show();
    }

    /**设置tab背景*/
    public void setBgColor(View v,int color){
        View v_status = v.findViewById(R.id.v_status);
        RelativeLayout bg_rel = v.findViewById(R.id.bg_rel);
        v_status.setBackgroundColor(getResources().getColor(color));
        bg_rel.setBackgroundColor(getResources().getColor(color));
    }
    public static class IntentBuilder{
            private final Context context;
            private Intent it;
            private final Class activity;
            public IntentBuilder (Context context, Class activity){
                this.context=context;
                this.activity=activity;
            }
            public IntentBuilder  CreateBuilder(){
                   Intent it=new Intent(context,activity);
                   this.it=it;
                   return this;
            }
            public IntentBuilder putExtra(String key, Object value){
                    if(it!=null){
                        if(value instanceof Integer){
                            it.putExtra(key, ConvertUtils.getInstance().ConvertInt(value,0));
                        }else if(value instanceof String){
                            if(value!=null){
                                it.putExtra(key, value.toString());
                            }
                        }
                    }
                   return this;
            }
            public void start(){
                if(context!=null)
                context.startActivity(it);
            }
    }

}
