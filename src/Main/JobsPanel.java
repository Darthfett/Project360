package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/*
 * This is the user interface component which contains the list of jobs in the system. 
 * It contains the functionality to add/edit jobs, delete jobs, and apply for jobs, 
 * depending on the type of user logged in, which is determined by the parameter passed to
 * the constructor.
 */
public class JobsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JList jobList;
	private JScrollPane scrollPane;
	private JLabel description;
	private JPanel descriptionPanel;
	private JPanel descriptionPadding;
	private JPanel innerPanel;
	private JPanel rightPanel;
	private JPanel buttonsPanel;	
	private JButton createButton;
	private JButton editButton;
	private JButton applyButton;
	private ArrayList<Job> jobs;
	private Types.UserLevel userType;

	/*
	 * The argument taken by the constructor of type Types.UserLevel
	 * determines which type of user is logged in to the system. 
	 * (See Types.java)
	 */
	public JobsPanel(Types.UserLevel userLevel) {
		setLayout(new GridLayout(1,2));
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		userType = userLevel;
		jobs = Job.getJobList();
		ArrayList<String> jobTitles = new ArrayList<String>();
		for (int i = 0; i < jobs.size(); i++) {
			jobTitles.add(jobs.get(i).getTitle());
		}
		
		jobList = new JList(jobTitles.toArray());
		innerPanel = new JPanel();
		innerPanel.setBackground(Color.white);
		jobList.setPreferredSize(new Dimension(340, 600));
		jobList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jobList.setLayoutOrientation(JList.VERTICAL_WRAP);
		jobList.addListSelectionListener(new JPSelectionListener());
		innerPanel.add(jobList);
		
		scrollPane = new JScrollPane(innerPanel);
		
		descriptionPanel = new JPanel();
		descriptionPadding = new JPanel();
		descriptionPanel.setBorder(BorderFactory.createTitledBorder("Job Information"));
		descriptionPanel.setVisible(false);
		descriptionPadding.add(descriptionPanel);
		rightPanel.add(descriptionPadding, BorderLayout.CENTER);
		
		description = new JLabel();
		description.setFont(new Font("Arial", Font.PLAIN, 12));
		String text = " ";
		String fText = String.format("<html><div WIDTH=%d>%s</div></html>", 320, text);
		description.setText(fText);
		
		descriptionPanel.add(description);
		
		buttonsPanel = new JPanel();
		rightPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		add(scrollPane);
		add(rightPanel);
		
		if (userLevel == Types.UserLevel.RECRUITER) {
			createButton = new JButton("Create new...");
			createButton.addActionListener(new JPListener());
			editButton = new JButton("Edit...");
			editButton.addActionListener(new JPListener());
			buttonsPanel.add(createButton);
			buttonsPanel.add(editButton);
	
		}
		
		if (userLevel == Types.UserLevel.APPLICANT) {
			applyButton = new JButton("Apply...");
			applyButton.addActionListener(new JPListener());
			applyButton.setEnabled(false);
			buttonsPanel.add(applyButton);
		}
	}
	
	/*
	 * Return a reference to this JobsPanel for use in this class' listener.
	 */
	public JPanel getThisPanel() {
		return this;
	}
	
	/*
	 * Return a reference to the job which is currently selected in the jobsList JList.
	 */
	public Job getSelectedJob() {
		Job selectedJob = null;
		if (jobList.getSelectedIndex() >= 0) {
			selectedJob =  jobs.get(jobList.getSelectedIndex());
		}
		return selectedJob;
	}
	
	/*
	 * This listener determines whether or not a job is selected. This is done so the apply
	 * button cannot be pressed unless a job is selected. It also causes the job's information
	 * of the selected job to be displayed in the right panel. Once a job is selected, the apply
	 * button is enabled.
	 */
	private class JPSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (getSelectedJob() != null) {
				String text = getSelectedJob().toString();
				String fText = String.format("<html><div WIDTH=%d>%s</div></html>", 320, text);
				description.setText(fText);
				descriptionPanel.setVisible(true);
				if (userType != Types.UserLevel.RECRUITER) {
					applyButton.setEnabled(true);
				}
			}
		}
	}
	
	/*
	 * This listener is used for the edit/create buttons for recruiters, and for the apply
	 * button for regular users. It first gets a reference to it parent RecruiterPanel/ApplicantPanel
	 * in order to use CardLayout to accomplish the view switching. As recruiter, pressing the create
	 * button shows a JobEditPanel('a'), and pressing edit shows a JobEditPanel('e') with information
	 * filled in based on the currently selected job. Pressing the apply button when not logged simply
	 * shows an ApplyPanel.
	 */
	private class JPListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			if (event.getSource() == createButton) {
				cl.show(cards, "JobAddPanel");
			}
			if (event.getSource() == editButton) {
				if (getSelectedJob() == null) {
					return;
				}
				RecruiterPanel recPanel = (RecruiterPanel) cards.getParent().getParent();
				JobEditPanel jePanel = recPanel.getJobEditPanel();
				Job job = getSelectedJob();
				jePanel.setJob(job);
				
				/* Labels */
				jePanel.getIdLabel().setText(job.getId().toString());
				
				/* TextFields */
				jePanel.getTitleField().setText(job.getTitle());
				if (job.getDeadline() != null) {
					jePanel.getDeadlineField().setText(new SimpleDateFormat("MM.dd.yyyy").format(job.getDeadline()));
				}
				if (job.getLocation() != null) {
					jePanel.getLocationField().setText(job.getLocation());
				}
				if (job.getSalary() != null) {
					jePanel.getSalaryField().setText(job.getSalary());
				}
				
				/* TextAreas */
				jePanel.getJobBenefitArea().setText(job.getBenefits());
				jePanel.getJobDescriptionArea().setText(job.getDescription());
				cl.show(cards, "JobEditPanel");
			}
			if (event.getSource() == applyButton) {
				cl.show(cards, "ApplyPanel");
			}
		}
	}
}
