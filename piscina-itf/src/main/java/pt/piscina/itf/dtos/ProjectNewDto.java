package pt.piscina.itf.dtos;

import java.sql.Timestamp;

public class ProjectNewDto {

	private String title;
	private String idProject;
	private String description;
	private String clientCompanyName;
	private Timestamp beginDate;
	private Timestamp endDate;
	private float budget;
	private String stage;
	private String managerEmail;
	private String creatorEmail;
	private String tipology;

	public ProjectNewDto() {		
	}

	//TODO remover String creatorEmail uma vez que esse campo vai no token
	public ProjectNewDto(String title, String idProject, String description, String clientCompanyName,
			Timestamp beginDate, Timestamp endDate, float budget, String stage, String creatorEmail, String managerEmail, String typology) {
		this.title = title;
		this.idProject = idProject;
		this.description = description;
		this.clientCompanyName = clientCompanyName;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.budget = budget;
		this.stage = stage;
		this.creatorEmail=creatorEmail;
		this.tipology = typology;
		this.managerEmail=managerEmail;

	}
	
	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClientCompanyName() {
		return clientCompanyName;
	}

	public void setClientCompanyName(String clientCompanyName) {
		this.clientCompanyName = clientCompanyName;
	}

	public Timestamp getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(Timestamp beginDate) {
		this.beginDate = beginDate;
	}

	public Timestamp getEndDate() {
		return endDate;
	}

	public void setEndDate(Timestamp endDate) {
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

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getTipology() {
		return tipology;
	}

	public void setTipology(String typology) {
		this.tipology = typology;
	}

	public String getCreatorEmail() {
		return creatorEmail;
	}

	public void setCreatorEmail(String creatorEmail) {
		this.creatorEmail = creatorEmail;
	}

}
