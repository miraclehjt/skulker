package org.lpw.skulker.copier;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @auth lpw
 */
public class Module {
    private static final String IN = "copier/module/in/";
    private static final String OUT = "copier/module/out/";
    private static final String[] TYPES = {"Ctrl", "Model", "Service", "ServiceImpl", "Dao", "DaoImpl"};
    private static final String GETTER_SETTER = "\n" +
            "    @Jsonable\n" +
            "    @Column(name = \"COLUMN\")\n" +
            "    public TYPE getUPPER() {\n" +
            "        return NAME;\n" +
            "    }\n" +
            "\n" +
            "    public void setUPPER(TYPE NAME) {\n" +
            "        this.NAME = NAME;\n" +
            "    }";

    /**
     * 生成标准功能模块。
     *
     * @param module       模块名。
     * @param pkg          包名，不包含模块名。
     * @param tephra       Tephra包名，为null则使用默认（org.lpw.tephra）。
     * @param modelSupport ModelSupport包名，为null则使用默认（org.lpw.tephra.dao.model）。
     * @throws IOException 未处理IO读写异常。
     */
    public static void copy(String module, String pkg, String tephra, String modelSupport, String[][] columns) throws IOException {
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

        map.put("TEPHRA", tephra == null ? "org.lpw.tephra" : tephra);
        map.put("MODEL_SUPPORT", modelSupport == null ? "org.lpw.tephra.dao.model" : modelSupport);
        getterSetter(map, columns);

        for (String type : TYPES)
            Copier.copy(IN + type + ".java", out + module + type + ".java", map);
    }

    protected static void getterSetter(Map<String, String> map, String[][] columns) {
        if (columns == null) {
            map.put("MODEL_IMPORT", "");
            map.put("MODEL_FIELD", "");
            map.put("MODEL_GS", "");

            return;
        }

        Set<String> types = new HashSet<>();
        StringBuilder fields = new StringBuilder();
        StringBuilder gs = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            String name = getName(columns[i][0]);
            String type = columns[i][1];
            int indexOf = type.lastIndexOf('.');
            if (indexOf > -1) {
                types.add(type);
                type = type.substring(indexOf + 1);
            }
            fields.append("\n    private ").append(type).append(' ').append(name).append(";");
            String upper = name.substring(0, 1).toUpperCase() + name.substring(1);
            if (gs.length() > 0)
                gs.append('\n');
            gs.append(GETTER_SETTER.replaceAll("COLUMN", columns[i][0]).replaceAll("TYPE", type)
                    .replaceAll("UPPER", upper).replaceAll("NAME", name));
        }
        StringBuilder imports = new StringBuilder();
        types.forEach(type -> imports.append(imports.length() == 0 ? "\n" : "").append("\nimport ").append(type).append(";"));

        map.put("MODEL_IMPORT", imports.toString());
        map.put("MODEL_FIELD", fields.toString());
        map.put("MODEL_GS", gs.toString());
    }

    protected static String getName(String column) {
        String name = column;
        if (name.startsWith("c_"))
            name = name.substring(2);
        for (int indexOf; (indexOf = name.indexOf('_')) > -1; ) {
            String ch = name.substring(indexOf, indexOf + 2);
            name = name.replaceAll(ch, ch.substring(1).toUpperCase());
        }

        return name;
    }
}
