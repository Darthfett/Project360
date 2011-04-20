package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
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

/*
 * This is the panel shown to recruiters and reviewers who wish to view or rate
 * an applicant.
 */
public class AppViewPanel extends ApplyPanel {
	
	private static final long serialVersionUID = 1L;
	private JButton rateViewButton;
	private JButton viewCancelButton;
	private JPanel commentPanel;
	private JPanel buttonPanel;
	private JLabel commentsLabel;
	private JTextArea comments;
	private JScrollPane commentsScrollPane;
	private Types.UserLevel userLevel;
	
	public AppViewPanel(Types.UserLevel userLevel) {
		super();
		this.userLevel = userLevel;
		
		nameField.setEnabled(false);
		nameField.setDisabledTextColor(Color.black);
		ref1Field.setEnabled(false);
		ref1Field.setDisabledTextColor(Color.black);
		ref2Field.setEnabled(false);
		ref2Field.setDisabledTextColor(Color.black);
		ref3Field.setEnabled(false);
		ref3Field.setDisabledTextColor(Color.black);
		resume.setEnabled(false);
		resume.setDisabledTextColor(Color.black);
		viewCancelButton = new JButton("Cancel");
		
		rateViewButton = new JButton();
		southPanel.remove(submitButton);
		southPanel.remove(cancelButton);
		if(userLevel == Types.UserLevel.REVIEWER){
			southPanel.setLayout(new BorderLayout());
			commentsLabel = new JLabel("Reviewer Comments");
			comments = new JTextArea();
			commentsScrollPane = new JScrollPane(comments);
					
			commentPanel = new JPanel();
			commentPanel.setLayout(new BorderLayout());
			buttonPanel = new JPanel();
			commentPanel.add(commentsLabel, BorderLayout.NORTH);
			commentPanel.add(commentsScrollPane, BorderLayout.CENTER);
			commentPanel.add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.WEST);
			commentPanel.add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.EAST);
			commentPanel.setPreferredSize(new Dimension(470, 100));
			buttonPanel.add(rateViewButton);
			buttonPanel.add(cancelButton);
			
			southPanel.add(commentPanel, BorderLayout.NORTH);
			southPanel.add(buttonPanel, BorderLayout.SOUTH);
			
			rateViewButton.setText("Rate...");
		}
		else{
			southPanel.add(rateViewButton);
			southPanel.add(viewCancelButton);
			rateViewButton.setText("View Rating...");
		}
		rateViewButton.addActionListener(new AVListener());
		viewCancelButton.addActionListener(new AVListener());
	}
	
	public JTextField getNameField() {
		return nameField;
	}
	
	public JTextField getRef1Field() {
		return ref1Field;
	}
	
	public JTextField getRef2Field() {
		return ref2Field;
	}
	
	public JTextField getRef3Field() {
		return ref3Field;
	}
	
	public JTextArea getResumeArea() {
		return resume;
	}
	
	
	private class AVListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			
			if (event.getSource() == viewCancelButton) {
				cl.show(cards, "ApplicantsPanel");
			}
			if (event.getSource() == rateViewButton) {
				if (userLevel == Types.UserLevel.RECRUITER) {
					Applicant applicant = null;
					ApplicantsPanel appsPanel = null;
					RatingsPanel ratePanel = null;
					RecruiterPanel recPanel = (RecruiterPanel) cards.getParent().getParent();
					appsPanel = recPanel.getApplicantsPanel();
					ratePanel = recPanel.getRatingsPanel();
					applicant = appsPanel.getSelectedApplicant();
					ratePanel.reset();
					System.out.println(applicant + "is the applicant to set RatingsPanel.");
					ratePanel.set(applicant);
					cl.show(cards, "RatingsPanel");
				}
				if (userLevel == Types.UserLevel.REVIEWER) {
					int rating;
					Applicant applicant = null;
					ApplicantsPanel appsPanel = null;
					ReviewerPanel revPanel = (ReviewerPanel) cards.getParent().getParent();
					appsPanel = revPanel.getApplicantsPanel();
					applicant = appsPanel.getSelectedApplicant();
					
					if(applicant != null){
						Object[] options = {"1", "2", "3", "4", "5", "Cancel"};
						rating = JOptionPane.showOptionDialog(getRootPane(),
								"Designate a rating (5 is good):",
								"Rate Applicant",
								JOptionPane.YES_NO_CANCEL_OPTION,
								JOptionPane.QUESTION_MESSAGE,
								null,
								options,
								options[5]);
						applicant.setReviewerRating(rating+1);
						applicant.setReviewerComment(comments.getText());
						applicant.save();
						System.out.println(applicant + "is what ReviewerPanel worked on.");
					
					cl.show(cards, "ApplicantsPanel");
					}
				}
			}
		}
	}
}
