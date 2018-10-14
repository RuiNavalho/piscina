package pt.uc.dei.itf.charts;

import java.util.Date;

public class HourPercCostDto {
	
	private Date date;
	private float percExec;
	private float cost;
	private int hoursWorked;
	
	public HourPercCostDto() {
	}
	
	public HourPercCostDto(Date date, float percExec, float cost, int hoursWorked) {
		this.date = date;
		this.percExec = percExec;
		this.cost = cost;
		this.hoursWorked = hoursWorked;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getPercExec() {
		return percExec;
	}
	public void setPercExec(float percExec) {
		this.percExec = percExec;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	public int getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}

}
