package me.hp.meutils.ui.mvp

/**
 * @author: HePing
 * @created: 2020/1/1.
 * @desc:
 */
abstract class IPresenter<V> {
    var view: V? = null

    fun onDestroy() {
        view = null
    }
}
