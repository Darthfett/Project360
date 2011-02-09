import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ApplicantPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private JobsPanel jobsPanel;
	private JTabbedPane tabs;
	
	public ApplicantPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(null);
		
		tabs = new JTabbedPane();
		jobsPanel = new JobsPanel(TheAppletItself.getCurrentUserLevel());
		
		tabs.setSize(770, 655);
		tabs.addTab("Jobs", jobsPanel);
		add(tabs);
	}
}
