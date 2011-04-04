package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class Job {
	private static File JobDatabaseLocation = new File("../Jobs");
	private static Hashtable<String,Job> Jobs = new Hashtable<String,Job>();
	private Hashtable<String, String> database;
	private static DateFormat dateFormat = new SimpleDateFormat("MMMMM.dd.yyyy");
	//title/description/id/date/deadline/location/salary/benefits
	
	public static ArrayList<Job> getJobList() {
		ArrayList<Job> jobList = new ArrayList<Job>();
		Job[] jobListArr = (Job[]) (Job.Jobs.values().toArray(new Job[0]));
		for (int i = 0; i < jobListArr.length; i++) {
			jobList.add(jobListArr[i]);
		}
		
		return jobList;
	}
	
	public static Job getJobFromId(Integer id) {
		return Job.Jobs.get(id.toString());
	}
	
	public Job() {
		database = new Hashtable<String, String>();
		database.put("id", "0"); //TODO Generate a Unique Job Id
		database.put("applicants", "");
	}
	
	public Integer getId() {
		return new Integer(database.get("id"));
	}
	
	public String getTitle() {
		return database.get("title");
	}
	
	public String getDescription() {
		return database.get("description");
	}
	
	public Date getPostDate() {
		try {
			return Job.dateFormat.parse(database.get("date"));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			//No post date
			return null;
		}
	}
	
	public Date getDeadline() {
		try {
			return Job.dateFormat.parse(database.get("deadline"));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		} catch (NullPointerException e) {
			//No deadline
			return null;
		}
	}
	
	public String getLocation() {
		return database.get("location");
	}
	
	public String getSalary() {
		return database.get("salary");
	}
	
	public ArrayList<Applicant> getApplicants() {
		String[] applicantIdStrings = database.get("applicants").split(",");
		ArrayList<Applicant> applicants = new ArrayList<Applicant>();
		if (applicantIdStrings.length == 1 && applicantIdStrings[0] == "") {
			return applicants;
		}
		for (int i = 0; i < applicantIdStrings.length; i++) {
			applicants.add(Applicant.getApplicantFromId(new Integer(applicantIdStrings[i])));
		}
		return applicants;
	}
	
	public void setId(Integer id) {
		database.put("id",id.toString());
	}
	
	public void setTitle(String title) {
		database.put("title",title);
	}
	
	public void setDescription(String desc) {
		database.put("description",desc);
	}
	
	public void setPostDate(Date postDate) {
		database.put("date",Job.dateFormat.format(postDate));
	}
	
	public void setDeadline(Date deadline) {
		database.put("deadline",Job.dateFormat.format(deadline));
	}
	
	public void setLocation(String location) {
		database.put("location", location);
	}
	
	public void setSalary(String salary) {
		database.put("salary", salary);
	}
	
	public void setApplicants(ArrayList<Applicant> applicants) {
		String value = "";
		for (int i = 0; i < applicants.size(); i++) {
			value += applicants.get(i).getId().toString();
			if (i+1 != applicants.size()) {
				value+= ",";
			}
		} 
		database.put("applicants", value);
	}
	
	public void addApplicant(Applicant applicant) {
		if (database.get("applicants") == "") {
			database.put("applicants",applicant.getId().toString());
		} else {
			database.put("applicants", database.get("applicants") + "," + applicant.getId().toString());
		}
	}
	
	public void removeApplicant(Applicant applicant) {
		ArrayList<Applicant> applicants = getApplicants();
		for (int i = 0; i < applicants.size(); i++) {
			if (applicants.get(i) == applicant) {
				applicants.remove(i);
				setApplicants(applicants);
			}
		}
	}

	public void setBenefits(String benefits) {
		database.put("benefits",benefits);
	}

	public String getBenefits() {
		return database.get("benefits");
	}
	
	public boolean remove() {
		/*
		 * remove will remove this Job from the Jobs directory, as well as remove it from
		 * the hashtable.
		 * 
		 * Returns the success of the removal of the Job (should never not succeed).
		 */
	
		String Id = this.getId().toString();
		File jobFile = new File(Job.JobDatabaseLocation,Id + ".job");
		boolean success = jobFile.delete();
		if (! success) {
			System.err.println("Cannot delete " + Id);
			return false;
		}
		ArrayList<Applicant> applicants = getApplicants();
		for (int i = 0; i < applicants.size(); i++) {
			applicants.get(i).remove();
		}
		Jobs.remove(Id);
		return success;
	}

	public void save() {
		/*
		 * save will save this Job to the Jobs directory, for future reloading.
		 */
		if (this.getId() == null) {
			System.err.println("Cannot save a Job with no Job Id.");
			return;
		}
		File dir = Job.JobDatabaseLocation;
		File jobFile = new File(dir,getId() + ".job");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(jobFile));
			Iterator<String> hashedKeys = this.database.keySet().iterator();
			String cur;
			while(hashedKeys.hasNext()) {
				cur = hashedKeys.next();
				out.write(cur + "=" + this.database.get(cur).replace("=", "\\="));
				out.newLine();
			
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Unable to open " + getId() + ".job for writing");
			e.printStackTrace();
			return;
		}
		
	}
	
	public static void loadJobList() {
		/*
		 * loadJobList loads all .job files in src/Main/Jobs/ , and parses them into Job objects.
		 * Each job is stored in the Job.Jobs Hashtable.
		 * Job information (title/description/id/date/deadline/location/salary/benefits) is stored in the user.database hashtable.
		 * 
		 * loadJobList will also clear all jobs currently in the hashtable
		 */
		Job.Jobs.clear();
		Hashtable<String,Hashtable<String,String>> directory_data = DatabaseParser.loadDatabase(Job.JobDatabaseLocation,"job");
		Enumeration<String> job_data_enum = directory_data.keys(); 
		while (job_data_enum.hasMoreElements()) {
			String file = job_data_enum.nextElement();
			Hashtable<String,String> job_data = directory_data.get(file);
			if (! job_data.containsKey("id")) {
				System.out.println("File " + file + " has no Job Id. Skipping that file.");
				continue;
			}
			Job new_job = new Job();
			new_job.database = job_data;
			Job.Jobs.put(job_data.get("id"), new_job);
		}
		
	}
}
