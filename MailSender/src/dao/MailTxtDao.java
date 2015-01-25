/**
 * 
 */
package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.MailModel;

/**
 * @author Tomek
 * 
 * nauczyciel, dziecko w szkole, wolontariusz w domu dziecka, poprostu fan o materialy promocyjne
 * 
 */
public class MailTxtDao implements JMailDao {

//	private StringBuilder start;
//	
//	private StringBuilder end;
//	
//	{
//		start = new StringBuilder();
//		end = new StringBuilder();
////		start.append(" Dzie� dobry, \n\n Jestem nauczycielem w szkole podstawowej w Babach.");
////		start.append("\n Razem z uczniami zak�adamy sportowy k�cik pami�tkowy. \n Zwracam si� z");
////		start.append(" uprzejm� pro�b� o przekazanie nam jakich� pami�tek, \n starych zdj��, bilet�w.\n Chcia�bym zauwa�y�,");
////		start.append(" �e pami�tka z tak znanego klubu jak \n ");
////		
////		end.append(" by�aby dla nas szczeg�lnie cenna. \n\n Pozdrawiam,\n");
////		end.append(" Tomasz �miechowicz \n Go�cimowice Drugie 51 \n 97-310 Moszczenica ");
//		 
////		start.append(" Dear Sir or Madam, \n\n I am a teacher in a primary school.");
////		start.append("\n Together with the students create a commemorative sports area. \n");
////		start.append(" I kindly asking you to give us some souvenirs, old photos, tickets. \n");
////		start.append(" I would like to note that the souvenir from ");
////		
////		end.append(" \n would be very important to us. \n\n Regards,\n");
////		end.append(" Tomasz �miechowicz \n POLAND \n Go�cimowice Drugie 51 \n 97-310 Moszczenica ");
//		
//		start.append("Hi ");
//		end.append(",\nI always wanted to collect sports memorabilia but my parents can not afford to buy. Please send me some small souvenirs. The school colleagues would be very jealous of me :)\n");
//
//		end.append("Regards,\n");
//		end.append("Magdalena Rytych\n");
//				end.append("Boguslawice 115c m 10\n");
//						end.append("97-320 Wolb�rz\n");
//								end.append("Poland\n");
//	}
//	
	@Override
	public List<MailModel> getMessages() {
		try {
			return read(new File("C:\\Users\\Tomek\\Desktop\\test.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<MailModel>();
	}

	private List<MailModel> read(File file) throws IOException {
		
		List<MailModel> mails = new ArrayList<>();
		
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
//		String clubName = "";
		while(scanner.hasNext()){
			mailModel.setRecipient(scanner.next());
			mailModel.setSubject("Welcome");
//			clubName = scanner.next();
		}
		scanner.close();
//		StringBuilder content = new StringBuilder();
//		content.append(start);
//		content.append(clubName);
//		content.append(end);
		
//		mailModel.setContent(content.toString());
		return mailModel;
	}
}