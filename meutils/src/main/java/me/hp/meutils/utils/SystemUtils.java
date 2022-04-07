package me.hp.meutils.utils;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.provider.Settings;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

import me.hp.meutils.MeUtils;

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
            MeUtils.getInstance().getContext().startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
        }

        /**
         * 跳转wifi连接页面
         */
        public static void wifiSetting() {
            MeUtils.getInstance().getContext().startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
        }

        /**
         * 跳转到系统设置页面
         */
        public static void setting() {
            MeUtils.getInstance().getContext().startActivity(new Intent(Settings.ACTION_SETTINGS));
        }

        /**
         * 跳转到日期时间设置页面
         */
        public static void dataOrTimeSetting() {
            MeUtils.getInstance().getContext().startActivity(new Intent(Settings.ACTION_DATE_SETTINGS));
        }

        /**
         * 跳转到指定程序的权限设置页面
         */
        public static void appPermissionSetting(String packageName) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MeUtils.getInstance().getContext().startActivity(intent);
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
            AudioManager am = (AudioManager) MeUtils.getInstance().getContext().getSystemService(AUDIO_SERVICE);
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
            AudioManager am = (AudioManager) MeUtils.getInstance().getContext().getSystemService(AUDIO_SERVICE);
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
            AudioManager am = (AudioManager) MeUtils.getInstance().getContext().getSystemService(AUDIO_SERVICE);
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
            AudioManager am = (AudioManager) MeUtils.getInstance().getContext().getSystemService(AUDIO_SERVICE);
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
            return MeUtils.getInstance().getContext().getResources().getDisplayMetrics().widthPixels;
        }

        /**
         * 获取屏幕像素高度
         *
         * @return
         */
        public static int getDeviceHeightPixel() {
            return MeUtils.getInstance().getContext().getResources().getDisplayMetrics().heightPixels;
        }

        /**
         * 获取设备屏幕密度比例
         *
         * @return
         */
        public static float getDeviceDensity() {
            return MeUtils.getInstance().getContext().getResources().getDisplayMetrics().density;
        }

        /**
         * 获取设备屏幕密度
         *
         * @return
         */
        public static float getDeviceDensityDPI() {
            return MeUtils.getInstance().getContext().getResources().getDisplayMetrics().densityDpi;
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
            final float scale = MeUtils.getInstance().getContext().getResources().getDisplayMetrics().density;
            return (int) (dipValue * scale + 0.5f);
        }

        /**
         * 将px值转换为sp值，保证文字大小不变
         *
         * @param pxValue （DisplayMetrics类中属性scaledDensity）
         * @return
         */
        public static int px2sp(float pxValue) {
            final float fontScale = MeUtils.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
            return (int) (pxValue / fontScale + 0.5f);
        }

        /**
         * 将sp值转换为px值，保证文字大小不变
         *
         * @param spValue （DisplayMetrics类中属性scaledDensity）
         * @return
         */
        public static int sp2px(float spValue) {
            final float fontScale = MeUtils.getInstance().getContext().getResources().getDisplayMetrics().scaledDensity;
            return (int) (spValue * fontScale + 0.5f);
        }
    }

    /**
     * 存储（byte）
     */
    public static class StorageUtils {
        public static final String TAG = StorageUtils.class.getSimpleName();
        private static DecimalFormat decimalFormat;


        /**
         * 单位转换
         *
         * @param storageSize
         * @param unit        换算进制  1024 或者 1000
         * @return
         */
        public static String getUnit(long storageSize, long unit) {
            if (storageSize == 0) {
                return "0KB";
            }

            //保留两位小数，同时四舍五入
            if (decimalFormat == null) {
                decimalFormat = new DecimalFormat("#.00");
            }

            if (storageSize < unit * unit) {//小于1M
                //KB显示
                return decimalFormat.format(storageSize / (float) unit) + "KB";

            } else if (storageSize >= unit * unit && storageSize < unit * unit * unit) {//小于1G
                //MB显示
                return decimalFormat.format(storageSize / unit / (float) unit) + "MB";

            } else if (storageSize >= unit * unit * unit && storageSize < unit * unit * unit * unit) {//小于1T
                //GB显示
                return decimalFormat.format(storageSize / unit / unit / (float) unit) + "GB";

            } else {
                //TB显示
                return decimalFormat.format(storageSize / unit / unit / unit / (float) unit) + "TB";
            }
        }

        /**
         * 是否挂载SDCard
         *
         * @return
         */
        public static boolean isSDCard() {
            String state = Environment.getExternalStorageState();
            return Environment.MEDIA_MOUNTED.equals(state);
        }

        /**
         * 获取系统总存储大小（这个大小是不包含系统占用的大小）
         * 比如是64G, 那么就等于这个值加操作系统占用的大小
         *
         * @return 字节bytes
         */
        public static long getTotalBytes() {
            if (isSDCard()) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                //获取总大小
                return statFs.getTotalBytes();
            }
            return 0;
        }

        /**
         * 获取当前系统可用的存储大小
         *
         * @return 字节bytes
         */
        public static long getAvailableBytes() {
            if (isSDCard()) {
                StatFs statFs = new StatFs(Environment.getExternalStorageDirectory().getPath());
                //可用大小
                return statFs.getAvailableBytes();
            }
            return 0;
        }

        /**
         * 获取操作系统占用的存储大小
         * 必须是7.1.1以上才能使用，以下的无法获取包含系统占用大小的总存储空间，只能获取可用的总存储空间（排除操作系统占用的）
         *
         * @return
         */
        @RequiresApi(api = Build.VERSION_CODES.N_MR1)
        public static long getOSBytes() {
            //总共，可使用
            long total = 0L, used = 0L, systemSize = 0L;

            StorageManager storageManager = (StorageManager) MeUtils.getInstance().getContext().getSystemService(Context.STORAGE_SERVICE);
            try {
                Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");
                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);

                for (Object obj : getVolumeInfo) {
                    Field getType = obj.getClass().getField("type");
                    int type = getType.getInt(obj);
//                    LogUtils.d(TAG, "type: " + type);
                    if (type == 1) {//TYPE_PRIVATE
                        long totalSize = 0L;
                        //获取内置内存总大小
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {//7.1.1
                            //5.0 6.0 7.0没有
                            Method getPrimaryStorageSize = StorageManager.class.getMethod("getPrimaryStorageSize");
                            totalSize = (long) getPrimaryStorageSize.invoke(storageManager);
                        }
                        systemSize = 0L;
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);
                            if (totalSize == 0) {
                                totalSize = f.getTotalSpace();
                            }
                            systemSize = totalSize - f.getTotalSpace();
                            used += totalSize - f.getFreeSpace();
                            total += totalSize;
                            return systemSize;
                        }
                    } else if (type == 0) {//TYPE_PUBLIC
                        //外置存储
                        Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
                        boolean readable = (boolean) isMountedReadable.invoke(obj);
                        if (readable) {
                            Method file = obj.getClass().getDeclaredMethod("getPath");
                            File f = (File) file.invoke(obj);
                            used += f.getTotalSpace() - f.getFreeSpace();
                            total += f.getTotalSpace();
                        }
                    }
                }
//                LogUtils.d(TAG, "total = " + getUnit(total, 1000) + " ,已用 used(with system) = " + getUnit(used, 1000) + "\n可用 available = " + getUnit(total - used, 1000));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return systemSize;
        }
    }
}
