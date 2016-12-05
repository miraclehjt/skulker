package org.lpw.skulker;

import org.lpw.skulker.copier.Module;

import java.io.IOException;

/**
 * @author lpw
 */
public class Ranch {
    public static void main(String[] args) {
        comment();
    }

    private static void comment() {
        copy("Comment", null, new String[][]{{"c_key", "VARCHAR(255)", "", "服务KEY"},
                {"c_owner", "FK", "k", "所有者ID"}, {"c_author", "FK", "k", "作者ID"},
                {"c_subject", "VARCHAR(255)", "", "标题"}, {"c_label", "VARCHAR(255)", "", "标签"},
                {"c_content", "TEXT", "", "内容"}, {"c_score", "INT", "", "评分"},
                {"c_time", "DATETIME", "n", "时间"}
        }, false, true);
    }

    private static void classify() {
        copy("Classify", null, new String[][]{{"c_code", "VARCHAR(255)", "k", "编码"},
                {"c_name", "VARCHAR(255)", "n", "名称"}, {"c_label", "TEXT", "", "标签"}}, true, false);
    }

    private static void copy(String module, String pkg, String[][] columns, boolean recycle, boolean audit) {
        try {
            if (recycle) {
                String[][] array = new String[columns.length + 1][];
                System.arraycopy(columns, 0, array, 0, columns.length);
                array[array.length - 1] = new String[]{"c_recycle", "INT", "ignore", "回收站；0-否，1-是"};

                Module.parse("lpw", module, (pkg == null ? "org.lpw.ranch" : ("org.lpw.ranch." + pkg)), null,
                        "org.lpw.ranch.recycle", "RecycleModelSupport", 36, array);
            } else if (audit) {
                String[][] array = new String[columns.length + 1][];
                System.arraycopy(columns, 0, array, 0, columns.length);
                array[array.length - 1] = new String[]{"c_audit", "INT", "ignore", "审核：0-待审核；1-审核通过；2-审核不通过"};

                Module.parse("lpw", module, (pkg == null ? "org.lpw.ranch" : ("org.lpw.ranch." + pkg)), null,
                        "org.lpw.ranch.audit", "AuditModelSupport", 36, array);
            } else {
                Module.parse("lpw", module, (pkg == null ? "org.lpw.ranch" : ("org.lpw.ranch." + pkg)), null,
                        null, null, 36, columns);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
