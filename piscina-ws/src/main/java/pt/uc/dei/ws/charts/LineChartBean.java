package pt.uc.dei.ws.charts;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import pt.piscina.itf.dtos.BusinessStatsDto;
import pt.piscina.itf.dtos.ClientDto;
import pt.piscina.itf.dtos.ProjectDto;
import pt.piscina.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.charts.BudgetDto;
import pt.uc.dei.itf.charts.ChartCountAndBudgetDto;
import pt.uc.dei.itf.charts.ChartCpiSpiDto;
import pt.uc.dei.itf.charts.ChartProjectGanttDto;
import pt.uc.dei.itf.charts.ChartTimePercHoursCost;
import pt.uc.dei.itf.charts.CountDto;
import pt.uc.dei.itf.charts.CpiSpiDto;
import pt.uc.dei.itf.charts.TaskGanttDto;
import pt.uc.dei.ws.bridges.ChartBridge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Named("chartLine")
@SessionScoped
public class LineChartBean implements Serializable {

	private static final long serialVersionUID = 1L;


	private ChartTimePercHoursCost chart = new ChartTimePercHoursCost();

	private ChartCountAndBudgetDto chart2 = new ChartCountAndBudgetDto();

	private List<BudgetDto> budgetPie = new ArrayList<BudgetDto>();

	private List<CountDto> countPie = new ArrayList<CountDto>();

	private String typeChart;
	
	private String projectId;
	
	private ChartCpiSpiDto chartCpiSpiDto;
	private List<CpiSpiDto> cpiSpiList = new ArrayList<>();
	
	private ChartProjectGanttDto gantt;
	
	private TimelineModel model=new TimelineModel();
	private Date start= new Date();
	private Date end= new Date();
	private Date endPlusOne= new Date();
	
	private boolean showHidePanel = false;

	@Inject
	private ChartBridge chartBridge;


	public LineChartBean() {
	}
	
	public void cleanFields(){
		projectId = "";
		showHidePanel = false;
	}
	public void findProjectCpiSpiTimeChart() {
		Response response = chartBridge.getProjectCpiSpiTimeChart(projectId);
		if (response.getStatus()==200) {
			this.chartCpiSpiDto = response.readEntity(ChartCpiSpiDto.class);
			this.cpiSpiList=chartCpiSpiDto.getList();
		}
		gantt=chartBridge.findProjectGanttChart2(projectId);
		updateProjectGanttChart();
	}

	public void findClientChartCountAndBudgetDto(ClientDto selectedClient) {
		Response response = chartBridge.findClientChartCountAndBudgetDto(selectedClient.getCompany());
		if (response.getStatus()==200) {
			this.chart2 = response.readEntity(ChartCountAndBudgetDto.class);
			budgetPie = new ArrayList<BudgetDto>();
			budgetPie.add(new BudgetDto(chart2.getTitle(), chart2.getBudgetThis()));
			budgetPie.add(new BudgetDto("Others", chart2.getBudgetOthers()));
			countPie = new ArrayList<CountDto>();
			countPie.add(new CountDto(chart2.getTitle(), chart2.getProjCountThis()));
			countPie.add(new CountDto("Others", chart2.getProjCountOthers()));
		}
	}

	public void findBusinessChartCountAndBudgetDto(BusinessStatsDto selectedBusiness) {
		Response response = chartBridge.findBusinessChartCountAndBudgetDto(selectedBusiness.getArea());
		if (response.getStatus()==200) {
			this.chart2 = response.readEntity(ChartCountAndBudgetDto.class);
			budgetPie = new ArrayList<BudgetDto>();
			budgetPie.add(new BudgetDto(chart2.getTitle(), chart2.getBudgetThis()));
			budgetPie.add(new BudgetDto("Others", chart2.getBudgetOthers()));
			countPie = new ArrayList<CountDto>();
			countPie.add(new CountDto(chart2.getTitle(), chart2.getProjCountThis()));
			countPie.add(new CountDto("Others", chart2.getProjCountOthers()));
		}
	}

