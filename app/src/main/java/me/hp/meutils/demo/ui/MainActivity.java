package me.hp.meutils.demo.ui;

import android.os.Bundle;

import me.hp.meutils.demo.databinding.ActivityMainBinding;
import me.hp.meutils.demo.ui.base.MVPActivity;
import me.hp.meutils.utils.LogUtils;
import me.hp.meutils.utils.SystemUtils;

/**
 * @author: HePing
 * @created: 2022/4/7
 * @desc: 首页
 */
public class MainActivity extends MVPActivity<ActivityMainBinding, MainContract.Presenter> implements MainContract.View {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    public void presenterAttachView() {
        getPresenter().setView(this);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        long tSize = SystemUtils.StorageUtils.getTotalBytes();
        long osSize = SystemUtils.StorageUtils.getOSBytes();
        LogUtils.d(TAG, "StorageUtils:" + SystemUtils.StorageUtils.getUnit(tSize, 1000) + " osSize:" + SystemUtils.StorageUtils.getUnit(osSize, 1000) + " total:" + SystemUtils.StorageUtils.getUnit(tSize + osSize, 1000));
    }

    @Override
    protected void initData() {

    }
}
