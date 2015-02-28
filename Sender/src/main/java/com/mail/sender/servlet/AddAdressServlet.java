
package com.mail.sender.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.mail.sender.dao.MailerDao;

/**
 * @author tsmiecho
 *
 */
public class AddAdressServlet extends HttpServlet {
	
	  @Override
	  public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		MailerDao manager = new MailerDao();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity entity = manager.getContentEntity();
		datastore.delete(entity.getKey());
		
		String language = req.getParameter("language");
		String content = req.getParameter("content");
	    
	    Key contentKey = KeyFactory.createKey("Content", content);

	    Date date = new Date();
	    Entity contentEntity = new Entity("Content", contentKey);
	    contentEntity.setProperty("language", language);
	    contentEntity.setProperty("creationDate", date);
	    contentEntity.setProperty("content", content);

	    datastore.put(contentEntity);
	    resp.sendRedirect("/");
	  }
	}