package sender;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 *  Klasa zawiera jednynie metode main
 *  
 * @author Tomek
 *
 */
public class Main {

	private Main(){	}
	
	private static final Logger LOGGER = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"Beans.xml"});
		 
		context.getBean("mainFrame");
		
	}

}
