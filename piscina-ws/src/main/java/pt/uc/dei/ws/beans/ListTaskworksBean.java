package pt.uc.dei.ws.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import pt.piscina.itf.dtos.AttachmentDto;
import pt.piscina.itf.dtos.ProjectDto;
import pt.piscina.itf.dtos.TaskLightDto;
import pt.piscina.itf.dtos.TaskworkDto;
import pt.uc.dei.ws.bridges.TaskWorkBridge;

@Named("listTaskworksBean")
@SessionScoped
public class ListTaskworksBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private List<TaskworkDto> taskworksList;
	private TaskworkDto selectedTaskwork;
	
	private AttachmentDto selectedAttachment;
	private StreamedContent file;
	
	@Inject
	private TaskWorkBridge taskWorkBridge;
	
	@Inject
	private ListTasksBean listTasksBean;
	
	@Inject
	private ListProjectsBean listProjectsBean;

	public ListTaskworksBean() {	
	}
	
    public void downloadFile() {
    	InputStream stream1 = new ByteArrayInputStream(selectedAttachment.getDocument());
       // InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream(arg0)
        file = new DefaultStreamedContent(stream1, null, selectedAttachment.getDocumentName());
    }
	
	public List<TaskworkDto> findMyWorkingHours() {
		taskworksList= taskWorkBridge.getMyWorkingHours();
		return taskworksList;
	}
	
	public List<TaskworkDto> findMyWorkingHoursInTask() {
		TaskLightDto selectedTask = listTasksBean.getSelectedTask();
		taskworksList= taskWorkBridge.findMyWorkingHoursInTask(selectedTask.getTaskId());
		return taskworksList;
	}
	
	public List<TaskworkDto> findMyWorkingHoursInProject() {
		ProjectDto selectedProject = listProjectsBean.getSelectedProject();
		taskworksList= taskWorkBridge.findMyWorkingHoursInProject(selectedProject.getIdProject());
		return taskworksList;
	}
	
	public List<TaskworkDto> findWorkingHoursInTask() {
		TaskLightDto selectedTask = listTasksBean.getSelectedTask();
		taskworksList= taskWorkBridge.findWorkingHoursInTask(selectedTask.getTaskId());
		return taskworksList;
	}
	
	public List<TaskworkDto> findWorkingHoursInProject() {
		ProjectDto selectedProject = listProjectsBean.getSelectedProject();
		taskworksList= taskWorkBridge.findWorkingHoursInProject(selectedProject.getIdProject());
		return taskworksList;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////

	public List<TaskworkDto> getTaskworksList() {
		return taskworksList;
	}

	public void setTaskworksList(List<TaskworkDto> taskworksList) {
		this.taskworksList = taskworksList;
	}

	public TaskworkDto getSelectedTaskwork() {
		return selectedTaskwork;
	}

	public void setSelectedTaskwork(TaskworkDto selectedTaskwork) {
		this.selectedTaskwork = selectedTaskwork;
	}

	public StreamedContent getFile() {
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public AttachmentDto getSelectedAttachment() {
		return selectedAttachment;
	}

	public void setSelectedAttachment(AttachmentDto selectedAttachment) {
		this.selectedAttachment = selectedAttachment;
	}
	
}
