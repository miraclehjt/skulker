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

    public static void main(String[] args) throws IOException {
        copy("HelloWorld", "org.lpw.skulker.demo");
    }

    public static void copy(String module, String pkg) throws IOException {
        Copier.init(OUT);
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
            Copier.copy(IN + type + ".java", OUT + module + type + ".java", map);
    }
}
