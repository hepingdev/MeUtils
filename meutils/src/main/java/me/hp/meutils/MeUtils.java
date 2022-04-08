package me.hp.meutils;

import android.content.Context;

import me.hp.meutils.utils.LogUtils;

/**
 * @author: HePing
 * @created: 2022/4/1
 * @desc: MeUtils配置
 */
public final class MeUtils {
    public static MeUtils getInstance() {
        return SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static final MeUtils instance = new MeUtils();
    }

    private Builder mBuilder;

    public void setBuilder(Builder mBuilder) {
        this.mBuilder = mBuilder;
    }

    public Context getContext() {
        return mBuilder.mContext;
    }

    public boolean isHideBottomNavigation() {
        return mBuilder.hideBottomNavigation;
    }


    /**
     * 建造器
     */
    public static final class Builder {
        private Context mContext;//全局上下文
        private boolean hideBottomNavigation;//是否隐藏底部虚拟导航栏（默认不隐藏）

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setHideBottomNavigation(boolean isHide) {
            this.hideBottomNavigation = isHide;
            return this;
        }

        public Builder setLogEnabled(boolean isEnabled) {
            LogUtils.setEnable(isEnabled);
            return this;
        }

        public void build(){
            MeUtils.getInstance().setBuilder(this);
        }
    }
}
