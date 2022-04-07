package me.hp.meutils.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.XXPermissions;

import java.util.List;

import me.hp.meutils.MeUtils;
import me.hp.meutils.utils.BarUtils;
import me.hp.meutils.utils.ClassUtils;
import me.hp.meutils.utils.LogUtils;


/**
 * @author: HePing
 * @created: 2018/09/04.
 * @desc:
 */
public abstract class IActivity<VB extends ViewBinding> extends AppCompatActivity implements OnPermissionCallback {
    public final String TAG = this.getClass().getSimpleName();
    protected VB mBinding;

    /**
     * 有时候需要先于加载视图做些操作，可重写此方法
     */
    protected void beforeSetContentView() {
        if (!MeUtils.getInstance().isHideBottomNavigation()) {
            BarUtils.NavigationUtils.hideBottomNavigation(getWindow());
        }
    }

    /**
     * 不是通过点击app图标启动（从后台程序点击进入）
     */
    protected void illegal() {
    }

    /**
     * 初始化presenter对象, MVPActivity需要，其他不用自己重写
     */
    protected void initPresenter() {
    }

    /**
     * 初始化View
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 此方法会请求权限成功后才会回调
     */
    protected abstract void initData();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d(TAG, "---------> onCreate <---------");
        //是从盒子app历史列表里面启动的
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_LAUNCHED_FROM_HISTORY) != 0) {
            LogUtils.e(TAG, "app非法启动!");
            illegal();
            return;
        }
        beforeSetContentView();
        mBinding = ClassUtils.viewBinding_newInstance(this, 0);
        if (mBinding == null) {
            LogUtils.e(TAG, "BindingView is null!!!");
            return;
        }
        setContentView(mBinding.getRoot());
        initPresenter();
        initView(savedInstanceState);

        /**
         * 请求权限
         */
        if (getPermissions() == null || getPermissions().length == 0 || XXPermissions.isGranted(this, getPermissions())) {
            initData();
            return;
        }
        XXPermissions.with(this).permission(getPermissions()).request(this);
    }

    /**
     * 页面需要的权限(页面进入一次性全部获取，如果需要分批，重写此方法)
     */
    protected String[] getPermissions() {
        return null;
    }

    @Override
    public void onGranted(List<String> permissions, boolean all) {
        if (all) {
            initData();
        } else {
            LogUtils.e(TAG, "权限被拒绝");
            // TODO: 2021/11/24 创建弹窗提醒 跳转到应用权限设置页
            //XXPermissions.startPermissionActivity(Activity activity, String... permissions);
        }
    }

    @Override
    public void onDenied(List<String> permissions, boolean never) {
        LogUtils.e(TAG, "权限被拒绝");
    }

    /**
     * 系统toolbar返回按钮
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    /**
     * 加载对话弹窗
     */
    public void showLoadDialog() {
    }

    public void hideLoadDialog() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "---------> onDestroy <---------");
    }
}
