/**
 * 
 */
package com.mail.sender.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.mail.sender.dao.MailerDao;

/**
 * @author tsmiecho
 *
 */
public class AddClubsServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		MailerDao dao = new MailerDao();
		
		dao.parseAndSaveData(req.getParameter("content"),req.getParameter("language"));
		resp.sendRedirect("/");
	}
}
