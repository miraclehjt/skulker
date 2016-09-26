package org.lpw.skulker.copier;

import org.lpw.skulker.util.Io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @auth lpw
 */
public class Copier {
    /**
     * 初始化输出路径。
     *
     * @param path 输出路径。
     */
    public static void init(String path) {
        Io.delete(path);
        new File(path).mkdirs();
    }

    /**
     * 复制文件。
     *
     * @param input  输入文件路径。
     * @param output 输出文件路径。
     * @param map    替换参数集。
     * @throws IOException 未处理IO读写异常。
     */
    public static void copy(String input, String output, Map<String, String> map) throws IOException {
        String string = new String(Io.read(input));
        List<String> keys = new ArrayList<>(map.keySet());
        Collections.sort(keys, (String o1, String o2) -> o2.length() - o1.length());
        for (String key : keys)
            string = string.replaceAll(key, map.get(key));
        Io.write(output, string.getBytes());
    }
}
