package sender.freemarker;


import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * @author Tomek
 *
 */
@Component
public class TemplateProcesor {
	
	Configuration configuration = new Configuration();
	
	public String process(String stringTemplate, String club ) throws IOException, TemplateException{
		
		Map<String, Object> objects = new HashMap<String, Object>();
		objects.put("club", club);
		
		Template template = new Template("content",  new StringReader(stringTemplate), configuration);
		
		StringWriter writer = new StringWriter();
		template.process(objects, writer);
		
		return writer.toString();
		
	}

}
