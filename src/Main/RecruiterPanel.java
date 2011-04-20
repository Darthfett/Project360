package Main;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/*
 * This is the panel which contains the entire user interface for recruiters. There
 * are three tabs: Jobs, Users, and Applicants, which contain a JobsPanel, UsersPanel,
 * and ApplicantsPanel respectively.
 */
public class RecruiterPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	// These constants are used by the CardLayout for each tab
	private static final String USERSPANEL = "UsersPanel";
	private static final String USEREDITPANEL = "UserEditPanel";
	private static final String USERADDPANEL = "UserAddPanel";
	private static final String JOBSPANEL = "JobsPanel";
	private static final String JOBADDPANEL = "JobAddPanel";
	private static final String JOBEDITPANEL = "JobEditPanel";
	private static final String APPLICANTSPANEL = "ApplicantsPanel";
	private static final String APPLICANTVIEWPANEL = "ApplicantViewPanel";
	private static final String RATINGSPANEL = "RatingsPanel";
	
	private JTabbedPane tabs;
	private JobsPanel jobsPanel;
	private JPanel usersCards;
	private JPanel jobsCards;
	private JPanel appsCards;
	private UsersPanel usersPanel;
	private UserEditPanel userEditPanel;
	private UserEditPanel userAddPanel;
	private JobEditPanel jobAddPanel;
	private JobEditPanel jobEditPanel;
	private ApplicantsPanel appsPanel;
	private AppViewPanel appViewPanel;
	private RatingsPanel ratingsPanel;
	private LogoutPanel logoutPanel;
	private Types.UserLevel userLevel;
	private User currentUser;
	
	/*
	 * The constructor takes the currently logged in user as a parameter.
	 * It also sets the userLevel Field as the current user level of the applet.
	 */
	public RecruiterPanel(User currentUser) {
		this.currentUser = currentUser;
		userLevel = TheAppletItself.getCurrentUserLevel();
		initUI(currentUser);
	}
	
	/*
	 * This function creates and lays out the actual user interface.
	 */
	private void initUI(User currentUser) {
		setSize(770, 600);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		tabs = new JTabbedPane();
		appsPanel = new ApplicantsPanel(currentUser, userLevel);
		appsCards = new JPanel();
		appsCards.setLayout(new CardLayout());
		appViewPanel = new AppViewPanel(userLevel);
		ratingsPanel = new RatingsPanel();
		appsCards.add(appsPanel, APPLICANTSPANEL);
		appsCards.add(appViewPanel, APPLICANTVIEWPANEL);
		appsCards.add(ratingsPanel, RATINGSPANEL);
		
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
		tabs.addTab("Applicants", appsCards);
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
	
	public AppViewPanel getAppViewPanel(){
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
	
	public void refreshApps() {
		appsCards.remove(appsPanel);
		appsPanel = new ApplicantsPanel(currentUser, userLevel);
		appsCards.add(appsPanel, APPLICANTSPANEL);
	}
}
