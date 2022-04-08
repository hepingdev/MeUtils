package me.hp.meutils.utils;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by heping on 2019/1/20.
 */
public class AssetsUtils {

    /**
     * 读取assets文件夹中的json文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getAssetsJsonFile(Context context, String fileName) {
        //将json文件转成字符串
        StringBuilder stringBuilder = new StringBuilder();
        InputStream inputStream;
        BufferedReader bufferedReader = null;
        try {
            //获取Assets资源管理器
            AssetManager assetManager = context.getAssets();
            inputStream = assetManager.open(fileName);//获取文件流
            //开始读取
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String readLine;
            while ((readLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }
}
