package Main;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ApplyPanel extends JPanel {
	private JButton submitButton;
	private JButton cancelButton;
	private JTextField nameField;
	private JTextField ref1Field;
	private JTextField ref2Field;
	private JTextField ref3Field;
	private JTextField resumeField;
	private JLabel nameLabel;
	private JLabel ref1Label;
	private JLabel ref2Label;
	private JLabel ref3Label;
	private JLabel resumeLabel;
	
	public ApplyPanel() {
		setLayout(new BorderLayout());
		submitButton = new JButton("Submit");
		cancelButton = new JButton("Cancel");
		nameField = new JTextField();
		ref1Field = new JTextField();
		ref2Field = new JTextField();
		ref3Field = new JTextField();
		resumeField = new JTextField();
		nameLabel = new JLabel("Name");
		ref1Label = new JLabel("Reference 1");
		ref2Label = new JLabel("Reference 2");
		ref3Label = new JLabel("Reference 3");
		resumeLabel = new JLabel("Resume");
		
		
	}
}
