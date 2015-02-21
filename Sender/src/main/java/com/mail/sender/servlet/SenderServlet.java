/**
 * 
 */
package com.mail.sender.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tsmiecho
 *
 */
public class SenderServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();

		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
 

		try {
		    Message msg = new MimeMessage(session);
		    msg.setFrom(new InternetAddress("smiechu18@gmail.com", "Example.com Admin"));
		    msg.addRecipient(Message.RecipientType.TO,
		     new InternetAddress("smiecho18@interia.pl", "Mr. User"));
		    msg.setSubject("Your Example.com account has been activated");
		    msg.setText("wdwdw\nwdwd");
		    Transport.send(msg);
		    
		    
		} catch (AddressException e) {
			 resp.setContentType("text/plain");
	            resp.getWriter().println("Something went wrong. Please try again.");
	            throw new RuntimeException(e);
		} catch (MessagingException e) {
			 resp.setContentType("text/plain");
	            resp.getWriter().println("Something went wrong. Please try again.");
	            throw new RuntimeException(e);
		}
		resp.sendRedirect("/");
	}
}
