/**
 * 
 */
package com.mail.sender.servlet;

import com.google.appengine.api.datastore.Entity;
import com.mail.sender.dao.MailerDao;
import com.mail.sender.service.MailBuilder;
import com.mail.sender.service.MailSender;
import freemarker.template.TemplateException;
import org.apache.commons.collections4.CollectionUtils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Servlet responsible for sending mails.
 * @author Tomasz Śmiechowicz
 * 
 */
public class SenderServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(MailBuilder.class.getName());

	private final MailerDao manager = new MailerDao();

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		List<Entity> clubs = findClubsForNotification();
		Entity content = findMailTemplate();
		List<Entity> errors = new ArrayList<>();
		for(Entity club : clubs){
			try {
				Message msg = MailBuilder.createMessage(club, content);
				MailSender.sendMail(msg);
			} catch (MessagingException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				errors.add(club);
			} catch (TemplateException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
				errors.add(club);
			}
		}
		updateClubEntries(clubs);
		sendReportAboutSending(clubs, errors);
		resp.sendRedirect("/");
	}

	private List<Entity> findClubsForNotification() {
		return manager.getOldestClubEntries();
	}

	private Entity findMailTemplate() {
		return manager.getContentEntity();
	}

	private void updateClubEntries(List<Entity> clubs) {
		manager.upDate(clubs);
	}

	private void sendReportAboutSending(List<Entity> clubs, List<Entity> errors) {
		Message msg = MailBuilder.createReportBody(CollectionUtils.subtract(clubs, errors), errors);
		try {
			MailSender.sendMail(msg);
		} catch (MessagingException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
