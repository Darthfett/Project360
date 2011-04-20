package Main;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RatingsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JLabel ref1Label;
	private JLabel ref2Label;
	private JLabel ref3Label;
	private JLabel revLabel;
	private JLabel revCommentLabel;
	private JLabel ref1Rating;
	private JLabel ref2Rating;
	private JLabel ref3Rating;
	private JLabel revRating;
	private JTextArea revComment;
	private JScrollPane revScroll;
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel padding;
	private JButton doneButton;
	
	public RatingsPanel() {
		
		ref1Label = new JLabel("First Reference Rating");
		ref2Label = new JLabel("Second Reference Rating");
		ref3Label = new JLabel("Third Reference Rating");
		revLabel = new JLabel("Reviewer Rating");
		revCommentLabel = new JLabel("Reviewer Comment");
		ref1Rating = new JLabel("Not yet rated");
		ref2Rating = new JLabel("Not yet rated");
		ref3Rating = new JLabel("Not yet rated");
		revRating = new JLabel("Not yet rated");
		revComment = new JTextArea();
		revScroll = new JScrollPane(revComment);
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		padding = new JPanel();
		doneButton = new JButton("Done");
		
		northPanel.setLayout(new GridLayout(4 ,2));
		northPanel.add(ref1Label);
		northPanel.add(ref1Rating);
		northPanel.add(ref2Label);
		northPanel.add(ref2Rating);
		northPanel.add(ref3Label);
		northPanel.add(ref3Rating);
		northPanel.add(revLabel);
		northPanel.add(revRating);
		northPanel.setPreferredSize(new Dimension(470, 80));
		padding.add(northPanel);
		
		centerPanel.setLayout(new BorderLayout());
		revComment.setEnabled(false);
		revComment.setDisabledTextColor(Color.black);
		centerPanel.add(revCommentLabel, BorderLayout.NORTH);
		centerPanel.add(revScroll, BorderLayout.CENTER);
		
		doneButton.addActionListener(new RListener());
		southPanel.add(doneButton);
		
		setLayout(new BorderLayout());
		add(padding, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(12,0)), BorderLayout.EAST);
	}
	
	public void reset() {
		ref1Rating.setText("Not yet rated");
		ref2Rating.setText("Not yet rated");
		ref3Rating.setText("Not yet rated");
		revRating.setText("Not yet rated");
		revComment.setText("");
	}
	
	public void set(Applicant applicant) {
		ArrayList<Integer> ratings = applicant.getReferenceRatings();
		if (ratings.size() == 0) {
			return;
		} else {
			if (ratings.size() == 1) {
				ref1Rating.setText(ratings.get(0).toString());
			}
			if (ratings.size() == 2) {
				ref1Rating.setText(ratings.get(0).toString());
				ref2Rating.setText(ratings.get(1).toString());
			} 
			if (ratings.size() == 3) {
				ref1Rating.setText(ratings.get(0).toString());
				ref2Rating.setText(ratings.get(1).toString());
				ref3Rating.setText(ratings.get(2).toString());
			}
		}
	}
	
	public RatingsPanel getThisPanel() {
		return this;
	}
	
	private class RListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JPanel cards = (JPanel) getThisPanel().getParent();
			CardLayout cl = (CardLayout) cards.getLayout();
			cl.show(cards, "ApplicantsPanel");
		}
	}
	
}
