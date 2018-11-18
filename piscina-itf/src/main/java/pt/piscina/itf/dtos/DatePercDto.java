package pt.piscina.itf.dtos;

import java.util.Date;

public class DatePercDto {
	private Date date;
	private Float percentage;
	
	public DatePercDto() {
	}
	
	public DatePercDto(Date date, Float percentage) {
		this.date = date;
		this.percentage = percentage;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Float getPercentage() {
		return percentage;
	}
	public void setPercentage(Float percentage) {
		this.percentage = percentage;
	}

}
