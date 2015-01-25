package sender;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import sender.dao.JMailDao;
import sender.dao.MailTxtDao;
import sender.freemarker.TemplateProcesor;
import sender.gui.MainFrame;
import sender.model.MailModel;



/**
 * Kontroler wysy³aj¹cy mejle
 * 
 * @author Tomek
 *
 */
@Component
public class MailSender {

	private static final Logger LOGGER = Logger.getLogger(MailSender.class);
	
	@Autowired
	private TemplateProcesor procesor; 
	
	public static void main(String[] args) {
		new MainFrame();
	}
	
	public void send(final MailModel model) {
		JMailDao mailDao = new MailTxtDao();
		List<MailModel> messages = mailDao.getMessages();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "poczta.interia.pl");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication( model.getSender(), model.getPassword());
					}
				});

		for (MailModel mail : messages) {
			Message message = new MimeMessage(session);
		
			try {
				message.setFrom(new InternetAddress( model.getSender()));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(mail.getRecipient()));
				message.setSubject(mail.getSubject());
				message.setText(procesor.process(model.getContent(), mail.getClub()));

				Transport.send(message);

				LOGGER.info("Done " + mail.getRecipient());
			} catch (MessagingException e) {
				LOGGER.error("MessagingException " + mail.getRecipient() + e);	
			} catch (IOException e) {
				LOGGER.error("IOException " + mail.getRecipient() + e);
			} catch (TemplateException e) {
				LOGGER.error("TemplateException " + mail.getRecipient() + e);
			}
		}

		LOGGER.debug("Finish");
	}

	public TemplateProcesor getProcesor() {
		return procesor;
	}

	public void setProcesor(TemplateProcesor procesor) {
		this.procesor = procesor;
	}
}