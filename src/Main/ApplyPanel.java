package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ApplyPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private int confirm;
	private JPanel northPanel;
	private JPanel southPanel;
	private JPanel centerPanel;
	private JPanel padding;
	private JLabel nameLabel;
	private JLabel ref1Label;
	private JLabel ref2Label;
	private JLabel ref3Label;
	private JLabel resumeLabel;
	private JScrollPane resumeScroll;
	protected JButton cancelButton;
	protected JTextField nameField;
	protected JTextField ref1Field;
	protected JTextField ref2Field;
	protected JTextField ref3Field;
	protected JButton submitButton;
	protected JTextArea resume;
	
	public ApplyPanel() {
		
		setLayout(new BorderLayout());
		
		northPanel = new JPanel();
		southPanel = new JPanel();
		centerPanel = new JPanel();
		padding = new JPanel();
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		nameField = new JTextField();
		ref1Field = new JTextField();
		ref2Field = new JTextField();
		ref3Field = new JTextField();
		nameLabel = new JLabel("Name");
		ref1Label = new JLabel("Reference 1");
		ref2Label = new JLabel("Reference 2");
		ref3Label = new JLabel("Reference 3");
		resumeLabel = new JLabel("Resume");
		resume = new JTextArea();
		resumeScroll = new JScrollPane(resume);
			
		northPanel.setLayout(new GridLayout(4, 2));
		northPanel.setPreferredSize(new Dimension(470, 80));
		northPanel.add(nameLabel);
		northPanel.add(nameField);
		northPanel.add(ref1Label);
		northPanel.add(ref1Field);
		northPanel.add(ref2Label);
		northPanel.add(ref2Field);
		northPanel.add(ref3Label);
		northPanel.add(ref3Field);
		padding.add(northPanel);
		
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(resumeLabel, BorderLayout.NORTH);
		centerPanel.add(resumeScroll, BorderLayout.CENTER);
		
		submitButton.addActionListener(new ApplyListener());
		cancelButton.addActionListener(new ApplyListener());
		southPanel.add(submitButton);
		southPanel.add(cancelButton);
		
		add(padding, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.EAST);
	}
	
	public JPanel getThisPanel() { 
		return this;
	}
	
	public void clearFields() {
		nameField.setText("");
		ref1Field.setText("");
		ref2Field.setText("");
		ref3Field.setText("");
		resume.setText("");
	}
	
	private class ApplyListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			JobsPanel jobsPanel = (JobsPanel) ((ApplicantPanel)cards.getParent().getParent()).getJobsPanel();
			CardLayout cl = (CardLayout) cards.getLayout();
			
			if (event.getSource() == submitButton) {
				confirm = JOptionPane.showConfirmDialog(null, "Submit application?",
									"Apply", JOptionPane.YES_NO_OPTION);
				if (confirm == JOptionPane.YES_OPTION) {
					if (nameField.getText().equals("") || ref1Field.getText().equals("") || ref2Field.getText().equals("") || ref3Field.getText().equals("") || resume.getText().equals("")) {
						return;
					}
					Applicant applicant = new Applicant();
					Reference ref1 = new Reference();
					Reference ref2 = new Reference();
					Reference ref3 = new Reference();
					
					ref1.setEmail(ref1Field.getText());
					ref1.addApplicant(applicant);
					ref2.setEmail(ref2Field.getText());
					ref2.addApplicant(applicant);
					ref3.setEmail(ref3Field.getText());
					ref3.addApplicant(applicant);
					ref1.save();
					ref2.save();
					ref3.save();
					
					applicant.addReference(ref1);
					applicant.addReference(ref2);
					applicant.addReference(ref3);
					applicant.setUsername(nameField.getText());
					applicant.setResume(resume.getText());
					applicant.setAppliedJob(jobsPanel.getSelectedJob());
					applicant.save();
					/*
					 * TODO: Make this do stuff
					 */
					clearFields();
					cl.show(cards, "JobsPanel");
				}
			}
			if (event.getSource() == cancelButton) {
				clearFields();
				cl.show(cards, "JobsPanel");
			}
		}
	}
}
