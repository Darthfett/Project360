package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserEditPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTextField unameField;
	private JTextField passwdField;
	private JTextField ulevelField;
	private JLabel unameLabel;
	private JLabel passwdLabel;
	private JLabel ulevelLabel;
	private JButton submitButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel padding;
	
	public UserEditPanel() {
		setLayout(new BorderLayout());
		northPanel = new JPanel();
		southPanel = new JPanel();
		padding = new JPanel();
		unameField = new JTextField();
		passwdField = new JTextField();
		ulevelField = new JTextField();
		unameLabel = new JLabel("Username", JLabel.CENTER);
		passwdLabel = new JLabel("Password", JLabel.CENTER);
		ulevelLabel = new JLabel("Userlevel", JLabel.CENTER);
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		
		northPanel.setLayout(new GridLayout(3,2));
		northPanel.add(unameLabel);
		northPanel.add(unameField);
		northPanel.add(passwdLabel);
		northPanel.add(passwdField);
		northPanel.add(ulevelLabel);
		northPanel.add(ulevelField);
		northPanel.setPreferredSize(new Dimension(470, 60));
		padding.add(northPanel);
		add(padding, BorderLayout.NORTH);
		
		southPanel.add(submitButton);
		southPanel.add(cancelButton);
		add(southPanel, BorderLayout.SOUTH);
		
		submitButton.addActionListener(new UEListener());
		cancelButton.addActionListener(new UEListener());
 	}
	
	public JPanel getThisPanel() {
		return this;
	}
	
	private class UEListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			if (event.getSource() == cancelButton) {
				cl.show(cards, "UsersPanel");
			}
			if (event.getSource() == submitButton) {
				String uname = unameField.getText();
				String passwd = passwdField.getText();
				String ulevel = ulevelField.getText();
				System.out.println(new User(uname, passwd, ulevel));
				User.loadUserList();
				((RecruiterPanel) cards.getParent().getParent()).refreshUsers();
				cl.show(cards, "UsersPanel");
			}
		}
	}
}
