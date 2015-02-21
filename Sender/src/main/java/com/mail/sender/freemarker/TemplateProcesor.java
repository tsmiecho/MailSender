package com.mail.sender.freemarker;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Komponent odpowiedzialny za edycje tekstu.
 * Zamienia parametr {club} na zadany w argumencie club. 
 * W dowolnej ilosci wystapien.
 * @author Tomek
 *
 */
public class TemplateProcesor {
	
	Configuration configuration = new Configuration();
	
	public String process(String stringTemplate, String club ) {
		
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("club", club);
		
		Template template;
		try {
			template = new Template("content",	new StringReader(stringTemplate), configuration);
			StringWriter writer = new StringWriter();
			template.process(objects, writer);
			return writer.toString();
	
		} catch (IOException | TemplateException e) {
			//TODO log
			return stringTemplate;
		}
		
	}

}
