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
		
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							JPanel replacePanel;
							LoginView loginView = new LoginView(null, true);
							loginView.setVisible(true);
							replacePanel = loginView.getReplacePanel();
							((JApplet) getTopLevelAncestor()).setContentPane(replacePanel);
							
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
