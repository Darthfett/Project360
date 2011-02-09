import java.util.concurrent.CountDownLatch;

import javax.swing.JApplet;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class TheAppletItself extends JApplet {
	
	/*
	 * This is the current user-level of the applet i.e. REVIEWER, RECRUITER, etc. 
	 * (see Types.java)
	 * It is set by the LoginView, and determines the content of the applet.
	 */
	private static Types.UserLevel currentUserLevel;
	private static CountDownLatch loginSignal;
	private static final long serialVersionUID = 1L;
	private JPanel rootPanel;
	
	public void init() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.platf.windows.WindowsLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		setSize(760, 660);
		
		/*
		 * This CountDownLatch is here to prevent init() from finishing before we 
		 * know what kind of user is logging in. It is important to know what the 
		 * user level is before we call setContentPane() on the applet, so we will
		 * wait for the login frame to finish up.
		 */
		loginSignal = new CountDownLatch(1);
		
		/* 
		 * This is an idiom to create a LoginView and show the JFrame. Basically
		 * it puts the job on the event-dispatching thread (EDT). Swing components
		 * are not thread safe, so the EDT is the only thread allowed to create/modify 
		 * swing components outside of listener methods.
		 */
		try {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					LoginView loginView = new LoginView();
					loginView.setVisible(true);
				}
			});
		}
		catch (Exception e) {
			System.err.println("There was a problem creating the login frame...");
			e.printStackTrace();
		}
		
		/*
		 * Here's where loginSignal comes into play. We wait for the login frame to
		 * decrement the CountDownLatch to 0. Only then does init() create the UI
		 * for the applet.
		 */
		try {
			loginSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
		switch (currentUserLevel) {
			case RECRUITER:
				rootPanel = new RecruiterPanel();
				break;
			case APPLICANT:
				rootPanel = new ApplicantPanel();
				break;
			case REVIEWER:
				rootPanel = new ReviewerPanel();
				break;
			case REFERENCE:
				rootPanel = new ReferencePanel();
				break;
			default:
				rootPanel = new ApplicantPanel();
				break;
		}
		setContentPane(rootPanel);
	}

	public static Types.UserLevel getCurrentUserLevel() {
		return currentUserLevel;
	}
	
	public static void setCurrentUserLevel(Types.UserLevel userLevel) {
		currentUserLevel = userLevel;
	}
	
	public static CountDownLatch getLoginSignal() {
		return loginSignal;
	}
}
