package Main;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class RecruiterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final String USERSPANEL = "UsersPanel";
	private static final String USEREDITPANEL = "UserEditPanel";
	private static final String USERADDPANEL = "UserAddPanel";
	private static final String JOBSPANEL = "JobsPanel";
	private static final String JOBADDPANEL = "JobAddPanel";
	private static final String JOBEDITPANEL = "JobEditPanel";
	private static final String APPLICANTSPANEL = "ApplicantsPanel";
	private static final String APPLICANTVIEWPANEL = "ApplicantViewPanel";
	private JTabbedPane tabs;
	private JobsPanel jobsPanel;
	private JPanel applicantsCards;
	private JPanel usersCards;
	private JPanel jobsCards;
	private UsersPanel usersPanel;
	private UserEditPanel userEditPanel;
	private UserEditPanel userAddPanel;
	private JobEditPanel jobAddPanel;
	private JobEditPanel jobEditPanel;
	private ApplicantsPanel appsPanel;
	private ApplicantViewPanel appViewPanel;
	private LogoutPanel logoutPanel;
	private Types.UserLevel userLevel;
	
	public RecruiterPanel() {
		userLevel = TheAppletItself.getCurrentUserLevel();
		initUI();
	}
	
	private void initUI() {
		setSize(770, 600);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		tabs = new JTabbedPane();
		
		applicantsCards = new JPanel();
		applicantsCards.setLayout(new CardLayout());
		appsPanel = new ApplicantsPanel(userLevel);
		appViewPanel = new ApplicantViewPanel(userLevel);
		applicantsCards.add(appsPanel, APPLICANTSPANEL);
		applicantsCards.add(appViewPanel, APPLICANTVIEWPANEL);
		
		usersCards = new JPanel();
		usersCards.setLayout(new CardLayout());
		usersPanel = new UsersPanel();
		userEditPanel = new UserEditPanel('e');
		userAddPanel = new UserEditPanel('a');
		usersCards.add(usersPanel, USERSPANEL);
		usersCards.add(userEditPanel, USEREDITPANEL);
		usersCards.add(userAddPanel, USERADDPANEL);
		
		jobsCards = new JPanel();
		jobsCards.setLayout(new CardLayout());
		jobsPanel = new JobsPanel(userLevel);
		jobEditPanel = new JobEditPanel('e');
		jobAddPanel = new JobEditPanel('a');
		jobsCards.add(jobsPanel, JOBSPANEL);
		jobsCards.add(jobAddPanel, JOBADDPANEL);
		jobsCards.add(jobEditPanel, JOBEDITPANEL);
		
		tabs.addTab("Jobs", jobsCards);
		tabs.addTab("Users", usersCards);
		tabs.addTab("Applicants", applicantsCards);
		tabs.setSize(770, 600);
		add(tabs, BorderLayout.CENTER);
		
		logoutPanel = new LogoutPanel();
		add(logoutPanel, BorderLayout.NORTH);
	}
	
	public JPanel getUsersCards() {
		return usersCards;
	}
	
	public JPanel getJobsCards() {
		return jobsCards;
	}
	
	public UsersPanel getUsersPanel() {
		return usersPanel;
	}
	
	public UserEditPanel getUserEditPanel() {
		return userEditPanel;
	}
	
	public JobsPanel getJobsPanel() {
		return jobsPanel;
	}
	
	public JobEditPanel getJobEditPanel() {
		return jobEditPanel;
	}
	
	public ApplicantsPanel getApplicantsPanel() {
		return appsPanel;
	}
	
	public ApplicantViewPanel getApplicantViewPanel() {
		return appViewPanel;
	}
	
	public void refreshUsers() {
		usersCards.remove(usersPanel);
		usersPanel = new UsersPanel();
		usersCards.add(usersPanel, USERSPANEL);
	}
	
	public void refreshJobs() {
		jobsCards.remove(jobsPanel);
		jobsPanel = new JobsPanel(userLevel);
		jobsCards.add(jobsPanel, JOBSPANEL);
	}
}
