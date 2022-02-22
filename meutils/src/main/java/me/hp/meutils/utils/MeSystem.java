package me.hp.meutils.utils;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

import static android.content.Context.AUDIO_SERVICE;

/**
 * @author: HePing
 * @created: 2022/1/1.
 * @desc:
 */
public class MeSystem {


    /**
     * 系统音量控制
     */
    public static class VolumeUtils {
        /**
         * 设置音量
         *
         * @param context
         * @param volumeValue 范围【0-100】
         */
        public static void setVolume(Context context, int volumeValue) {
            AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
            int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int volume = maxVolume * volumeValue / 100;
            Log.e("Volume", "setVolume: " + volume);
            am.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI);
        }

        /**
         * 获取当前的音量值
         *
         * @param context
         * @param needActual 是否输出0-100之间的数
         * @return
         */
        public static int getCurrentVolume(Context context, boolean needActual) {
            AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
            if (needActual) {
                int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                return (int) (Math.ceil((am.getStreamVolume(AudioManager.STREAM_MUSIC) * 100f) / maxVolume));
            } else {
                return am.getStreamVolume(AudioManager.STREAM_MUSIC);
            }
        }

        public static void addVolume(Context context) {
            AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
            int curVolue = am.getStreamVolume(AudioManager.STREAM_MUSIC) + 1;
            int maxVolume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            if (curVolue > maxVolume) {
                curVolue = maxVolume;
            }
            am.setStreamVolume(AudioManager.STREAM_MUSIC, curVolue, AudioManager.FLAG_SHOW_UI);
        }

        public static void subVolume(Context context) {
            AudioManager am = (AudioManager) context.getSystemService(AUDIO_SERVICE);
            int curVolue = am.getStreamVolume(AudioManager.STREAM_MUSIC) - 1;
            if (curVolue < 0) {
                curVolue = 0;
            }
            am.setStreamVolume(AudioManager.STREAM_MUSIC, curVolue, AudioManager.FLAG_SHOW_UI);
        }
    }

}
