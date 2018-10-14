package pt.uc.dei.itf.charts;

import java.util.Date;
import java.util.List;

public class ChartTimePercHoursCost {
	
	private String title;
	private List<HourPercCostDto> list;
	private Date begin;
	private Date end;
	private Float budget;
	private int estimateDurationInHours;
	
	public ChartTimePercHoursCost() {
	}
	
	public ChartTimePercHoursCost(String title, List<HourPercCostDto> list, Date begin, Date end, Float budget,
			int estimateDurationInHours) {
		this.title = title;
		this.list = list;
		this.begin = begin;
		this.end = end;
		this.budget = budget;
		this.estimateDurationInHours = estimateDurationInHours;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<HourPercCostDto> getList() {
		return list;
	}
	public void setList(List<HourPercCostDto> list) {
		this.list = list;
	}
	public Date getBegin() {
		return begin;
	}
	public void setBegin(Date begin) {
		this.begin = begin;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public Float getBudget() {
		return budget;
	}
	public void setBudget(Float budget) {
		this.budget = budget;
	}
	public int getEstimateDurationInHours() {
		return estimateDurationInHours;
	}
	public void setEstimateDurationInHours(int estimateDurationInHours) {
		this.estimateDurationInHours = estimateDurationInHours;
	}

}
