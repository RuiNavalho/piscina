package pt.uc.dei.itf.charts;

public class BudgetDto {
	
	private String title;
	private float budget;
	
	public BudgetDto() {
	}

	public BudgetDto(String title, float budget) {
		this.title = title;
		this.budget = budget;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public float getBudget() {
		return budget;
	}
	public void setBudget(float budget) {
		this.budget = budget;
	}

}
