package pramati.wc.datatypes;

public class MonthAndLinkDatatype {
	String mnthYear;
	String hyprlynk;
	
	public MonthAndLinkDatatype() {}
	
	/**
	 * 
	 * @param mnthYear
	 * @param hyprlynk
	 */
	public MonthAndLinkDatatype(String mnthYear,String hyprlynk){
		this.mnthYear=mnthYear.trim();
		this.hyprlynk=hyprlynk.trim();
	}

	public String getMnthYear() {
		return mnthYear;
	}

	public void setMnthYear(String mnthYear) {
		this.mnthYear = mnthYear;
	}

	public String getHyprlynk() {
		return hyprlynk;
	}

	public void setHyprlynk(String hyprlynk) {
		this.hyprlynk = hyprlynk;
	}
	
	
}
