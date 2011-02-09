import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RecruiterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabs;
	private JobsPanel jobsPanel;
	private JPanel usersPanel;
	private JPanel appPanel;
	
	public RecruiterPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(null);
		
		tabs = new JTabbedPane();
		jobsPanel = new JobsPanel(TheAppletItself.getCurrentUserLevel());
		usersPanel = new UsersPanel();
		appPanel = new ApplicantsPanel(TheAppletItself.getCurrentUserLevel());
		
		tabs.addTab("Jobs", jobsPanel);
		tabs.addTab("Users", usersPanel);
		tabs.addTab("Applicants", appPanel);
		tabs.setSize(770, 655);
		add(tabs);
	}
}
