package org.lpw.skulker;

import org.lpw.skulker.copier.Module;

import java.io.IOException;

/**
 * @author lpw
 */
public class Ranch {
    public static void main(String[] args) {
        classify();
    }

    private static void classify() {
        copy("Classify", null, new String[][]{{"c_code", "VARCHAR(255)", "k", "编码"},
                {"c_name", "VARCHAR(255)", "n", "名称"}, {"c_label", "TEXT", "", "标签"}});
    }

    private static void copy(String module, String pkg, String[][] columns) {
        try {
            String[][] array = new String[columns.length + 1][];
            for (int i = 0; i < columns.length; i++)
                array[i] = columns[i];
            array[columns.length] = new String[]{"c_recycle", "INT", "ignore", "回收站；0-否，1-是。"};

            Module.parse("lpw", module, (pkg == null ? "org.lpw.ranch" : ("org.lpw.ranch." + pkg)), null,
                    "org.lpw.ranch.model", null, 36, array);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
