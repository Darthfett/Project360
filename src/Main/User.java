package Main;
import java.io.File;
import java.util.ArrayList;
import Main.Types.UserLevel;

public abstract class User {
	public String username;
	public String password;
	public UserLevel userLevel;
	
	public static ArrayList<String> loadUserList() {
		System.out.println("DEBUG: Attempting to load users");
		ArrayList<String> userList = new ArrayList<String>();
		File dir = new File("Users");
		String[] subdirectories = dir.list();
		if (subdirectories == null) {
			System.out.println("DEBUG: No existing users, or unable to find Users/*");
			return new ArrayList<String>();
		}
		for (int i=0; i < subdirectories.length; i++) {
			userList.add(subdirectories[i]);
		}
		return userList;
	}
}
