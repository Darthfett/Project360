package Main;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class JobEditPanel extends JPanel{
	
	private JLabel titleLabel;
	private JLabel descLabel;
	private JLabel deadlineLabel;
	private JLabel locationLabel;
	private JLabel salaryLabel;
	private JLabel benefitsLabel;
	private JTextField titleField;
	private JTextField deadlineField;
	private JTextField locationField;
	private JTextField salaryField;
	private JTextArea benefits;
	private JTextArea description;
	private JScrollPane bScroll;
	private JScrollPane dScroll;
	
	public JobEditPanel(char mode) {
		
		setLayout(new BorderLayout());
		
		titleLabel = new JLabel("Job Title");
		descLabel = new JLabel("Job Description");
		deadlineLabel = new JLabel("Application Deadline");
		locationLabel = new JLabel("Location");
		salaryLabel = new JLabel("Salary");
		benefitsLabel = new JLabel("Benefits");
		titleField = new JTextField();
		deadlineField = new JTextField();
		locationField = new JTextField();
		salaryField = new JTextField();
		benefits = new JTextArea();
		description = new JTextArea();
		bScroll = new JScrollPane(benefits);
		dScroll = new JScrollPane(description);
		
		
	}
}
