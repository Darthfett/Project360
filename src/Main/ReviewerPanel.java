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
		this.currentUser = currentUser;
		initUI(currentUser);
	}
	
	private void initUI(User currentUser) {
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		logoutPanel = new LogoutPanel(currentUser);
		add(logoutPanel, BorderLayout.NORTH);
		tabs = new JTabbedPane();
		
		appViewPanel = new AppViewPanel(currentUser, Types.UserLevel.REVIEWER);
		appsPanel = new ApplicantsPanel(currentUser, Types.UserLevel.REVIEWER);
		appsCards = new JPanel();
		appsCards.setLayout(new CardLayout());
		appsCards.add(appsPanel, APPLICANTSPANEL);
		appsCards.add(appViewPanel, APPLICANTVIEWPANEL);
		
		tabs.setSize(770, 655);
		tabs.addTab("My Applicants", appsCards);
		add(tabs);
	}
	
	public AppViewPanel getAppViewPanel() {
		return appViewPanel;
	}
	
	public ApplicantsPanel getApplicantsPanel() {
		return appsPanel;
	}
	
	public void refreshApps() {
		appsCards.remove(appsPanel);
		appsPanel = new ApplicantsPanel(currentUser, currentUser.getUserLevel());
		appsCards.add(appsPanel, APPLICANTSPANEL);
	}
}
