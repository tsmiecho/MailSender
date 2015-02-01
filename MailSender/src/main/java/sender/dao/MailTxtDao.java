package sender.dao;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import sender.model.MailModel;


/**
 * @author Tomek
 * 
 * Implementacja oparta na plikach tekstowych
 * 
 */

@Component
public class MailTxtDao implements JMailDao {
	
	private static final Logger LOGGER = Logger.getLogger(MailTxtDao.class);
	
	@Override
	public List<MailModel> getMessages() {
		try {
			return read(new File("C:\\Users\\Tomek\\Desktop\\test.txt"));
		} catch (IOException e) {
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<MailModel>();
	}

	private List<MailModel> read(File file) throws IOException {
		
		List<MailModel> mails = new ArrayList<MailModel>();
		
		FileReader reader = new FileReader(file);
		String currentLine;

		BufferedReader br = new BufferedReader(reader);

		while ((currentLine = br.readLine()) != null) {
			mails.add(createMailModel(currentLine));
		}
		br.close();
		return mails;
	}

	private MailModel createMailModel(String currentLine) {

		MailModel mailModel = new MailModel();
		Scanner scanner = new Scanner(currentLine);
		scanner.useDelimiter(";");
		while(scanner.hasNext()){
			mailModel.setRecipient(scanner.next());
			mailModel.setSubject("Welcome");
			mailModel.setClub(scanner.next());
		}
		scanner.close();
		return mailModel;
	}
}