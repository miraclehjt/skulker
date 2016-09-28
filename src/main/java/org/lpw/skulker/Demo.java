package org.lpw.skulker;

import org.lpw.skulker.copier.Module;

import java.io.IOException;

/**
 * @auth lpw
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        // 生成标准功能模块。
        Module.copy("HelloWorld", "org.lpw.skulker.demo", null, null,
                new String[][]{{"c_name", "VARCHAR(255)", "k", "名称"},
                        {"c_type", "INT", "", "类型"},
                        {"c_time", "DATETIME", "", "时间"}});
    }
}
