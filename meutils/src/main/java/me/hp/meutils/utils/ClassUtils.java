package me.hp.meutils.utils;

import android.view.LayoutInflater;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author: HePing
 * @created: 2021/11/24
 * @desc:
 */
public final class ClassUtils {
    /**
     * 通过反射获取类的实例
     *
     * @param o
     * @param paramIndex
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Object o, int paramIndex) {
        try {
            Type type = o.getClass().getGenericSuperclass();
            Class<T> clazz = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[paramIndex];
            if (clazz != null) {
                return clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用反射获取ViewBinding对象实例(一般是基类封装viewBinding使用)
     *
     * @return
     */
    public static <VB> VB viewBinding_newInstance(AppCompatActivity activity, int paramIndex) {
        try {
            Type type = activity.getClass().getGenericSuperclass();
            Class<VB> clazz = (Class<VB>) ((ParameterizedType) type).getActualTypeArguments()[paramIndex];
            if (clazz != null) {
                Method method = clazz.getMethod("inflate", LayoutInflater.class);
                return (VB) method.invoke(null, activity.getLayoutInflater());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
