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

	private static final Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {
		
		
		if(logger.isDebugEnabled()){
			logger.debug("This is debug : " + args);
		}
		
		if(logger.isInfoEnabled()){
			logger.info("This is info : " + args);
		}
		
		logger.warn("This is warn : " + args);
		logger.error("This is error : " + args);
		logger.fatal("This is fatal : " + args);
//		ApplicationContext a = new ClassPathXmlApplicationContext("/spring/config/Beans.xml");
		
		System.out.println("dupa");
	}
}
