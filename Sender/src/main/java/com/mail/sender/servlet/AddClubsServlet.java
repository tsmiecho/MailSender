package com.mail.sender.servlet;

import com.google.appengine.api.datastore.Entity;
import com.mail.sender.util.AppUtils;
import com.mail.sender.dao.MailerDao;
import com.mail.sender.model.Language;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet responsible for adding club to the data base.
 *
 * @author Tomasz Śmiechowicz
 */
public class AddClubsServlet extends HttpServlet {

    private static final Logger log = Logger.getLogger(AddClubsServlet.class.getName());

    private final MailerDao dao = new MailerDao();

    private final boolean testMode = Boolean.valueOf(AppUtils.getBundle("test.mode"));

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(log.isLoggable(Level.INFO)){
            log.info("Data from request content["+req.getParameter("content")+
                             "], language["+req.getParameter("language")+"]");
        }
        final Language language = Language.valueOf(req.getParameter("language"));
        final List<Entity> entities = dao.parseData(req.getParameter("content"), language);
        final List<String> mailAdresses = dao.getAllMailAdresses();
        if(log.isLoggable(Level.INFO)){
            log.info("Clubs before filtering ["+entities+"]");
        }
        CollectionUtils.filter(entities, new Predicate<Entity>() {
            @Override
            public boolean evaluate(Entity entity) {
                if(!mailAdresses.contains(entity.getProperty("mail"))){
                    return true;
                }
                return false;
            }
        });
        if(log.isLoggable(Level.INFO)){
            log.info("Clubs after filtering ["+entities+"]");
        }

        if(testMode){
            if(log.isLoggable(Level.INFO)){
                log.info("Test mode! Skipping saving data");
            }
            resp.sendRedirect("/");
            return;
        }

        dao.saveEntries(entities);
        if(log.isLoggable(Level.INFO)){
            log.info("doPost finished, redirect now");
        }
        resp.sendRedirect("/");
    }
}
