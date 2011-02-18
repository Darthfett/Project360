package Main;

import java.io.*;
import java.util.*;

import Main.Types.UserLevel;

public class User {
	//TODO: Make additional constructors that save the user.
	private Hashtable<String, String> data;
	private static File DataDir = new File("src/Main/Users");
	private static ArrayList<User> Users = new ArrayList<User>();

	public String getUserName() {
		return (String) data.get("username");
	}
	
	public String getUserLevelString() {
		return (String) data.get("userlevel");
	}
	
	public String getUserPassword() {
		return (String) data.get("password");
	}

	public UserLevel getUserLevel() {
		String userLevelString = (String) data.get("userlevel");
		if (userLevelString == "recruiter") {
			return UserLevel.RECRUITER;
		} else if (userLevelString == "reviewer") {
			return UserLevel.REVIEWER;
		} else if (userLevelString == "reference") {
			return UserLevel.REFERENCE;
		} else if (userLevelString == "applicant") {
			return UserLevel.APPLICANT;
		} else {
			System.out.println("DEBUG: Unknown user level: " + userLevelString);
			return UserLevel.UNKNOWN;
		}
	}

	public User() {
		//TODO: Replace this constructor with constructor that saves users
		this.data = new Hashtable<String, String>();
	}

	public static void loadUserList(){
		/*
		 * loadUserList loads all .user files in src/Main/Users/ , and parses them into User objects.
		 * Each user is stored in the User.Users ArrayList.
		 * User information (username/password/userlevel) is stored in the user.data hashtable.
		 * 
		 */
		System.out.println("DEBUG: Attempting to load users");
		File dir = new File("src/Main/Users");
		File currentUser;
		BufferedReader bufferedReader;
		System.out.println(dir.getAbsolutePath());
		String[] userFiles = dir.list();
		if (userFiles == null) {
			System.out.println("DEBUG: No existing users, or unable to find Users/*");
			return;
		}
		for (int i = 0; i < userFiles.length; i++) {
			System.out.println("DEBUG: Found " + userFiles[i]);
			currentUser = new File(User.DataDir, userFiles[i]);
			try { //Try to open the file
				bufferedReader = new BufferedReader(new FileReader(currentUser));
			} catch (FileNotFoundException ex) {
				System.out.println("DEBUG: Unable to open " + userFiles[i]);
				continue;
			}
			try {
				String line = bufferedReader.readLine();
				while (line != null) {
					ArrayList<String> keyVal = new ArrayList<String>();
					String[] temp = line.replace(" ", "").replace("\n", "").split("=");
					for (int j = 0; j < temp.length; j++) {
						keyVal.add(temp[j]);
					}
					User newUser = new User();
					if (keyVal.size() == 1) {
						keyVal.add("");
					} else if (keyVal.size() == 2) {
					} else {
						System.out.println("WARNING: Invalid User File: " + userFiles[i]);
						continue;
					}
					newUser.data.put(keyVal.get(0), keyVal.get(1));

					line = bufferedReader.readLine();

					Users.add(newUser);
				}
			} catch (IOException ex) {
				System.out.println("DEBUG: Unable to read from " + userFiles[i]);

			}
		}
	}
}
