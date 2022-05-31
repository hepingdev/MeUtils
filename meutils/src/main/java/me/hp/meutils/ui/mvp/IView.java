package me.hp.meutils.ui.mvp;

import androidx.lifecycle.LifecycleOwner;

/**
 * @author: hepingdev
 * @created: 2019/1/19.
 * @desc:
 */
public interface IView extends LifecycleOwner {

    default void showLoadDialog(){}

    default void hideLoadDialog(){}

}
