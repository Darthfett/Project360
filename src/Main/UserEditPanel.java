package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UserEditPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private final String[] ulevelLabels = {"recruiter", "reviewer", "reference"};
	private char mode;
	private JTextField unameField;
	private JTextField passwdField;
	private JComboBox ulevelBox;
	private JLabel unameLabel;
	private JLabel passwdLabel;
	private JLabel ulevelLabel;
	private JButton submitButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel padding;
	private User user;
	
	// mode: a for add mode, e for edit mode
	public UserEditPanel(char mode) {
		this.mode = mode;
		setLayout(new BorderLayout());
		northPanel = new JPanel();
		southPanel = new JPanel();
		padding = new JPanel();
		unameField = new JTextField();
		passwdField = new JTextField();
		ulevelBox = new JComboBox(ulevelLabels);
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
		northPanel.add(ulevelBox);
		northPanel.setPreferredSize(new Dimension(470, 60));
		padding.add(northPanel);
		add(padding, BorderLayout.NORTH);
		
		southPanel.add(submitButton);
		southPanel.add(cancelButton);
		add(southPanel, BorderLayout.SOUTH);
		
		if (mode == 'e') {
			deleteButton = new JButton("Delete User");
			southPanel.add(deleteButton);
			deleteButton.addActionListener(new UEListener());
			unameField.setEditable(false);
		}
		
		submitButton.addActionListener(new UEListener());
		cancelButton.addActionListener(new UEListener());
 	}
	
	public JPanel getThisPanel() {
		return this;
	}
	
	public void clearFields() {
		unameField.setText("");
		passwdField.setText("");
	}
	
	public char getMode() {
		return this.mode;
	}
	
	public JTextField getUnameField() {
		return unameField;
	}
	
	public JTextField getPasswdField() {
		return passwdField;
	}
	
	public JComboBox getUlevelBox() {
		return ulevelBox;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	private class UEListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			RecruiterPanel recPanel = (RecruiterPanel) cards.getParent().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			if (event.getSource() == cancelButton) {
				cl.show(cards, "UsersPanel");
				clearFields();
			}
			if (getMode() == 'a') {
				if (event.getSource() == submitButton) {
					String uname = unameField.getText();
					String passwd = passwdField.getText();
					String ulevel = (String) ulevelBox.getSelectedItem();
					User userToAdd = new User(uname, passwd, ulevel);
					userToAdd.save();
					recPanel.refreshUsers();
					cl.show(cards, "UsersPanel");
					clearFields();
				}
			}
			if (getMode() == 'e') {
				if (event.getSource() == submitButton) {
					user.setUserName(unameField.getText());
					user.setUserPassword(passwdField.getText());
					user.setUserLevel((String) ulevelBox.getSelectedItem());
					user.save();
					recPanel.refreshUsers();
					cl.show(cards, "UsersPanel");
				}
				if (event.getSource() == deleteButton) {
					user.remove();
					recPanel.refreshUsers();
					cl.show(cards, "UsersPanel");
				}
			}
		}
	}
}
