package Main;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Main.Types.UserLevel;

/*
 * This is the entirely of the Reference user's UI. It simply contains an
 * applicants panel which shows only the applicants which referred. It then
 * provides a simple dialog which allows the reference to rate the applicant.
 */
public class ReferencePanel extends JPanel {
	private LogoutPanel logoutPanel;
	private JTabbedPane tabs;
	private ApplicantsPanel appsPanel;
	private User currentUser;
	private UserLevel userLevel;
	private static final String APPLICANTSPANEL = "My Applicants";
	
	private static final long serialVersionUID = 1L;
	
	public void refreshApps() {
		tabs.remove(appsPanel);
		appsPanel = new ApplicantsPanel(currentUser, userLevel);
		tabs.addTab(APPLICANTSPANEL, appsPanel);
	}

	public ReferencePanel(User reference) {
		initUI(reference);
	}
	
	private void initUI(User reference){
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		currentUser = reference;
		userLevel = reference.getUserLevel();
		
		logoutPanel = new LogoutPanel(currentUser);
		add(logoutPanel, BorderLayout.NORTH);
	
		tabs = new JTabbedPane();
		appsPanel = new ApplicantsPanel(reference, Types.UserLevel.REFERENCE);
		
		tabs.setSize(770, 655);
		tabs.addTab("My Applicants", appsPanel);
		add(tabs);
	}
}
