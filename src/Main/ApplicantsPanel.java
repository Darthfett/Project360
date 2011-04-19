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

/*
 * The applicants panel class contains a list which displays a list of applicants
 * that exist in the system. It displays different sets of applicants depending on
 * which user is logged in. Recruiters can see all of the applicants, and reviewers
 * and references onyl see the applicants they need to see.
 */

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

	/*
	 * The constructor for ApplicantsPanel takes a User object, the currently
	 * logged in user, and a UserLevel (See Types.java).
	 */
	public ApplicantsPanel(User currentUser, Types.UserLevel currentUserLevel) {
		userLevel = currentUserLevel;
		this.currentUser = currentUser;
		if (userLevel == Types.UserLevel.APPLICANT) {
			currentUser = null;
		}
		
		initUI(currentUser, userLevel);
	}
	
	/*
	 * Return a reference to the applicant object which is currently selected
	 * in the appsTable JTable.
	 */
	public Applicant getSelectedApplicant() {
		Integer selectedRow = appsTable.getSelectedRow();
		Applicant selectedApplicant = null;
		if (selectedRow >= 0) {
			selectedApplicant = applicants.get(appsTable.getSelectedRow());
		}
		return selectedApplicant;
	}

	/*
	 * Create and lay out the user interface of the ApplicantsPanel.
	 */
	private void initUI(User currentUser, Types.UserLevel unused_currentUserLevel) {
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

			viewButton.addActionListener(new APPListener());

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
			
			data = new String[assigned.size()][2];
			for (int i = 0; i < assigned.size(); i++) {
				data[i][0] = assigned.get(i).getAppliedJob().getTitle();
				data[i][1] = assigned.get(i).getUsername();
			}
			
			rateButton = new JButton("Rate...");
			rateButton.setBounds(600, 55, 120, 30);
			rateButton.addActionListener(new APPListener());
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
	
	/*
	 * Return a reference to this ApplicantsPanel for use by the 
	 * button listener.
	 */
	public ApplicantsPanel getThisPanel() {
		return this;
	}
	
	/*
	 * This is the listener which is applied to the view/rate button.
	 * It accomplishes the following:
	 * 	o Get a reference to the parent RecruiterPanel/ReviewerPanel/ReferencePanel,
	 * 		which is used CardLayout to accomplish the view switching to an AppViewPanel.
	 * 	o Determine which type of AppViewPanel to show
	 *  o Show the appropriate panel, either to view the applicant, or to rate the applicant
	 *  	in the case of a Reference user.
	 */
	private class APPListener implements ActionListener {

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
				avp.getRef1Field().setText(refs.get(0).getUsername());
				avp.getRef2Field().setText(refs.get(1).getUsername());
				avp.getRef3Field().setText(refs.get(2).getUsername());
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
						((Reference) currentUser).removeApplicant(tempApplicant);
						tempApplicant.save();
						((Reference) currentUser).save();
					}
				}

			}
		}

	}
}
