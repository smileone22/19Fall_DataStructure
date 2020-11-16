package project2;
import java.util.*;


	/**
	 * RestaurantList class is used to store a collection of Restaurant objects. 
	 * This class inherits all of its properties from an ArrayList<Restaurant>. It 
	 * adds Restaurant-specific functions that allow search by Restaurant name
	 * and by zip code. 
	 * @author Heewon Kim
	 *
	 */
public class RestaurantList extends ArrayList<Restaurant>{
	
	
	//default constructor 
	public RestaurantList (){
		super();
	}
	
	/**
	 * Search through the list of restaurants for an object that contains
	 * the keyword as a restaurant name.
	 * @param string keyword from the user input
	 * @return a list of restaurant objects whose names contain keyword as a substring ( case insensitive)
	 */
	public RestaurantList getMatchingRestaurants (String keyword) {
		RestaurantList matchingOnes= new RestaurantList();  
		if (keyword==null || keyword =="")
			return null;
		
		for (Restaurant r : this ){
			String name = r.getName().toLowerCase();
			if (name.contains(keyword.toLowerCase()))
				matchingOnes.add(r);
			else
				continue;
		}
		if (matchingOnes.isEmpty())
			return null;
		else
			Collections.sort(matchingOnes);
			return matchingOnes;
		}
	
	/**
	 *  Search through the list of restaurants for an object matching 
	 * the given zip code.
	 * @param string keyword of zip from the user input
	 * @return a list of restaurant objects whose postal code equals keyword
	 */
	public RestaurantList getMatchingZip (String keyword){
		RestaurantList matchingOnes= new RestaurantList();  
		if (keyword==null || keyword =="")
			return null;
		for (Restaurant r : this ){
			String zip = r.getZip();
			if (zip.equals( keyword ))
				matchingOnes.add(r);
			else
				continue;
		}
		if (matchingOnes.isEmpty())
			return null;
		Collections.sort(matchingOnes);
		return matchingOnes;
	}
	
	/**
	 * Prints out the list of names of the restaurant objects stored in the list
	 * @return a String containing a semi-colong and space separating list of the names
	 * of the Restaurant objects stored. 
	 */
	@Override
	public String toString() {
		String str="";
		for (int i=0;i<this.size();i++){
			str+=(this.get(i).getName().toString()+"; ");
		}
		return str;
	}
}