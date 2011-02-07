import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginView extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String errorMsg = "Invalid username/password";
	private JButton loginButton;
	private JButton cancelButton;
	private JTextField uname;
	private JPasswordField passwd;
	private JLabel unameLabel, passwdLabel;
	private JLabel errorLabel;
	private JPanel panel;
	
	
	public LoginView() {
		initUI();
	}
	
	private void initUI() {

		setSize(350, 210);
		setTitle("Login");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		panel = new JPanel();
		loginButton = new JButton("Login");
		cancelButton = new JButton("Cancel");
		uname = new JTextField();
		passwd = new JPasswordField();
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
		
		uname.setBounds(125, 48, 170, 20);
		panel.add(uname);
		
		passwdLabel.setBounds(50, 80, 68, 20);
		panel.add(passwdLabel);
		
		passwd.setBounds(125, 80, 170, 20);
		panel.add(passwd);
		
		cancelButton.setBounds(190, 132, 80, 25);
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		panel.add(cancelButton);
		
		loginButton.setBounds(90, 132, 80, 25);
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				
				/*
				 * Do some magic here with the user data to figure out whether
				 * the login info is valid, and if so, what type of user has
				 * logged in. Then set the applet's static currentUserLevel
				 * to the appropriate value.
				 */
				
				/*
				 * Temporary code to test the various UIs. This also shows how
				 * to change the user-level, signal a login to the applet, and
				 * kill the login frame.
				 */
				
				String user = uname.getText();
				CountDownLatch loginSignal = TheAppletItself.getLoginSignal();
				
				if (user.equals("recruiter")) {
					TheAppletItself.setCurrentUserLevel(Types.UserLevel.RECRUITER);
					loginSignal.countDown();
					cleanUp();
				}
				else if (user.equals("reviewer")) {
					TheAppletItself.setCurrentUserLevel(Types.UserLevel.REVIEWER);
					loginSignal.countDown();
					cleanUp();
				}
				else if (user.equals("reference")) {
					TheAppletItself.setCurrentUserLevel(Types.UserLevel.REFERENCE);
					loginSignal.countDown();
					cleanUp();
				}
				else if (user.equals("applicant")) {
					TheAppletItself.setCurrentUserLevel(Types.UserLevel.REFERENCE);
					loginSignal.countDown();
					cleanUp();
				}
				else {
					errorLabel.setText(errorMsg);
				}
				
				//End temporary code

			}
		});
		panel.add(loginButton);
	}
	
	/*
	 * This method gets rid of the login frame. It needs to be called when a
	 * successful login occurs.
	 */
	public void cleanUp() {
		this.setVisible(false);
		this.dispose();
	}
}
