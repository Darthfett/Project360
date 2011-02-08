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
	private JButton createButton;
	private JButton editButton;
	
	
	String[] sampleData = {"Software Engineer", "Senior Software Engineer", "Operations Coordinator",
			"Program Manager", "Software Tester", "Ice Cream Tasting Specialist"};

	public JobsPanel(Types.UserLevel userLevel) {
		setLayout(null);
		
		jobList = new JList(sampleData);
		jobList.setPreferredSize(new Dimension(430, 600));
		jobList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jobList.setLayoutOrientation(JList.VERTICAL_WRAP);
		
		scrollPane = new JScrollPane(jobList);
		scrollPane.setBounds(30, 55, 450, 550);
		add(scrollPane);
		
		description = new JLabel();
		description.setBounds(12, 16, 210, 400);
		description.setFont(new Font("Arial", Font.PLAIN, 12));
		String descText = "<html><p>Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum" +
				" lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum, " +
				"lorem ipsum lorem ipsum lorem ipsum. <br><br>Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum, lorem ipsum.</html>";
		description.setText(descText);
			
		
		descriptionPanel = new JPanel();
		descriptionPanel.setBorder(BorderFactory.createTitledBorder("Job Description"));
		descriptionPanel.setBounds(505, 55, 230, 440);
		descriptionPanel.setLayout(null);
		descriptionPanel.add(description);
		add(descriptionPanel);
		
		if (userLevel == Types.UserLevel.RECRUITER) {
			createButton = new JButton("Create new...");
			editButton = new JButton("Edit...");
			
			createButton.setBounds(545, 515, 150, 30);
			add(createButton);
			
			editButton.setBounds(545, 565, 150, 30);
			add(editButton);
		}
	}
}
