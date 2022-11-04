package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Write {

	/**
	 * The method who write in the json
	 * @param d - The deck we want to write in the json
	 */
	public static void writeJson(Deck d) {	
		
		// Declare a Gson
		Gson gson=new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		String json1 = gson.toJson(d);
		PrintWriter writer = null;	
		
		// Open the file
		try {
			try {
				writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream("questions.json"), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}	
		
		// Write the question and close de writer
		writer.println(json1);
		writer.close();
	}
}