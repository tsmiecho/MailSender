package sender;

import gui.MainFrame;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.MailModel;
import dao.JMailDao;
import dao.MailTxtDao;


/**
 * Kontroler wysy³aj¹cy mejle
 * 
 * @author Tomek
 *
 */
public class MailSender {

	public static void main(String... vargs) {

		MainFrame frame = new MainFrame();		
	}

	public void send(final MailModel model) {
		JMailDao mailDao = new MailTxtDao();
		List<MailModel> messages = mailDao.getMessages();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "poczta.interia.pl");
		// props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication( model.getSender(), model.getPassword());
//								"m.rytych2003@poczta.fm", "a7188236");
					}
				});

		for (MailModel mail : messages) {
			Message message = new MimeMessage(session);
			// message.setFrom(new
			// InternetAddress("tomasz.smiechowicz15@gmail.com"));
			try {
//				message.setFrom(new InternetAddress("m.rytych2003@poczta.fm"));
				message.setFrom(new InternetAddress( model.getSender()));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(mail.getRecipient()));
				message.setSubject(mail.getSubject());
//				message.setText(model.getContent());
				message.setText(model.getContent());

				Transport.send(message);

			} catch (MessagingException e) {
				System.out.println("Error" + mail.getRecipient() + e);
			}
			System.out.println("Done " + mail.getRecipient());
		}

		System.out.println("Finish");
	}
}