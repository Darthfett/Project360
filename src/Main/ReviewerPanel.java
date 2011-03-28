package Main;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;


public class ReviewerPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private ApplicantsPanel appsPanel;
	private JTabbedPane tabs;
	private LogoutPanel logoutPanel;
	
	public ReviewerPanel() {
		initUI();
	}
	
	private void initUI() {
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(null);
		
		logoutPanel = new LogoutPanel();
		add(logoutPanel, BorderLayout.NORTH);
		
		tabs = new JTabbedPane();
		appsPanel = new ApplicantsPanel(TheAppletItself.getCurrentUserLevel());
		
		tabs.setSize(770, 655);
		tabs.addTab("My Applicants", appsPanel);
		add(tabs);
	}

}
