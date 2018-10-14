package pt.uc.dei.ws.util;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named("tabs")
@SessionScoped
public class Tabs implements Serializable {

	private static final long serialVersionUID = 1L;

	private String projectTab = "project_details";
	private String activitiesTab = "activities_details";
	private String clientTab = "clients_details";
	private String businessTab = "business_details";
	private String registerTab = "register_details";
	private String registoHTab = "register_details";
	private String reportTab = "none";
	
	private String projectInclude = "none";
	private String reportInclude = "none";
	private String projectSearchInclude = "none";
	
	private String reportSearchInclude = "none";
	private String reportSearchResultsInclude = "none";
	
	private String userInclude = "none";
	private String userSearchInclude = "none";
	private String registerInclude = "none";

	private String activeTab1="";
	private String activeTab2="";
	private String activeTab3="";
	private String activeTab4="";
	private String activeTab5="";
	private String activeTab6="";
	private String activeTab7="";
	private String activeTab8="";
	
	private String typeLink = "none";
	private String typeLink2 = "none";
	
	private String type;

	private boolean visibilityPanel = false;

	private boolean showResources;
	

	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private Date hoje = new Date();
	private Date amanha = new Date();

	private String pageName;
	private String pagina="none";


	public Tabs() {
	}

	public void resourceAllocation(){
		setShowResources(true);
	}

	public void showPanel (String pagina){
		setPagina(pagina);
		if (pagina.equals("activities_allocations_list")){
			setShowResources(false);
		}
	}

	public void hidePanel(String pagina){
		setPageName(pagina);
	}

	public String backPage(String pageName) {
		return pageName +".xhtml?faces-redirect=true";
	}

	public String goToPage(String pageName){
		return pageName +".xhtml?faces-redirect=true";
	}

	public String showPage(String pageName){
		return pageName +".xhtml?faces-redirect=true";
	}
	public String goToResourceAllocation(){
		showActivities("allocations",2);
		return "activities_edit.xhtml?faces-redirect=true";
	}
	public void showProject(String tab, int activo) {
		projectTab="project_"+tab;
		setActiveTab1("");
		setActiveTab2("");
		setActiveTab3("");
		setActiveTab4("");
		setActiveTab5("");
		setActiveTab6("");
		setActiveTab7("");

		switch (activo) {
		case 1:
			setActiveTab1("active");
			break;
		case 2:
			setActiveTab2("active");
			break;
		case 3:
			setActiveTab3("active");
			break;
		case 4:
			setActiveTab4("active");
			break;	
		case 5:
			setActiveTab5("active");
			break;
		case 6:
			setActiveTab6("active");
			break;
		case 7:
			setActiveTab7("active");
			break;
		default:
			break;
		}
	}

	public void showProjectInclude(String insertInclude, int activo) {
		projectInclude="projects_"+insertInclude;
		setActiveTab1("");
		setActiveTab2("");
		setActiveTab3("");
		switch (activo) {
		case 1:
			setActiveTab1("active");
			break;
		case 2:
			setActiveTab2("active");
			break;
		case 3:
			setActiveTab3("active");
			break;	
		default:
			break;
		}
	}
	public void showRegisterInclude(String insertInclude) {
		setRegisterInclude("register_"+insertInclude);
	}
	
	public void showUserInclude(String insertInclude) {
		userInclude="users_"+insertInclude;
	}
	
	public void showUserReportSearchInclude(String insertInclude){
		reportSearchInclude="report_"+insertInclude;
	}
	
	public void showProjectSearchInclude(String projectSearchInclude){
		setProjectSearchInclude("projects_"+projectSearchInclude);
	}
	public void showUserSearchInclude(String projectSearchInclude){
		setUserSearchInclude("users_"+projectSearchInclude);
	}

