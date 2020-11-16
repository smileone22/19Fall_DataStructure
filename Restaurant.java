package project2;
import java.util.*;
import java.lang.Object;



/**
 * This class represents a restaurant. 
 * This class stores the restaurant's name, zip, phone number, adress as a representation.
 * The restaurant also has an inspection list that has the list of inspection.
 * This class implements Comparable <Restaurant>
 * @author Heewon Kim
 *
 */

public class Restaurant implements Comparable<Restaurant>{
	private String name;
	private String zip;
	private String phone="";
	private String adress="";
	private ArrayList<Inspection> inspectionList = new ArrayList<>();
	
	/**
	 * Constructs a restaurant object with specified name and zip number. 
	 * @param name the name of the restaurant
	 * @param zip the postal code of the restaurant
	 * @throws IllegalArgumentException if parameters are invalid 
	 */
	public Restaurant(String name, String zip)throws IllegalArgumentException {
		setName(name);
		setZip(zip);
	}
	
	/**
	 * Constructs a restaurant object with specified name,zip, adress, and phone number. 
	 * @param name the name of the restaurant
	 * @param zip the postal code of the restaurant
	 * @param adress the adress of the restaurant
	 * @param phone the restaurant's phone number
	 * @throws IllegalArgumentException if parameters are invalid 
	 */
	public Restaurant (String name, String zip, String adress, String phone )throws IllegalArgumentException {
		setName(name);
		setZip(zip);
		setAdress(adress);
		setPhone(phone);
		
	}
	
	/**
	 * Adds a given inspection to the list of inspections for the current restaurant object. 
	 * @param name the name of the restaurant 
	 * @throws IllegalArgumentException if the parameter is null
	 */
	
	public void addInspection (Inspection inspect)throws IllegalArgumentException {
		if (inspect==null)
			throw new IllegalArgumentException();
		this.inspectionList.add(inspect);
		
	}
	
	/**
	 * The restaurant and the object is equal if it is a restaurant object, has the same name, 
	 * and has the same zip code.
	 * @param Object being compared 
	 * @return boolean true if it is equal, false if the object and this restaurant is not equal. 
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Restaurant))
			return false;
		Restaurant other = (Restaurant) obj;
		if (this.getName().equalsIgnoreCase(other.getName())){
			if (this.getZip().equals(other.getZip())) 
				return true;
			else
				return false;
		}
		return false;
		
	}
	
	/**
	 * Compares restaurant based on name of the business as the primary key and
	 * the zip code as a secondary key. 
	 * @param Restaurant object being compared 
	 * @return positive number if name and zip code are in alphabetical ,smaller to larger
	 * order, negative if opposite, zero if they are same. 
	 */
	@Override
	public int compareTo(Restaurant o) {
		// TODO Auto-generated method stub
			if (this.getName().equalsIgnoreCase(o.getName())){
				if (Integer.parseInt(this.getZip())==Integer.parseInt(getZip()))
					return 0;
				else if (Integer.parseInt(this.getZip()) < Integer.parseInt(o.getZip()))
					return -1;
				else
					return 1;
			}
			else if (this.getName().compareToIgnoreCase(o.getName())<0 )
				return -1;
			else 
				return 1;

	}
	

	
	//prints out all the representation of a restaurant in a given format 
	@Override
	public String toString() {
		String str = name + "\n";
		str+="---------------------------------------------"+ "\n";
		str+=String.format("adress%40s:%s","address" , adress)+ "\n";
		str+=String.format("zip%40s:%s","zip" , zip)+ "\n";
		str+=String.format("phone%40s:%s","phone" , phone)+ "\n";
		str+="recent inspection results :"+ "\n";
		// if this restaurant's inspection list has a most recent and second recent date 
		if (this.getRecent()!=null && this.getSecondRecent()!=null){
			for(int i=0;i<this.inspectionList.size();i++){
				if (this.inspectionList.get(i).getDate().equals(this.getRecent()))
					//adding inspections that match the most recent date
					str+=this.inspectionList.get(i).toString();}
			for(int i=0;i<this.inspectionList.size();i++){
				if (this.inspectionList.get(i).getDate().equals(this.getSecondRecent()))
					//adding inspections that match the second recent date
					str+=this.inspectionList.get(i).toString();
			}
		}
		// restaurant's inspection list has a most recent but not the second recent date 
		if (this.getRecent()!=null && this.getSecondRecent()==null){
			for(int i=0;i<this.inspectionList.size();i++){
				if (this.inspectionList.get(i).getDate().equals(this.getRecent()))
					//adding inspections that matches the only recent date
					str+=this.inspectionList.get(i).toString();
			}
		}
		// restaurant's inspection list does not have a most recent and the second recent date 
		if (this.getRecent()==null && this.getSecondRecent()==null)
			str+="";
		return str;
	}
	
	/**
	 * Set restaurant's valid address
	 * @throws IllegalArgumentException if any address is null or empty
	 */
	public void setAdress(String adress)throws IllegalArgumentException {
		if ( adress==null || adress=="")
			throw new IllegalArgumentException();
		this.adress = adress;
	}

	/**
	 * Set restaurant's valid phone
	 * @throws IllegalArgumentException if any phone number is null or empty
	 */
	public void setPhone(String phone)throws IllegalArgumentException {
		if (phone==null || phone =="")
			throw new IllegalArgumentException();
		this.phone = phone;
	}
	
	/**
	 * Set restaurant's valid name
	 * @throws IllegalArgumentException the name is empty
	 */
	public void setName(String name)throws IllegalArgumentException {
		if(name=="")
			throw new IllegalArgumentException();
		else
			this.name = name;
	}
	
	/**
	 * Set restaurant's valid zipcode
	 * @throws IllegalArgumentException if it is not a five long digit.
	 */
	public void setZip(String zip)throws IllegalArgumentException {
		if (zip.length()!=5){
			throw new IllegalArgumentException();}
		for (int i=0;i<zip.length();i++){
			char pos = zip.charAt(i);
			if (Character.isDigit(pos)==false)
				throw new IllegalArgumentException();
		}
		this.zip = zip;
	}
	
	
	/**
	 * Returns the most recent date of in the restaurant object's inspection list. 
	 * @return date the most recent date  
	 */
	public Date getRecent(){
		Date recent=null;
		for (int i=1;i<inspectionList.size();i++){
			if (inspectionList.get(i).getDate().compareTo(inspectionList.get(i-1).getDate())>0)
				recent=inspectionList.get(i).getDate();
		 }
		return recent;
	}
	
	/**
	 * Returns the second recent date of in the restaurant object's inspection list. 
	 * @return date second recent date  
	 */
	public Date getSecondRecent(){
		Date Secondrecent=null;
		for (int i=1;i<inspectionList.size();i++){
			if (inspectionList.get(i).getDate().compareTo(inspectionList.get(i-1).getDate())>0&&inspectionList.get(i).getDate().compareTo(this.getRecent())<0 )
				Secondrecent=inspectionList.get(i).getDate();
		 }
		return Secondrecent;
	}

	
	public ArrayList<Inspection> getInspectionList() {
		return inspectionList;
	}

	public void setInspectionList(ArrayList<Inspection> inspectionList) {
		this.inspectionList = inspectionList;
	}
	

	public String getName() {
		return name;
	}

	public String getZip() {
		return zip;
	}

	public String getPhone() {
		return phone;
	}

	public String getAdress() {
		return adress;
	}
	
	
	
	
}

	
