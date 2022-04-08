package me.hp.meutils.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author: HePing
 * @created: 2022/1/1.
 * @desc: 资源地址编码解码
 */
public final class URLUtil {

    /**
     * 解码
     *
     * @param url
     * @param charset 字符集  如：utf-8
     * @return
     */
    public static String decode(String url, String charset) {
        String result = "";
        try {
            result = URLDecoder.decode(url, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 编码
     *
     * @param url
     * @param charset 字符集  如：utf-8
     * @return
     */
    public static String encode(String url, String charset) {
        String result = "";
        try {
            result = URLEncoder.encode(url, charset);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
}
