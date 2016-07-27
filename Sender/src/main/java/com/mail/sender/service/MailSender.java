package com.mail.sender.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;

/**
 * Class responsible for transporting emails.
 * @author Tomasz Smiechowicz
 */
public class MailSender {

    /**
     * Method responsible for transporting emails.
     * @param msg - javax mail object
     */
    public static void sendMail(Message msg) throws MessagingException {
        Transport.send(msg);
    }
}
