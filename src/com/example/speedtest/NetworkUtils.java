package com.example.speedtest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetworkUtils {  
	  
    /** 
     * 网络是否可用 
     *  
     * @param activity 
     * @return 
     */  
    public static boolean isNetworkAvailable(Context context) {  
        ConnectivityManager connectivity = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        if (connectivity == null) {  
        } else {  
            NetworkInfo[] info = connectivity.getAllNetworkInfo();  
            if (info != null) {  
                for (int i = 0; i < info.length; i++) {  
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {  
                        return true;  
                    }  
                }  
            }  
        }  
        return false;  
    }  
  
  
    /** 
     * 判断当前网络是否是wifi网络 
     * if(activeNetInfo.getType()==ConnectivityManager.TYPE_MOBILE) {  
     *  
     * @param context 
     * @return boolean 
     */  
    public static boolean isWifi(Context context) {  
        ConnectivityManager connectivityManager = (ConnectivityManager) context  
                .getSystemService(Context.CONNECTIVITY_SERVICE);  
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();  
        if (activeNetInfo != null  
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {  
            return true;  
        }  
        return false;  
    }  
    
    public void netType(TelephonyManager tm,String type){
    	int typeNum = tm.getNetworkType();
		switch (typeNum) {

		case TelephonyManager.NETWORK_TYPE_GPRS:
			type = "GPRS";
			break;

		case TelephonyManager.NETWORK_TYPE_EDGE:
			type = "EDGE";
			break;

		case TelephonyManager.NETWORK_TYPE_UMTS:
			type = "UMTS";
			break;

		case TelephonyManager.NETWORK_TYPE_CDMA:
			type = "CDMA";
			break;

		case TelephonyManager.NETWORK_TYPE_EVDO_0:
			type = "EVDO_0";
			break;

		case TelephonyManager.NETWORK_TYPE_EVDO_A:
			type = "EVDO_A";
			break;

		case TelephonyManager.NETWORK_TYPE_1xRTT:
			type = "1xRTT";
			break;

		case TelephonyManager.NETWORK_TYPE_HSDPA:
			type = "HSDPA";
			break;

		case TelephonyManager.NETWORK_TYPE_HSUPA:
			type = "HSUPA";
			break;

		case TelephonyManager.NETWORK_TYPE_HSPA:
			type = "HSPA";
			break;

		case TelephonyManager.NETWORK_TYPE_IDEN:
			type = "IDEN";
			break;

		case TelephonyManager.NETWORK_TYPE_EVDO_B:
			type = "EVDO_B";
			break;

		case TelephonyManager.NETWORK_TYPE_LTE:
			type = "LTE";
			break;

		case TelephonyManager.NETWORK_TYPE_EHRPD:
			type = "EHRPD";
			break;

		case TelephonyManager.NETWORK_TYPE_HSPAP:
			type = "HSPAP";
			break;

		case TelephonyManager.NETWORK_TYPE_UNKNOWN:
			type = "UNKNOWN";
			break;
		}
    }
}