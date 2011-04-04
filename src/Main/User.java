package Main;

import java.io.*;
import java.util.*;

import Main.DatabaseParser;

import Main.Types.UserLevel;

/*
 * User class
 * 
 * 
 * Members
 * 	Static
 *    	- UserDatabaseLocation:File
 *  	- Users:Hashtable<String,User> //Maps usernames to Users
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
 *   	+ getUsername():String
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
	private Hashtable<String, String> database;
	private static File UserDatabaseLocation = new File("../Users");
	private static Hashtable<String,User> Users = new Hashtable<String,User>();
	private String oldName;
	
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

	public String getUsername() {
		return (String) database.get("username");
	}
	
	public String getUserLevelString() {
		return (String) database.get("userlevel");
	}

	public UserLevel getUserLevel() {
		String userLevelString = ((String) database.get("userlevel"));
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
		return (String) database.get("password");
	}
	
	public boolean remove() {
		/*
		 * remove will remove this User from the Users directory, as well as remove it from
		 * the hashtable.
		 * 
		 * Returns the success of the removal of the User (should never not succeed).
		 */
		String username;
		if (oldName == null) {
			username = getUsername();
		} else {
			username = oldName;
		}
		File userFile = new File(User.UserDatabaseLocation,username + ".user");
		boolean success = userFile.delete();
		if (! success) {
			System.err.println("Cannot delete " + username);
			return false;
		}
		Users.remove(username);
		oldName = null;
		return success;
	}
	
	public void save() {
		/*
		 * save will save this User to the Users directory, for future reloading.
		 */
		if (this.getUsername() == null) {
			System.err.println("Cannot save a User with no username.");
			return;
		}
		File dir = User.UserDatabaseLocation;
		if (oldName != null && ! oldName.equals(getUsername())) {
			File oldUserFile = new File(dir,oldName + ".user");
			if (oldUserFile.exists()) {
				oldUserFile.delete();
			} else {
				System.out.println("Warning: " + oldName + ".user user File does not exist?");
			}
		}
		File userFile = new File(dir,getUsername() + ".user");
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(userFile));
			Iterator<String> hashedKeys = this.database.keySet().iterator();
			String cur;
			while(hashedKeys.hasNext()) {
				cur = hashedKeys.next();
				out.write(cur + "=" + this.database.get(cur).replace("=", "\\="));
				out.newLine();
			
			}
			oldName = getUsername();
			out.close();
		} catch (IOException e) {
			System.err.println("Unable to open " + getUsername() + ".user for writing");
			e.printStackTrace();
			return;
		}
		
	}
	
	public boolean setUserName(String username) {
		if (username.equalsIgnoreCase(getUsername())) {
			return true;
		}
		if (User.getUserFromUserName(username) != null) {
			return false;
		}
		User.Users.remove(getUsername());
		User.Users.put(username,this);
		this.database.put("username", username);
		return true;
	}
	
	public void setUserPassword(String password) {
		this.database.put("password", password);
	}
	
	public void setUserLevel(String userLevel) {
		this.database.put("userlevel", userLevel);
	}
	
	public User(String username, String password, String userlevel) {
		this.database = new Hashtable<String, String>();
		this.database.put("username",username);
		this.database.put("password",password);
		this.database.put("userlevel",userlevel);
		User.Users.put(username, this);
		this.oldName = null;
	}

	public User() {oldName = null;}

	public static void loadUserList() {
		/*
		 * loadUserList loads all .user files in src/Main/Users/ , and parses them into User objects.
		 * Each user is stored in the User.Users Hashtable.
		 * User information (username/password/userlevel) is stored in the user.database hashtable.
		 * 
		 * loadUserList will also clear all users currently in the hashtable
		 */
		User.Users.clear();
		Hashtable<String,Hashtable<String,String>> directory_data = DatabaseParser.loadDatabase(User.UserDatabaseLocation,"user");
		Enumeration<String> user_data_enum = directory_data.keys(); 
		while (user_data_enum.hasMoreElements()) {
			String file = user_data_enum.nextElement();
			Hashtable<String,String> user_data = directory_data.get(file);
			if (! user_data.containsKey("username")) {
				System.out.println("File " + file + " has no username. Skipping that file.");
				continue;
			}
			User new_user = new User();
			new_user.database = user_data;
			new_user.oldName = user_data.get("username");
			User.Users.put(user_data.get("username"), new_user);
		}
	}
}
