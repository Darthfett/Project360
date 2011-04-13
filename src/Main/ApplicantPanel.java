package Main;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ApplicantPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLYPANEL = "ApplyPanel";
	private static final String JOBSPANEL = "JobsPanel";
	private JobsPanel jobsPanel;
	private ApplyPanel applyPanel;
	private JPanel loginPanel;
	private JPanel jobsCards;
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

		jobsCards = new JPanel();
		jobsCards.setLayout(new CardLayout());
		jobsPanel = new JobsPanel(TheAppletItself.getCurrentUserLevel());
		applyPanel = new ApplyPanel();
		jobsCards.add(jobsPanel, JOBSPANEL);
		jobsCards.add(applyPanel, APPLYPANEL);
		
		tabs = new JTabbedPane();
		tabs.setSize(770, 655);
		tabs.addTab("Jobs", jobsCards);
		add(tabs, BorderLayout.CENTER);
	}
	
	public JobsPanel getJobsPanel() {
		return jobsPanel;
	}
}
