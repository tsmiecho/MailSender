/**
 * 
 */
package com.mail.sender.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		dao.saveData(req.getParameter("content"));
		resp.sendRedirect("/");
	}
}
