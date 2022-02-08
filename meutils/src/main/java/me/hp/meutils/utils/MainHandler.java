package me.hp.meutils.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @author: HePing
 * @created: 2019/1/20
 * @desc: Handler主线程回调
 */
public class MainHandler extends Handler {

    public static MainHandler getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final MainHandler instance = new MainHandler();
    }

    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
