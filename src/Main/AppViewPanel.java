package Main;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*
 * This is the panel shown to recruiters and reviewers who wish to view or rate
 * an applicant.
 */
public class AppViewPanel extends ApplyPanel {
	
	private static final long serialVersionUID = 1L;
	
	public AppViewPanel(Types.UserLevel userLevel) {
		super();
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
		if(userLevel == Types.UserLevel.RECRUITER)
			submitButton.setText("View Rating...");
		else
			submitButton.setText("Rate...");
		submitButton.addActionListener(new AVListener());
		cancelButton.addActionListener(new AVListener());
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
			if (event.getSource() == cancelButton) {
				cl.show(cards, "ApplicantsPanel");
			}
			if (event.getSource() == submitButton) {
				/*
				 * Need to implement this stuff
				 */
				cl.show(cards, "ApplicantsPanel");
			}
		}
	}
}
