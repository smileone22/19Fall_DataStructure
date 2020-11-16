package project2;
import java.io.File;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.*;


public class SFRestaurantData {
	
	/**
	 * This class is the program allows user to choose from two queries on restaurants.
	 * The program is interactive. 
	 * It contains main method and is responsible for opening, reading data files, obtaining user input, performing data validation.
	 * In the interactive part, the user enters name Keyword or zip keyword of a restaurant. The program 
	 * responds by printing the representations of restaurant that matches the user input. 
	 * 
	 * @author Heewon Kim
	 *
	 */
	public static void main(String args[]){
		// using command line arugmetns
		File scoreFile = new File(args[0]);         
		if (!scoreFile.canRead()){
			System.err.println("Error: the file "+scoreFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		//open the file for reading 
		Scanner inRestaurant = null; 
		
		try {
			inRestaurant = new Scanner (scoreFile ) ;
		} catch (FileNotFoundException e) {
			System.err.println("Error: the file "+scoreFile.getAbsolutePath()+
											" cannot be opened for reading.\n");
			System.exit(1);
		}
		
		String line= null;
		ArrayList<String> current = null;  
		Restaurant currentRestaurant=null;
		RestaurantList list = new RestaurantList (); 
		Date currentDate;
		Inspection currentInspection;
		int index;
		// creating a list of restaurants by assessing every lines in the data set. 
		while (inRestaurant.hasNextLine()) {
			try { 
				line = inRestaurant.nextLine(); 
				
			}
			catch (NoSuchElementException ex ) {
				//caused by an incomplete or miss-formatted line in the input file
				System.err.println(line);
				continue; 	
			}
			try {
				current=splitCSVLine(line);
				if (current.get(1).isEmpty() || current.get(5).isEmpty() || current.get(11).isEmpty() )
					continue;
				//create a restaurant and check if a restaurant is already in the list
				currentRestaurant = new Restaurant (current.get(1),current.get(5),current.get(2),current.get(9));
				
				if (list.contains(currentRestaurant)){
					//just add inspection to the restaurant that already exists in the list of restaurants.  
					index=list.indexOf(currentRestaurant);
					currentDate = new Date(current.get(11));
					currentInspection = new Inspection (currentDate,Integer.parseInt(current.get(12)),current.get(15),current.get(16));
					list.get(index).addInspection(currentInspection);
					continue;
				}
				//adding a new restaurant object to the list of restaurants.
				list.add(currentRestaurant);
				
					
			}
			catch (IllegalArgumentException ex ) {
				//ignore this exception and skip to the next line 
			}
		}
		
		
		//Allow user to issue different queries
		System.out.println(list.size());
		Scanner userInput  = new Scanner (System.in); 
		String userValue = "";
		//printing out the first format of the query
		System.out.println("Search the database by matching keywords to titles or actor names.");
			System.out.println("\t To search for matching restaurant names, enter ");
			System.out.println("\t\t name KEYWORD");
			System.out.println("\t To search for restaurants in a zip code, enter ");
			System.out.println("\t\t zip KEYWORD");
			System.out.println("\t To finish the program, enter ");
			System.out.println("\t\t quit");
		
		do{ 
			System.out.println("\n"+"Enter your search query: ");
			userValue = userInput.nextLine();
			if (!userValue.equalsIgnoreCase("quit")){
				RestaurantList r=new RestaurantList();//to keep matching restaurants
				try{
					//user input name query
					if (userValue.substring(0,4).toLowerCase().equals("name")){
						String keyword=userValue.substring(5);
						r=list.getMatchingRestaurants(keyword);
						if (r==null){
							System.out.println("No matches found. Try again");
							continue;
						}
						for (int i=0;i<r.size();i++){
							System.out.println(r.get(i).toString());
						}
					}
					//user input zip query
					else if (userValue.substring(0,3).toLowerCase().equals("zip")){
							String keyword=userValue.substring(4);
							r=list.getMatchingZip(keyword);
							if (r==null){
								System.out.println("No matches found. Try again");
								continue;
							}
							for (int i=0;i<r.size();i++){
								System.out.println(r.get(i).toString());
							}
					}
					} catch(IllegalArgumentException ex ) {
					System.out.println("Error: This is not a valid query. Try again"); 
					continue;
				}
					
				
			}
		} while (!userValue.equalsIgnoreCase("quit"));
		userInput.close();
	}
	
	
	
	 /**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries so that they may contain commas)
	 * @author Joanna Klukowska
	 * @param textLine a line of text to be passed
	 * @return an Arraylist object containing all individual entries found on that line
	 */	
	public static ArrayList<String> splitCSVLine(String textLine){
		if (textLine == null ) return null;
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;
		
		// iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			// handle smart quotes as well as regular quotes
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') {
				// change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false;
					}
				else {
					insideQuotes = true;
					insideEntry = true;
					}}
			else if (Character.isWhitespace(nextChar)) {
				if ( insideQuotes || insideEntry ) {
				// add it to the current entry
					nextWord.append( nextChar );}
				else { // skip all spaces between entries
					continue;}} 
			else if ( nextChar == ',') {
				if (insideQuotes){ // comma inside an entry
					nextWord.append(nextChar);}
				else { // end of entry found
					insideEntry = false;
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();}
			}
			else {// add all other characters to the nextWord
				nextWord.append(nextChar);
				insideEntry = true;}
			}
		// add the last word ( assuming not empty )
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}
		return entries;
	}
		
		
		
}
	


