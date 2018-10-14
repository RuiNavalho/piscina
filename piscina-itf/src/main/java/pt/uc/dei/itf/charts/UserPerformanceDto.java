package pt.uc.dei.itf.charts;

import java.util.Date;

public class UserPerformanceDto {
	
	private Date date;
	private int hoursWorked;
	private int hoursProduced;
	private float prodIndex;
	
	public UserPerformanceDto() {
	}
	
	public UserPerformanceDto(Date date, int hoursWorked, int hoursProduced, float prodIndex) {
		this.date = date;
		this.hoursWorked = hoursWorked;
		this.hoursProduced = hoursProduced;
		this.setProdIndex(prodIndex);
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	public int getHoursProduced() {
		return hoursProduced;
	}
	public void setHoursProduced(int hoursProduced) {
		this.hoursProduced = hoursProduced;
	}
	public float getProdIndex() {
		return prodIndex;
	}

	public void setProdIndex(float prodIndex) {
		this.prodIndex = prodIndex;
	}

}
