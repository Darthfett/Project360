package Main;
import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Main.Types.UserLevel;


public class TheAppletItself extends JApplet {
	
	/*
	 * This is the current user-level of the applet i.e. REVIEWER, RECRUITER, etc. 
	 * (see Types.java)
	 * It is set by the LoginView, and determines the content of the applet.
	 */
	private static Types.UserLevel currentUserLevel;
	private static final long serialVersionUID = 1L;
	private JPanel rootPanel;
	private User currentUser = null;
	
	public void init() {
		setSize(770, 600);
		User.loadUserList();
		Applicant.loadApplicantList();
		Job.loadJobList();
		Reference.loadReferenceList();
		
		
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					initUI();
				}
			});
		}
		catch (Exception e) {
			System.err.println("There was a problem creating the Applet UI...");
			e.printStackTrace();
		}
	}
	
	private void initUI() {
		currentUserLevel = UserLevel.APPLICANT;
		rootPanel = new ApplicantPanel(currentUser);
		getContentPane().add(rootPanel);
	}

	public static Types.UserLevel getCurrentUserLevel() {
		return currentUserLevel;
	}
	
	public static void setCurrentUserLevel(UserLevel userLevel) {
		currentUserLevel = userLevel;
	}
}
