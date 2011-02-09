import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;


public class JobsPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JList jobList;
	private JScrollPane scrollPane;
	private JLabel description;
	private JPanel descriptionPanel;
	private JPanel innerPanel;
	private JButton firstButton;
	private JButton secondButton;
	
	
	String[] sampleData = {"Software Engineer", "Senior Software Engineer", "Operations Coordinator",
			"Program Manager", "Software Tester", "Ice Cream Tasting Specialist"};

	public JobsPanel(Types.UserLevel userLevel) {
		setLayout(null);
		
		jobList = new JList(sampleData);
		innerPanel = new JPanel();
		innerPanel.setBackground(Color.white);
		jobList.setPreferredSize(new Dimension(320, 600));
		jobList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jobList.setLayoutOrientation(JList.VERTICAL_WRAP);
		innerPanel.add(jobList);
		
		scrollPane = new JScrollPane(innerPanel);
		scrollPane.setBounds(30, 55, 380, 550);
		add(scrollPane);
		
		description = new JLabel();
		description.setBounds(12, 16, 270, 460);
		description.setFont(new Font("Arial", Font.PLAIN, 12));
		String descText = "<html>Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum" +
				" lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum, " +
				"lorem ipsum lorem ipsum lorem ipsum. <br><br>Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum, lorem ipsum." +
				"<br><br>Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum, lorem ipsum." +
				"Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum, lorem ipsum. Lorem ipsum, lorem" +
				" ipsum lorem ipsum lorem ipsum, lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum, lorem ipsum.</html>";
		description.setText(descText);
			
		
		descriptionPanel = new JPanel();
		descriptionPanel.setBorder(BorderFactory.createTitledBorder("Job Description"));
		descriptionPanel.setBounds(435, 55, 290, 478);
		descriptionPanel.setLayout(null);
		descriptionPanel.add(description);
		add(descriptionPanel);
		
		if (userLevel == Types.UserLevel.RECRUITER) {
			firstButton = new JButton("Create new...");
			secondButton = new JButton("Edit...");
			
			firstButton.setBounds(445, 565, 120, 30);
			secondButton.setBounds(590, 565, 120, 30);
		}
		
		if (userLevel == Types.UserLevel.APPLICANT) {
			firstButton = new JButton("More Info...");
			secondButton = new JButton("Apply...");
			
			firstButton.setBounds(445, 565, 120, 30);
			secondButton.setBounds(590, 565, 120, 30);
		}
		add(firstButton);
		add(secondButton);
	}
}
