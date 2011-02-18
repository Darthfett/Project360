package Main;

import java.io.*;
import java.util.*;

import Main.Types.UserLevel;

public class User {
	//TODO: Make additional constructors that save the user.
	private Hashtable<String, String> data;
	private static File DataDir = new File("../Users");
	private static ArrayList<User> Users = new ArrayList<User>();
	
	@SuppressWarnings("unchecked")
	public static ArrayList<User> getUserList() {
		return (ArrayList<User>) User.Users.clone();
	}

	public String getUserName() {
		return (String) data.get("username");
	}
	
	public String getUserLevelString() {
		return (String) data.get("userlevel");
	}
	
	public String getUserPassword() {
		return (String) data.get("password");
	}
	
	private void saveUser() {
		System.out.println("DEBUG: Attempting to save user data to file: " + getUserName());
		File dir = User.DataDir;
		File userFile = new File(dir,getUserName() + ".user");
		String fileData = "";
		fileData.concat("username="+getUserName());
		fileData.concat("\npassword="+getUserPassword());
		fileData.concat("\nuserlevel="+getUserLevelString());
		//FileWriter fileWriter = new FileWriter(userFile);
		System.out.println("ERROR: Problem with saving user to file: Not Yet Implemented");
		
	}
	
	public void setUserName(String username) {
		this.data.put("username", username);
		this.saveUser();
	}
	
	public void setUserPassword(String password) {
		this.data.put("password", password);
		this.saveUser();
	}
	
	public void setUserLevel(String userLevel) {
		this.data.put("userlevel", userLevel);
		this.saveUser();
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
	
	public User(String username, String password, String userlevel) {
		
	}

	public User() {
		//TODO: Replace this constructor with constructor that saves users
		this.data = new Hashtable<String, String>();
		this.data.put("username","temp");
		this.data.put("password","");
		this.data.put("userlevel","applicant");
	}

	public static void loadUserList(){
		/*
		 * loadUserList loads all .user files in src/Main/Users/ , and parses them into User objects.
		 * Each user is stored in the User.Users ArrayList.
		 * User information (username/password/userlevel) is stored in the user.data hashtable.
		 * 
		 */
		System.out.println("DEBUG: Attempting to load users");
		File dir = User.DataDir;
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
			currentUser = new File(dir, userFiles[i]);
			try { //Try to open the file
				bufferedReader = new BufferedReader(new FileReader(currentUser));
			} catch (FileNotFoundException ex) {
				System.out.println("DEBUG: Unable to open " + userFiles[i]);
				continue;
			}
			try { //Try to read the data from the file
				String line = bufferedReader.readLine();
				//Lines -> Arrays split by '='
				User newUser = new User();
				Users.add(newUser);
				while (line != null) {
					ArrayList<String> keyVal = new ArrayList<String>();
					String[] temp = line.replace(" ", "").replace("\n", "").toLowerCase().split("=");
					for (int j = 0; j < temp.length; j++) {
						keyVal.add(temp[j]);
					}
					//Arrays -> Users with arrays in hashtable
					if (keyVal.size() == 1) {
						keyVal.add("");
					} else if (keyVal.size() == 2) {
					} else {
						System.out.print("WARNING: Invalid User File: " + userFiles[i] + " line: ");
						for(int j=0; j < keyVal.size();j++) {
							System.out.print(keyVal.get(j) +",");
						}
						continue;
					}
					newUser.data.put(keyVal.get(0), keyVal.get(1));
					line = bufferedReader.readLine();

				}
			} catch (IOException ex) {
				System.out.println("DEBUG: Unable to read from " + userFiles[i]);

			}
		}
	}
}
