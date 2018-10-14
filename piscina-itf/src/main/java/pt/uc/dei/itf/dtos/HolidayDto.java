package pt.uc.dei.itf.dtos;

import java.sql.Timestamp;
import java.util.Date;

public class HolidayDto {
	
	private int id;
	private String holidayname;
	private Date day;
	
	public HolidayDto() {
	}
	
	public HolidayDto(String holidayname, Date day) {
		this.holidayname = holidayname;
		this.day = day;
	}
	
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
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
}
