import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RecruiterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabs;
	private JobsPanel jobsPanel;
	private JPanel second;
	private JPanel third;
	
	public RecruiterPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 670);
		tabs = new JTabbedPane();
		jobsPanel = new JobsPanel(TheAppletItself.getCurrentUserLevel());
		second = new JPanel();
		third = new JPanel();
		
		setBackground(Color.black);
		setLayout(null);
		tabs.addTab("Jobs", jobsPanel);
		tabs.addTab("Users", second);
		tabs.addTab("Applicants", third);
		tabs.setSize(770, 655);
		add(tabs);
	}
}
