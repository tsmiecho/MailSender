package com.mail.sender.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class responsible for transporting emails.
 * @author Tomasz Smiechowicz
 */
public class MailSender {

    private static final Logger log = Logger.getLogger(MailBuilder.class.getName());

    /**
     * Method responsible for transporting emails.
     * @param msg - javax mail object
     */
    public static void sendMail(Message msg) throws MessagingException {
        Transport.send(msg);
        if(log.isLoggable(Level.INFO)){
            log.info("Email to "+ Arrays.toString(msg.getAllRecipients())+"was sent");
        }
    }
}
