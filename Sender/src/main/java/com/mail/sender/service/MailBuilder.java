package com.mail.sender.service;

import com.google.appengine.api.datastore.Entity;
import com.mail.sender.freemarker.TemplateProcesor;
import freemarker.template.TemplateException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Class responsible for creating emails.
 * @author Tomasz Smiechowicz
 */
public final class MailBuilder {

    private static final Logger log = Logger.getLogger(MailBuilder.class.getName());

    public static Message createMessage(Entity club, Entity content) throws IOException, MessagingException,
                                                                            TemplateException {
        Session session = Session.getDefaultInstance(new Properties(), null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("smiechu18@gmail.com", "smiechu18@gmail.com"));
        final String mail = (String) club.getProperty("mail");
        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(mail, mail));
        msg.setSubject("Warm greetings");
        msg.setText(TemplateProcesor.process((String) club.getProperty("club"),
                                             (String) content.getProperty("content")));

        return msg;
    }

    public static Message createReportBody(Collection<Entity> subtract, List<Entity> errors) {
//TODO
        return null;
    }
}
