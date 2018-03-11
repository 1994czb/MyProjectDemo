package com.example.administrator.myprojectdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * 操作网络的类
 * */
//// TODO: 2018/3/11 判断网络类型，是否联网
public class NetHelperUtils {

	//判断是否有网络连接
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	//判断WIFI网络是否可用
	public static boolean isWifiConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mWiFiNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			if (mWiFiNetworkInfo != null) {
				return mWiFiNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	//判断MOBILE网络是否可用
	public static boolean isMobileConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mMobileNetworkInfo = mConnectivityManager
					.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (mMobileNetworkInfo != null) {
				return mMobileNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	//获取当前网络连接的类型信息
	public static int getConnectedType(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
				return mNetworkInfo.getType();
			}
		}
		return -1;
	}

	public  static  int mNetWorkType;
	/** 没有网络 */
	public static final int NETWORKTYPE_INVALID = 0;
	/** wap网络 */
	public static final int NETWORKTYPE_WAP = 1;
	/** 2G网络 */
	public static final int NETWORKTYPE_2G = 2;
	/** 3G和3G以上网络，或统称为快速网络 */
	public static final int NETWORKTYPE_3G = 3;
	/** wifi网络 */
	public static final int NETWORKTYPE_WIFI = 4;

	//判断是否是FastMobileNetWork，将3G或者3G以上的网络称为快速网络
	private static boolean isFastMobileNetwork(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
			case TelephonyManager.NETWORK_TYPE_1xRTT:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_CDMA:
				return false; // ~ 14-64 kbps
			case TelephonyManager.NETWORK_TYPE_EDGE:
				return false; // ~ 50-100 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_0:
				return true; // ~ 400-1000 kbps
			case TelephonyManager.NETWORK_TYPE_EVDO_A:
				return true; // ~ 600-1400 kbps
			case TelephonyManager.NETWORK_TYPE_GPRS:
				return false; // ~ 100 kbps
			case TelephonyManager.NETWORK_TYPE_HSDPA:
				return true; // ~ 2-14 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPA:
				return true; // ~ 700-1700 kbps
			case TelephonyManager.NETWORK_TYPE_HSUPA:
				return true; // ~ 1-23 Mbps
			case TelephonyManager.NETWORK_TYPE_UMTS:
				return true; // ~ 400-7000 kbps
			case TelephonyManager.NETWORK_TYPE_EHRPD:
				return true; // ~ 1-2 Mbps
			case TelephonyManager.NETWORK_TYPE_EVDO_B:
				return true; // ~ 5 Mbps
			case TelephonyManager.NETWORK_TYPE_HSPAP:
				return true; // ~ 10-20 Mbps
			case TelephonyManager.NETWORK_TYPE_IDEN:
				return false; // ~25 kbps
			case TelephonyManager.NETWORK_TYPE_LTE:
				return true; // ~ 10+ Mbps
			case TelephonyManager.NETWORK_TYPE_UNKNOWN:
				return false;
			default:
				return false;
		}
	}

	//获取当前网络连接的类型信息
	public static int getNetWorkType(Context context) {
		ConnectivityManager manager=(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=manager.getActiveNetworkInfo();
		if(networkInfo!=null&&networkInfo.isConnected()){
			String type=networkInfo.getTypeName();
			if(type.equalsIgnoreCase("WIFI")){
				mNetWorkType=NETWORKTYPE_WIFI;
			}else if(type.equalsIgnoreCase("MOBILE")){
				String proxyHost=android.net.Proxy.getDefaultHost();
				mNetWorkType = TextUtils.isEmpty(proxyHost) ? (isFastMobileNetwork(context) ? NETWORKTYPE_3G
						: NETWORKTYPE_2G)
						: NETWORKTYPE_WAP;
			}
		}else{
			mNetWorkType=NETWORKTYPE_INVALID;
		}
		return mNetWorkType;
	}

}
