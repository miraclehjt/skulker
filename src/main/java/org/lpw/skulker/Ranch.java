package org.lpw.skulker;

import org.lpw.skulker.copier.Module;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lpw
 */
public class Ranch {
    public static void main(String[] args) {
        last();
    }

    private static void last() {
        copy("Last", null, new String[][]{{"c_user", "FK", "uk", "用户ID"},
                {"c_type", "VARCHAR(255)", "n", "类型"},{"c_json", "TEXT", "", "扩展数据"},
                {"c_time", "DATETIME", "", "时间"}
        }, false, false);
    }

    private static void snapshot() {
        copy("Snapshot", null, new String[][]{{"c_data", "TEXT", "n", "数据"},
                {"c_content", "TEXT", "n", "内容"}, {"c_time", "DATETIME", "", "时间"},
                {"c_md5", "CHAR(32)", "uk", "MD5值"}
        }, false, false);
    }

    private static void message() {
        copy("Message", null, new String[][]{{"c_sender", "FK", "n", "发送者ID"},
                {"c_type", "INT", "", "接收者类型：0-好友；1-群组"}, {"c_receiver", "FK", "n", "接收者ID"},
                {"c_format", "INT", "", "消息格式：0-文本；1-图片；2-音频；3-视频；4-文件；5-红包；6-公告；7-名片"},
                {"c_content", "VARCHAR(2048)", "n", "内容"}, {"c_time", "DATETIME", "k", "发送时间"},
                {"c_code", "VARCHAR(64)", "uk", "校验码"}
        }, false, false);
    }

    private static void member() {
        copy("Member", "group", new String[][]{{"c_group", "FK", "k", "群组ID"},
                {"c_user", "FK", "k", "用户ID"}, {"c_nick", "VARCHAR(255)", "", "群组昵称"},
                {"c_reason", "VARCHAR(255)", "", "申请加入理由"},
                {"c_type", "INT", "", "类型：0-待审核；1-普通成员；2-管理员；3-所有者"},
                {"c_join", "DATETIME", "", "加入时间"}
        }, false, false);
    }

    private static void group() {
        copy("Group", null, new String[][]{{"c_owner", "FK", "k", "所有者ID"},
                {"c_name", "VARCHAR(255)", "", "名称"}, {"c_note", "VARCHAR(255)", "", "公告"},
                {"c_member", "INT", "", "成员数"}, {"c_audit", "INT", "", "新成员是否需要审核：0-否；1-是"},
                {"c_create", "DATETIME", "n", "创建时间"}
        }, false, false);
    }

    private static void friend() {
        copy("Friend", null, new String[][]{{"c_owner", "FK", "k", "所有者ID"},
                {"c_user", "FK", "", "好友ID"}, {"c_memo", "VARCHAR(255)", "", "备注"},
                {"c_state", "INT", "", "状态：0-待对方确认；1-待己方确认；2-已通过；3-已拒绝/拉黑"},
                {"c_create", "DATETIME", "n", "创建时间"}
        }, false, false);
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
                {"c_source", "TEXT", "n", "内容源"}, {"c_content", "TEXT", "n", "内容"},
                {"c_read", "INT", "", "阅读次数"}, {"c_favorite", "INT", "", "收藏次数"},
                {"c_comment", "INT", "", "评论次数"}, {"c_score", "INT", "", "得分"},
                {"c_time", "DATETIME", "n", "更新时间"}
        }, true, true);
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
                        {"c_key", "VARCHAR(255)", "", "键"}, {"c_value", "VARCHAR(255)", "n", "值"},
                        {"c_name", "VARCHAR(255)", "n", "名称"}, {"c_json", "TEXT", "", "JSON扩展"}},
                true, false);
    }

    private static void copy(String module, String pkg, String[][] columns, boolean recycle, boolean audit) {
        try {
            List<String[]> list = new ArrayList<>();
            list.addAll(Arrays.asList(columns));
            if (audit) {
                list.add(new String[]{"c_audit", "INT", "ignore", "审核：0-待审核；1-审核通过；2-审核不通过"});
                list.add(new String[]{"c_audit_remark", "VARCHAR(255)", "ignore", "审核备注"});
            }
            if (audit || recycle)
                list.add(new String[]{"c_recycle", "INT", "ignore", "回收站；0-否，1-是"});
            String[][] array = list.toArray(new String[0][]);

            if (audit)
                Module.parse("lpw", module, (pkg == null ? "org.lpw.ranch" : ("org.lpw.ranch." + pkg)), null,
                        "org.lpw.ranch.audit", "AuditModelSupport", 36, array);
            else if (recycle)
                Module.parse("lpw", module, (pkg == null ? "org.lpw.ranch" : ("org.lpw.ranch." + pkg)), null,
                        "org.lpw.ranch.recycle", "RecycleModelSupport", 36, array);
            else
                Module.parse("lpw", module, (pkg == null ? "org.lpw.ranch" : ("org.lpw.ranch." + pkg)), null,
                        null, null, 36, columns);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
