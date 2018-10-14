package pt.uc.dei.ws.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import pt.uc.dei.itf.dtos.AllocationDto;
import pt.uc.dei.itf.dtos.ChartUserAllocations;
import pt.uc.dei.itf.dtos.TaskLightDto;
import pt.uc.dei.itf.errors.ErrorMessage;
import pt.uc.dei.ws.bridges.ProjectBridge;
import pt.uc.dei.ws.bridges.TaskBridge;
import pt.uc.dei.ws.bridges.UserBridge;
import pt.uc.dei.ws.util.ErrorsHandler;

@Named("listTasksBean")
@SessionScoped
public class ListTasksBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<TaskLightDto> tasksList;
	private TaskLightDto selectedTask;
	private List<ChartUserAllocations> workersToTask;
	private ChartUserAllocations selectedWorker= new ChartUserAllocations();
	private List<TaskLightDto> precendentTasksList;
	private List<TaskLightDto> possiblePrecendentTasksList;
	private TaskLightDto selectedTaskPrecedent;
	private List<AllocationDto> taskAllocations;
	private AllocationDto selectedTaskAllocation;
	
	private boolean showResources;

	private Date beginDate;
	private Date endDate;

	@Inject
	private TaskBridge taskBridge;

	@Inject
	private UserBridge userBridge;

	@Inject
	private ErrorsHandler errorsHandler;

	@Inject
	private ProjectBridge projectBridge;

	public ListTasksBean() {
	}

	public void cleanTasksDates(){
		beginDate = null;
		endDate= null;
	}

	public void newTaskCreated(int createdTaskId) {
		selectedTask=taskBridge.findTaskById(createdTaskId);
	}

	public boolean isPrecedent(TaskLightDto task){
		for (int i = 0; i < precendentTasksList.size(); i++) {
			if(precendentTasksList.get(i).getTaskId()==task.getTaskId()){
				return true;
			}
		}
		return false;
	}


	public void assignTaskPrecendence(TaskLightDto task) {
		Response response = taskBridge.assignTaskPrecendence(selectedTask, task);
		if (response.getStatus()==200) {
			errorsHandler.assignTaskPrecendence(true, null);
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.assignTaskPrecendence(false, errors);
		}
		findPossiblePrecendentTasksList();
		findPrecendentTasksList();

	}

	public void unAssignTaskPrecendence(TaskLightDto task) {
		Response response = taskBridge.unAssignTaskPrecendence(selectedTask, task);
		if (response.getStatus()==200) {
			errorsHandler.unAssignTaskPrecendence(true, null);
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.unAssignTaskPrecendence(false, errors);
		}
		findPossiblePrecendentTasksList();
		findPrecendentTasksList();
	}


	public void alocarPrecedencias() {
		findPrecendentTasksList();
		findPossiblePrecendentTasksList();
	}

	public String updateSelectedTask(TaskLightDto selectedTask) {
		this.selectedTask=selectedTask;
		if(this.selectedTask.getSkill()==null){
			this.selectedTask.setSkill("Nenhum");
		}
		return "activities_edit.xhtml?faces-redirect=true";
	}
	
	public String updateTaskWithAllocation(AllocationDto allocDto) {
		this.selectedTask = taskBridge.findTaskById(allocDto.getTaskId());
		return "activities_edit.xhtml?faces-redirect=true";
	}

	public List<TaskLightDto> findAllTasks() {
		this.tasksList=taskBridge.getAllTasks();
		return tasksList;
	}

	public List<TaskLightDto> findMyWorkingTasks() {
		this.tasksList=taskBridge.getMyWorkingTasks();
		return tasksList;
	}

	public List<TaskLightDto> findPrecendentTasksList() {
		this.precendentTasksList=taskBridge.findPrecendentTasksList(selectedTask);
		return precendentTasksList;
	}

	public List<TaskLightDto> findPossiblePrecendentTasksList() {
		this.possiblePrecendentTasksList=taskBridge.findPossiblePrecendentTasksList(selectedTask);
		return possiblePrecendentTasksList;
	}

	public List<AllocationDto> findTaskAllocations() {
		this.taskAllocations=taskBridge.findTaskAllocations(selectedTask);
		return taskAllocations;
	}


	public String allocateWorkerToTask() {
		Response response = taskBridge.allocateWorkerToTask(selectedWorker.getFreePercentage(), selectedTask.getTaskId(), selectedWorker.getEmail(), beginDate, endDate);
		if (response.getStatus()==200) {
			newTaskCreated(selectedTask.getTaskId());
			errorsHandler.allocateWorkerToTask(true, null);		
			return "activities_edit.xhtml";
		} else {		
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.allocateWorkerToTask(false, errors);

		}
		return null;
	}
	
	

	public String removeWorkerFromTask() {
		Response response = taskBridge.removeWorkerFromTask(selectedTaskAllocation.getAllocationId());
		if (response.getStatus()==200) {
			findTaskAllocations();
			errorsHandler.removeWorkerFromTask(true, null);	
			return null;
		} else {
			findTaskAllocations();
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.removeWorkerFromTask(false, errors);
		}
		return null;
	}
	
	public String updateAllocationDates() {
		Response response = taskBridge.updateAllocationDates(selectedTaskAllocation);
		if (response.getStatus()==200) {
			findTaskAllocations();
			errorsHandler.updateAllocationDates(true, null);	
			return null;
		} else {
			findTaskAllocations();
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateAllocationDates(false, errors);
		}
		return null;
	}
	
	public void resourceAllocation(){
		if (beginDate.after(endDate)) {
			errorsHandler.beginDateAfterEndDateFindAvailableWorkersToTaskErrorMessage();
			setShowResources(false);
		} else {
			setShowResources(true);
		}
		
	}

	public List<ChartUserAllocations> findAvailableWorkersToTask() {


		Response response = userBridge.getAvailableWorkersToTask(selectedTask.getTaskId(), beginDate, endDate);	
		if (response.getStatus()==200) {
			workersToTask= response.readEntity(new GenericType<List<ChartUserAllocations>>() {});
			return workersToTask;
		} else {	
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});


			errorsHandler.findAvailableWorkersToTask(false, errors);		
		}
		return null;
	}

	//TODO tratar dos erros
	public String updateTask() {
		Response response = taskBridge.updateTask(selectedTask);
		if (response.getStatus()==200) {
			errorsHandler.updateTask(true, null);
			return "activities_edit.xhtml";
		} else {
			List<ErrorMessage> errors = response.readEntity(new GenericType<List<ErrorMessage>>() {});
			errorsHandler.updateTask(false, errors);
		}
		return null;
	}

	//	private void setBooleanErrors(List<ErrorMessage> errors) {
	//		for (int i=0; i<errors.size(); i++)  {
	//			ErrorMessage errorMessage = errors.get(i);
	//			switch(errorMessage){
	//			case TASK_CANNOT_START_BEFORE_PROJECT:{
	//				taskCannotStartBeforeProject=true;
	//			}
	//			case TASK_ALREADY_IN_PROGRESS: {
	//				taskAlreadyInProgress=true;
	//			}
	//			case TASK_CANNOT_END_AFTER_PROJECT: {
	//				taskCannotEndAfterProject=true;
	//			}
	//			case MUST_BE_POSITIVE_VALUE: {
	//				taskHourCostMustBePositive=true;
	//			}
	//			case TASK_MUST_END_AFTER_ALL_PRECEDENTS_START: {
	//				taskMustEndAfterAllPrecedentsStart=true;
	//			}
	//			case TASK_MUST_BEGIN_BEFORE_ALL_NEXT_END: {
	//				taskMustBeginBeforeAllNextEnd=true;
	//			}
	//			case END_DATE_MUST_BE_AFTER_BEGIN_DATE: {
	//				endMustBeAfterBeginDate=true;
	//			}
	//
	//			default:{
	//			}
	//			}
	//		}	
	//	}

	public void updateListTasksBean(String idProject) {
		this.tasksList=projectBridge.getAllProjectTasks(idProject);

	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<TaskLightDto> getTasksList() {
		return tasksList;
	}
	public void setTasksList(List<TaskLightDto> tasksList) {
		this.tasksList = tasksList;
	}
	public TaskLightDto getSelectedTask() {
		return selectedTask;
	}
	public void setSelectedTask(TaskLightDto selectedTask) {
		this.selectedTask=selectedTask;
		if(this.selectedTask.getSkill()==null){
			this.selectedTask.setSkill("Nenhum");
		}
	}

	public List<TaskLightDto> getPrecendentTasksList() {
		return findPrecendentTasksList();
		//return precendentTasksList;
	}

	public List<ChartUserAllocations> getWorkersToTask() {
		return workersToTask;
	}

	public void setWorkersToTask(List<ChartUserAllocations> workersToTask) {
		this.workersToTask = workersToTask;
	}

	public ChartUserAllocations getSelectedWorker() {
		return selectedWorker;
	}

	public void setSelectedWorker(ChartUserAllocations selectedWorker) {
		this.selectedWorker = selectedWorker;
	}

	public void setPrecendentTasksList(List<TaskLightDto> precendentTasksList) {
		this.precendentTasksList = precendentTasksList;
	}

	public List<TaskLightDto> getPossiblePrecendentTasksList() {
		return possiblePrecendentTasksList;
	}

	public void setPossiblePrecendentTasksList(List<TaskLightDto> possiblePrecendentTasksList) {
		this.possiblePrecendentTasksList = possiblePrecendentTasksList;
	}

	public TaskLightDto getSelectedTaskPrecedent() {
		return selectedTaskPrecedent;
	}

	public void setSelectedTaskPrecedent(TaskLightDto selectedTaskPrecedent) {
		this.selectedTaskPrecedent = selectedTaskPrecedent;
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

	public List<AllocationDto> getTaskAllocations() {
		return taskAllocations;
	}

	public void setTaskAllocations(List<AllocationDto> taskAllocations) {
		this.taskAllocations = taskAllocations;
	}

	public AllocationDto getSelectedTaskAllocation() {
		return selectedTaskAllocation;
	}

	public void setSelectedTaskAllocation(AllocationDto selectedTaskAllocation) {
		this.selectedTaskAllocation = selectedTaskAllocation;
	}

	public boolean isShowResources() {
		return showResources;
	}

	public void setShowResources(boolean showResources) {
		this.showResources = showResources;
	}

}
