package me.hp.meutils.ui.mvp

import androidx.lifecycle.LifecycleOwner

/**
 * @author: HePing
 * @created: 2020/1/1.
 * @desc:
 */
interface IView : LifecycleOwner {

    fun showLoadDialog()

    fun hideLoadDialog()

}
