package Main;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RecruiterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabs;
	private JobsPanel_ jobsPanel;
	private JPanel usersPanel;
	private JPanel appPanel;
	private LogoutPanel logoutPanel;
	
	public RecruiterPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 600);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		tabs = new JTabbedPane();
		jobsPanel = new JobsPanel_(TheAppletItself.getCurrentUserLevel());
		appPanel = new ApplicantsPanel(TheAppletItself.getCurrentUserLevel());
		usersPanel = new UsersPanel();
		
		tabs.addTab("Jobs", jobsPanel);
		tabs.addTab("Users", usersPanel);
		tabs.addTab("Applicants", appPanel);
		tabs.setSize(770, 600);
		add(tabs, BorderLayout.CENTER);
		
		logoutPanel = new LogoutPanel();
		add(logoutPanel, BorderLayout.NORTH);
	}
}
