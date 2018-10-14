package pt.uc.dei.itf.dtos;

public class BusinessStatsDto {
	
	private String area;
	private int totalNumberOfProjects;
	private int activeNumberOfProjects;
	private float totalBudget;
	private float activeTotalBudget;
	
	public BusinessStatsDto() {
	}
	
	public BusinessStatsDto(String area, int totalNumberOfProjects, int activeNumberOfProjects, float totalBudget,
			float activeTotalBudget) {
		this.area = area;
		this.totalNumberOfProjects = totalNumberOfProjects;
		this.activeNumberOfProjects = activeNumberOfProjects;
		this.totalBudget = totalBudget;
		this.activeTotalBudget = activeTotalBudget;
	}
	
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getTotalNumberOfProjects() {
		return totalNumberOfProjects;
	}
	public void setTotalNumberOfProjects(int totalNumberOfProjects) {
		this.totalNumberOfProjects = totalNumberOfProjects;
	}
	public int getActiveNumberOfProjects() {
		return activeNumberOfProjects;
	}
	public void setActiveNumberOfProjects(int activeNumberOfProjects) {
		this.activeNumberOfProjects = activeNumberOfProjects;
	}
	public float getTotalBudget() {
		return totalBudget;
	}
	public void setTotalBudget(float totalBudget) {
		this.totalBudget = totalBudget;
	}
	public float getActiveTotalBudget() {
		return activeTotalBudget;
	}
	public void setActiveTotalBudget(float activeTotalBudget) {
		this.activeTotalBudget = activeTotalBudget;
	}

}
