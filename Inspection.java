package project2;

/**
 * Inspection class is used to create inspection that goes into the inspection list of a restaurant object. 
 * This class implements Comparable <Inspection> 
 * 
 * @author Heewon Kim
 *
 */

public class Inspection implements Comparable<Inspection>{
	
	private Date date;
	private int score;
	private String violation;
	private String risk; 
	
	/**
	 * Constructs a Inspection object with specified date, score, violation and risk. 
	 * @param date the date of inspection
	 * @param score integer value of score of inspection
	 * @param violation string violation description
	 * @param risk string risk of inspection  
	 * @throws IllegalArgumentException if parameters are invalid 
	 */
	
	public Inspection (Date date, int score, String violation, String risk) throws IllegalArgumentException {
		if (date==null)
			throw new IllegalArgumentException();
		if (score<0 || score>100)
			throw new IllegalArgumentException();
		setDate(date);
		setScore(score);
		if (violation==null){
			violation="";	
			setViolation(violation);}
		if (risk==null){
			risk=""; 
			setRisk(risk);}
	}

	//prints out the inspection 
	@Override
	public String toString() {
		String str="";
		str+=this.date + ",";
		str+=this.score+",";
		if (violation.equals(""))
			str+=this.risk;
		else
			str+=this.violation+""+this.risk;
		return str;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public String getViolation() {
		return violation;
	}


	public void setViolation(String violation) {
		this.violation = violation;
	}


	public String getRisk() {
		return risk;
	}


	public void setRisk(String risk) {
		this.risk = risk;
	}

	/**
	 * Compare dates of inspection 
	 * @returns positive number if the the date of inspection of this object is 
	 * later than the date of inspection of the other object that is being compared,
	 *  negative if opposite, O if it is them same date. 
	 */
	@Override 
	public int compareTo(Inspection o) {
		
		if (this.getDate().compareTo(o.getDate())==0)
			return 0;
		else if (this.getDate().compareTo(o.getDate())>0)
			return 1;
		else 
			return -1;

	}
}
