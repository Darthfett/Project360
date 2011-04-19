package Main;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ApplicantViewPanel extends ApplyPanel {
	
	public ApplicantViewPanel(Types.UserLevel userLevel) {
		super();
		if (userLevel == Types.UserLevel.RECRUITER)
			submitButton.setText("View Rating...");
		if (userLevel == Types.UserLevel.REVIEWER)
			submitButton.setText("Rate...");
		nameField.setEnabled(false);
		ref1Field.setEnabled(false);
		ref2Field.setEnabled(false);
		ref3Field.setEnabled(false);
		resume.setEnabled(false);
		
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
	
	public JTextArea getResume() {
		return resume;
	}
	
	private class AVListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			if (event.getSource() == cancelButton) {
				cl.show(cards, "ApplicantsPanel");
			}
		}
	}
}
