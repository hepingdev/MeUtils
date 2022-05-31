package me.hp.meutils.ui.observer;

import androidx.lifecycle.DefaultLifecycleObserver;

/**
 * @author: hepingdev
 * @created: 2020/09/09.
 * @desc: Activity / fragment生命周期回调监听类
 * 使用方法：
 * 1. 创建一个类（A）继承此类
 * 2. 在实现了{@link androidx.lifecycle.LifecycleOwner}的类{@link androidx.appcompat.app.AppCompatActivity} or {@link androidx.fragment.app.Fragment}中使用getLifecycle().addObserver(new A());即可监听。
 */
public class PageLifecycleCallback implements DefaultLifecycleObserver {
}

