package org.lpw.skulker.util;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

import java.io.*;

/**
 * @auth lpw
 */
public class FreeMarker {
    public static void process(String root, String name, String output, Object data) throws IOException {
        try {
            OutputStream outputStream = new FileOutputStream(output);
            getConfiguration(root).getTemplate(name).process(data, new OutputStreamWriter(outputStream));
            outputStream.close();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    protected static Configuration getConfiguration(String root) throws IOException {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
        configuration.setDirectoryForTemplateLoading(new File(root));
        configuration.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_25));
        configuration.setTemplateExceptionHandler((TemplateException te, Environment env, Writer out) -> te.printStackTrace());

        return configuration;
    }
}
