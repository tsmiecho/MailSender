package com.mail.sender.model;

import java.util.Date;

public class MailHistory {

	private String name;
	
	private String mail;
	
	private Date dateOfSent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Date getDateOfSent() {
		return dateOfSent;
	}

	public void setDateOfSent(Date dateOfSent) {
		this.dateOfSent = dateOfSent;
	}
	
	
}
