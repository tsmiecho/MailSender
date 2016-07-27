package com.mail.sender.servlet;

import com.mail.sender.dao.MailerDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tomasz Śmiechowicz
 *
 */
public class AddClubsServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		MailerDao dao = new MailerDao();
//		dao.deleteAllClubs();
		dao.parseAndSaveData(req.getParameter("content"),req.getParameter("language"));
		resp.sendRedirect("/");
	}
}
