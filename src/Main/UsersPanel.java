package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

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
		String[][] sampleData = {{"User0", "Passwd0", "Recruiter"}, {"User1", "Passwd1", "Applicant"}, 
		{"User2", "Passwd2", "Reviewer"}, {"User3", "Passwd3", "Applicant"},
		{"User4", "Passwd4", "Applicant"}, {"User5", "Passwd5", "Reviewer"}};
		
		usersTable = new JTable(sampleData, columnNames);
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
}
