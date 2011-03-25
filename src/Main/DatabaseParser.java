package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class DatabaseParser {


	public static Hashtable<String,Hashtable<String,String>> loadDatabase(File directory, String extension){
		/*
		 * loadDatabase loads all .'extension' files in directory , and parses them into a Hashtable
		 * of file names pointing to a Hashtable with 'username'/'password'/'userlevel' pointing to their
		 * actual username/password/userlevel
		 * 
		 */
		Hashtable<String,Hashtable<String,String>> database = new Hashtable<String,Hashtable<String,String>>();
		File current_file;
		BufferedReader reader;
		String[] all_files = directory.list();
		if (all_files == null) {
			// directory is not a directory
			return database;
		}
		ArrayList<String> files = new ArrayList<String>();
		for (int i = 0; i < all_files.length; i++) {
			int extension_position = all_files[i].lastIndexOf('.');
			if (extension_position != -1) {
				if (all_files[i].substring(extension_position+1).equalsIgnoreCase(extension)) {
					files.add(all_files[i]);
				}
			}
		}
		for (int i = 0; i < files.size(); i++) {
			current_file = new File(directory, files.get(i));
			try { //Try to open the file
				reader = new BufferedReader(new FileReader(current_file));
			} catch (FileNotFoundException ex) {
				System.out.println("Unable to open " + files.get(i));
				continue;
			}
			Hashtable<String,String> current_file_data = new Hashtable<String,String>();
			try { //Try to read the data from the file
				String line = reader.readLine();
				//Lines -> Arrays split by '='
				while (line != null) {
					ArrayList<String> keyVal = new ArrayList<String>();
					String[] temp = line.replace(" ", "").replace("\n", "").toLowerCase().split("=");
					for (int j = 0; j < temp.length; j++) {
						keyVal.add(temp[j]);
					}
					
					if (keyVal.size() == 1) {
						keyVal.add("");
					} else if (keyVal.size() != 2) {
						for(int j=0; j < keyVal.size();j++) {
							System.out.print(keyVal.get(j) +",");
						}
						continue;
					}
					current_file_data.put(keyVal.get(0), keyVal.get(1));
					line = reader.readLine();

				}
			
			} catch (IOException ex) {
				System.out.println("DEBUG: Unable to read from " + files.get(i));
				continue;
			}
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			database.put(files.get(i), current_file_data);
			
		}
		return database;
	}
}
