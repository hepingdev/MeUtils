//package me.hp.meutils;
//
//import me.hp.meutils.utils.LogUtils;
//
///**
// * @author: hepingdev
// * @created: 2020/5/20.
// * @desc: MeUtils配置
// */
//public final class MeUtils {
//    public static MeUtils getInstance() {
//        return SingletonHolder.instance;
//    }
//
//    private static class SingletonHolder {
//        private static final MeUtils instance = new MeUtils();
//    }
//
//    private Builder mBuilder;
//
//    public void setBuilder(Builder mBuilder) {
//        this.mBuilder = mBuilder;
//    }
//
//    public boolean isHideBottomNavigation() {
//        return mBuilder.hideBottomNavigation;
//    }
//
//
//    /**
//     * 建造器
//     */
//    public static final class Builder {
//        private boolean hideBottomNavigation;//是否隐藏底部虚拟导航栏（默认不隐藏）
//
//        public Builder setHideBottomNavigation(boolean isHide) {
//            this.hideBottomNavigation = isHide;
//            return this;
//        }
//
//        public Builder setLogEnabled(boolean isEnabled) {
//            LogUtils.setEnable(isEnabled);
//            return this;
//        }
//
//        public void build(){
//            MeUtils.getInstance().setBuilder(this);
//        }
//    }
//}
