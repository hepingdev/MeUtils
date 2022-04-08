package me.hp.meutils.utils;

import android.net.TrafficStats;

import java.util.Timer;
import java.util.TimerTask;

import me.hp.meutils.MeUtils;

/**
 * @author: HePing
 * @created: 2021/10/14
 * @desc: 实时网速获取（实际这个网速没有参考意义，只是用作下载/上传时的速率显示）
 */
public final class NetSpeedUtils {
    private static final String TAG = "NetSpeedUtils";

    private long lastTotalRxBytes = 0;
    private long lastTimeStamp = 0;
    private Timer timer;
    private TimerTask mTimerTask;
    private long netSpeed;

    private boolean isStart;//是否已经开启

    public void startShowNetSpeed() {
        if (isStart) return;
        isStart = true;
        lastTotalRxBytes = getTotalRxBytes();
        lastTimeStamp = System.currentTimeMillis();
        // 1s后启动任务，每2s执行一次、
        if (timer == null) {
            timer = new Timer();
        }
        if (mTimerTask == null) {
            mTimerTask = new TimerTask() {
                @Override
                public void run() {
                    showNetSpeed();
                }
            };
        }
        timer.schedule(mTimerTask, 1000, 1000);
    }

    public void unbindShowNetSpeed() {
        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        isStart = false;
    }

    /**
     * KB输出
     *
     * @return
     */
    private long getTotalRxBytes() {
        return TrafficStats.getUidRxBytes(MeUtils.getInstance().getContext().getApplicationInfo().uid) == TrafficStats.UNSUPPORTED ? 0 : (TrafficStats.getTotalRxBytes() / 1024);//转为KB
    }

    private void showNetSpeed() {
        long nowTotalRxBytes = getTotalRxBytes();
        long nowTimeStamp = System.currentTimeMillis();
        netSpeed = Math.round((nowTotalRxBytes - lastTotalRxBytes) * 1000 / (nowTimeStamp - lastTimeStamp));//转换成KB/s
//        long speed2 = ((nowTotalRxBytes - lastTotalRxBytes) * 1000 % (nowTimeStamp - lastTimeStamp));//毫秒转换
        lastTimeStamp = nowTimeStamp;
        lastTotalRxBytes = nowTotalRxBytes;
//        Log.d(TAG, "showNetSpeed: " + netSpeed + "KB/s");
//        Log.d(TAG, "showNetSpeed: " + String.valueOf(speed) + "." + String.valueOf(speed2) + " KB/s");
    }

    /**
     * 获取网速(KB/s)
     *
     * @return
     */
    public long getNetSpeed() {
        return netSpeed;
    }
}
