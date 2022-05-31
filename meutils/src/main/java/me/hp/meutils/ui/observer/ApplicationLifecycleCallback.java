package me.hp.meutils.ui.observer;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

/**
 * @author: hepingdev
 * @created: 2020/09/09.
 * @desc: app前台和后台回调监听类
 * <p>
 * * Application生命周期观察，提供整个应用进程的生命周期
 * *
 * * Lifecycle.Event.ON_CREATE只会分发一次，Lifecycle.Event.ON_DESTROY不会被分发。
 * *
 * * 第一个Activity进入时，ProcessLifecycleOwner将分派Lifecycle.Event.ON_START, Lifecycle.Event.ON_RESUME。
 * * 而Lifecycle.Event.ON_PAUSE, Lifecycle.Event.ON_STOP，将在最后一个Activit退出后后延迟分发。如果由于配置更改而销毁并重新创建活动，则此延迟足以保证ProcessLifecycleOwner不会发送任何事件。
 * *
 * * 作用：监听应用程序进入前台或后台
 */
public class ApplicationLifecycleCallback implements DefaultLifecycleObserver {

    private boolean isAppForeground;

    @Override
    public void onStart(@NonNull LifecycleOwner owner) {
        isAppForeground = true;
    }

    @Override
    public void onStop(@NonNull LifecycleOwner owner) {
        isAppForeground = false;
    }

    /**
     * 获取app是否在后台的标志
     * @return
     */
    public boolean isAppForeground() {
        return isAppForeground;
    }
}
