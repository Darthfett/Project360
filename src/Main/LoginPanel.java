package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/*
 * This is the black bar that appears at the top of the interface when no user is
 * logged in. It contains a single button to login, which shows the login
 * dialog box, LoginView.
 */
public class LoginPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton loginButton;
	private JPanel buttonPanel;
	
	public LoginPanel() {
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(760, 30));
		setBackground(Color.black);
		
		loginButton = new JButton("Login");
		loginButton.setBackground(Color.black);
		loginButton.setForeground(Color.lightGray);
		loginButton.setFocusable(false);
		
		buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.black);
		buttonPanel.add(loginButton);
		
		add(buttonPanel, BorderLayout.EAST);
		
		/*
		 * This listener allows the login button to show the LoginView.
		 * The Dialog is modal, which allows us time to extract 
		 * the type of user logging in with the getReplacePanel() call.
		 * Then we set the content pane of the applet to the appropriate panel
		 * type. The two function calls at the end, invalidate()/validate() are
		 * used to fix the UI after the content pane has been switched.
		 */
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							JPanel replacePanel;
							LoginView loginView = new LoginView(null, true);
							loginView.setVisible(true);
							replacePanel = loginView.getReplacePanel();
							JApplet applet = (JApplet) getTopLevelAncestor();
							if (replacePanel != null) {
								applet.setContentPane(replacePanel);
								applet.invalidate();
								applet.validate();
							}
						}
					});
				} catch(Exception e) {
					System.err.println("There was a problem creating the login dialog...");
					e.printStackTrace();
				}
			}
		});
	}
}
