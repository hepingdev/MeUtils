package me.hp.meutils.ui;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ProcessLifecycleOwner;

import me.hp.meutils.ui.observer.ApplicationLifecycleCallback;

/**
 * @author: hepingdev
 * @created: 2018/09/04.
 * @desc: Application入口类
 */
public abstract class IApplication extends Application {
    protected static Context mContext;
    private static ApplicationLifecycleCallback applicationLifecycleCallback;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //注册App生命周期观察者
        applicationLifecycleCallback = new ApplicationLifecycleCallback();
        ProcessLifecycleOwner.get().getLifecycle().addObserver(applicationLifecycleCallback);
        onApplicationCreate();
    }

    /**
     * 子类实现
     */
    protected abstract void onApplicationCreate();

    /**
     * 获取全局上下文
     *
     * @return
     */
    public static Context getContext() {
        return mContext;
    }

    /**
     * 判断APP是否在前台
     */
    public static boolean isAppForeground() {
        return applicationLifecycleCallback.isAppForeground();
    }
}
