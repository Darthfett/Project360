package Main;

import java.io.*;
import java.util.*;

import Main.Types.UserLevel;

/*
 * User class
 * 
 * 
 * Members
 * 	Static
 *    	- DataDir:File
 *  	- Users:Hashtable<String,User>
 *  	- userList:ArrayList<User>
 * 
 * 	Dynamic
 * 		- data:Hashtable
 * 
 * Methods
 * 	 Static
 * 		+ getUserFromUserName(String):User
 * 
 * 	 Dynamic
 * 		+ getUserList():ArrayList<User>
 *   	+ getUserName():String
 *   	+ getUserLevelString():String
 *   	+ getUserLevel():Main.Types.UserLevel
 * 		+ getUserPassword():String
 * 		+ remove()
 * 		+ save()
 * 		+ setUserName(String)
 * 		+ setUserPassword(String)
 * 		+ setUserLevel(String)
 * 		+ loadUserList()
 * 
 */

public class User {
	//TODO: Make additional constructors that save the user.
	private Hashtable<String, String> data;
	private static File DataDir = new File("../Users");
	private static Hashtable<String,User> Users = new Hashtable<String,User>();
	
	public static ArrayList<User> getUserList() {
		ArrayList<User> userList = new ArrayList<User>();
		User[] userListArr = (User[]) (User.Users.values().toArray(new User[0]));
		for (int i = 0; i < userListArr.length; i++) {
			userList.add(userListArr[i]);
		}
		
		return userList;
	}
	
	public static User getUserFromUserName(String username) {
		/* 
		 * Given a username, will try to find a User of that username.
		 * Invalid usernames will return null.
		 */
		return User.Users.get(username);
	}

	public String getUserName() {
		return (String) data.get("username");
	}
	
	public String getUserLevelString() {
		return (String) data.get("userlevel");
	}

	public UserLevel getUserLevel() {
		String userLevelString = ((String) data.get("userlevel"));//.replace(" ","").replace("\n","");
		if (userLevelString.equals("recruiter")) {
			return UserLevel.RECRUITER;
		} else if (userLevelString.equals("reviewer")) {
			return UserLevel.REVIEWER;
		} else if (userLevelString.equals("reference")) {
			return UserLevel.REFERENCE;
		} else if (userLevelString.equals("applicant")) {
			return UserLevel.APPLICANT;
		} else {
			System.out.println("DEBUG: Unknown user level: " + userLevelString);
			return UserLevel.UNKNOWN;
		}
	}
	
	public String getUserPassword() {
		return (String) data.get("password");
	}
	
	public boolean remove() {
		/*
		 * remove will remove this User from the Users directory, as well as remove it from
		 * the hashtable.
		 * 
		 * Returns the success of the removal of the User (should never not succeed).
		 */
		String userName = getUserName();
		File userFile = new File(User.DataDir, userName + ".user");
		boolean success = userFile.delete();
		if (! success) {
			System.out.println("ERROR: Cannot delete " + userName);
		}
		Users.remove(userName);
		return success;
	}
	
	public void save() {
		/*
		 * save will save this User to the Users directory, for future reloading.
		 */
		System.out.println("DEBUG: Attempting to save user data to file: " + getUserName());
		if (this.getUserName() == null) {
			System.out.println("ERROR: Cannot save a User with no username.");
			return;
		}
		File dir = User.DataDir;
		File userFile = new File(dir,getUserName() + ".user");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(userFile));
			Iterator<String> hashedKeys = this.data.keySet().iterator();
			String cur;
			while(hashedKeys.hasNext()) {
				cur = hashedKeys.next();
				out.write(cur + "=" + this.data.get(cur));
				out.newLine();
			
			}
			System.out.println("Success!");
			out.close();
		} catch (IOException e) {
			System.out.println("ERROR: Unable to open " + getUserName() + ".user for writing");
			e.printStackTrace();
			return;
		}
		
	}
	
	public void setUserName(String username) {
		this.data.put("username", username);
	}
	
	public void setUserPassword(String password) {
		this.data.put("password", password);
	}
	
	public void setUserLevel(String userLevel) {
		this.data.put("userlevel", userLevel);
	}
	
	public User(String username, String password, String userlevel) {
		this.data = new Hashtable<String, String>();
		this.data.put("username",username);
		this.data.put("password",password);
		this.data.put("userlevel",userlevel);
	}

	public User() {
		//TODO: Replace this constructor with constructor that saves users
		this.data = new Hashtable<String, String>();
		this.data.put("password","");
		this.data.put("userlevel","");
	}

	public static void loadUserList(){
		/*
		 * loadUserList loads all .user files in src/Main/Users/ , and parses them into User objects.
		 * Each user is stored in the User.Users ArrayList.
		 * User information (username/password/userlevel) is stored in the user.data hashtable.
		 * 
		 * loadUserList will also clear all users currently in the hashtable
		 */
		System.out.println("DEBUG: Attempting to load users");
		User.Users.clear();
		File dir = User.DataDir;
		File currentUser;
		BufferedReader bufferedReader;
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
						System.out.print("WARNING: Invalid line in User File: " + userFiles[i]);
						for(int j=0; j < keyVal.size();j++) {
							System.out.print(keyVal.get(j) +",");
						}
						continue;
					}
					newUser.data.put(keyVal.get(0), keyVal.get(1));
					line = bufferedReader.readLine();

				}
			if (newUser.data.containsKey("username")) {
				User.Users.put(newUser.data.get("username"), newUser);
			} else {
				System.out.println("WARNING: User file " + userFiles[i] + " has no username");
			}
			
			} catch (IOException ex) {
				System.out.println("DEBUG: Unable to read from " + userFiles[i]);

			}
			try {
				bufferedReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Users loaded");
	}
}
