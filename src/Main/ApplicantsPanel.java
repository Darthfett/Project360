package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ApplicantsPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTable appsTable;
	private JScrollPane scrollPane;
	private JButton viewButton;
	private JButton rateButton;
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
		
		if (userLevel == Types.UserLevel.RECRUITER){
			/*
			 * The recruiter will see all applicants.
			 */

			viewButton = new JButton("View...");
			viewButton.setBounds(600, 55, 120, 30);
			viewButton.addActionListener(new ButtonListener());
			add(viewButton);
		}

		if (userLevel == Types.UserLevel.REVIEWER){
			/*
			 * For the reviewer, we will show only a subset of the applicants.
			 */
			viewButton = new JButton("View...");
			viewButton.addActionListener(new ButtonListener());
			viewButton.setBounds(600, 55, 120, 30);
			add(viewButton);
		}

		if (userLevel == Types.UserLevel.REFERENCE){
			/*
			 * For the reviewer, we will show only a subset of the applicants.
			 */
			rateButton = new JButton("Rate...");
			rateButton.setBounds(600, 55, 120, 30);
			rateButton.addActionListener(new ButtonListener());
			add(rateButton);
		}
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			JButton source = (JButton)event.getSource();

			if(source == viewButton){
				//handle view here
			}
			if(source == rateButton){
				Applicant tempApplicant = getSelectedApplicant();

				if(tempApplicant != null){
					Object[] options = {"1", "2", "3", "4", "5", "Cancel"};
					int n = JOptionPane.showOptionDialog(getRootPane(),
							"Designate a rating (5 is good):",
							"Rate Applicant",
							JOptionPane.YES_NO_CANCEL_OPTION,
							JOptionPane.QUESTION_MESSAGE,
							null,
							options,
							options[5]);
					if(n < 6 & n > 0){
						tempApplicant.addReferenceRating(n);
					}
				}

			}
		}

	}
}
