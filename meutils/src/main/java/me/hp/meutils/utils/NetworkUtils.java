package me.hp.meutils.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import me.hp.meutils.ui.IApplication;

/**
 * @author: HePing
 * @created: 2019/12/9
 * @desc:
 */
public final class NetworkUtils {

    /**
     * 判断网络是否可用
     * 注意：使用需要在清单文件中声明权限：android.permission.ACCESS_NETWORK_STATE
     *
     * @return
     */
    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) IApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * 前提是有网络: isNetworkAvailable() 才调用此方法
     * 注意：使用需要在清单文件中声明权限：android.permission.ACCESS_NETWORK_STATE
     *
     * @return
     */
    public static boolean isWifi() {
        ConnectivityManager connectivityManager = (ConnectivityManager) IApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo && wifiInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
