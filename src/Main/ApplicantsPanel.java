package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

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
	private ArrayList<Applicant> applicants;

	public ApplicantsPanel(Types.UserLevel userLevel) {
		initUI(userLevel);
	}
	
	public Applicant getSelectedApplicant() {
		Integer selectedRow = appsTable.getSelectedRow();
		Applicant selectedApplicant = null;
		if (selectedRow >= 0) {
			selectedApplicant = applicants.get(appsTable.getSelectedRow());
		}
		return selectedApplicant;
	}

	public void initUI(Types.UserLevel userLevel) {
		setLayout(null);
		String[] columnNames = {"Jobs", "Applicants"};
		applicants = Applicant.getApplicantList();
		String[][] data = new String[applicants.size()][2];
		for (int i = 0; i < applicants.size(); i++) {
			data[i][0] = applicants.get(i).getAppliedJob().getTitle();
			data[i][1] = applicants.get(i).getUsername();
		}
		
		appsTable = new JTable(data, columnNames);
		appsTable.setPreferredSize(new Dimension(480, 570));
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new BorderLayout());
		innerPanel.add(appsTable, BorderLayout.CENTER);
		innerPanel.add(appsTable.getTableHeader(), BorderLayout.NORTH);
		
		scrollPane = new JScrollPane(innerPanel);
		
		scrollPane.setBounds(30, 55, 540, 490);
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
		
		if (userLevel == Types.UserLevel.REFERENCE){
			/*
			 * For the reviewer, we will show only a subset of the applicants.
			 */
		}
	}
}