	public void findProjectHourPercCostDtoList(ProjectDto selectedProject) {
		Response response = chartBridge.getProjectTimeEvolutionCharts(selectedProject.getIdProject());
		if (response.getStatus()==200) {
			this.chart = response.readEntity(ChartTimePercHoursCost.class);
		}
	}

	public void findTaskHourPercCostDtoList(TaskLightDto selectedTask) {
		Response response = chartBridge.getTaskTimeEvolutionCharts(selectedTask.getTaskId());
		if (response.getStatus()==200) {
			this.chart = response.readEntity(ChartTimePercHoursCost.class);
		}
	}

	private void updateProjectGanttChart() {
		start = gantt.getBeginDate();
		//TODO mudar esta forma de adicionar 1 dia para uma mais elagante
		end = new Date(gantt.getEndDate().getTime()+86400000);
		endPlusOne = new Date(end.getTime()+86400000);
		List<TaskGanttDto> tasks = gantt.getTasksList();
		// create timeline model  
		model = new TimelineModel();	
		for (int i=0; i<tasks.size(); i++) {
			Date start= new Date(tasks.get(i).getBeginDate().getTime());
			//TODO mudar forma de adicionar 24h
			Date end= new Date(tasks.get(i).getEndDate().getTime()+86400000);
			String text  = tasks.get(i).getPercentageExecuted()+"-"+tasks.get(i).getTaskName();
			String color=null;
			if(tasks.get(i).getPercentageExecuted()<25){
				color="red";
			} else if(tasks.get(i).getPercentageExecuted()<50){
				color="orange";
			} else if(tasks.get(i).getPercentageExecuted()<75){
				color="yellow";
			} else if(tasks.get(i).getPercentageExecuted()<100){
				color="almostGreen";
			} else {
				color="green";
			}
			TimelineEvent event = new TimelineEvent(text, start, end, true, tasks.get(i).getTaskId()+"-"+tasks.get(i).getTaskName(), color);  
			model.add(event); 
		}
	}
	
	public ChartTimePercHoursCost getChart() {
		return chart;
	}

	public void setChart(ChartTimePercHoursCost chart) {
		this.chart = chart;
	}

	public ChartCountAndBudgetDto getChart2() {
		return chart2;
	}

	public void setChart2(ChartCountAndBudgetDto chart2) {
		this.chart2 = chart2;
	}

	public List<BudgetDto> getBudgetPie() {
		return budgetPie;
	}

	public void setBudgetPie(List<BudgetDto> budgetPie) {
		this.budgetPie = budgetPie;
	}

	public List<CountDto> getCountPie() {
		return countPie;
	}

	public void setCountPie(List<CountDto> countPie) {
		this.countPie = countPie;
	}

	public String getTypeChart() {
		return typeChart;
	}

	public void setTypeChart(String typeChart) {
		this.typeChart = typeChart;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public ChartCpiSpiDto getChartCpiSpiDto() {
		return chartCpiSpiDto;
	}

	public void setChartCpiSpiDto(ChartCpiSpiDto chartCpiSpiDto) {
		this.chartCpiSpiDto = chartCpiSpiDto;
	}

	public List<CpiSpiDto> getCpiSpiList() {
		return cpiSpiList;
	}

	public void setCpiSpiList(List<CpiSpiDto> cpiSpiList) {
		this.cpiSpiList = cpiSpiList;
	}

	public boolean isShowHidePanel() {
		return showHidePanel;
	}

	public void setShowHidePanel(boolean showHidePanel) {
		this.showHidePanel = showHidePanel;
	}
	
	public ChartProjectGanttDto getGantt() {
		return gantt;
	}

	public void setGantt(ChartProjectGanttDto gantt) {
		this.gantt = gantt;
	}

	public TimelineModel getModel() {
		return model;
	}

	public void setModel(TimelineModel model) {
		this.model = model;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getEndPlusOne() {
		return endPlusOne;
	}

	public void setEndPlusOne(Date endPlusOne) {
		this.endPlusOne = endPlusOne;
	}


	//    public void clicked() {
	//        FacesMessage m = new FacesMessage("You clicked " + selectedPoint + " on series " + selectedSeries);
	//        FacesContext.getCurrentInstance().addMessage("", m);        
	//    }



}