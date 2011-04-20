package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * The JobEditPanel contains the recruiters user interface for creating and editing 
 * jobs in the system.
 */
public class JobEditPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton submitButton;
	private JButton cancelButton;
	private JButton deleteButton;
	private JLabel titleLabel;
	private JLabel descLabel;
	private JLabel deadlineLabel;
	private JLabel locationLabel;
	private JLabel salaryLabel;
	private JLabel benefitsLabel;
	private JLabel jobIdLabel;
	private JLabel jobId;
	private JTextField titleField;
	private JTextField deadlineField;
	private JTextField locationField;
	private JTextField salaryField;
	private JTextArea benefits;
	private JTextArea description;
	private JScrollPane bScroll;
	private JScrollPane dScroll;
	private JPanel padding;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel centerPanel;
	private JPanel centerTop;
	private JPanel centerBottom;
	private Job job;
	private char mode;
	
	/*
	 * The constructor takes a character as a parameter, which determines if this
	 * JobEditPanel is for creating or editing jobs: 'a' for add, 'e' for edit.
	 */
	public JobEditPanel(char mode) {
		
		this.mode = mode;
		setLayout(new BorderLayout());
		
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		deleteButton = new JButton("Delete");
		titleLabel = new JLabel("Job Title");
		descLabel = new JLabel("Job Description");
		deadlineLabel = new JLabel("Application Deadline (mm/dd/yyyy)");
		locationLabel = new JLabel("Location");
		salaryLabel = new JLabel("Salary");
		benefitsLabel = new JLabel("Benefits");
		jobIdLabel = new JLabel("Job ID");
		jobId = new JLabel();
		titleField = new JTextField();
		deadlineField = new JTextField();
		locationField = new JTextField();
		salaryField = new JTextField();
		benefits = new JTextArea();
		description = new JTextArea();
		bScroll = new JScrollPane(benefits);
		dScroll = new JScrollPane(description);
		padding = new JPanel();
		centerTop = new JPanel();
		centerBottom = new JPanel();
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		northPanel.setLayout(new GridLayout(5, 2));
		northPanel.add(jobIdLabel);
		northPanel.add(jobId);
		northPanel.add(titleLabel);
		northPanel.add(titleField);
		northPanel.add(deadlineLabel);
		northPanel.add(deadlineField);
		northPanel.add(locationLabel);
		northPanel.add(locationField);
		northPanel.add(salaryLabel);
		northPanel.add(salaryField);
		northPanel.setPreferredSize(new Dimension(470, 80));
		padding.add(northPanel);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(centerTop, BorderLayout.NORTH);
		centerPanel.add(centerBottom, BorderLayout.CENTER);
		centerTop.setLayout(new BorderLayout());		
		centerTop.setPreferredSize(new Dimension(470,50));
		centerTop.add(benefitsLabel, BorderLayout.NORTH);
		centerTop.add(bScroll, BorderLayout.CENTER);
		centerBottom.setLayout(new BorderLayout());
		centerBottom.add(descLabel, BorderLayout.NORTH);
		centerBottom.add(dScroll, BorderLayout.CENTER);
		
		submitButton.addActionListener(new JEListener());
		cancelButton.addActionListener(new JEListener());
		deleteButton.addActionListener(new JEListener());
		southPanel.add(submitButton);
		southPanel.add(cancelButton);
		if (mode == 'e') {
			southPanel.add(deleteButton);
		}
		
		add(padding, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.EAST);
	}
	
	public JLabel getIdLabel() {
		return jobIdLabel;
	}
		
	public JTextField getTitleField() {
		return titleField;
	}
	
	public JTextField getDeadlineField() {
		return deadlineField;
	}
	
	public JTextField getLocationField() {
		return locationField;
	}
	
	public JTextField getSalaryField() {
		return salaryField;
	}
	
	public JTextArea getJobBenefitArea() {
		return benefits;
	}
	
	public JTextArea getJobDescriptionArea() {
		return description;
	}
	
	public void setJob(Job job) {
		this.job = job;
	}
	
	public void clearFields() {
		titleField.setText("");
		deadlineField.setText("");
		locationField.setText("");
		salaryField.setText("");
		benefits.setText("");
		description.setText("");
	}
	
	/*
	 * Return a reference to this JobEditPanel for use in the listener class below.
	 */
	public JPanel getThisPanel() {
		return this;
	}
	
	/*
	 * This listener is applied to this panel's buttons and accomplishes the following:
	 * o Determine which button is being pressed, and redirect control accordingly.
	 * o If the cancel button is being pressed, show the JobsPanel.
	 * o If the submit button is being pressed, either create a new job or edit an existing
	 * 		job using the information from the text fields.
	 * 	o Save the job object in the database.
	 * 	o Clear the text fields and show the User edit
	 * o If the delete button is pressed, delete the job which is being edited from the database,
	 *  remove it from the jobs list, and show the JobsPanel.
	 */
	private class JEListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			RecruiterPanel recPanel = (RecruiterPanel) cards.getParent().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			if (event.getSource() == deleteButton) {
				ArrayList<Applicant> applicants = job.getApplicants();
				for (int i = 0; i < applicants.size(); i++) {
					ArrayList<User> refs = applicants.get(i).getReferences();
					for (int j = 0; j < refs.size(); j++) {
						((Reference)refs.get(i)).removeApplicant(applicants.get(i));
					}
					applicants.get(i).remove();
				}
				ArrayList<User> revs = Reviewer.getUserList();
				for (int i = 0; i < revs.size(); i++) {
					if (revs.get(i).getUserLevel() == Types.UserLevel.REVIEWER) {
						((Reviewer) revs.get(i)).removeJob(job);
					}
				}
				job.remove();
				recPanel.refreshJobs();
				cl.show(cards, "JobsPanel");
				clearFields();
			}
			if (event.getSource() == cancelButton) {
				cl.show(cards, "JobsPanel");
				clearFields();
			}
			if (event.getSource() ==  submitButton) {
				if (titleField.getText().equals("") || description.getText().equals("")) {
					return;
				}
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				if (mode == 'a') {
					job = new Job();
					job.setPostDate(new Date());
				}
				try {
					
					job.setTitle(titleField.getText());
					job.setDescription(description.getText());
					try {
						Date deadline = sdf.parse(deadlineField.getText());
						if (deadline != null) {
							job.setDeadline(deadline);
						}
					} catch (java.text.ParseException e) {}
					job.setPostDate(new Date());
					if (! locationField.getText().equals("")) {
						job.setLocation(locationField.getText());
					}
					if (! salaryField.getText().equals("")) {
						job.setSalary(salaryField.getText());
					}
					if (! benefits.getText().equals("")) {
						job.setBenefits(benefits.getText());
					}
					job.save();
					recPanel.refreshJobs();
				}
				catch (Exception e) {	
					System.out.println("Something bad happened while creating the job, check date format.");
					e.printStackTrace();
				}
				cl.show(cards, "JobsPanel");
				clearFields();
			}
		}
	}
}
