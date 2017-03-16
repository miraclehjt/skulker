package org.lpw.skulker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author lpw
 */
public class MergeSql {
    public static void main(String[] args) throws IOException {
        new File("output").mkdirs();
        OutputStream outputStream = new FileOutputStream("output/ranch.sql");
        scan(outputStream, "create.sql", new File("/home/lpw/work/ranch"));
        outputStream.close();
    }

    private static void scan(OutputStream outputStream, String name, File file) throws IOException {
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                scan(outputStream, name, f);

            return;
        }

        if (!file.getName().equals(name))
            return;

        System.out.println(file.getAbsolutePath());
        InputStream inputStream = new FileInputStream(file);
        byte[] buffer = new byte[1024];
        for (int len; (len = inputStream.read(buffer)) > -1; )
            outputStream.write(buffer, 0, len);
        outputStream.write("\n".getBytes());
        inputStream.close();
    }
}
