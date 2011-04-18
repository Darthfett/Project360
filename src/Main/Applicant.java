package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class Applicant {
	private static File ApplicantDatabaseLocation = new File("../Applicants");
	private static Hashtable<String,Applicant> Applicants = new Hashtable<String,Applicant>();
	private Hashtable<String, String> database;
	private Integer oldId;
	
	public static ArrayList<Applicant> getApplicantList() {
		ArrayList<Applicant> applicantList = new ArrayList<Applicant>();
		Applicant[] applicantListArr = (Applicant[]) (Applicant.Applicants.values().toArray(new Applicant[0]));
		for (int i = 0; i < applicantListArr.length; i++) {
			applicantList.add(applicantListArr[i]);
		}
		
		return applicantList;
	}
	
	public static Applicant getApplicantFromId(Integer id) {
		return Applicant.Applicants.get(id.toString());
	}
	
	public Applicant() {
		database = new Hashtable<String, String>();
		database.put("id", "0"); //TODO Generate a Unique Applicant Id
		database.put("referenceRatings", "");
		ArrayList<Applicant> applicantList = Applicant.getApplicantList();
		int i;
		for (i = 0; i < applicantList.size(); i++) {
			if (Applicant.getApplicantFromId(new Integer(i)) == null) {
				database.put("id",new Integer(i).toString());
				break;
			}
		}
		if (database.get("id") == null) {
			System.out.println("new id");
			database.put("id", new Integer(i+1).toString());
		}
		Applicant.Applicants.put(database.get("id"), this);
		oldId = null;
	}
	
	public Applicant(Integer id) {
		database = new Hashtable<String, String>();
		ArrayList<Applicant> applicantList = Applicant.getApplicantList();
		int i;
		if (Applicant.getApplicantFromId(id) == null) {
			database.put("id", id.toString());
		} else {
			for (i = 0; i < applicantList.size(); i++) {
				if (Applicant.getApplicantFromId(new Integer(i)) == null) {
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
		Applicant.Applicants.put(database.get("id"), this);
		oldId = null;
	}

	public void setId(Integer id){
		database.put("id", id.toString());
	}
	
	public Integer getId(){
		return new Integer(database.get("id"));
	}
	
	public void setReferenceRatings(ArrayList<Integer> referenceRatings) {
		String value = "";
		for (int i = 0; i < referenceRatings.size(); i++) {
			value += referenceRatings.get(i).toString();
			if (i+1 != referenceRatings.size()) {
				value+= ",";
			}
		} 
		database.put("referenceRatings", value);
	}

	public void addReferenceRating(Integer referenceRating) {
		if (database.get("referenceRatings").equals("")) {
			database.put("referenceRatings", referenceRating.toString());
		} else {
			database.put("referenceRatings",database.get("referenceRatings") + "," + referenceRating.toString());
		}
	}

	public ArrayList<Integer> getReferenceRatings() {
		String value = database.get("referenceRatings");
		ArrayList<Integer> referenceRatings = new ArrayList<Integer>();
		if (value.contains(",")) {
			String[] ratings = value.split(",");
			for (int i = 0; i < ratings.length; i++) {
				referenceRatings.add(new Integer(ratings[i]));
			}
		} else if (! value.equals("")) {
			referenceRatings.add(new Integer(value));
		}
		return referenceRatings;	
	}

	public void addReference(User reference) {
		if (! (database.get("references") == null) && ! database.get("references").equals("")) {
			database.put("references", database.get("references") + reference.getUsername());
		} else {
			database.put("references",reference.getUsername());
		}
	}

	public ArrayList<User> getReferences() {
		ArrayList<User> references = new ArrayList<User>();
		String value = database.get("references");
		if (value == null) {
			return references;
		}
		if (value.contains(",")) {
			String[] refs = value.split(",");
			for (int i = 0; i < refs.length; i++) {
				references.add(User.getUserFromUserName(refs[i]));
			}
		} else if (! value.equals("")) {
			references.add(User.getUserFromUserName(value));
		}
		return references;
	}

	public void setReviewerComment(String reviewerComment) {
		database.put("reviewerComment", reviewerComment);
	}

	public String getReviewerComment() {
		return database.get("reviewerComment");
	}

	public void setReviewerRating(Integer reviewerRating) {
		database.put("reviewerRating", reviewerRating.toString());
	}

	public Integer getReviewerRating() {
		return new Integer(database.get("reviewerRating"));
	}

	public void setResume(String resume) {
		database.put("resume", resume);
	}

	public String getResume() {
		return database.get("resume");
	}

	public void setUsername(String username) {
		database.put("username", username);
	}

	public String getUsername() {
		return database.get("username");
	}
	
	public void setAppliedJob(Job appliedJob) {
		Job oldJob = getAppliedJob();
		database.put("appliedJob",appliedJob.getId().toString());
		if (oldJob != null) {
			oldJob.removeApplicant(this);
		}
		
	}
	
	public Job getAppliedJob() {
		if (database.get("appliedJob") == null) {
			return null;
		}
		return Job.getJobFromId(new Integer(database.get("appliedJob")));
	}

	public boolean remove() {
		/*
		 * remove will remove this Applicant from the Applicants directory, as well as remove it from
		 * the hashtable.
		 * 
		 * Returns the success of the removal of the Applicant (should never not succeed).
		 */
		String id;
		if (oldId == null) {
			id = this.getId().toString();
		} else {
			id = oldId.toString();
		}
		File applicantFile = new File(Applicant.ApplicantDatabaseLocation,id + ".applicant");
		boolean success = applicantFile.delete();
		if (! success) {
			System.err.println("Cannot delete " + id);
			return false;
		}
		Job appliedJob = getAppliedJob();
		if (appliedJob != null) {
			appliedJob.removeApplicant(this);
		}
		Applicants.remove(id);
		return success;
	}

	public void save() {
		/*
		 * save will save this Applicant to the Applicants directory, for future reloading.
		 */
		if (this.getId() == null) {
			System.err.println("Cannot save a Applicant with no Applicant Id.");
			return;
		}
		if (this.getId() == null) {
			System.err.println("Cannot save a Job with no Job Id.");
			return;
		}
		File dir = Applicant.ApplicantDatabaseLocation;
		File applicantFile;
		if (oldId == null) {
			applicantFile = new File(dir,getId() + ".applicant");
		} else {
			applicantFile = new File(dir,oldId + ".applicant");
			if (! applicantFile.exists()) {
				System.out.println("Attempting to delete applicant " + oldId + ".applicant, but it does not exist?");
			} else {
				applicantFile.delete();
			}
			applicantFile = new File(dir, getId() + ".applicant");
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(applicantFile));
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
			System.err.println("Unable to open " + getId() + ".applicant for writing");
			e.printStackTrace();
			return;
		}
		
	}
	
	public static void loadApplicantList() {
		/*
		 * loadApplicantList loads all .applicant files in src/Main/Applicants/ , and parses them into Applicant objects.
		 * Each applicant is stored in the Applicant.Applicants Hashtable.
		 * Applicant information (title/description/id/date/deadline/location/salary/benefits) is stored in the user.database hashtable.
		 * 
		 * loadApplicantList will also clear all applicants currently in the hashtable
		 */
		Applicant.Applicants.clear();
		Hashtable<String,Hashtable<String,String>> directory_data = DatabaseParser.loadDatabase(Applicant.ApplicantDatabaseLocation,"applicant");
		Enumeration<String> applicant_data_enum = directory_data.keys(); 
		while (applicant_data_enum.hasMoreElements()) {
			String file = applicant_data_enum.nextElement();
			Hashtable<String,String> applicant_data = directory_data.get(file);
			if (! applicant_data.containsKey("id")) {
				System.out.println("File " + file + " has no Applicant Id. Skipping that file.");
				continue;
			}
			Applicant new_applicant = new Applicant(new Integer(applicant_data.get("id")));
			new_applicant.oldId = new Integer(file.split("\\.")[0]);
			new_applicant.database = applicant_data;
		}
	}
}
