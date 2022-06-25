package me.hp.meutils.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @author: HePing
 * @created: 2022/4/1
 * @desc: 文件工具类
 */
public final class FileUtils {
    public static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 将bitmap保存为图片文件
     * 需在子线程调用
     *
     * @param bitmap
     * @param savePath(path and filename)
     * @param quality       0-100  最高质量是100 中等是50 越往下越差
     * @return
     * @throws IOException 如果抛出异常表示操作失败
     */
    public static File saveBitmap(Bitmap bitmap, String savePath, int quality) throws IOException {
        if (StringUtils.isEmpty(savePath)) throw new IOException("savePath is empty.");

        File saveFile = new File(savePath);
        if (saveFile != null && saveFile.exists()) {
            saveFile.delete();
        }
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(saveFile);
            bitmap.compress(format, quality, stream);
            stream.flush();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return saveFile;
    }

    /**
     * 复制文件
     *
     * @param src  原始文件
     * @param dest 目标文件
     * @throws IOException 如果抛出异常表示操作失败
     */
    public static void copyFile(File src, File dest) throws IOException {
        if (src == null || dest == null) {
            throw new IOException("src or dest is null!");
        }
        if (dest.exists()) {
            dest.delete();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(src));
            bos = new BufferedOutputStream(new FileOutputStream(dest));
            byte[] bytes = new byte[1024 * 10];
            int length;
            while ((length = bis.read(bytes)) != -1) {
                bos.write(bytes, 0, length);
            }
            bos.flush();
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (bis != null) {
                bis.close();
            }
        }
    }

    /**
     * 生成唯一标示
     *
     * @return
     */
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }

    /**
     * 计算文件的 MD5
     *
     * @param file
     * @throws Exception 如果抛出异常表示操作失败
     */
    public static String getFileMD5(File file) throws Exception {
        FileInputStream in = null;
        try {
            in = new FileInputStream(file);
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            return bi.toString(16);
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 解压文件
     *
     * @param fileName
     * @param outPath
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void unZipFile(String fileName, String outPath) throws IOException {
        //解压文件
        ZipFile zipFile = new ZipFile(fileName, Charset.forName("GBK"));
        InputStream is = null;
        Enumeration e = zipFile.entries();
        ZipEntry entry = null;
        File dstFile = null;
        FileOutputStream fos = null;
        int count = 0;
        byte[] buffer = new byte[8192];
        while (e.hasMoreElements()) {
            entry = (ZipEntry) e.nextElement();
            //如果是文件夹则不处理
            if (entry.isDirectory()) {
                continue;
            }
            //通过文件名解析出文件夹
            String[] subDirs = entry.getName().split("/");
            String dirName = outPath;
            String dstFileName = "";
            for (int i = 0; i < subDirs.length; i++) {
                String dir = subDirs[i];

                //最外层的文件夹  删除空格
                if (0 == i) {
                    dir = dir.replaceAll(" ", "");
                }

                if (subDirs.length - 1 == i) {
                    dstFileName = dir;
                } else {
                    dirName += dir + "/";
                }
            }
            File dir = new File(dirName);
            if (!dir.exists()) {
                Log.d(TAG, "unzip path:" + dir.getAbsolutePath());
                dir.mkdirs();
            }

            is = zipFile.getInputStream(entry);
            dstFile = new File(dirName + dstFileName);
//                dstFile.deleteOnExit();
            fos = new FileOutputStream(dstFile);
            while ((count = is.read(buffer, 0, buffer.length)) != -1) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
        }
        zipFile.close();
        //解压完删除压缩文件
        delFileOrFolder(fileName);
    }

    /**
     * 将字符串数据写入文件
     *
     * @param filePath
     * @param FileName
     * @param value
     */
    public static void write2File(String filePath, String FileName, String value) throws IOException {
        if (value.length() < 1) {
            throw new IOException("value length < 1!!!");
        }
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File h5ConfigFile = new File(filePath + FileName);
        if (h5ConfigFile.exists()) {
            h5ConfigFile.delete();
        }

        FileOutputStream fos = null;
        OutputStreamWriter bos = null;
        try {
            h5ConfigFile.createNewFile();
            fos = new FileOutputStream(h5ConfigFile);
            bos = new OutputStreamWriter(fos, "utf-8");
            bos.write(value);
            bos.flush();
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 在指定文件中追加数据
     *
     * @param filePath
     * @param FileName
     * @param value
     */
    public static void appendFile(String filePath, String FileName, String value) throws IOException {
        if (value.length() < 1) {
            throw new IOException("value length < 1!!!");
        }

        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File h5ConfigFile = new File(filePath + FileName);

        FileOutputStream fos = null;
        OutputStreamWriter bos = null;
        try {
            //文件不存在就直接新建
            if (!h5ConfigFile.exists()) {
                h5ConfigFile.createNewFile();
            }
            fos = new FileOutputStream(h5ConfigFile, true);
            bos = new OutputStreamWriter(fos, "utf-8");
            bos.write(value);
            bos.flush();
        } finally {
            if (bos != null) {
                bos.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 读取文件内容（字符串返回）
     *
     * @param path
     * @return if null or "" 代表读取失败或者空文件
     */
    public static String readFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return null;
        }
        String result = "";
        try {
            //打开文件输入流
            FileInputStream inputStream = new FileInputStream(file);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
            reader.close();
        } catch (Exception e1) {
            e1.printStackTrace();
            result = null;
        }
        return result;
    }

    /**
     * 获取文件大小
     *
     * @param path
     * @return
     */
    public static long getFileSize(String path) {
        if (StringUtils.isEmpty(path)) return 0;
        return getFileSize(new File(path));
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return byte
     */
    public static long getFileSize(File file) {
        if (file == null || !file.exists()) {
            return 0;
        } else if (file.isFile()) {
            return file.length();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                long total = 0;
                for (File sonFile : files) {
                    total += getFileSize(sonFile);
                }
                return total;
            }
        }
        return 0;
    }


    /**
     * Delete file or folder.
     *
     * @param path path.
     * @return is succeed.
     * @see #delFileOrFolder(File)
     */
    public static boolean delFileOrFolder(String path) {
        if (StringUtils.isEmpty(path)) return false;
        return delFileOrFolder(new File(path));
    }

    /**
     * Delete file or folder.
     *
     * @param file file.
     * @return is succeed.
     * @see #delFileOrFolder(String)
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean delFileOrFolder(File file) {
        if (file == null || !file.exists()) {
            // do nothing
        } else if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File sonFile : files) {
                    delFileOrFolder(sonFile);
                }
            }
            file.delete();
        }
        return true;
    }
}
