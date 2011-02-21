package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Main.Types.UserLevel;

public class LoginPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton loginButton;
	private JPanel bigPanel;
	private JPanel buttonPanel;
	private JLabel unameLabel;
	private JLabel passwdLabel;
	private JTextField unameField;
	private JPasswordField passwdField;
	
	public LoginPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(760, 68));
		setBackground(Color.black);
		
		unameField = new JTextField();
		unameField.setBackground(Color.lightGray);
		unameLabel = new JLabel("Username");
		unameLabel.setForeground(Color.lightGray);
		
		passwdField = new JPasswordField();
		passwdField.setBackground(Color.lightGray);
		passwdLabel = new JLabel("Password");
		passwdLabel.setForeground(Color.lightGray);
		
		loginButton = new JButton("Login");
		loginButton.setBackground(Color.black);
		loginButton.setForeground(Color.lightGray);
		loginButton.setPreferredSize(new Dimension(75, 17));
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);
		buttonPanel.add(loginButton);
		
		bigPanel = new JPanel();
		bigPanel.setLayout(new GridLayout(3, 2));
		bigPanel.setBackground(Color.black);
		bigPanel.setPreferredSize(new Dimension(230, 72));
		bigPanel.add(unameLabel);
		bigPanel.add(unameField);
		bigPanel.add(passwdLabel);
		bigPanel.add(passwdField);
		bigPanel.add(new JLabel(""));
		bigPanel.add(buttonPanel);
		add(bigPanel, BorderLayout.EAST);
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				char[] passArray = passwdField.getPassword();
				String passwd = new String(passArray);
				String uname = unameField.getText();
				ArrayList<User> userList = User.getUserList();
				for (int i = 0; i < userList.size(); i++) {
					User check = userList.get(i);
					if (check.getUserName().equals(uname)) {
						if (check.getUserPassword().equals(passwd)) {
							JPanel replacePanel;
							Types.UserLevel userLevel = check.getUserLevel();
							TheAppletItself.setCurrentUserLevel(userLevel);
							switch(userLevel) {
								case RECRUITER:
									replacePanel = new RecruiterPanel();
									break;
								case REVIEWER:
									replacePanel = new ReviewerPanel();
									break;
								case REFERENCE:
									replacePanel = new ReferencePanel();
									break;
								default:
									replacePanel = new ApplicantPanel();
									break;
							}
							((JApplet) getTopLevelAncestor()).setContentPane(replacePanel);
							
						}
					}
				}
				
			}
		});
	}
}
