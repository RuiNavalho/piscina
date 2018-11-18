package pt.uc.dei.ws.charts;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import pt.piscina.itf.dtos.AllocationDto;
import pt.piscina.itf.dtos.ProjectDto;
import pt.piscina.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.charts.ChartProjectAllocationsDto;
import pt.uc.dei.itf.charts.ChartProjectGanttDto;
import pt.uc.dei.itf.charts.TaskGanttDto;
import pt.uc.dei.ws.bridges.ChartBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("chartAllocations")
@SessionScoped
public class ChartTimeline implements Serializable {  

	private static final long serialVersionUID = 1L;

	private TimelineModel model=new TimelineModel();
	private Date start= new Date();
	private Date end= new Date();
	private Date endPlusOne= new Date();
	private ChartProjectAllocationsDto chartAllocs;
	private ChartProjectGanttDto gantt;
	


	@Inject
	private ChartBridge chartBridge;
	
	@Inject
	private ErrorsHandler errorsHandler;
	
	public ChartTimeline() {
	}
	
	public void findProjectAllocationsChart(ProjectDto selectedProject) {
		this.chartAllocs = chartBridge.findProjectAllocationsChart(selectedProject.getIdProject());
		updateProjectAllocationsChart();
	}
	
	public void findProjectGanttChart(ProjectDto selectedProject) {
		Response response = chartBridge.findProjectGanttChart(selectedProject.getIdProject());
		if (response.getStatus()==200) {
			this.gantt = response.readEntity(ChartProjectGanttDto.class);
			updateProjectGanttChart();
		}
	}

	public void findTaskAllocationsChart(TaskLightDto selectedTask) {
		Response response = chartBridge.findTaskAllocationsChart(selectedTask.getTaskId());
		if (response.getStatus()==200) {
			this.chartAllocs = response.readEntity(ChartProjectAllocationsDto.class);
			updateTaskAllocationsChart();
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
	
	private void updateTaskAllocationsChart() {
		start = chartAllocs.getBeginDate();
		//TODO mudar esta forma de adicionar 1 dia para uma mais elagante
		end = new Date(chartAllocs.getEndDate().getTime()+86400000);
		endPlusOne = new Date(end.getTime()+86400000);
		List<AllocationDto> allocs = chartAllocs.getAllocsList();
		// create timeline model  
		model = new TimelineModel();	
		for (int i=0; i<allocs.size(); i++) {
			Date start= new Date(allocs.get(i).getBeginDate().getTime());
			//TODO mudar forma de adicionar 24h
			Date end= new Date(allocs.get(i).getEndDate().getTime()+86400000);
			String text  = allocs.get(i).getAllocPercentage()+"% - "+allocs.get(i).getTaskName();
			String color=null;
			if(allocs.get(i).getAllocPercentage()<25){
				color="blue4";
			} else if(allocs.get(i).getAllocPercentage()<50){
				color="blue3";
			} else if(allocs.get(i).getAllocPercentage()<75){
				color="blue2";
			} else {
				color="blue1";
			}	
			TimelineEvent event = new TimelineEvent(text, start, end, true, allocs.get(i).getWorkerEmail()+"-"+allocs.get(i).getWorkerName(), color);  
			model.add(event); 
		}
	}

	public void updateProjectAllocationsChart() {  
		start = chartAllocs.getBeginDate();
		//TODO mudar esta forma de adicionar 1 dia para uma mais elagante
		end = new Date(chartAllocs.getEndDate().getTime()+86400000);
		endPlusOne = new Date(end.getTime()+86400000);
		List<AllocationDto> allocs = chartAllocs.getAllocsList();
		List<String> tasks = new ArrayList<String>();
		for (int i=0; i<allocs.size(); i++) {
			if (!tasks.contains(allocs.get(i).getTaskName())) {
				tasks.add(allocs.get(i).getTaskName());
			}
		}

		// create timeline model  
		model = new TimelineModel();
		
		for (int i=0; i<allocs.size(); i++) {
			Date start= new Date(allocs.get(i).getBeginDate().getTime());
			//TODO mudar forma de adicionar 24h
			Date end= new Date(allocs.get(i).getEndDate().getTime()+86400000);
			String text  = allocs.get(i).getAllocPercentage()+"% - "+allocs.get(i).getWorkerName();
			String color=null;
			if(allocs.get(i).getAllocPercentage()<25){
				color="blue4";
			} else if(allocs.get(i).getAllocPercentage()<50){
				color="blue3";
			} else if(allocs.get(i).getAllocPercentage()<75){
				color="blue2";
			} else {
				color="blue1";
			}	
			TimelineEvent event = new TimelineEvent(text, start, end, true, allocs.get(i).getTaskId()+"-"+allocs.get(i).getTaskName(), color);  
			model.add(event); 
		}
	} 
	
	
	public void updateUserAllocationsChart(ChartProjectAllocationsDto cup) {  
		this.chartAllocs = cup;
		start = chartAllocs.getBeginDate();
		//TODO mudar esta forma de adicionar 1 dia para uma mais elagante
		end = new Date(chartAllocs.getEndDate().getTime()+86400000);
		endPlusOne = new Date(end.getTime()+86400000);
		List<AllocationDto> allocs = chartAllocs.getAllocsList();
		List<String> tasks = new ArrayList<String>();
		for (int i=0; i<allocs.size(); i++) {
			if (!tasks.contains(allocs.get(i).getTaskName())) {
				tasks.add(allocs.get(i).getTaskName());
			}
		}

		// create timeline model  
		model = new TimelineModel();
		
		for (int i=0; i<allocs.size(); i++) {
			Date start= new Date(allocs.get(i).getBeginDate().getTime());
			//TODO mudar forma de adicionar 24h
			Date end= new Date(allocs.get(i).getEndDate().getTime()+86400000);
			String text  = allocs.get(i).getAllocPercentage()+"% - "+allocs.get(i).getTaskName();
			String color=null;
			if(allocs.get(i).getAllocPercentage()<25){
				color="red";
			} else if(allocs.get(i).getAllocPercentage()<50){
				color="orange";
			} else if(allocs.get(i).getAllocPercentage()<75){
				color="yellow";
			} else {
				color="green";
			}	
			TimelineEvent event = new TimelineEvent(text, start, end, true, "Project: "+allocs.get(i).getProjectId(), color);  
			model.add(event); 
		}
	} 

	public TimelineModel getModel() {  
		return model;  
	}  

	public Date getStart() {  
		return start;  
	}  

	public Date getEnd() {  
		return end;  
	}

	public Date getEndPlusOne() {
		return endPlusOne;
	}

	public void setEndPlusOne(Date endPlusOne) {
		this.endPlusOne = endPlusOne;
	}

	public ChartProjectGanttDto getGantt() {
		return gantt;
	}

	public void setGantt(ChartProjectGanttDto gantt) {
		this.gantt = gantt;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
}
