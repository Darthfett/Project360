package Main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JDialog{

	private static final long serialVersionUID = 1L;
	private static final String errorMsg = "Invalid username/password";
	private JButton loginButton;
	private JButton cancelButton;
	private JLabel unameLabel;
	private JLabel passwdLabel;
	private JLabel errorLabel;
	private JPanel panel;
	private JPanel replacePanel;
	private JTextField unameField;
	private JPasswordField passwdField;
	
	public LoginView(JDialog owner, boolean modal) {
		super(owner, modal);
		initUI();
	}
	
	private void initUI() {

		setSize(350, 210);
		setTitle("Login");
		setLocationRelativeTo(null);
		setResizable(false);
		
		panel = new JPanel();
		loginButton = new JButton("Login");
		cancelButton = new JButton("Cancel");
		unameField = new JTextField();
		passwdField = new JPasswordField();
		unameLabel = new JLabel("Username");
		passwdLabel = new JLabel("Password");
		errorLabel = new JLabel();
		
		getContentPane().add(panel);
		panel.setLayout(null);
		
		errorLabel.setBounds(90, 12, 180, 20);
		errorLabel.setForeground(Color.lightGray);
		panel.add(errorLabel);
		
		unameLabel.setBounds(50, 48, 68, 20);
		panel.add(unameLabel);
		
		unameField.setBounds(125, 48, 170, 20);
		panel.add(unameField);
		
		passwdLabel.setBounds(50, 80, 68, 20);
		panel.add(passwdLabel);
		
		passwdField.setBounds(125, 80, 170, 20);
		panel.add(passwdField);
		
		panel.add(cancelButton);
		cancelButton.setBounds(190, 132, 80, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				cleanUp();
			}
		});
		
		panel.add(loginButton);
		getRootPane().setDefaultButton(loginButton);
		loginButton.setBounds(90, 132, 80, 25);
		loginButton.addActionListener(new LoginListener());
	}
	
	/*
	 * This method gets rid of the login dialog. It needs to be called when a
	 * successful login occurs.
	 */
	public void cleanUp() {
		this.setVisible(false);
		this.dispose();
	}
	
	public JPanel getReplacePanel() {
		return replacePanel;
	}
	
	private class LoginListener implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			
			char[] passArray = passwdField.getPassword();
			String passwd = new String(passArray);
			String uname = unameField.getText();
			User currentUser = User.getUserFromUserName(uname);
			if (currentUser == null) {
				Reference currentReference = Reference.getReferenceFromEmail(uname);
				if (currentReference == null) {
					errorLabel.setText(errorMsg);
					return;
				} else {
					if (! currentReference.getPassword().equals(passwd)) {
						errorLabel.setText(errorMsg);
						return;
					} else {
						replacePanel = new ReferencePanel();
						cleanUp();
						return;
					}
				}
			}
			if (! currentUser.getPassword().equals(passwd)) {
				errorLabel.setText(errorMsg);
				return;
			}
			Types.UserLevel userLevel = currentUser.getUserLevel();
			TheAppletItself.setCurrentUserLevel(userLevel);
			switch(userLevel) {
				case RECRUITER:
					replacePanel = new RecruiterPanel();
					cleanUp();
					break;
				case REVIEWER:
					replacePanel = new ReviewerPanel();
					cleanUp();
					break;
				case REFERENCE:
					replacePanel = new ReferencePanel();
					cleanUp();
					break;
				default:
					replacePanel = null;
					cleanUp();
					break;
			}			
		}
	}
}


