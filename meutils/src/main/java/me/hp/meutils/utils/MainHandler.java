package me.hp.meutils.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @author: hepingdev
 * @created: 2019/1/20.
 * @desc:
 */
public final class MainHandler extends Handler {

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
