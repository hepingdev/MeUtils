package me.hp.meutils.ui.views.webview;

import android.webkit.JavascriptInterface;

import me.hp.meutils.utils.LogUtils;

/**
 * @author: hepingdev
 * @created: 2020/6/20.
 * @desc:
 */
public class JSInterface {
    /**
     * 此方法用来参考
     * H5调用：window.android.todo("test");
     * @param command
     */
    @JavascriptInterface
    public void todo(String command) {
        LogUtils.d("JSInterface", "todo:" + command);
    }
}
