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

    private static void auth() {
        copy("Auth", "user", new String[][]{{"c_user", "FK", "", "用户ID"},
                {"c_uid", "VARCHAR(255)", "uk", "认证ID"}, {"c_type", "INT", "", "类型：0-机器码；1-自有账号；其他为第三方账号"}
        }, false, false);
    }

    private static void user() {
        // 注：password需取消@Jsonable注解。
        copy("User", null, new String[][]{{"c_password", "CHAR(32)", "", "密码"},
                {"c_name", "VARCHAR(255)", "", "姓名"}, {"c_nick", "VARCHAR(255)", "", "昵称"},
                {"c_mobile", "CHAR(11)", "k", "手机号"}, {"c_email", "VARCHAR(255)", "k", "Email地址"},
                {"c_portrait", "VARCHAR(255)", "", "头像"}, {"c_gender", "INT", "", "性别：0-未知；1-男；2-女"},
                {"c_address", "VARCHAR(255)", "", "详细地址"}, {"c_birthday", "DATE", "", "出生日期"},
                {"c_code", "CHAR(8)", "uk", "唯一编码"}, {"c_register", "DATETIME", "", "注册时间"},
                {"c_grade", "INT", "", "等级：<50为用户；>=50为管理员；99为超级管理员"},
                {"c_state", "INT", "", "状态：0-正常；1-禁用"}
        }, false, false);
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
                {"c_pinyin", "VARCHAR(255)", "k", "拼音码"}, {"c_name", "VARCHAR(255)", "n", "名称"},
                {"c_json", "TEXT", "", "JSON扩展"}}, true, false);
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
