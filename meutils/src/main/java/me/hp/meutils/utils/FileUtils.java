package me.hp.meutils.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.bell.ai.framework.base.config.StoragePathConfig;

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

    public static String saveTempBitmap(Bitmap bitmap, File file) throws IOException {
        if (file.exists()) {
            file.delete();
        }
        Bitmap.CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try {
            stream = new FileOutputStream(file);
            bitmap.compress(format, quality, stream);
            stream.flush();
        } finally {
            if (stream != null) {
                stream.close();
            }
        }
        return file.getAbsolutePath();
    }

    public static void copyFile(File src, File dest) throws IOException {
        copyFile(new FileInputStream(src), dest);
    }

    public static void copyFile(InputStream is, File dest) throws IOException {
        if (is == null) {
            return;
        }
        if (dest.exists()) {
            dest.delete();
        }
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            bis = new BufferedInputStream(is);
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
     * @return
     */
    public static String getMd5ByFile(File file) throws Exception {
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void unZipFile(String fileName, String outPath) {
        //解压文件
        try {
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
            File deleteFile = new File(fileName);
            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void write2File(String filePath, String FileName, String value) {
        if (value.length() < 1) {
            return;
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
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }


    public static void appendFile(String filePath, String FileName, String value) {
        if (value.length() < 1) {
            return;
        }
        File dir = new File(filePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File h5ConfigFile = new File(filePath + FileName);

        FileOutputStream fos = null;
        OutputStreamWriter bos = null;
        try {
            if (!h5ConfigFile.exists()) {
                h5ConfigFile.createNewFile();
            }
            fos = new FileOutputStream(h5ConfigFile, true);
            bos = new OutputStreamWriter(fos, "utf-8");
            bos.write(value);
            bos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

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
//        if (StringUtils.isEmpty(path)) return false;
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
