package me.hp.meutils.demo.ui;

import android.os.Bundle;

import me.hp.meutils.demo.databinding.ActivityMainBinding;
import me.hp.meutils.ui.mvp.IMVPActivity;
import me.hp.meutils.utils.LogUtils;

/**
 * @author: HePing
 * @created: 2022/4/7
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
