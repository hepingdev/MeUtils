package me.hp.meutils.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: hepingdev
 * @created: 2018/06/02.
 * @desc: 字符串工具类
 */
public final class StringUtils {
    public static final String TAG = StringUtils.class.getSimpleName();


    /**
     * 判断一个字符串是否为空。会自动去掉首尾空格
     *
     * @param str 字符串
     * @return 字符串不为 null 且不是空字符串时返回 false，否则返回true
     */
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断一个或多个字符串是否为空
     * 常见使用场景： 需要判断多个字符串是否存在空，比如账号密码
     * @param strArray 字符串数组
     * @return 所有字符串均不为空时返回 false，否则返回true
     */
    public static boolean hasEmpty(String... strArray) {
        for (String str : strArray) {
            if(isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 截取并重编码字符串
     *
     * @param text   目标字符串
     * @param length 截取长度
     * @param encode 采用的编码方式
     * @return 截取后的字符串。如果重编码失败，则返回null
     */
    public static String substring(String text, int length, String encode) {
        if (text == null) {
            return null;
        }
        try {
            StringBuilder sb = new StringBuilder();
            int currentLength = 0;
            for (char c : text.toCharArray()) {
                currentLength += String.valueOf(c).getBytes(encode).length;
                if (currentLength <= length) {
                    sb.append(c);
                } else {
                    break;
                }
            }
            return sb.toString();
        } catch (Exception e) {
            Log.e(TAG, "substring##error!", e);
        }
        return null;
    }

    /**
     * 将字符串中的字符都转换为全角
     *
     * @param str 要转换的字符串
     * @return 全角字符串
     */
    public static String toSBC(String str) {
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    public enum MD5Type {
        LOWER_16, LOWER_32, UPPER_16, UPPER_32
    }

    /**
     * 获取字符串MD5
     * @param text
     * @param md5Type 16位大写小写，32位大写小写
     * @return MD5
     */
    public static String getMD5(String text, MD5Type md5Type) {
        StringBuffer sb = new StringBuffer();
        // 得到一个信息摘要器
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] result = digest.digest(text.getBytes());
            // 把每一个byte做一个与运算 0xff
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    sb.append("0");
                }
                sb.append(str);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        switch (md5Type) {
            case LOWER_16:
                return sb.toString().substring(8, 24);
            case LOWER_32:
                return sb.toString();
            case UPPER_16:
                return sb.toString().substring(8, 24).toUpperCase();
            case UPPER_32:
                return sb.toString().toUpperCase();
            default:
                return null;
        }
    }
}
