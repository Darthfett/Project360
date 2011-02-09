import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class ApplicantsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable appsTable;
	private JScrollPane scrollPane;
	private JButton viewButton;
	private JPanel innerPanel;
	
	public ApplicantsPanel(Types.UserLevel userLevel) {
		initUI(userLevel);
	}
	
	public void initUI(Types.UserLevel userLevel) {
		setLayout(null);
		String[] columnNames = {"Jobs", "Applicants"};
		String[][] sampleData = {{"Software Engineer", "John Doe"}, {"Software Engineer", "Jane Doe"},
				{"Senior Software Engineer", "John Doe",}, {"Senior Software Engineer", "Jane Doe"}};
		
		
		appsTable = new JTable(sampleData, columnNames);
		appsTable.setPreferredSize(new Dimension(480, 570));
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(appsTable, BorderLayout.CENTER);
		innerPanel.add(appsTable.getTableHeader(), BorderLayout.NORTH);
		
		scrollPane = new JScrollPane(innerPanel);
		
		scrollPane.setBounds(30, 55, 540, 550);
		scrollPane.setBackground(Color.white);
		add(scrollPane);

		viewButton = new JButton("View...");
		viewButton.setBounds(600, 55, 120, 30);
		add(viewButton);
		
		
		if (userLevel == Types.UserLevel.RECRUITER){
			/*
			 * The recruiter will see all applicants.
			 */
		}
		
		if (userLevel == Types.UserLevel.REVIEWER){
			/*
			 * For the reviewer, we will show only a subset of the applicants.
			 */
		}
	}
}
