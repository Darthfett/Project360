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
	private JTabbedPane tabs;
	private JobsPanel_ jobsPanel;
	private JPanel usersCards;
	private JPanel usersPanel;
	private JPanel userEditPanel;
	private JPanel appPanel;
	private LogoutPanel logoutPanel;
	
	public RecruiterPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 600);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		tabs = new JTabbedPane();
		jobsPanel = new JobsPanel_(TheAppletItself.getCurrentUserLevel());
		appPanel = new ApplicantsPanel(TheAppletItself.getCurrentUserLevel());
		
		usersCards = new JPanel();
		usersCards.setLayout(new CardLayout());
		usersPanel = new UsersPanel();
		userEditPanel = new UserEditPanel();
		usersCards.add(usersPanel, USERSPANEL);
		usersCards.add(userEditPanel, USEREDITPANEL);
		
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
	
	public void refreshUsers() {
		usersCards.remove(usersPanel);
		usersPanel = new UsersPanel();
		usersCards.add(usersPanel, USERSPANEL);
	}
}
