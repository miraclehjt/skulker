package org.lpw.skulker.copier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @auth lpw
 */
public class Module {
    private static final String IN = "copier/module/in/";
    private static final String OUT = "copier/module/out/";
    private static final String[] TYPES = {"Ctrl", "Model", "Service", "ServiceImpl", "Dao", "DaoImpl"};

    /**
     * 生成标准功能模块。
     *
     * @param module       模块名。
     * @param pkg          包名，不包含模块名。
     * @param modelSupport ModelSupport包名。
     * @throws IOException 未处理IO读写异常。
     */
    public static void copy(String module, String pkg, String modelSupport) throws IOException {
        String out = OUT + module.toLowerCase() + "/";
        Copier.init(out);
        Map<String, String> map = new HashMap<>();
        map.put("MODULE", module);
        map.put("mODULE", module.substring(0, 1).toLowerCase() + module.substring(1));
        StringBuilder sb = new StringBuilder();
        for (char ch : module.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                ch += 'a' - 'A';
                if (sb.length() > 0)
                    sb.append('-');
            }
            sb.append(ch);
        }
        map.put("MODULE-", sb.toString());
        map.put("MODULE_", sb.toString().replace('-', '_'));

        map.put("PACKAGE0", module.toLowerCase());
        String[] packages = pkg.split("\\.");
        for (int i = 0; i < packages.length; i++)
            map.put("PACKAGE" + (packages.length - i), packages[i]);
        for (String type : TYPES)
            Copier.copy(IN + type + ".java", out + module + type + ".java", map);

        map.put("MODEL_SUPPORT", modelSupport);
    }
}
