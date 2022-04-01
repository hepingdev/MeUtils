package me.hp.meutils.demo.ui.base

import androidx.viewbinding.ViewBinding
import me.hp.meutils.ui.mvp.IPresenter
import me.hp.meutils.ui.mvp.IView
import me.hp.meutils.utils.ClassUtils
import me.hp.meutils.utils.LogUtils

/**
 * @author: HePing
 * @created: 2021/11/24
 * @desc:
 */
abstract class MVPActivity<VB : ViewBinding, BP : IPresenter<IView>> : BaseActivity<VB>() {

    var mPresenter: BP? = null

    override fun initPresenter() {
        super.initPresenter()
        mPresenter = ClassUtils.newInstance(this, 1)
        if (mPresenter != null) {
            presenterAttachView()
        } else {
            LogUtils.d(TAG, "")
        }
    }

    /**
     * presenter绑定view
     */
    abstract fun presenterAttachView()

    override fun onDestroy() {
        super.onDestroy()
        mPresenter?.onDestroy()
        mPresenter = null
    }
}
