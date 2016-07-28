package com.mail.sender.service;

import com.google.appengine.api.datastore.Entity;
import com.mail.sender.freemarker.TemplateProcesor;
import freemarker.template.TemplateException;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
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
        Multipart multipart = new MimeMultipart();
        // Text message part
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(TemplateProcesor.process( club.getProperty("club"),
                                                         (String) content.getProperty("content")));
        multipart.addBodyPart(messageBodyPart);
        msg.setContent(multipart);

        return msg;
    }

    public static Message createReportBody(Collection<Entity> subtract, List<Entity> errors) {
        Session session = Session.getDefaultInstance(new Properties(), null);
        Message msg = new MimeMessage(session);
        try {
            msg.setFrom(new InternetAddress("smiechu18@gmail.com", "smiechu18@gmail.com"));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress("smiecho18@interia.pl", "smiecho18@interia.pl"));
            msg.setSubject("Sending report");
            Multipart multipart = new MimeMultipart();
            // Text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(buildText(subtract, errors));
            multipart.addBodyPart(messageBodyPart);
            msg.setContent(multipart);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.log(Level.SEVERE, e.getMessage(), e);
        }
        return msg;
    }

    private static String buildText(Collection<Entity> subtract, List<Entity> errors) {
        StringBuilder sb = new StringBuilder("Today following emails were sent:\n");
        for(Entity e : subtract){
            sb.append(e.getProperty("mail")).append('\n');
        }
        sb.append("Today's errors:\n");
        for(Entity e : errors){
            sb.append(e.getProperty("mail")).append('\n');
        }
        return sb.toString();
    }
}
