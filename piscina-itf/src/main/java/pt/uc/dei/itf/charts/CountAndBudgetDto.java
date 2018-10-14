package pt.uc.dei.itf.charts;

import java.util.Date;

public class CountAndBudgetDto {

	private Date date;
	private float budget;
	private int projCount;
	
	public CountAndBudgetDto() {
	}

	public CountAndBudgetDto(Date date, float budget, int projCount) {
		this.date = date;
		this.budget = budget;
		this.projCount = projCount;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getBudget() {
		return budget;
	}
	public void setBudget(float budget) {
		this.budget = budget;
	}
	public int getProjCount() {
		return projCount;
	}
	public void setProjCount(int projCount) {
		this.projCount = projCount;
	}

}
