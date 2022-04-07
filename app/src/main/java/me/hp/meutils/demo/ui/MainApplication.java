package me.hp.meutils.demo.ui;

import android.app.Application;

import me.hp.meutils.MeUtils;

/**
 * @author: HePing
 * @created: 2022/4/7
 * @desc:
 */
public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MeUtils.getInstance().setBuilder(
                new MeUtils.Builder(this)
                        .setLogEnabled(true)
                        .setHideBottomNavigation(true));
    }
}
