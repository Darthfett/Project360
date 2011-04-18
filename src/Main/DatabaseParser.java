package Main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

public class DatabaseParser {

	
	public static String strip(String toStrip) {
		/*
		 * Strips all spaces from the beginning and end of the string
		 */
		int start;
		int end;
		for (start = 0; start < toStrip.length(); start++) {
			if (toStrip.charAt(start) != ' ' && toStrip.charAt(start) != '\n') {
				break;
			}
		}
		for (end = toStrip.length()-1; end > -1; end--) {
			if (toStrip.charAt(end) != ' ' && toStrip.charAt(end) != '\n') {
				break;
			}
		}
		return toStrip.substring(start,end+1);
	}

	public static Hashtable<String,Hashtable<String,String>> loadDatabase(File directory, String extension){
		/*
		 * loadDatabase loads all .'extension' files in directory , and parses them into a Hashtable
		 * of file names pointing to a Hashtable with keys pointing to their value.
		 * 
		 * The file format:
		 * lines with key mapping to value:
		 * 		key1=value
		 * 		key2=value
		 * 		More of key2 value
		 * 		key3=anothervalue
		 * 
		 * This file would map 
		 * "key1"=>"value", 
		 * "key2"=>"value\nMore of key2 value", 
		 * "key3"=>"anothervalue"
		 * 
		 * Note that multi-line values ARE supported, but the file must Escape any '=' signs.
		 * This can be done by replacing all '=' symbols with "\\=" 
		 * 		(here the second backslash escapes the first)
		 * 
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
			String key = "";
			String value = "";
			try { //Try to read the data from the file
				String line = reader.readLine();
				while (line != null) {
					if (line.contains("\\=")) {
						String[] temp = line.split("\\=");
						if (temp[0].contains("=")) {
							if (! key.equals("")) {
								current_file_data.put(key,value);
								System.out.println("put " + key + ": " + value);
								key = "";
								value = "";
							}
							String[] keyVals = line.replace("\\=","=").split("=");
							if (keyVals.length > 2) {
								String val = "";
								for (int j = 1; j < keyVals.length; j++) {
									val += keyVals[j];
								}
								keyVals = new String[]{keyVals[0],val};
							}
							key = keyVals[0].replace(" ","");
							if (keyVals.length > 1) {
								value = keyVals[1];								
							} else {
								value = "";
							}
							//keyVals is now length 2
						} else {
							value += "\n" + line.replace("\\=","=");
						}
					} else {
					
						if (line.contains("=")) {
							if (! key.equals("")) {
								current_file_data.put(key,value);
								key = "";
								value = "";
							}
							String[] keyVals = line.split("=");
							if (keyVals.length > 2) {
								String val = "";
								for (int j = 1; j < keyVals.length; j++) {
									val += keyVals[j];
								}
								keyVals = new String[]{keyVals[0],val};
							}
							key = keyVals[0].replace(" ","");
							if (keyVals.length > 1) {
								value = keyVals[1];								
							} else {
								value = "";
							}
							//keyVals is now length 2
						} else {
							value += "\n" + line;
						}
					}
					line = reader.readLine();
				}	
				if (! key.equals("")) {
					current_file_data.put(key,value);
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
