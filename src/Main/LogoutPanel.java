package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * This is the top panel of the UI when a user is logged in, it appears as the black
 * bar at the top. When the logout button is pressed, the applet's content pane is 
 * set as a new applicant panel.
 */
public class LogoutPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JButton logoutButton;
	private JPanel buttonPanel;
	private JLabel userLabel;
	private User currentUser;
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public LogoutPanel(User currentUser) {
		this.currentUser = currentUser;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(760, 30));
		setBackground(Color.black);
		
		userLabel = new JLabel();
		userLabel.setFont(new Font("arial", Font.PLAIN, 16));
		logoutButton = new JButton("Logout");
		logoutButton.setBackground(Color.black);
		logoutButton.setForeground(Color.lightGray);
		logoutButton.setFocusable(false);
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);
		buttonPanel.add(userLabel);
		buttonPanel.add(logoutButton);
		
		add(buttonPanel, BorderLayout.EAST);
		
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				TheAppletItself.setCurrentUserLevel(Types.UserLevel.APPLICANT);
				JApplet applet = (JApplet) getThisPanel().getTopLevelAncestor();
				JPanel replacePanel = new ApplicantPanel(getCurrentUser());
				applet.setContentPane(replacePanel);
				applet.invalidate();
				applet.validate();
			}
		});
		
	}
	
	public LogoutPanel getThisPanel() {
		return this;
	}
}
