package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class UsersPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTable usersTable;
	private JScrollPane scrollPane;
	private JButton addButton;
	private JButton editButton;
	private JPanel innerPanel;
	
	public UsersPanel() {
		initUI();
	}
	
	public void initUI() {
		setLayout(null);
		
		String[] columnNames = {"Username", "Password", "Userlevel"};
		String[][] data = buildUserData();
		
		usersTable = new JTable(data, columnNames);
		usersTable.setPreferredSize(new Dimension(480, 570));
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(usersTable, BorderLayout.CENTER);
		innerPanel.add(usersTable.getTableHeader(), BorderLayout.NORTH);
		
		scrollPane = new JScrollPane(innerPanel);
		
		scrollPane.setBounds(30, 55, 540, 550);
		scrollPane.setBackground(Color.white);
		add(scrollPane);
		
		addButton = new JButton("Add...");
		editButton = new JButton("Edit...");
		
		addButton.setBounds(600, 55, 120, 30);
		editButton.setBounds(600, 105, 120, 30);
		
		add(addButton);
		add(editButton);
	}
	
	public String[][] buildUserData() {	
		ArrayList<User> userList = User.getUserList();
		String[][] usersData = new String[userList.size()][3];
		for (int i = 0; i < userList.size(); i++ ) {
			for (int j = 0; j < 3; j++ ) {
				if (j == 0)
					usersData[i][j] = userList.get(i).getUserName();
				if (j == 1)
				 	usersData[i][j] = userList.get(i).getUserPassword();
				if (j == 2)
					usersData[i][j] = userList.get(i).getUserLevelString();
			}
		}
		return usersData;
	}
}
