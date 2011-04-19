package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
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
	private Types.UserLevel userLevel;
	private User currentUser;

	public ApplicantsPanel(User currentUser, Types.UserLevel currentUserLevel) {
		userLevel = currentUserLevel;
		if (userLevel == Types.UserLevel.APPLICANT) {
			currentUser = null;
		}
		
		initUI(currentUser, userLevel);
	}
	
	public Applicant getSelectedApplicant() {
		Integer selectedRow = appsTable.getSelectedRow();
		Applicant selectedApplicant = null;
		if (selectedRow >= 0) {
			selectedApplicant = applicants.get(appsTable.getSelectedRow());
		}
		return selectedApplicant;
	}

	public void initUI(User currentUser, Types.UserLevel unused_currentUserLevel) {
		setLayout(null);
		String[] columnNames = {"Jobs", "Applicants"};
		String[][] data = null;
		
		if (userLevel == Types.UserLevel.RECRUITER){
			/*
			 * The recruiter will see all applicants.
			 */
			applicants = Applicant.getApplicantList();
			data = new String[applicants.size()][2];
			for (int i = 0; i < applicants.size(); i++) {
				data[i][0] = applicants.get(i).getAppliedJob().getTitle();
				data[i][1] = applicants.get(i).getUsername();
			}

			viewButton = new JButton("View...");
			viewButton.setBounds(600, 55, 120, 30);
<<<<<<< HEAD
			viewButton.addActionListener(new APPListener());
=======
			viewButton.addActionListener(new ButtonListener());
>>>>>>> bd8cc1deed16ab8d9bc7db4c4d0ec36e2d8cfeb9
		}
		if (userLevel == Types.UserLevel.REVIEWER){
			/*
			 * For the reviewer, we will show only a subset of the applicants.
			 */
			ArrayList<Job> jobs = ((Reviewer) currentUser).getJobs();
			applicants = new ArrayList<Applicant>();
			for (int i = 0; i < jobs.size(); i++) {
				applicants.addAll(jobs.get(i).getApplicants());
			}
			data = new String[applicants.size()][2];
			for (int i = 0; i < applicants.size(); i++) {
				data[i][0] = applicants.get(i).getAppliedJob().getTitle();
				data[i][1] = applicants.get(i).getUsername();
			}
			
			viewButton = new JButton("View...");
			viewButton.addActionListener(new APPListener());
			viewButton.setBounds(600, 55, 120, 30);
		}

		if (userLevel == Types.UserLevel.REFERENCE){
			/*
			 * For the reference, we will show only a subset of the applicants.
			 */
			applicants = Applicant.getApplicantList();
			ArrayList<Applicant> assigned = ((Reference) currentUser).getApplicants();
			
			data = new String[applicants.size()][2];
			for (int i = 0; i < applicants.size(); i++) {
				for (int j = 0; j < assigned.size(); j++) {
					if (assigned.get(j).equals(applicants.get(i))) {
						data[i][0] = applicants.get(i).getAppliedJob().getTitle();
						data[i][1] = applicants.get(i).getUsername();
						break;
					}
				}
			}
			
			rateButton = new JButton("Rate...");
			rateButton.setBounds(600, 55, 120, 30);
<<<<<<< HEAD
			rateButton.addActionListener(new APPListener());
=======
			rateButton.addActionListener(new ButtonListener());
>>>>>>> bd8cc1deed16ab8d9bc7db4c4d0ec36e2d8cfeb9
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
		if (userLevel == Types.UserLevel.REFERENCE) {
			add(rateButton);
		} else {
			add(viewButton);
		}
	}
	
	public ApplicantsPanel getThisPanel() {
		return this;
	}
	
	private class APPListener implements ActionListener{

		public void actionPerformed(ActionEvent event) {
			JButton source = (JButton)event.getSource();

			if(source == viewButton){
				JPanel cards = (JPanel) getThisPanel().getParent();
				CardLayout cl = (CardLayout) cards.getLayout();
				Applicant app = getThisPanel().getSelectedApplicant();
				JPanel parent = null;
				AppViewPanel avp = null;
				if (userLevel == Types.UserLevel.RECRUITER) {
					parent = (RecruiterPanel) cards.getParent().getParent();
					avp = ((RecruiterPanel) parent).getAppViewPanel();
				}
				if (userLevel == Types.UserLevel.REVIEWER) {
					parent = (ReviewerPanel) cards.getParent().getParent();
					avp = ((ReviewerPanel) parent).getAppViewPanel();
				}
				
				//Not sure if this stuff is correct or not...
				ArrayList<User> refs = app.getReferences();
				avp.getNameField().setText(app.getUsername());
				//avp.getRef1Field().setText(refs.get(0).getUsername());
				//avp.getRef2Field().setText(refs.get(1).getUsername());
				//avp.getRef3Field().setText(refs.get(2).getUsername());
				avp.getResumeArea().setText(app.getResume());
				
				cl.show(cards, "ApplicantViewPanel");
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
