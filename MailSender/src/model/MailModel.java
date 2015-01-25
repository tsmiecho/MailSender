/**
 * 
 */
package model;

/**
 * Model reprezentujacy mejla
 * 
 * @author Tomek
 *
 */
public class MailModel {

	private String recipient;
	
	private String sender;
	
	private String password;
	
	private String subject;
	
	private String content;

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
