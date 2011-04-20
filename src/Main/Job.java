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
	private Integer oldId;
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
		ArrayList<Job> jobList = Job.getJobList();
		int i;
		for (i = 0; i < jobList.size(); i++) {
			if (Job.getJobFromId(new Integer(i)) == null) {
				database.put("id",new Integer(i).toString());
				break;
			}
		}
		if (database.get("id") == null) {
			database.put("id", new Integer(i+1).toString());
		}
		database.put("applicants", "");
		Job.Jobs.put(database.get("id"), this);
		oldId = null;
	}
	
	public Job(Integer id) {
		database = new Hashtable<String, String>();
		ArrayList<Job> jobList = Job.getJobList();
		int i;
		if (Job.getJobFromId(id) == null) {
			database.put("id", id.toString());
		} else {
			for (i = 0; i < jobList.size(); i++) {
				if (Job.getJobFromId(new Integer(i)) == null) {
					database.put("id",new Integer(i).toString());
					break;
				}
			}
			if (database.get("id") == null) {
				System.out.println("new id");
				database.put("id", new Integer(i+1).toString());
			}
			
		}
		database.put("applicants", "");
		Job.Jobs.put(database.get("id"), this);
		oldId = null;
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
		System.out.println("Get Applicants");
		ArrayList<Applicant> applicants = new ArrayList<Applicant>();
		if (database.get("applicants") == null) {
			System.out.println("Applicants are null (?)");
			return applicants;
		}
		System.out.println(database.get("applicants"));
		String[] applicantIdStrings = database.get("applicants").split(",");
		if (applicantIdStrings.length == 1 && applicantIdStrings[0] == "") {
			System.out.println("No Applicants");
			return applicants;
		}
		System.out.println(applicantIdStrings);
		for (int i = 0; i < applicantIdStrings.length; i++) {
			System.out.println(Applicant.getApplicantFromId(new Integer(applicantIdStrings[i])));
			applicants.add(Applicant.getApplicantFromId(new Integer(applicantIdStrings[i])));
		}
		return applicants;
	}
	
	public void setTitle(String title) {
		database.put("title",title);
	}
	
	public boolean setId(Integer id) {
		if (id.equals(database.get("id"))) {
			return true;
		}
		if (Job.getJobFromId(id) != null) {
			return false;
		}
		Job.Jobs.remove(getId().toString());
		database.put("id",id.toString());
		Job.Jobs.put(id.toString(), this);
		return true;
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
		if (database.get("applicants") == null || database.get("applicants") == "") {
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
		String id;
		if (oldId == null) {
			id = this.getId().toString();
		} else {
			id = oldId.toString();
		}
		File jobFile = new File(Job.JobDatabaseLocation,id + ".job");
		boolean success = jobFile.delete();
		if (! success) {
			System.err.println("Cannot delete " + id);
			return false;
		}
		ArrayList<Applicant> applicants = getApplicants();
		for (int i = 0; i < applicants.size(); i++) {
			applicants.get(i).remove();
		}
		Jobs.remove(id);
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
		File jobFile;
		if (oldId == null) {
			jobFile = new File(dir,getId() + ".job");
		} else {
			jobFile = new File(dir,oldId + ".job");
			if (! jobFile.exists()) {
				System.out.println("Attempting to delete job " + oldId + ".job, but it does not exist?");
			} else {
				jobFile.delete();
			}
			jobFile = new File(dir, getId() + ".job");
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(jobFile));
			Iterator<String> hashedKeys = this.database.keySet().iterator();
			String cur;
			while(hashedKeys.hasNext()) {
				cur = hashedKeys.next();
				if (this.database.get(cur) != null && this.database.get(cur) != "") {
					out.write(cur + "=" + this.database.get(cur).replace("=", "\\="));
					out.newLine();
				}
			
			}
			out.close();
		} catch (IOException e) {
			System.err.println("Unable to open " + getId() + ".job for writing");
			e.printStackTrace();
			return;
		}
		oldId = null;
		
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
				Job new_job = new Job();
				new_job.oldId = new Integer(file.split(".")[0]);
				new_job.database = job_data;
			}
			Job new_job = new Job(new Integer(job_data.get("id")));
			new_job.oldId = new Integer(file.split("\\.")[0]);
			new_job.database = job_data;
		}
		
	}
	
	public String toString() {
		//title/description/id/date/deadline/location/salary/benefits
		ArrayList<String> s = new ArrayList<String>();

		if (getId() != null) {
			s.add("Id: " + getId());
		}
		if (getTitle() != null) {
			s.add("Title: " + getTitle());
		}
		if (getDescription() != null) {
			s.add("Description: " + getDescription());
		}
		if (getPostDate() != null) {
			s.add("Posting Date: " + getPostDate());
		}
		if (getDeadline() != null) {
			s.add("Deadline: " + getDeadline());
		}
		if (getLocation() != null) {
			s.add("Location: " + getLocation());
		}
		if (getSalary() != null) {
			s.add("Salary: " + getSalary());
		}
		if (getBenefits() != null) {
			s.add("Benefits: " + getBenefits());
		}
		String str = new String("");
		for (int i = 0; i < s.size(); i++) {
			str = str + s.get(i) + "<br><br>";
		}
		if (str.endsWith("\n")) {
			str = str.substring(0, str.length() - 2);
		}
		return str;
	}
}
