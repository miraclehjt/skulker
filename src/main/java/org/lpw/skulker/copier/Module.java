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
     * @param columns      字段集；二维数组，每行元素依次为：字段名、类型、索引（k-索引）、说明。
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
        model(map, columns);

        for (String type : TYPES)
            Copier.copy(IN + type + ".java", out + module + type + ".java", map);
        Copier.copy(IN + "ddl.sql", out + "ddl.sql", map);
    }

    protected static void model(Map<String, String> map, String[][] columns) {
        if (columns == null) {
            map.put("MODEL_IMPORT", "");
            map.put("MODEL_FIELD", "");
            map.put("MODEL_GS", "");
            map.put("COLUMNS", "");
            map.put("KEYS", "");

            return;
        }

        Set<String> types = new HashSet<>();
        StringBuilder fields = new StringBuilder();
        StringBuilder gs = new StringBuilder();
        StringBuilder ddlColumns = new StringBuilder();
        StringBuilder ddlKeys = new StringBuilder();
        for (int i = 0; i < columns.length; i++) {
            String name = getName(columns[i][0]);
            String type = getType(columns[i][1]);
            int indexOf = type.lastIndexOf('.');
            if (indexOf > -1) {
                types.add(type);
                type = type.substring(indexOf + 1);
            }
            fields.append("\n    private ").append(type).append(' ').append(name).append("; // ").append(columns[i][3]);
            String upper = name.substring(0, 1).toUpperCase() + name.substring(1);
            if (gs.length() > 0)
                gs.append('\n');
            gs.append(GETTER_SETTER.replaceAll("COLUMN", columns[i][0]).replaceAll("TYPE", type)
                    .replaceAll("UPPER", upper).replaceAll("NAME", name));

            ddlColumns.append("\n  ").append(columns[i][0]).append(' ').append(columns[i][1]).append(' ');
            if (type.equals("int"))
                ddlColumns.append("DEFAULT 0");
            else
                ddlColumns.append(columns[i][2].equals("k") ? "NOT" : "DEFAULT").append(" NULL");
            ddlColumns.append(" COMMENT '").append(columns[i][3]).append("',");
            if (columns[i][2].equals("k")) {
                ddlKeys.append(",\n  KEY k_").append(map.get("PACKAGE1")).append('_').append(map.get("MODULE_"))
                        .append('_').append(columns[i][0].startsWith("c_") ? columns[i][0].substring(2) : columns[i][0])
                        .append('(').append(columns[i][0]).append(')');
            }
        }
        StringBuilder imports = new StringBuilder();
        types.forEach(type -> imports.append(imports.length() == 0 ? "\n" : "").append("\nimport ").append(type).append(";"));

        map.put("MODEL_IMPORT", imports.toString());
        map.put("MODEL_FIELD", fields.toString());
        map.put("MODEL_GS", gs.toString());
        map.put("DDL_COLUMNS", ddlColumns.toString());
        map.put("DDL_KEYS", ddlKeys.toString());
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

    protected static String getType(String type) {
        String lower = type.toLowerCase();
        if (lower.contains("char") || lower.contains("text"))
            return "String";

        if (lower.contains("bigint") || lower.contains("long"))
            return "long";

        if (lower.contains("int") || lower.contains("integer"))
            return "int";

        if (lower.contains("float") || lower.contains("double"))
            return "double";

        if (lower.contains("decimal") || lower.contains("numeric")) {
            int[] decimal = decimal(lower.substring(lower.indexOf('(') + 1, lower.indexOf(')')));
            if (decimal[1] == 0)
                return decimal[0] > 11 ? "long" : "int";

            return "double";
        }

        if (lower.equals("date"))
            return "java.sql.Date";

        if (lower.equals("datetime"))
            return "java.sql.Timestamp";

        return lower;
    }

    protected static int[] decimal(String range) {
        int indexOf = range.indexOf(',');
        if (indexOf == -1)
            return new int[]{Integer.parseInt(range), 0};

        return new int[]{Integer.parseInt(range.substring(0, indexOf)), Integer.parseInt(range.substring(indexOf + 1))};
    }
}
