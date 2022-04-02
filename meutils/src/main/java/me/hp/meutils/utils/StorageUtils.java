package me.hp.meutils.utils;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import androidx.annotation.NonNull;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author: HePing
 * @Modify: 2021/12/28
 * @desc: 存储工具类，返回单位：byte
 */
public class StorageUtils {
    public static long systemStorageSize = 0;

    /**
     * 智能获取外部存储器的大小  不包括系统所占的内存
     * 注：获取系统的总内存需要传入上下文  并且如果获得一次之后  其实以后就不会变了
     * 所以会保存这个值
     *
     * @return
     */
    public static long getStrageSize() {
        if (0 != systemStorageSize) {
            return systemStorageSize;
        }

        long size = 0;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSizeLong();
            long blockCount = sf.getBlockCountLong();
            size = blockSize * blockCount;
        }
        return size;
    }

    public static long getStrageFreeSize() {
        long size = 0;
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = sf.getBlockSizeLong();
            long availCount = sf.getAvailableBlocksLong();
            size = availCount * blockSize;
        }
        return size;
    }

    /**
     * 计算一下总共的内存数量  大小会保存在G_SystemStrageSize中
     *
     * @param context
     * @return
     */
    public static void calculatSystemStrageSize(@NonNull Context context) {
//        calculatSystemStrageSize(context, null);
    }

//    public static void calculatSystemStrageSize(@NonNull Context context, Callback<Long> callback) {
//        if (null == context || systemStorageSize > 0) {
//            if (callback != null) {
//                callback.callback(systemStorageSize);
//            }
//            return;
//        }
//        DeviceUtils.EXECUTORS.execute(() -> {
//            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
//            try {
//                Method getVolumes = StorageManager.class.getDeclaredMethod("getVolumes");//6.0
//                List<Object> getVolumeInfo = (List<Object>) getVolumes.invoke(storageManager);
//                long total = 0L, used = 0L;
//                for (Object obj : getVolumeInfo) {
//                    Field getType = obj.getClass().getField("type");
//                    int type = getType.getInt(obj);
//                    MyLogger.d("StorageUtil", "type: " + type);
//                    if (type == 1) {//TYPE_PRIVATE
//                        long totalSize = 0L;
//                        //获取内置内存总大小
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {//7.1.1
//                            //5.0 6.0 7.0没有
//                            Method getPrimaryStorageSize = StorageManager.class.getMethod("getPrimaryStorageSize");
//                            totalSize = (long) getPrimaryStorageSize.invoke(storageManager);
//                            long systemSize = 0L;
//                            Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
//                            boolean readable = (boolean) isMountedReadable.invoke(obj);
//                            if (readable) {
//                                Method file = obj.getClass().getDeclaredMethod("getPath");
//                                File f = (File) file.invoke(obj);
//
//                                if (totalSize == 0) {
//                                    totalSize = f.getTotalSpace();
//                                }
//                                systemSize = totalSize - f.getTotalSpace();
//                                used += totalSize - f.getFreeSpace();
//                                total += totalSize;
//                            }
//                        }
//                    } else if (type == 0) {//TYPE_PUBLIC
//                        //外置存储
////                    Method isMountedReadable = obj.getClass().getDeclaredMethod("isMountedReadable");
////                    boolean readable = (boolean) isMountedReadable.invoke(obj);
////                    if (readable) {
////                        Method file = obj.getClass().getDeclaredMethod("getPath");
////                        File f = (File) file.invoke(obj);
////                        used += f.getTotalSpace() - f.getFreeSpace();
////                        total += f.getTotalSpace();
////                    }
//                    } else if (type == 2) {//TYPE_EMULATED
//
//                    }
//                }
//                MyLogger.d("StorageUtil", "总内存 total = " + (total) + " ,已用 used(with system) = " + (used)
//                        + "\n可用 available = " + (total - used));
//                systemStorageSize = total;
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                if (callback != null) {
//                    callback.callback(systemStorageSize);
//                }
//            }
//        });
//    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFileSize(File file) {
        if (!file.exists())
            return 0;
        if (!file.isDirectory())
            return file.length();
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) size = size + getFileSize(fileList[i]);
                else size = size + fileList[i].length();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 应用外部的缓存目录
     *
     * @param context
     * @return
     */
    public static File getExternalCacheDir(Context context) {
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = context.getCacheDir();
        }
        return cacheDir;
    }


    /**
     * @param storageSize Byte
     * @return
     */
    public static String getShowStorageInfo(long storageSize) {
        return getShowStorageInfo(null, storageSize);
    }

    public static String getShowStorageInfo(DecimalFormat decimalFormat, long storageSize) {
        if (storageSize == 0) {
            return "0KB";
        }
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.00");
        }
        //KB显示
        if (storageSize < 1024 * 1024) {
            return decimalFormat.format(storageSize / 1024f) + "KB";
        } else if (storageSize >= 1024 * 1024 && storageSize < 1024 * 1024 * 1024) {
            //MB显示
            return decimalFormat.format(storageSize / 1024f / 1024f) + "MB";
        } else {
            //GB显示
            return decimalFormat.format(storageSize / 1024f / 1024f / 1024f) + "GB";
        }
    }
}
