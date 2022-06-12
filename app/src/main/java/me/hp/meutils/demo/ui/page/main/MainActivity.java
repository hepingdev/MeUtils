package me.hp.meutils.demo.ui.page.main;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;

import org.jetbrains.annotations.NotNull;

import me.hp.meutils.demo.databinding.ActivityMainBinding;
import me.hp.meutils.demo.ui.page.main.contract.MainContract;
import me.hp.meutils.ui.mvp.IMVPActivity;
import me.hp.meutils.utils.LogUtils;

/**
 * @author: hepingdev
 * @created: 2022/1/1
 * @desc: 首页
 */
public class MainActivity extends IMVPActivity<ActivityMainBinding, MainContract.Presenter> implements MainContract.View {

    @Override
    protected void initView(Bundle savedInstanceState) {
    }

    @Override
    protected void initData() {
        mPresenter.init();
        mPresenter.initHappy();
    }

    @Override
    public void update(String text) {
        LogUtils.d(TAG, "update##" + text);
    }

    @Override
    public void happy(String happy) {
        LogUtils.d(TAG, "happy##"+happy);
    }
}
