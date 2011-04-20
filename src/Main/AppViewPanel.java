package Main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
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
		southPanel.add(rateViewButton);
		southPanel.add(viewCancelButton);
		if(userLevel == Types.UserLevel.RECRUITER)
			rateViewButton.setText("View Rating...");
		else
			rateViewButton.setText("Rate...");
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
					ratePanel.set(applicant);
					cl.show(cards, "RatingsPanel");
				}
				if (userLevel == Types.UserLevel.RECRUITER) {
					/*
					 * Need interface for reviewers to rate/comment
					 */
				}
			}
		}
	}
}
