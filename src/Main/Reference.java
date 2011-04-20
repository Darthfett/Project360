package Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;

public class Reference extends User{
	private static File ReferenceDatabaseLocation = new File("../References");
	private static Hashtable<String, Reference> References = new Hashtable<String, Reference>();
	private Integer oldId;

	/* Reference constructor */

	public Reference() {
		database = new Hashtable<String, String>();
		ArrayList<Reference> referenceList = Reference.getReferenceList();
		int i;
		for (i = 0; i < referenceList.size(); i++) {
			if (Reference.getReferenceFromId(new Integer(i)) == null) {
				database.put("id", new Integer(i).toString());
				break;
			}
		}
		if (database.get("id") == null) {
			database.put("id", new Integer(i + 1).toString());
		}
		database.put("applicants", "");
		database.put("password", PasswordGenerator.createPassword());
		database.put("userlevel", "reference");
		Reference.References.put(database.get("id"), this);
		oldId = null;
	}

	public Reference(Integer id) {
		database = new Hashtable<String, String>();
		ArrayList<Reference> referenceList = Reference.getReferenceList();
		int i;
		if (Reference.getReferenceFromId(id) == null) {
			database.put("id", id.toString());
		} else {
			for (i = 0; i < referenceList.size(); i++) {
				if (Reference.getReferenceFromId(new Integer(i)) == null) {
					database.put("id", new Integer(i).toString());
					break;
				}
			}
			if (database.get("id") == null) {
				database.put("id", new Integer(i + 1).toString());
			}

		}
		database.put("applicants", "");
		database.put("password", PasswordGenerator.createPassword());
		database.put("userlevel", "reference");
		Reference.References.put(database.get("id"), this);
		oldId = null;
	}

	public static ArrayList<Reference> getReferenceList() {
		ArrayList<Reference> referenceList = new ArrayList<Reference>();
		Reference[] referenceListArr = (Reference[]) (Reference.References.values().toArray(new Reference[0]));
		for (int i = 0; i < referenceListArr.length; i++) {
			referenceList.add(referenceListArr[i]);
		}

		return referenceList;
	}

	public static Reference getReferenceFromId(Integer id) {
		return Reference.References.get(id.toString());
	}
	
	public static Reference getReferenceFromEmail(String email) {
		ArrayList<Reference> refs = Reference.getReferenceList();
		for (int i = 0; i < refs.size(); i++) {
			if (refs.get(i).getEmail().equalsIgnoreCase(email)) {
				return refs.get(i);
			}
		}
		return null;
	}
	
	public static User getUserFromUserName(String username) {
		/* 
		 * Given a username, will try to find a User of that username.
		 * Invalid usernames will return null.
		 */
		return Reference.getReferenceFromEmail(username);
	}

	public Integer getId() {
		return new Integer(database.get("id"));
	}
	
	public void setEmail(String email) {
		database.put("email", email);
	}
	
	public String getEmail() {
		return database.get("email");
	}
	
	public void setUsername(String email) {
		database.put("email", email);
	}
	
	public String getUsername() {
		return database.get("email");
	}

	/* Adding an Applicant for the Reference to rate */
	public void addApplicant(Applicant applicant) {
		ArrayList<Applicant> applicants = getApplicants();
		for (int i = 0; i < applicants.size(); i++) {
			if (applicants.get(i).equals(applicant)) {
				return;
			}
		}
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

	public ArrayList<Applicant> getApplicants() {
		ArrayList<Applicant> applicants = new ArrayList<Applicant>();
		if (database.get("applicants") == null) {
			return applicants;
		}
		String[] applicantIdStrings = database.get("applicants").split(",");
		if (applicantIdStrings.length == 1 && applicantIdStrings[0] == "") {
			return applicants;
		}
		for (int i = 0; i < applicantIdStrings.length; i++) {
			applicants.add(Applicant.getApplicantFromId(new Integer(applicantIdStrings[i])));
		}
		return applicants;
	}
	

	public boolean remove() {
		/*
		 * remove will remove this Reference from the References directory, as
		 * well as remove it from the hashtable.
		 * 
		 * Returns the success of the removal of the Reference (should never not
		 * succeed).
		 */
		String id;
		if (oldId == null) {
			id = this.getId().toString();
		} else {
			id = oldId.toString();
		}
		File referenceFile = new File(Reference.ReferenceDatabaseLocation, id
				+ ".reference");
		boolean success = referenceFile.delete();
		if (!success) {
			System.err.println("Cannot delete " + id);
			return false;
		}
		ArrayList<Applicant> applicants = getApplicants();
		for (int i = 0; i < applicants.size(); i++) {
			applicants.get(i).remove();
		}
		References.remove(id);
		return success;
	}

	public void save() {
		/*
		 * save will save this Reference to the References directory, for future
		 * reloading.
		 */
		if (this.getId() == null) {
			System.err.println("Cannot save a Reference with no Reference Id.");
			return;
		}
		File dir = Reference.ReferenceDatabaseLocation;
		File referenceFile;
		if (oldId == null) {
			referenceFile = new File(dir, getId() + ".reference");
		} else {
			referenceFile = new File(dir, oldId + ".reference");
			if (!referenceFile.exists()) {
				System.out.println("Attempting to delete reference " + oldId
						+ ".reference, but it does not exist?");
			} else {
				referenceFile.delete();
			}
			referenceFile = new File(dir, getId() + ".reference");
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(referenceFile));
			Iterator<String> hashedKeys = this.database.keySet().iterator();
			String cur;
			while (hashedKeys.hasNext()) {
				cur = hashedKeys.next();
				if (this.database.get(cur) != null && this.database.get(cur) != "") {
					out.write(cur + "="
							+ this.database.get(cur).replace("=", "\\="));
					out.newLine();
				}

			}
			out.close();
		} catch (IOException e) {
			System.err.println("Unable to open " + getId()
					+ ".reference for writing");
			e.printStackTrace();
			return;
		}
		oldId = null;

	}

	public static void loadReferenceList() {
		/*
		 * loadReferenceList loads all .reference files in src/Main/References/
		 * , and parses them into Reference objects. Each reference is stored in
		 * the Reference.References Hashtable. Reference information
		 * (title/description/id/date/deadline/location/salary/benefits) is
		 * stored in the user.database hashtable.
		 * 
		 * loadReferenceList will also clear all references currently in the
		 * hashtable
		 */
		Reference.References.clear();
		Hashtable<String, Hashtable<String, String>> directory_data = DatabaseParser
				.loadDatabase(Reference.ReferenceDatabaseLocation, "reference");
		Enumeration<String> reference_data_enum = directory_data.keys();
		while (reference_data_enum.hasMoreElements()) {
			String file = reference_data_enum.nextElement();
			Hashtable<String, String> reference_data = directory_data.get(file);
			if (!reference_data.containsKey("id")) {
				Reference new_reference = new Reference();
				new_reference.oldId = new Integer(file.split(".")[0]);
				new_reference.database = reference_data;
			}
			Reference new_reference = new Reference(new Integer(reference_data
					.get("id")));
			new_reference.oldId = new Integer(file.split("\\.")[0]);
			new_reference.database = reference_data;
		}

	}
	
	public void setPassword(String password) {
		database.put("password", password);
	}

	public String getPassword() {
		return database.get("password");
	}

}
