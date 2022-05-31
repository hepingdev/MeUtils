package me.hp.meutils.ui.mvp;

/**
 * @author: hepingdev
 * @created: 2019/1/19.
 * @desc: MVP presenter基类
 */
public abstract class IPresenter<V> {
    protected V mView;

    public void setView(V view) {
        mView = view;
    }

    /**
     * -------------------------声明周期回调-------------------------
     */
    public void onCreate() {
    }

    public void onStart() {
    }

    public void onRestart() {
    }

    public void onPause() {
    }

    public void onDestroy() {
        mView = null;
    }
}
