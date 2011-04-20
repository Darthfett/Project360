package Main;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/*
 * This is the JPanel which is shown by the applet when an applicant is using the
 * system. It is the default content of the applet when no user is logged in. The 
 * panel consists of a login panel at the top, and and list of jobs, which is a 
 * JobsPanel object. The selected job's information is displayed in the right-hand
 * panel. When a job is selected, the user may begin applying for that job by
 * pressing the apply button.
 */
public class ApplicantPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final String APPLYPANEL = "ApplyPanel";
	private static final String JOBSPANEL = "JobsPanel";
	private JobsPanel jobsPanel;
	private ApplyPanel applyPanel;
	private JPanel loginPanel;
	private JPanel jobsCards;
	private JTabbedPane tabs;
	private User currentUser;
	
	public ApplicantPanel(User currentUser) {
		this.currentUser = currentUser;
		initUI();
	}
	
	/*
	 * This function sets up the user interface for the panel.
	 */
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
