package org.lpw.skulker;

import org.lpw.skulker.copier.Module;

import java.io.IOException;

/**
 * @auth lpw
 */
public class Demo {
    public static void main(String[] args) throws IOException {
        // 生成标准功能模块。
        Module.parse("lpw", "HelloWorld", "org.lpw.skulker.demo", null, null, null, 36,
                new String[][]{{"c_key", "FK", "uk", "Key"},
                        {"c_name", "VARCHAR(255)", "k", "名称"},
                        {"c_type", "int", "", "类型"},
                        {"c_time", "DateTime", "", "时间"}});
    }
}
