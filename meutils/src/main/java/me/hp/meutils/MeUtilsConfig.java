package me.hp.meutils;

import android.content.Context;

import me.hp.meutils.utils.LogUtils;

/**
 * @author: HePing
 * @created: 2022/4/1
 * @desc: MeUtils配置
 */
public final class MeUtilsConfig {
    private static final String TAG = "MeUtilsConfig";

    private static MeUtilsConfig mUtilsConfig;
    /**
     * 全局Context
     *
     * @return
     */
    private Context mContext;
    private boolean isBottomNavigation;

    public static MeUtilsConfig getInstance() {
        if (mUtilsConfig == null) {
            throw new NullPointerException("MeUtilsConfig is null!");
        }
        return mUtilsConfig;
    }

    private MeUtilsConfig(Context context) {
        this.mContext = context;
    }

    public static MeUtilsConfig with(Context context) {
        if (mUtilsConfig == null) {
            mUtilsConfig = new MeUtilsConfig(context);
        }
        return mUtilsConfig;
    }

    /**
     * MeUtilsConfig日志开关
     *
     * @param isEnabled
     * @return
     */
    public MeUtilsConfig setLogEnabled(boolean isEnabled) {
        LogUtils.setEnable(isEnabled);
        return this;
    }

    /**
     * 是否显示底部导航栏
     */
    public MeUtilsConfig setBottomNavigation(boolean isEnabled) {
        this.isBottomNavigation = isEnabled;
        return this;
    }

    public Context getContext() {
        return mContext;
    }

    public boolean isBottomNavigation() {
        return isBottomNavigation;
    }
}
