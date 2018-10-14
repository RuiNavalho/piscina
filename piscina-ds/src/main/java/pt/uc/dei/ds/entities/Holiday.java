package pt.uc.dei.ds.entities;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * The persistent class for the business database table.
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name="Holiday.findAll", query="SELECT h FROM Holiday h"),
	@NamedQuery(name="Holiday.findHolidaysAfterDate", query="SELECT h FROM Holiday h WHERE h.day>=:beginDate"),
	@NamedQuery(name="Holiday.findHolidayByDate", query="SELECT h FROM Holiday h WHERE h.day=:day"),
	@NamedQuery(name="Holiday.findHolidaysBetweenDates", query="SELECT h FROM Holiday h WHERE h.day>=:beginDate AND h.day<=:endDate"),
})
public class Holiday implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String holidayname;
	
	private Timestamp day;

	public Holiday() {
	}
	
	public Holiday(String holidayname, Timestamp day) {
		this.holidayname = holidayname;
		this.day = day;
	}


	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHolidayname() {
		return holidayname;
	}

	public void setHolidayname(String holidayname) {
		this.holidayname = holidayname;
	}

	public Timestamp getDay() {
		return day;
	}

	public void setDay(Timestamp day) {
		this.day = day;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
