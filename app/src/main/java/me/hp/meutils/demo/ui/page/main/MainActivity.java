package me.hp.meutils.demo.ui.page.main;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Arrays;

import me.hp.meutils.demo.databinding.ActivityMainBinding;
import me.hp.meutils.demo.ui.page.main.adapter.MainListAdapter;
import me.hp.meutils.demo.ui.page.main.contract.MainContract;
import me.hp.meutils.ui.mvp.IMVPActivity;
import me.hp.meutils.utils.LogUtils;

/**
 * @author: hepingdev
 * @created: 2022/1/1
 * @desc: 首页
 */
public class MainActivity extends IMVPActivity<ActivityMainBinding, MainContract.Presenter> implements MainContract.View {

    private MainListAdapter mAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (mAdapter == null)
            mAdapter = new MainListAdapter(Arrays.asList("北京", "上海", "杭州", "深圳", "广州", "成都", "长沙"));
        mBinding.activityMainRecycler.setLayoutManager(new LinearLayoutManager(this));
        mBinding.activityMainRecycler.setAdapter(mAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void update(String text) {
        LogUtils.d(TAG, "update##" + text);
    }

    @Override
    public void happy(String happy) {
        LogUtils.d(TAG, "happy##" + happy);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter = null;
    }
}
