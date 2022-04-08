package me.hp.meutils.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * @author: HePing
 * @created: 2022/4/1
 * @desc: 状态栏 / 导航栏工具类
 */
public final class BarUtils {
    /**
     * 底部导航栏
     */
    public static class NavigationBarUtils {
        /**
         * 隐藏底部导航栏
         *
         * @param window
         */
        public static void hideBottomNavigation(Window window) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            uiFlags |= 0x00001000;
            window.getDecorView().setSystemUiVisibility(uiFlags);
            window.setFormat(PixelFormat.TRANSLUCENT);
        }
    }


    /**
     * 顶部状态栏
     */
    public static class StatusBarUtils {
        /**
         * 沉浸式状态栏(系统状态栏透明)
         *
         * @param activity
         */
        public static void setStatusBarAlpha(Activity activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = activity.getWindow();
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }
}
