package me.hp.meutils.ui.mvp;

import androidx.viewbinding.ViewBinding;

import me.hp.meutils.ui.IActivity;
import me.hp.meutils.utils.ClassUtils;
import me.hp.meutils.utils.LogUtils;

/**
 * @author: hepingdev
 * @created: 2018/09/04.
 * @desc: Activity基类(MVP开发架构)
 * 使用方法：继承此类，编写继承契约类（view继承自{@link IView} presenter继承自{@link IPresenter})，即可。
 */
public abstract class IMVPActivity<VB extends ViewBinding, P extends IPresenter> extends IActivity<VB> {

    protected P mPresenter;

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mPresenter = ClassUtils.newInstance(this, 1);
        if (mPresenter != null) {
            mPresenter.setView(this);
            mPresenter.onCreate();
        } else {
            LogUtils.e(TAG, "Presenter or PresenterView is null!");
        }
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (mPresenter != null) {
            mPresenter.onStart();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mPresenter != null) {
            mPresenter.onRestart();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPresenter != null) {
            mPresenter.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.onDestroy();
            mPresenter = null;
        }
    }
}
