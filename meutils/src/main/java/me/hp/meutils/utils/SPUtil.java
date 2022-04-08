package me.hp.meutils.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author: HePing
 * @created: 2019/4/20
 * @desc: SharedPreferences工具类
 */
public final class SPUtil {

    private SharedPreferences sharedPreferences;


    /**
     * 获取一条 boolean 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static boolean getBoolean(Context context, String sharedPreferencesName, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 获取一条 int 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static int getInt(Context context, String sharedPreferencesName, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取一条 long 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static long getLong(Context context, String sharedPreferencesName, String key, long defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取一条 float 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static float getFloat(Context context, String sharedPreferencesName, String key, float defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取一条 String 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static String getString(Context context, String sharedPreferencesName, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 获取一条 Set<String> 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static Set<String> getStringSet(Context context, String sharedPreferencesName, String key, Set<String> defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getStringSet(key, defValue);
    }

    /**
     * 清除指定的信息
     *
     * @param context 上下文
     * @param name    键名
     * @param key     若为null 则删除name下所有的键值
     */
    public static void clearPreference(Context context, String name, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            if (key != null) {
                editor.remove(key);
            } else {
                editor.clear();
            }
//        commit()是同步，会阻塞主线程，会返回结果。 apply()是异步，不会返回结果
            editor.apply();
        }
    }

    /**
     * 保存数据，数据类型仅限 boolean，int，long，float，String，String[]
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param value                 数据值
     * @return 保存成功则返回 true，否则返回 false
     */
    public static boolean saveData(Context context, String sharedPreferencesName, String key, Object value) {
        try {
            SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
            if (value instanceof Boolean) {
                return sp.edit().putBoolean(key, (Boolean) value).commit();
            } else if (value instanceof Integer) {
                return sp.edit().putInt(key, (Integer) value).commit();
            } else if (value instanceof Long) {
                return sp.edit().putLong(key, (Long) value).commit();
            } else if (value instanceof Float) {
                return sp.edit().putFloat(key, (Float) value).commit();
            } else if (value instanceof String) {
                return sp.edit().putString(key, (String) value).commit();
            } else if (value instanceof String[]) {
                HashSet<String> stringSet = new HashSet<>();
                Collections.addAll(stringSet, (String[]) value);
                return sp.edit().putStringSet(key, stringSet).commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取一条 boolean 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static boolean getData(Context context, String sharedPreferencesName, String key, boolean defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 获取一条 int 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static int getData(Context context, String sharedPreferencesName, String key, int defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getInt(key, defValue);
    }

    /**
     * 获取一条 long 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static long getData(Context context, String sharedPreferencesName, String key, long defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getLong(key, defValue);
    }

    /**
     * 获取一条 float 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static float getData(Context context, String sharedPreferencesName, String key, float defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getFloat(key, defValue);
    }

    /**
     * 获取一条 String 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static String getData(Context context, String sharedPreferencesName, String key, String defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getString(key, defValue);
    }

    /**
     * 获取一条 Set<String> 数据
     *
     * @param context               上下文
     * @param sharedPreferencesName SharedPreferences名称
     * @param key                   数据名称
     * @param defValue              默认值
     * @return 返回数据的值。如果获取失败，则返回默认值
     */
    public static Set<String> getData(Context context, String sharedPreferencesName, String key, Set<String> defValue) {
        SharedPreferences sp = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        return sp.getStringSet(key, defValue);
    }

    /**
     * 清空数据
     */
    public static void clearData(Context context, String sharedPreferencesName) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE);
        if(sharedPreferences != null){
            //先拿到SharedPreferences的编辑器
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
        }
    }
}
