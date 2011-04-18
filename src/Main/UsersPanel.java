package Main;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class UsersPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTable usersTable;
	private JScrollPane scrollPane;
	private JButton addButton;
	private JButton editButton;
	private JPanel innerPanel;
	private String[][] data;
	
	public UsersPanel() {
		initUI();
	}
	
	public void initUI() {
		setLayout(null);
		
		String[] columnNames = {"Username", "Password", "Userlevel"};
		data = buildUserData();
		
		usersTable = new JTable(data, columnNames);
		usersTable.setPreferredSize(new Dimension(480, 570));
		usersTable.getSelectionModel().addListSelectionListener(new UPSelectionListener());
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(usersTable, BorderLayout.CENTER);
		innerPanel.add(usersTable.getTableHeader(), BorderLayout.NORTH);
		
		scrollPane = new JScrollPane(innerPanel);
		
		scrollPane.setBounds(30, 55, 540, 490);
		scrollPane.setBackground(Color.white);
		add(scrollPane);
		
		addButton = new JButton("Add...");
		editButton = new JButton("Edit...");
		
		addButton.setBounds(600, 55, 120, 30);
		editButton.setBounds(600, 105, 120, 30);
		editButton.setEnabled(false);
		
		addButton.addActionListener(new UPListener());
		editButton.addActionListener(new UPListener());
		
		add(addButton);
		add(editButton);
	}
	
	public String[][] buildUserData() {	
		ArrayList<User> userList = User.getUserList();
		String[][] usersData = new String[userList.size()][3];
		for (int i = 0; i < userList.size(); i++ ) {
			usersData[i][0] = userList.get(i).getUsername();
			usersData[i][1] = userList.get(i).getPassword();
			usersData[i][2] = userList.get(i).getUserLevelString();
		}
		return usersData;
	}
	
	public UsersPanel getThisPanel() {
		return this;
	}
	
	public String getSelectedUser() {
		int rowIndex = usersTable.getSelectedRow();
		String user = null;
		if (rowIndex >=0 )
			user = data[rowIndex][0];
		return user;
	}
	
	private class UPListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			if (event.getSource() == editButton) {
				cl.show(cards, "UserEditPanel");
				RecruiterPanel recPanel = (RecruiterPanel) cards.getParent().getParent();
				String userName = recPanel.getUsersPanel().getSelectedUser();
				User user = User.getUserFromUserName(userName);
				recPanel.getUserEditPanel().getUnameField().setText(user.getUsername());
				recPanel.getUserEditPanel().getPasswdField().setText(user.getPassword());
				recPanel.getUserEditPanel().getUlevelBox().setSelectedItem((String) user.getUserLevelString());
				recPanel.getUserEditPanel().setUser(user);
			}
			if (event.getSource() == addButton) {
				cl.show(cards, "UserAddPanel");
			}
		}
	}
	
	private class UPSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (getSelectedUser() != null) {
				editButton.setEnabled(true);
			}
		}
	}
}
