package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.MailModel;
import sender.MailSender;

/**
 * Glowny komponent gui
 * 
 * @author Tomek
 *
 */
public class MainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton button = new JButton("Wyœlij");
	private JTextArea textArea = new JTextArea(8, 40);
	private JLabel labelMail = new JLabel("Mail:");
	private JLabel labelPass = new JLabel("Has³o:");
	private JTextField mail = new JTextField(20);
	private JTextField password = new JTextField(20);
	private JPanel panel = new JPanel();
	
	public MainFrame() {
		super("");
		
		setSize(1100, 550);
		setLocation(50,50);
		
		add(textArea,BorderLayout.CENTER);
		button.addActionListener(new SendButtonListener());
		
		panel.add(labelMail);
		panel.add(mail);
		panel.add(labelPass);
		panel.add(password);
		add(panel, BorderLayout.NORTH);
		add(button, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	private class SendButtonListener  implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			MailSender sender = new MailSender();
			MailModel model = new MailModel();
			model.setSender(mail.getText());
			model.setPassword(password.getText());
			model.setContent(textArea.getText());
			sender.send(model);
			button.setBackground(Color.darkGray);
			button.setEnabled(false);
		}

	}
}
