package Main;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;


public class ReferencePanel extends JPanel {
	private LogoutPanel logoutPanel;
	
	private static final long serialVersionUID = 1L;

	public ReferencePanel() {
		initUI();
	}
	
	private void initUI(){
		setSize(770, 670);
		setBackground(Color.black);
		setLayout(new BorderLayout());
		
		logoutPanel = new LogoutPanel();
		add(logoutPanel, BorderLayout.NORTH);
	}
}
