package pt.uc.dei.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import pt.uc.dei.ds.daos.TaskstageDao;
import pt.uc.dei.ds.entities.Taskstage;
import pt.uc.dei.ds.util.InternalError;
import pt.uc.dei.itf.dtos.TaskstageDto;

public class TaskstageService implements Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(TaskstageService.class);

	@Inject
	private TaskstageDao taskstageDao;

	public TaskstageService() {	
	}

	public Response getAllTaskstages() {	
		try {
			List<Taskstage> findAll = taskstageDao.findAll();
			List<TaskstageDto> list = getTaskstagesListAsDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<TaskstageDto> getTaskstagesListAsDto(List<Taskstage> allTaskstages) {
		List<TaskstageDto> list = new ArrayList<TaskstageDto>();
		for (int i=0; i<allTaskstages.size(); i++) {
			list.add(new TaskstageDto(allTaskstages.get(i).getTaskStage()));
		}
		return list;
	}


}
