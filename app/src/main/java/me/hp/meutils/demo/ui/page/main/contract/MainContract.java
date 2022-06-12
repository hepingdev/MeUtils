package me.hp.meutils.demo.ui.page.main.contract;

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
            mView.update("这是请求的数据");
        }

        public void initHappy(){
            mView.happy("传递快乐");
        }
    }

    public interface View extends IView {
        void update(String text);

        void happy(String happy);
    }
}
