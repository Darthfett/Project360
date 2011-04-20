package Main;

import java.util.ArrayList;

/*
 * Reviewer class extends User
 * 
 * Works as a User that will rate and comment on applicants
 * 
 * 	 Dynamic
 * 		+ getJobs():ArrayList<Job>
 * 		+ setJobs(ArrayList<Job>)
 * 		+ addJob(Job)
 * 		+ removeJob(Job)
 * 
 */
public class Reviewer extends User {
	/* Add a Job for Reviewer to rate */
	public void addJob(Job job) {
		ArrayList<Job> jobs = getJobs();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).equals(job)) {
				return;
			}
		}
		if (database.get("jobs") == null || database.get("jobs") == "") {
			database.put("jobs",job.getId().toString());
		} else {
			database.put("jobs", database.get("jobs") + "," + job.getId().toString());
		}
	}
	
	public void removeJob(Job job) {
		ArrayList<Job> jobs = getJobs();
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i) == job) {
				jobs.remove(i);
				setJobs(jobs);
			}
		}
	}
	
	public void setJobs(ArrayList<Job> jobs) {
		String value = "";
		for (int i = 0; i < jobs.size(); i++) {
			value += jobs.get(i).getId().toString();
			if (i+1 != jobs.size()) {
				value+= ",";
			}
		} 
		database.put("jobs", value);
	}

	public ArrayList<Job> getJobs() {
		ArrayList<Job> jobs = new ArrayList<Job>();
		if (database.get("jobs") == null) {
			return jobs;
		}
		String[] jobIdStrings = database.get("jobs").split(",");
		if (jobIdStrings.length == 1 && jobIdStrings[0] == "") {
			return jobs;
		}
		for (int i = 0; i < jobIdStrings.length; i++) {
			jobs.add(Job.getJobFromId(new Integer(jobIdStrings[i])));
		}
		return jobs;
	}
	
}
