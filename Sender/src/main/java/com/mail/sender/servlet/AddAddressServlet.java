package com.mail.sender.servlet;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mail.sender.util.AppUtils;
import com.mail.sender.dao.MailerDao;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Tomasz Śmiechowicz
 */
public class AddAddressServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddAddressServlet.class.getName());

    private final MailerDao dao = new MailerDao();

    private final boolean testMode = Boolean.valueOf(AppUtils.getBundle("test.mode"));

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        if(!testMode){
            if(log.isLoggable(Level.INFO)){
                log.info("Current content entity will be removed");
            }
            //currently should be only one
            Entity entity = dao.getContentEntity();
            dao.delete(entity.getKey());
        }

        String language = req.getParameter("language");
        String content = req.getParameter("content");

        Key contentKey = KeyFactory.createKey("Content", content);

        Date date = new Date();
        Entity contentEntity = new Entity("Content", contentKey);
        contentEntity.setProperty("language", language);
        contentEntity.setProperty("creationDate", date);
        contentEntity.setProperty("content", content);

        if(log.isLoggable(Level.INFO)){
            log.info("Entry to save"+contentEntity);
        }

        if(testMode){
            if(log.isLoggable(Level.INFO)){
                log.info("Test mode! Skipping saving data");
            }
            resp.sendRedirect("/");
            return;
        }

        dao.saveEntries(Arrays.asList(contentEntity));
        if(log.isLoggable(Level.INFO)){
            log.info("doPost finished, redirect now");
        }
        resp.sendRedirect("/");
    }
}