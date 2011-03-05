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
	private JTabbedPane tabs;
	private JobsPanel jobsPanel;
	private JPanel usersCards;
	private UsersPanel usersPanel;
	private UserEditPanel userEditPanel;
	private UserEditPanel userAddPanel;
	private ApplicantsPanel appPanel;
	private LogoutPanel logoutPanel;
	
	public RecruiterPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 600);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		tabs = new JTabbedPane();
		jobsPanel = new JobsPanel(TheAppletItself.getCurrentUserLevel());
		appPanel = new ApplicantsPanel(TheAppletItself.getCurrentUserLevel());
		
		usersCards = new JPanel();
		usersCards.setLayout(new CardLayout());
		usersPanel = new UsersPanel();
		userEditPanel = new UserEditPanel('e');
		userAddPanel =new UserEditPanel('a');
		usersCards.add(usersPanel, USERSPANEL);
		usersCards.add(userEditPanel, USEREDITPANEL);
		usersCards.add(userAddPanel, USERADDPANEL);
		
		tabs.addTab("Jobs", jobsPanel);
		tabs.addTab("Users", usersCards);
		tabs.addTab("Applicants", appPanel);
		tabs.setSize(770, 600);
		add(tabs, BorderLayout.CENTER);
		
		logoutPanel = new LogoutPanel();
		add(logoutPanel, BorderLayout.NORTH);
	}
	
	public JPanel getUsersCards() {
		return usersCards;
	}
	
	public UsersPanel getUsersPanel() {
		return usersPanel;
	}
	
	public UserEditPanel getUserEditPanel() {
		return userEditPanel;
	}
	
	public void refreshUsers() {
		usersCards.remove(usersPanel);
		usersPanel = new UsersPanel();
		usersCards.add(usersPanel, USERSPANEL);
	}
}
