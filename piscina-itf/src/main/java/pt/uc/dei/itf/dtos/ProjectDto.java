package pt.uc.dei.itf.dtos;

import java.util.Date;

public class ProjectDto {
	
	private String title;
	private String idProject;
	private ClientLightDto clientLightDto;
	private Date beginDate;
	private Date endDate;
	private float budget;
	private String stage;
	private String managerName;
	private String managerEmail;
	private Double costPerformanceIndicator;
	private Double timePerformanceIndicator;
	private String description;
	private String typology;
	private Integer percentageExecuted;
	private int initialDurationHours;
	private int nowDurationHours;
	private int workedHours;

	
	public ProjectDto() {
	}

	public ProjectDto(String title, String idProject, ClientLightDto clientLightDto, Date beginDate, Date endDate,
			float budget, String stage, String managerName, String managerEmail, Double costPerformanceIndicator,
			Double timePerformanceIndicator, String description, String typology, 
			Integer percentageExecuted, int initialDurationHours, int nowDurationHours, int workedHours) {
		this.title = title;
		this.idProject = idProject;
		this.clientLightDto = clientLightDto;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.budget = budget;
		this.stage = stage;
		this.managerName = managerName;
		this.managerEmail = managerEmail;
		this.costPerformanceIndicator = costPerformanceIndicator;
		this.timePerformanceIndicator = timePerformanceIndicator;
		this.description=description;
		this.typology=typology;
		this.percentageExecuted=percentageExecuted;
		this.initialDurationHours=initialDurationHours;
		this.nowDurationHours=nowDurationHours;
		this.setWorkedHours(workedHours);
	}
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIdProject() {
		return idProject;
	}

	public void setIdProject(String idProject) {
		this.idProject = idProject;
	}

	public ClientLightDto getClientLightDto() {
		return clientLightDto;
	}

	public void setClientLightDto(ClientLightDto clientLightDto) {
		this.clientLightDto = clientLightDto;
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

	public float getBudget() {
		return budget;
	}

	public void setBudget(float budget) {
		this.budget = budget;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public Double getCostPerformanceIndicator() {
		return costPerformanceIndicator;
	}

	public void setCostPerformanceIndicator(Double costPerformanceIndicator) {
		this.costPerformanceIndicator = costPerformanceIndicator;
	}

	public Double getTimePerformanceIndicator() {
		return timePerformanceIndicator;
	}

	public void setTimePerformanceIndicator(Double timePerformanceIndicator) {
		this.timePerformanceIndicator = timePerformanceIndicator;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTypology() {
		return typology;
	}

	public void setTypology(String typology) {
		this.typology = typology;
	}

	public Integer getPercentageExecuted() {
		return percentageExecuted;
	}

	public void setPercentageExecuted(Integer percentageExecuted) {
		this.percentageExecuted = percentageExecuted;
	}

	public int getInitialDurationHours() {
		return initialDurationHours;
	}

	public void setInitialDurationHours(int initialDurationHours) {
		this.initialDurationHours = initialDurationHours;
	}

	public int getNowDurationHours() {
		return nowDurationHours;
	}

	public void setNowDurationHours(int nowDurationHours) {
		this.nowDurationHours = nowDurationHours;
	}

	public int getWorkedHours() {
		return workedHours;
	}

	public void setWorkedHours(int workedHours) {
		this.workedHours = workedHours;
	}


	
}
