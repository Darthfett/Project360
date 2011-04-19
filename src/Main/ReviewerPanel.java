package Main;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ReviewerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String APPLICANTSPANEL = "ApplicantsPanel";
	private static final String APPLICANTVIEWPANEL = "ApplicantViewPanel";
	private ApplicantsPanel appsPanel;
	private AppViewPanel appViewPanel;
	private JPanel appsCards;
	private JTabbedPane tabs;
	private LogoutPanel logoutPanel;
	private User currentUser;
	
	public ReviewerPanel(User currentUser) {
<<<<<<< HEAD
		this.currentUser = currentUser;
=======
>>>>>>> bd8cc1deed16ab8d9bc7db4c4d0ec36e2d8cfeb9
		initUI(currentUser);
	}
	
	private void initUI(User currentUser) {
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		logoutPanel = new LogoutPanel();
		add(logoutPanel, BorderLayout.NORTH);
		tabs = new JTabbedPane();
<<<<<<< HEAD
		
		appViewPanel = new AppViewPanel(Types.UserLevel.REVIEWER);
		appsPanel = new ApplicantsPanel(currentUser, Types.UserLevel.REVIEWER);
		appsCards = new JPanel();
		appsCards.setLayout(new CardLayout());
		appsCards.add(appsPanel, APPLICANTSPANEL);
		appsCards.add(appViewPanel, APPLICANTVIEWPANEL);
=======
		appsPanel = new ApplicantsPanel(currentUser, TheAppletItself.getCurrentUserLevel());
>>>>>>> bd8cc1deed16ab8d9bc7db4c4d0ec36e2d8cfeb9
		
		tabs.setSize(770, 655);
		tabs.addTab("My Applicants", appsPanel);
		add(tabs);
	}
	
	public AppViewPanel getAppViewPanel() {
		return appViewPanel;
	}
	
	public void refreshApps() {
		appsCards.remove(appsPanel);
		appsPanel = new ApplicantsPanel(currentUser, currentUser.getUserLevel());
		appsCards.add(appsPanel, APPLICANTSPANEL);
	}
}
