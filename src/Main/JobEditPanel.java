package Main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JobEditPanel extends JPanel{
	
	private JButton submitButton;
	private JButton cancelButton;
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
	
	//mode: 'a' for add, 'e' for edit
	public JobEditPanel(char mode) {
		
		setLayout(new BorderLayout());
		
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		titleLabel = new JLabel("Job Title");
		descLabel = new JLabel("Job Description");
		deadlineLabel = new JLabel("Application Deadline");
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
		centerTop.add(benefitsLabel, BorderLayout.NORTH);
		centerTop.add(benefits, BorderLayout.CENTER);
		centerBottom.setLayout(new BorderLayout());
		centerBottom.add(descLabel, BorderLayout.NORTH);
		centerBottom.add(description, BorderLayout.CENTER);
		
		southPanel.add(submitButton);
		southPanel.add(cancelButton);
		
		add(padding, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.EAST);
	}
}
