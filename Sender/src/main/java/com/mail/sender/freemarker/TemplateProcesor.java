package com.mail.sender.freemarker;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for freemarker processing.
 *
 * @author Tomek
 */
public class TemplateProcesor {

    private static final Logger log = Logger.getLogger(TemplateProcesor.class.getName());


    /**
     * Method processes template using freemarker.
     *
     * @param valueBean - bean which contains all values for freemarker
     * @param stringTemplate - template for processing
     * @return processed content
     * @throws IOException
     * @throws TemplateException
     */
    public static <T> String process(T valueBean, String stringTemplate) throws IOException, TemplateException {
        Configuration config = new Configuration(Configuration.VERSION_2_3_21);
        config.setDefaultEncoding("utf-8");
        config.setLocale(Locale.ENGLISH);
        Template template = new Template("content", new StringReader(stringTemplate), config);
        StringWriter writer = new StringWriter();
        Map<String, T> objects = new HashMap<>();
        objects.put("bean", valueBean);
        if(log.isLoggable(Level.CONFIG)){
            log.config("Values before freemarker processing template = ["+stringTemplate+"],\n "+objects);
        }
        template.process(objects, new StringWriter());
        return writer.toString();
    }
}