	public void showActivities(String tab, int activo) {

		if (tab.equals("allocations")) {
			setShowResources(false);
			setPagina("none");
		}
		activitiesTab="activities_"+tab;

		setActiveTab1("");
		setActiveTab2("");
		setActiveTab3("");
		setActiveTab4("");
		setActiveTab5("");
		setActiveTab6("");
		setActiveTab7("");
		setActiveTab8("");

		switch (activo) {
		case 1:
			setActiveTab1("active");
			break;
		case 2:
			setActiveTab2("active");
			break;
		case 3:
			setActiveTab3("active");
			break;
		case 4:
			setActiveTab4("active");
			break;
		case 5:
			setActiveTab5("active");
			break;
		case 6:
			setActiveTab6("active");
			break;
		case 7:
			setActiveTab7("active");
			break;
		case 8:
			setActiveTab7("active");
			break;
		default:
			break;
		}
	}

	public void showClient(String tab, int activo) {
		clientTab="clients_"+tab;
		setActiveTab1("");
		setActiveTab2("");
		setActiveTab3("");


		switch (activo) {
		case 1:
			setActiveTab1("active");
			break;
		case 2:
			setActiveTab2("active");
			break;
		case 3:
			setActiveTab3("active");
			break;
		default:
			break;
		}
	}

	public void showBusiness(String tab, int activo){
		businessTab = "business_"+tab;
		setActiveTab1("");
		setActiveTab2("");
		setActiveTab3("");

		switch (activo) {
		case 1:
			setActiveTab1("active");
			break;
		case 2:
			setActiveTab2("active");
			break;
		case 3:
			setActiveTab3("active");
			break;
		default:
			break;
		}
	}

	public void showRegistoHoras(String tab, int activo){
		registoHTab = "register_"+tab;
		setActiveTab1("");
		setActiveTab2("");
		setActiveTab3("");

		switch (activo) {
		case 1:
			setActiveTab1("active");
			break;
		case 2:
			setActiveTab2("active");
			break;
		case 3:
			setActiveTab3("active");
			break;
		default:
			break;
		}
	}
	
	public void showReport(String tab, int activo){
		reportTab = "report_"+tab;
		setActiveTab1("");
		setActiveTab2("");

		switch (activo) {
		case 1:
			setActiveTab1("active");
			break;
		case 2:
			setActiveTab2("active");
			break;
		default:
			break;
		}
	}

	
	public void showRegister(String tab) {
		setRegisterTab("register_"+tab);
	}

	public void resetProjectTab() {
		projectTab = "project_details";
	}

	public void resetTaskTab() {
		activitiesTab = "activities_details";
	}
	public void resetClientTab() {
		clientTab = "clients_details";
	}

	public void submitAllocation(){
		setShowResources(false);
		setPagina("none");
	}

	public void moveStep(String step){
		setRegistoHTab(step);
	}

