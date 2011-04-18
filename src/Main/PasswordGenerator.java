package Main;

import java.util.Random;

public class PasswordGenerator {
	private static String allowedChars = new String("abdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789");
	
	public static String createPassword() {
		System.out.println("Create a freakin' password!:");
		Integer length = new Random().nextInt(4) + 8;
		String password = "";
		Integer random;
		for (int i = 0; i < length; i++) {
			random = new Random().nextInt(PasswordGenerator.allowedChars.length());
			String next = allowedChars.substring(random,random+1);
			password += next;
		}
		System.out.println(password);
		return password;
	}

}
