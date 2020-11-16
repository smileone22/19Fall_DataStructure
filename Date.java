package project2;
/**
 * This class represents a date. 
 * It uses two equivalent representations: 
 * It implements Comparable <Date>
 * String date and integers that constitute a single string
 * 
 * @author Heewon Kim
 *
 */

public class Date implements Comparable<Date> {
	private String date; 
	
	/**
	 * Constructs a new date object with specified date. 
	 * @param date to be used 
	 * @throws IllegalArgumentException  if date is invalid 
	 */
	
	public Date (String date) throws IllegalArgumentException {
		if (date==null)
			throw new IllegalArgumentException();
		if (validateDateFormat(date))
			throw new IllegalArgumentException();
		int day= getDay(date);
		int month= getMonth(date);
		int year = getYear (date);
		if (validateDate(month, day, year)==false)
			throw new IllegalArgumentException();
		
		setDate(date);
	}
	
	/**
	 * Constructs a new date object with specified month, day, and year. 
	 * @param month to be used for this date
	 * @param day to be used for this date
	 * @param year to be used for this date
	 * @throws IllegalArgumentException if the value of date is invalid 
	 */
	
	public Date (int month, int day, int year) throws IllegalArgumentException {
		if (validateDate(month,day,year)==false)
			throw new IllegalArgumentException();

		setDate(month+"/"+day+"/"+year);
	}
	
	
	/**
	 * Validates the date
	 * @param month to be used for this date
	 * @param day to be used for this date
	 * @param year to be used for this date
	 * @return true if the values of month, day, year are valid, false if it is not valid. 
	 */
	public boolean validateDate(int month, int day, int year)  {
		if (month < 1 || month > 12 )
			return false;
		//validate year
		if (year < 2000 || year > 2025 )
			return false;
		//validate values for depending on month and year
		if ( day<0)
			return false;
		else if ( year%4==0 && month==2)
			if ( day>29)
				return false;
			else 
				return true;
		else if (month==2 && day>28)
			return false;
		else if(month ==1||month==3||month==5||month==7||month==8||month==10||month==12)
			if (day>31)
				return false;
			else 
				return true;
		else if (month==4||month==6||month==9||month==11)
			if (day>30)
				return false;
			else
				return true;
		else
			return true;
	}
	
	/**
	 * Validates the format of the date
	 * @param string form of date 
	 * @return true if the date is in valid format mm/dd/yy or mm/dd/yyyy, false if it is not in a valid format. 
	 */
	
	public boolean validateDateFormat(String date){
		//check if / is in right position 
		if (date.charAt(2)!='/' && date.charAt(5)!='/')
			return false;
		String month= date.substring(0, 2);
		String day=date.substring(3,5);
		String year=date.substring(6);
		//check length of year 
		if (year.length()!=2 ||year.length()!=4)
			return false;
		//check if mm,dd,yy are all integers
		try {
			Integer.parseInt(month);
			Integer.parseInt(day);
			Integer.parseInt(year);
		}catch(Exception e){
			return false;
		}
		return true; 
	}
	
	/**
	 * extracts month from the String date
	 * @param String date 
	 * @returns integer value of month 
	 */
	public int getMonth(String date){
		String month=date.substring(0, 2);
		return Integer.parseInt(month); 
	}
	
	/**
	 * extracts day from the String date
	 * @param String date 
	 * @returns integer value of day 
	 */
	public int getDay(String date){
		String day=date.substring(3, 5);
		return Integer.parseInt(day); 
	}
	
	/**
	 * extracts year from the String date
	 * @param String date 
	 * @returns integer value of year
	 */
	public int getYear(String date){
		String year=date.substring(6);
		return Integer.parseInt(year); 
	}

	
    //getter
	public String getDate() {
		return date;
	}
	
	//setter 
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * Compare dates
	 * @param Date object o 
	 * @returns positive number if the the date of this object is later than 
	 * the the date of the object that is being compared, negative if opposite,
	 * O if it is them same date. 
	 */
	@Override
	public int compareTo(Date o) {		
		if (this.date.equals(o.date))
			return 0;
		else if (this.getYear(this.date)<o.getYear(o.date))
			return -1;
		else if (this.getYear(this.date)>o.getYear(o.date))
			return 1; 
		else if (this.getYear(this.date)==o.getYear(o.date)){
			 	//when the year, month are equal
				if (this.getMonth(this.date)==o.getMonth(o.date)){
					if (this.getDay(this.date)<o.getDay(o.date))
						return -1;
					else if (this.getDay(this.date)>o.getDay(o.date))
						return 1;}
				//when this month is later
				if (this.getMonth(this.date)>o.getMonth(o.date))
					return 1;
				//when this month is earlier
				else
					return -1;
				}
		return 0;
		}
		
	

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Date other = (Date) obj;
		if (this.date == null) {
			if (other.date != null)
				return false;
		} else if (!this.date.equals(other.date))
			return false;
		return true;
	}

	/**
	 * Returns the string representation of this date.
	 * @returns the string representation of this date object 
	 */
	@Override
	public String toString() {
		return getMonth(date)+"/"+getDay(date)+"/"+getYear(date);
	}
	

	
	
	
}
