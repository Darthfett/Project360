package Main;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ReviewerPanel extends JPanel {
	private static final String APPLICANTSPANEL = "ApplicantsPanel";
	private static final String APPLICANTVIEWPANEL = "ApplicantViewPanel";
	private static final long serialVersionUID = 1L;
	private JTabbedPane tabs;
	private LogoutPanel logoutPanel;
	private JPanel applicantsCards;
	private ApplicantsPanel appsPanel;
	private ApplicantViewPanel appViewPanel;
	private Types.UserLevel userLevel;
	
	
	public ReviewerPanel() {
		userLevel = TheAppletItself.getCurrentUserLevel();
		initUI();
	}
	
	private void initUI() {
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		logoutPanel = new LogoutPanel();
		add(logoutPanel, BorderLayout.NORTH);
		
		tabs = new JTabbedPane();
		
		applicantsCards = new JPanel();
		applicantsCards.setLayout(new CardLayout());
		appsPanel = new ApplicantsPanel(userLevel);
		appViewPanel = new ApplicantViewPanel(userLevel);
		applicantsCards.add(appsPanel, APPLICANTSPANEL);
		applicantsCards.add(appViewPanel, APPLICANTVIEWPANEL);
		
		tabs.setSize(770, 655);
		tabs.addTab("My Applicants", applicantsCards);
		add(tabs);
	}
	
	public ApplicantsPanel getApplicantsPanel() {
		return appsPanel;
	}
	
	public ApplicantViewPanel getApplicantViewPanel() {
		return appViewPanel;
	}

}
