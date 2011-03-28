package Main;

import java.util.ArrayList;

public class Applicant {
	private ArrayList<Integer> referenceRatings;
	private ArrayList<User> references;
	private String reviewerComment;
	private int reviewerRating;
	private String resume;
	private String username;
	
	
	Applicant(){
		setReferenceRatings(new ArrayList<Integer>());
		setReviewerComment(new String());
	}


	public void setReferenceRatings(ArrayList<Integer> referenceRatings) {
		this.referenceRatings = referenceRatings;
	}

	public void addReferenceRating(int referenceRating) {
		referenceRatings.add(referenceRating);
	}

	public ArrayList<Integer> getReferenceRatings() {
		return referenceRatings;
	}

	public void addReference(User reference) {
		references.add(reference);
	}


	public ArrayList<User> getReferences() {
		return references;
	}


	public void setReviewerComment(String reviewerComment) {
		this.reviewerComment = reviewerComment;
	}


	public String getReviewerComment() {
		return reviewerComment;
	}


	public void setReviewerRating(int reviewerRating) {
		this.reviewerRating = reviewerRating;
	}


	public int getReviewerRating() {
		return reviewerRating;
	}


	public void setResume(String resume) {
		this.resume = resume;
	}


	public String getResume() {
		return resume;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getUsername() {
		return username;
	}
	
	
	
}
