package me.hp.meutils.utils;

import android.graphics.PixelFormat;
import android.view.View;
import android.view.Window;

/**
 * @author: HePing
 * @created: 2022/4/1
 * @desc: 状态栏 / 导航栏工具类
 */
public class BarUtils {
    /**
     * 导航栏
     */
    public static class NavigationUtils {
        /**
         * 隐藏底部导航栏
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
}
