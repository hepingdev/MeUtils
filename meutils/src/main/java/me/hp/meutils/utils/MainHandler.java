package com.bell.ai.framework.base.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by ApplePie on 2019/1/20.
 */
public class MainHandler extends Handler {

    private static MainHandler mainHandler;

    public static MainHandler getInstance() {
        if (mainHandler == null) {
            synchronized (MainHandler.class) {
                if (mainHandler == null) {
                    mainHandler = new MainHandler();
                }
            }
        }
        return mainHandler;
    }

    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
