package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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

	public JobsPanel(Types.UserLevel userLevel) {
		setLayout(new GridLayout(1,2));
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());

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
	
	public JPanel getThisPanel() {
		return this;
	}
	
	public Job getSelectedJob() {
		Job selectedJob =  jobs.get(jobList.getSelectedIndex());
		return selectedJob;
	}
	
	private class JPSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent event) {
			if (getSelectedJob() != null) {
				String text = getSelectedJob().getDescription();
				String fText = String.format("<html><div WIDTH=%d>%s</div></html>", 320, text);
				description.setText(fText);
				descriptionPanel.setVisible(true);
				applyButton.setEnabled(true);
			}
		}
	}
	
	private class JPListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			if (event.getSource() == createButton) {
				cl.show(cards, "JobAddPanel");
			}
			if (event.getSource() == editButton) {
				RecruiterPanel recPanel = (RecruiterPanel) cards.getParent().getParent();
				JobsPanel jobsPanel = recPanel.getJobsPanel();
				/*
				 * TODO: Make this do stuff... get correct job from job list with jobsPanel
				 * reference above, and load correct info into jobEditPanel fields.
				 */
				
				cl.show(cards, "JobEditPanel");
			}
			if (event.getSource() == applyButton) {
				ApplicantPanel appPanel = (ApplicantPanel) cards.getParent().getParent();
				JobsPanel jobsPanel = appPanel.getJobsPanel();
				/*
				 * TODO: Make this do stuff too... get correct job from job list, load
				 * correct info into applyPanel fields.
				 */
				cl.show(cards, "ApplyPanel");
			}
		}
	}
}
