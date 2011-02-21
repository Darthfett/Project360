package Main;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ApplicantPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JobsPanel_ jobsPanel;
	private JPanel loginPanel;
	private JTabbedPane tabs;
	
	public ApplicantPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		loginPanel = new LoginPanel();
		add(loginPanel, BorderLayout.NORTH);
		
		tabs = new JTabbedPane();
		jobsPanel = new JobsPanel_(TheAppletItself.getCurrentUserLevel());
		
		tabs.setSize(770, 655);
		tabs.addTab("Jobs", jobsPanel);
		add(tabs, BorderLayout.CENTER);
	}
}
