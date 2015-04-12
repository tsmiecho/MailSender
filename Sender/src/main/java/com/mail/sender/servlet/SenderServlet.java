/**
 * 
 */
package com.mail.sender.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.Entity;
import com.mail.sender.dao.MailerDao;
import com.mail.sender.freemarker.TemplateProcesor;

/**
 * @author tsmiecho
 * 
 */
public class SenderServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		TemplateProcesor procesor = new TemplateProcesor();
		MailerDao manager = new MailerDao();
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Entity content = manager.getContentEntity();
		List<Entity> clubs = manager.getOldestClubEntries();
		List<Entity> clubsToUpdate = new ArrayList<Entity>();
		try {
			for(Entity entity : clubs){
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress("smiechu18@gmail.com",
						"smiechu18@gmail.com"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(
						(String)entity.getProperty("mail"), (String)entity.getProperty("mail")));
				msg.setSubject("Warm greetings");
				
				msg.setText(procesor.process((String) content.getProperty("content"), (String)entity.getProperty("club")));
				Transport.send(msg);
				clubsToUpdate.add(entity);
			}
			

		} catch (AddressException e) {
			resp.setContentType("text/plain");
			resp.getWriter().println("Something went wrong. Please try again.");
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			resp.setContentType("text/plain");
			resp.getWriter().println("Something went wrong. Please try again.");
			throw new RuntimeException(e);
		}
		manager.upDate(clubsToUpdate);
		resp.sendRedirect("/");
	}
}
