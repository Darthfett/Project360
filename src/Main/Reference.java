package Main;

import java.util.ArrayList;

public class Reference extends User {
	private String email;
	private ArrayList<Applicant> applicants;
	
	/* Reference constructor */
	Reference(){
		email = new String();
		applicants = new ArrayList<Applicant>();
	}
	
	/* Reference constructor with email */
	Reference(String email) {
		this.email = email;
		applicants = new ArrayList<Applicant>();
	}
	
	/* Adding an Applicant for the Reference to rate */
	public void addApplicant(Applicant applicant) {
		applicants.add(applicant);
	}
	
	/* Removing an Applicant from the Reference's list of Applicants to rate */
	public void removeApplicant(int applicantId) {
		for(int i = 0; i < applicants.size(); i++){
			if(applicants.get(i).getId() == applicantId){
				applicants.remove(i);
			}
		}
	}
	
	/* setter for ArrayList of Applicants to be rated by Reference */
	public void setApplicants(ArrayList<Applicant> applicants) {
		this.applicants = applicants;
	}
	
	/* getter for list of Applicants the Reference must rate */
	public ArrayList<Applicant> getApplicants() {
		return applicants;
	}
	
	/* setter for Reference email address */
	public void setEmail(String email) {
		this.email = email;
	}

	/* getter for Reference email address */
	public String getEmail() {
		return email;
	}
}
