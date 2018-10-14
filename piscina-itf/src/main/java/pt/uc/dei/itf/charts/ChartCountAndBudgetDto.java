package pt.uc.dei.itf.charts;

import java.util.Date;
import java.util.List;

public class ChartCountAndBudgetDto {
	
	private String title;
	private Date beginDate;
	private Date endDate;
	private List<CountAndBudgetDto> list;
	
	private int projCountOthers;
	private float budgetOthers;
	private float budgetThis;
	private int projCountThis;
	
	public ChartCountAndBudgetDto() {
	}
	
	public ChartCountAndBudgetDto(String title, Date beginDate, Date endDate, List<CountAndBudgetDto> list) {
		this.title = title;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.list = list;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public List<CountAndBudgetDto> getList() {
		return list;
	}
	public void setList(List<CountAndBudgetDto> list) {
		this.list = list;
	}

	public int getProjCountOthers() {
		return projCountOthers;
	}

	public void setProjCountOthers(int projCountOthers) {
		this.projCountOthers = projCountOthers;
	}

	public float getBudgetOthers() {
		return budgetOthers;
	}

	public void setBudgetOthers(float budgetOthers) {
		this.budgetOthers = budgetOthers;
	}

	public float getBudgetThis() {
		return budgetThis;
	}

	public void setBudgetThis(float budgetThis) {
		this.budgetThis = budgetThis;
	}

	public int getProjCountThis() {
		return projCountThis;
	}

	public void setProjCountThis(int projCountThis) {
		this.projCountThis = projCountThis;
	}

}