	public Date tomorrowOrLaterDate(Date date) {
		Date hoje = new Date();
		Date amanha=null;
		Calendar c = Calendar.getInstance();
		c.setTime(hoje);
		c.add(Calendar.DATE, 1);
		amanha = c.getTime();
		if(amanha.after(date)){
			return amanha;	
		} else{
			return date;
		}

	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public String getProjectTab() {
		return projectTab;
	}
	public void setProjectTab(String projectTab) {
		this.projectTab = projectTab;
	}
	public String getActivitiesTab() {
		return activitiesTab;
	}
	public void setActivitiesTab(String activitiesTab) {
		this.activitiesTab = activitiesTab;
	}
	public String getClientTab() {
		return clientTab;
	}
	public void setClientTab(String clientTab) {
		this.clientTab = clientTab;
	}

	public String getBusinessTab() {
		return businessTab;
	}

	public void setBusinessTab(String businessTab) {
		this.businessTab = businessTab;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getActiveTab1() {
		return activeTab1;
	}

	public void setActiveTab1(String activeTab1) {
		this.activeTab1 = activeTab1;
	}

	public String getActiveTab2() {
		return activeTab2;
	}

	public void setActiveTab2(String activeTab2) {
		this.activeTab2 = activeTab2;
	}

	public String getActiveTab3() {
		return activeTab3;
	}

	public void setActiveTab3(String activeTab3) {
		this.activeTab3 = activeTab3;
	}

	public String getActiveTab4() {
		return activeTab4;
	}

	public void setActiveTab4(String activeTab4) {
		this.activeTab4 = activeTab4;
	}

	public String getActiveTab5() {
		return activeTab5;
	}

	public void setActiveTab5(String activeTab5) {
		this.activeTab5 = activeTab5;
	}

	public String getActiveTab6() {
		return activeTab6;
	}

	public void setActiveTab6(String activeTab6) {
		this.activeTab6 = activeTab6;
	}

	public String getActiveTab7() {
		return activeTab7;
	}

	public void setActiveTab7(String activeTab7) {
		this.activeTab7 = activeTab7;
	}

	public String getProjectInclude() {
		return projectInclude;
	}

	public void setProjectInclude(String projectInclude) {
		this.projectInclude = projectInclude;
	}

	public String getRegisterTab() {
		return registerTab;
	}

	public void setRegisterTab(String registerTab) {
		this.registerTab = registerTab;
	}

	public Date getHoje() {
		hoje= new Date();
		return hoje;
	}

	public void setHoje(Date hoje) {
		this.hoje = hoje;
	}

	public DateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public boolean isVisibilityPanel() {
		return visibilityPanel;
	}

	public void setVisibilityPanel(boolean visibilityPanel) {
		this.visibilityPanel = visibilityPanel;
	}

	public String getPagina() {
		return pagina;
	}

	public void setPagina(String pagina) {
		this.pagina = pagina;
	}


	public boolean isShowResources() {
		return showResources;
	}

	public void setShowResources(boolean showResources) {
		this.showResources = showResources;
	}

	public String getRegistoHTab() {
		return registoHTab;
	}

	public void setRegistoHTab(String registoHTab) {
		this.registoHTab = registoHTab;
	}

	public String getActiveTab8() {
		return activeTab8;
	}

	public void setActiveTab8(String activeTab8) {
		this.activeTab8 = activeTab8;
	}

	public Date getAmanha() {
		Date hoje = new Date();
		Date amanha=null;
		Calendar c = Calendar.getInstance();
		c.setTime(hoje);
		c.add(Calendar.DATE, 1);
		amanha = c.getTime();
		return amanha;
	}

	public void setAmanha(Date amanha) {
		this.amanha = amanha;
	}

	public String getUserInclude() {
		return userInclude;
	}

	public void setUserInclude(String userInclude) {
		this.userInclude = userInclude;
	}

	public String getRegisterInclude() {
		return registerInclude;
	}

	public void setRegisterInclude(String registerInclude) {
		this.registerInclude = registerInclude;
	}

	public String getProjectSearchInclude() {
		return projectSearchInclude;
	}

	public void setProjectSearchInclude(String projectSearchInclude) {
		this.projectSearchInclude = projectSearchInclude;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserSearchInclude() {
		return userSearchInclude;
	}

	public void setUserSearchInclude(String userSearchInclude) {
		this.userSearchInclude = userSearchInclude;
	}

	public String getReportTab() {
		return reportTab;
	}

	public void setReportTab(String reportTab) {
		this.reportTab = reportTab;
	}

	public String getReportInclude() {
		return reportInclude;
	}

	public void setReportInclude(String reportInclude) {
		this.reportInclude = reportInclude;
	}

	public String getReportSearchInclude() {
		return reportSearchInclude;
	}

	public void setReportSearchInclude(String reportSearchInclude) {
		this.reportSearchInclude = reportSearchInclude;
	}

	public String getReportSearchResultsInclude() {
		return reportSearchResultsInclude;
	}

	public void setReportSearchResultsInclude(String reportSearchResultsInclude) {
		this.reportSearchResultsInclude = reportSearchResultsInclude;
	}

	public String getTypeLink() {
		return typeLink;
	}

	public void setTypeLink(String typeLink) {
		this.typeLink = typeLink;
	}

	public String getTypeLink2() {
		return typeLink2;
	}

	public void setTypeLink2(String typeLink2) {
		this.typeLink2 = typeLink2;
	}

}
