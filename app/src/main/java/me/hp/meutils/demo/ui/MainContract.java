package me.hp.meutils.demo.ui;

import me.hp.meutils.ui.mvp.IPresenter;
import me.hp.meutils.ui.mvp.IView;

/**
 * @author: HePing
 * @created: 2022/4/7
 * @desc:
 */
public final class MainContract {

    public static class Presenter extends IPresenter<View> {
        public void init(){

        }
    }

    public interface View extends IView {
//        void update();
    }
}
