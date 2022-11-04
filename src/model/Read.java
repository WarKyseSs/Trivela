package model;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Read {

	/**
	 * The method who read the json
	 */
	public static void readFile() {		
		try {
		    // Create a reader
			File file = new File("questions.json");
		    Reader reader = Files.newBufferedReader(file.toPath());
			
		    // Convert JSON array to list of Card
		    Deck d = new Gson().fromJson(reader, new TypeToken<Deck>() {}.getType());
		    
		    ConnectJson.setDeck(d);
		    
		    // Close the reader
			reader.close();
			
		} catch (Exception ex) {
		    ex.printStackTrace();
		}
	}
}
