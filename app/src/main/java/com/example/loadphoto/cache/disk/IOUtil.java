package com.example.loadphoto.cache.disk;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;

public class IOUtil {

    public static final int EXISTS = 1;
    public static final int IS_NOT_EXISTS = 2;
    public static final int IS_FILE = 4;
    public static final int IS_DIRECTORY = 8;
    public static final int IS_HIDDEN = 16;
    public static final int CAN_READ = 32;
    public static final int CAN_WRITE = 64;
    public static final int CAN_EXECUTE = 128;

    /**
     * 关闭指定的字节输入流（InputStream）
     *
     * @param in
     */
    public static void close(InputStream in) {
        if (in != null) {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭指定的字节输出流（OutputStream）
     *
     * @param out
     */
    public static void close(OutputStream out) {
        if (out != null) {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭指定的字符输入流（Reader）
     *
     * @param reader
     */
    public static void close(Reader reader) {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭指定的字符输出流（Writer）
     *
     * @param writer
     */
    public static void close(Writer writer) {
        if (writer != null) {
            try {
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查指定文件（File）对象的一个或多个属性： •File对象是否不存在 •File对象是否存在 •File对象是否为文件
     * •File对象是否为目录 •File对象是否为隐藏文件 •File对象是否可读 •File对象是否可写 •File对象是否可执行
     */

    public static boolean checkFile(File file, Integer attrs) {
        if (file == null) {
            return false;
        }
        // 判断文件对象是否存在
        if ((attrs & IOUtil.EXISTS) == IOUtil.EXISTS && !file.exists()) {
            return false;
        }
        // 判断文件对象是否不存在
        if ((attrs & IOUtil.IS_NOT_EXISTS) == IOUtil.IS_NOT_EXISTS
                && file.exists()) {
            return false;
        }
        // 文件对象是否可读
        if ((attrs & IOUtil.CAN_READ) == IOUtil.CAN_READ && !file.canRead()) {
            return false;
        }
        // 文件对象是否可读
        if ((attrs & IOUtil.CAN_WRITE) == IOUtil.CAN_WRITE && !file.canWrite()) {
            return false;
        }
        // 文件对象是否是文件
        if ((attrs & IOUtil.IS_FILE) == IOUtil.IS_FILE && !file.isFile()) {
            return false;
        }
        // 文件对象是否是具有隐藏属性
        if ((attrs & IOUtil.IS_HIDDEN) == IOUtil.IS_HIDDEN && !file.isHidden()) {
            return false;
        }
        // 文件对象是否是具有隐藏属性
        if ((attrs & IOUtil.IS_DIRECTORY) == IOUtil.IS_DIRECTORY
                && !file.isDirectory()) {
            return false;
        }
        // 文件对象的路径是否可执行
        if ((attrs & IOUtil.CAN_EXECUTE) == IOUtil.CAN_EXECUTE
                && !file.canExecute()) {
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        boolean flag = IOUtil.checkFile(new File("D:\\luo.txt"),
                IOUtil.IS_HIDDEN | IOUtil.EXISTS);
        System.out.println(flag);
    }
}