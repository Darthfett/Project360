package Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class JobsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JList jobList;
	private JScrollPane scrollPane;
	private JLabel description;
	private JPanel descriptionPanel;
	private JPanel descriptionPadding;
	private JPanel innerPanel;
	private JPanel rightPanel;
	private JPanel buttonsPanel;	
	private JButton createButton;
	private JButton editButton;
	private JButton applyButton;

	
	String[] sampleData = {"Software Engineer", "Senior Software Engineer", "Operations Coordinator",
			"Program Manager", "Software Tester", "Ice Cream Tasting Specialist"};

	public JobsPanel(Types.UserLevel userLevel) {
		setLayout(new GridLayout(1,2));
		
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		
		jobList = new JList(sampleData);
		innerPanel = new JPanel();
		innerPanel.setBackground(Color.white);
		jobList.setPreferredSize(new Dimension(340, 600));
		jobList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		jobList.setLayoutOrientation(JList.VERTICAL_WRAP);
		innerPanel.add(jobList);
		
		scrollPane = new JScrollPane(innerPanel);
		
		descriptionPanel = new JPanel();
		descriptionPadding = new JPanel();
		descriptionPanel.setBorder(BorderFactory.createTitledBorder("Job Description"));
		descriptionPadding.add(descriptionPanel);
		rightPanel.add(descriptionPadding, BorderLayout.CENTER);
		
		description = new JLabel();
		description.setFont(new Font("Arial", Font.PLAIN, 12));
		String text = "Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum" +
				" lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum, " +
				"lorem ipsum lorem ipsum lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum, lorem ipsum." +
				"Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum, lorem ipsum." +
				"Lorem ipsum, lorem ipsum lorem ipsum lorem ipsum, lorem ipsum. Lorem ipsum, lorem" +
				" ipsum lorem ipsum lorem ipsum, lorem ipsum. Lorem ipsum, lorem ipsum lorem ipsum " +
				"lorem ipsum, lorem ipsum.";
		String fText = String.format("<html><div WIDTH=%d>%s</div></html>", 320, text);
		description.setText(fText);
		
		descriptionPanel.add(description);
		
		buttonsPanel = new JPanel();
		rightPanel.add(buttonsPanel, BorderLayout.SOUTH);
		
		add(scrollPane);
		add(rightPanel);
		
		if (userLevel == Types.UserLevel.RECRUITER) {
			createButton = new JButton("Create new...");
			editButton = new JButton("Edit...");
			buttonsPanel.add(createButton);
			buttonsPanel.add(editButton);
	
		}
		
		if (userLevel == Types.UserLevel.APPLICANT) {
			applyButton = new JButton("Apply...");
			buttonsPanel.add(applyButton);
		}
	}
}
