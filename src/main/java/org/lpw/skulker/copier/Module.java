package org.lpw.skulker.copier;

import org.lpw.skulker.util.FreeMarker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
     * @param author              开发者。
     * @param module              模块名。
     * @param pkg                 包名，不包含模块名。
     * @param tephra              Tephra包名，为null则使用默认（org.lpw.tephra）。
     * @param modelSupportPackage ModelSupport包名，为null则使用默认（org.lpw.tephra.dao.model）。
     * @param modelSupportName    ModelSupport类名，为null则使用默认（ModelSupport）。
     * @param idLength            ID长度。
     * @param columns             字段集；二维数组，每行元素依次为：字段名、类型、设置（k-索引、n-不为NULL）、说明。
     * @throws IOException 未处理IO读写异常。
     */
    public static void parse(String author, String module, String pkg, String tephra, String modelSupportPackage, String modelSupportName, int idLength, String[][] columns) throws IOException {
        String out = OUT + module.toLowerCase() + "/";
        Copier.init(out);
        Map<String, Object> map = new HashMap<>();

        StringBuilder name = new StringBuilder();
        for (char ch : module.toCharArray()) {
            if (ch >= 'A' && ch <= 'Z') {
                ch += 'a' - 'A';
                if (name.length() > 0)
                    name.append('_');
            }
            name.append(ch);
        }
        map.put("author", author);
        map.put("module", module);
        map.put("moduleName", module.substring(0, 1).toLowerCase() + module.substring(1));
        map.put("module_name", name.toString());
        map.put("pkg", pkg);
        map.put("packages", pkg.split("\\."));
        map.put("tephra", tephra == null ? tephra = "org.lpw.tephra" : tephra);
        map.put("modelSupportPackage", modelSupportPackage == null ? tephra + ".dao.model" : modelSupportPackage);
        map.put("modelSupportName", modelSupportName == null ? "ModelSupport" : modelSupportName);
        map.put("idLength", idLength);
        model(map, columns, idLength);

        for (String type : TYPES)
            FreeMarker.process(IN, type + ".java", out + module + type + ".java", map);
        FreeMarker.process(IN, "ddl.sql", out + "ddl.sql", map);
    }

    protected static void model(Map<String, Object> map, String[][] columns, int idLength) {
        if (columns == null || columns.length == 0)
            return;

        Set<String> types = new HashSet<>();
        List<Column> list = new ArrayList<>();
        for (int i = 0; i < columns.length; i++) {
            Column column = new Column();
            column.setName(columns[i][0]);
            column.setField(getName(column.getName()));
            column.setMethod(column.getField().substring(0, 1).toUpperCase() + column.getField().substring(1));
            column.setType(getColumnType(columns[i][1], idLength));
            column.setJavaType(getJavaType(column.getType()));
            int indexOf = column.getJavaType().lastIndexOf('.');
            if (indexOf > -1) {
                types.add(column.getJavaType());
                column.setJavaType(column.getJavaType().substring(indexOf + 1));
            }
            if (columns[i][1].equalsIgnoreCase("auto")) {
                column.setUnique(true);
                column.setNotNull(true);
                column.setAuto(true);
            } else if (columns[i][2].equals("uk")) {
                column.setUnique(true);
                column.setNotNull(true);
            } else if (columns[i][2].equals("k")) {
                column.setKey(true);
                column.setNotNull(true);
            } else if (columns[i][2].equals("n"))
                column.setNotNull(true);
            if (column.getJavaType().equals("int") || column.getJavaType().equals("long") || column.getJavaType().equals("double"))
                column.setNumber(true);
            column.setComment(columns[i][3]);
            column.setIgnoreJava(columns[i][2].equals("ignore"));
            list.add(column);
        }
        map.put("types", types);
        map.put("columns", list);
    }

    private static String getName(String column) {
        String name = column;
        if (name.startsWith("c_"))
            name = name.substring(2);
        for (int indexOf; (indexOf = name.indexOf('_')) > -1; ) {
            String ch = name.substring(indexOf, indexOf + 2);
            name = name.replaceAll(ch, ch.substring(1).toUpperCase());
        }

        return name;
    }


    private static String getColumnType(String type, int idLength) {
        if (type.equalsIgnoreCase("id") || type.equalsIgnoreCase("fk"))
            return ("CHAR(" + idLength + ")");

        if (type.equalsIgnoreCase("auto"))
            return "BIGINT AUTO_INCREMENT";

        return type;
    }

    private static String getJavaType(String columnType) {
        String lower = columnType.toLowerCase();
        if (lower.contains("char") || lower.contains("text"))
            return "String";

        if (lower.contains("bigint") || lower.contains("long"))
            return "long";

        if (lower.contains("int"))
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

    private static int[] decimal(String range) {
        int indexOf = range.indexOf(',');
        if (indexOf == -1)
            return new int[]{Integer.parseInt(range), 0};

        return new int[]{Integer.parseInt(range.substring(0, indexOf)), Integer.parseInt(range.substring(indexOf + 1))};
    }
}
