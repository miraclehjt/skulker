package org.lpw.skulker.util;

import java.io.*;

/**
 * @auth lpw
 */
public class Io {
    /**
     * 读取指定路径下的文件。
     *
     * @param path 文件所在路径。
     * @return 文件内容。如果读文件异常则返回null，如果文件为空则返回空字符串""。
     * @throws IOException 未处理IO读写异常。
     */
    public static byte[] read(String path) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        InputStream input = new FileInputStream(path);
        copy(input, output);
        input.close();
        output.close();

        return output.toByteArray();
    }

    /**
     * 写入文件。
     *
     * @param path    文件所在路径。
     * @param content 文件内容。
     * @throws IOException 未处理IO读写异常。
     */
    public static void write(String path, byte[] content) throws IOException {
        OutputStream output = new FileOutputStream(path);
        output.write(content);
        output.close();
    }

    /**
     * 将输入流中的数据复制到输出流中。
     *
     * @param input  输入流。
     * @param output 输出流。
     * @throws IOException 未处理IO读写异常。
     */
    public static void copy(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        for (int length; (length = input.read(buffer)) > -1; )
            output.write(buffer, 0, length);
        output.flush();
    }

    /**
     * 删除文件。
     *
     * @param path 文件所在目录。
     */
    public static void delete(String path) {
        delete(new File(path));
    }

    /**
     * 递归删除文件及目录。
     *
     * @param file 要删除的文件或目录。
     */
    protected static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null)
                for (File f : files)
                    f.delete();
        }
        file.delete();
    }
}
