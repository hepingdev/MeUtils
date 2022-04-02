package me.hp.meutils.utils;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.provider.Settings;

import me.hp.meutils.MeUtilsConfig;

import static android.content.Context.AUDIO_SERVICE;

/**
 * @author: HePing
 * @created: 2022/1/1.
 * @desc: 音量 / 屏幕 /
 */
public class SystemUtils {
    /**
     * 系统页面
     */
    public static class PageUtils {
        /**
         * 跳转到蓝牙页面
         */
        public static void bluetoothSetting() {
            MeUtilsConfig.getInstance().getContext().startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
        }

        /**
         * 跳转wifi连接页面
         */
        public static void wifiSetting() {
            MeUtilsConfig.getInstance().getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }

        /**
         * 跳转到系统设置页面
         */
        public static void setting() {
            MeUtilsConfig.getInstance().getContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
        }

        /**
         * 跳转到日期时间设置页面
         */
        public static void dataOrTimeSetting() {
            MeUtilsConfig.getInstance().getContext().startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
        }

        /**
         * 跳转到指定程序的权限设置页面
         */
        public static void appPermissionSetting(String packageName) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MeUtilsConfig.getInstance().getContext().startActivity(intent);
        }
    }

    /**
     * 系统音量控制
     */
    public static class VolumeUtils {
        public static final String TAG = VolumeUtils.class.getSimpleName();

        /**
         * 设置音量
         *
         * @param volumeValue 范围【0-100】
         */
        public static void setVolume(int volumeValue) {
            AudioManager am = (AudioManager) MeUtilsConfig.getInstance().getContext().getSystemService(AUDIO_SERVICE);
            int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int volume = maxVolume * volumeValue / 100;
            LogUtils.d(TAG, "setVolume: " + volume);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
        }

        /**
         * 获取当前的音量值
         *
         * @param needActual 是否输出0-100之间的数
         * @return
         */
        public static int getCurrentVolume(boolean needActual) {
            AudioManager am = (AudioManager) MeUtilsConfig.getInstance().getContext().getSystemService(AUDIO_SERVICE);
            if (needActual) {
                int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                return (int) (Math.ceil((am.getStreamVolume(AudioManager.STREAM_MUSIC) * 100f) / maxVolume));
            } else {
                return am.getStreamVolume(AudioManager.STREAM_MUSIC);
            }
        }

        /**
         * 增加音量
         */
        public static void addVolume() {
            AudioManager am = (AudioManager) MeUtilsConfig.getInstance().getContext().getSystemService(AUDIO_SERVICE);
            int curVolue = am.getStreamVolume(AudioManager.STREAM_MUSIC) + 1;
            int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            if (curVolue > maxVolume) {
                curVolue = maxVolume;
            }
            am.setStreamVolume(AudioManager.STREAM_MUSIC, curVolue, AudioManager.FLAG_SHOW_UI);
        }

        /**
         * 减少音量
         */
        public static void subVolume() {
            AudioManager am = (AudioManager) MeUtilsConfig.getInstance().getContext().getSystemService(AUDIO_SERVICE);
            int curVolue = am.getStreamVolume(AudioManager.STREAM_MUSIC) - 1;
            if (curVolue < 0) {
                curVolue = 0;
            }
            am.setStreamVolume(AudioManager.STREAM_MUSIC, curVolue, AudioManager.FLAG_SHOW_UI);
        }
    }

    /**
     * 屏幕参数
     */
    public static class ScreenUtils {
        public static final String TAG = ScreenUtils.class.getSimpleName();

        /**
         * 获取屏幕像素宽度
         *
         * @return
         */
        public static int getDeviceWidthPixel() {
            return MeUtilsConfig.getInstance().getContext().getResources().getDisplayMetrics().widthPixels;
        }

        /**
         * 获取屏幕像素高度
         *
         * @return
         */
        public static int getDeviceHeightPixel() {
            return MeUtilsConfig.getInstance().getContext().getResources().getDisplayMetrics().heightPixels;
        }

        /**
         * 获取设备屏幕密度比例
         *
         * @return
         */
        public static float getDeviceDensity() {
            return MeUtilsConfig.getInstance().getContext().getResources().getDisplayMetrics().density;
        }

        /**
         * 获取设备屏幕密度
         *
         * @return
         */
        public static float getDeviceDensityDPI() {
            return MeUtilsConfig.getInstance().getContext().getResources().getDisplayMetrics().densityDpi;
        }

        /**
         * 打印设备屏幕基础信息
         *
         * @return
         */
        public static void printInfo() {
            LogUtils.d(TAG, "ScreenInfo:【width:" + getDeviceWidthPixel() + "px，height:" + getDeviceHeightPixel() + "px，density:" + getDeviceDensity() + "，densityDPI:" + getDeviceDensityDPI());
        }


        /**
         * 将px值转换为dip或dp值，保证尺寸大小不变
         *
         * @param pxValue （DisplayMetrics类中属性density）
         * @return
         */
        public static int px2dip(Context context, float pxValue) {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }

        /**
         * 将dip或dp值转换为px值，保证尺寸大小不变
         * 解释为什么要加0.5f, 其实思路就是跟getDimensionPixelSize一样的，往上取整
         *
         * @param dipValue （DisplayMetrics类中属性density）
         * @return
         */
        public static int dip2px(float dipValue) {
            final float scale = MeUtilsConfig.getInstance().getContext().getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }

        /**
         * 将px值转换为sp值，保证文字大小不变
         *
         * @param pxValue （DisplayMetrics类中属性scaledDensity）
         * @return
         */
        public static int px2sp(float pxValue) {
            final float fontScale = MeUtilsConfig.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         * @param spValue （DisplayMetrics类中属性scaledDensity）
         * @return
         */
        public static int sp2px(float spValue) {
            final float fontScale = MeUtilsConfig.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
    }

    /**
     * 多媒体
     */
    public static class MediaUtils {
        public static final String TAG = MediaUtils.class.getSimpleName();


    }
}
