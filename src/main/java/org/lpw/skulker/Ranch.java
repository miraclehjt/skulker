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

    private static void doc() {
        copy("Doc", null, new String[][]{{"c_key", "VARCHAR(255)", "k", "类型KEY"},
                {"c_owner", "FK", "k", "所有者ID"}, {"c_author", "FK", "k", "作者ID"},
                {"c_score_min", "INT", "", "最小分值"}, {"c_score_max", "INT", "", "最大分值"},
                {"c_sort", "INT", "", "顺序"}, {"c_subject", "VARCHAR(255)", "n", "标题"},
                {"c_image", "VARCHAR(255)", "", "主图URI地址"}, {"c_thumbnail", "VARCHAR(255)", "", "缩略图URI地址"},
                {"c_summary", "TEXT", "", "摘要"}, {"c_label", "TEXT", "", "标签"},
                {"c_content", "TEXT", "n", "内容"}, {"c_read", "INT", "", "阅读次数"},
                {"c_favorite", "INT", "", "收藏次数"}, {"c_comment", "INT", "", "评论次数"},
                {"c_score", "INT", "", "得分"}, {"c_time", "DATETIME", "n", "时间"}
        }, false, true);
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
                String[][] array = new String[columns.length + 2][];
                System.arraycopy(columns, 0, array, 0, columns.length);
                array[array.length - 2] = new String[]{"c_audit", "INT", "ignore", "审核：0-待审核；1-审核通过；2-审核不通过"};
                array[array.length - 1] = new String[]{"c_audit_remark", "VARCHAR(255)", "ignore", "审核备注"};

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
