package pt.uc.dei.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import pt.uc.dei.ds.daos.TasktypeDao;
import pt.uc.dei.ds.entities.Tasktype;
import pt.uc.dei.ds.util.InternalError;
import pt.uc.dei.itf.dtos.TasktypeDto;

public class TasktypeService implements Serializable{

	private static final long serialVersionUID = 1L;
	private final static Logger logger = Logger.getLogger(TasktypeService.class);

	@Inject
	private TasktypeDao tasktypeDao;

	public TasktypeService() {	
	}

	public Response getAllTasktypes() {	
		try {
			List<Tasktype> findAll = tasktypeDao.findAll();
			List<TasktypeDto> list = getTasktypesListAsDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<TasktypeDto> getTasktypesListAsDto(List<Tasktype> allTasktypes) {
		List<TasktypeDto> list = new ArrayList<TasktypeDto>();
		for (int i=0; i<allTasktypes.size(); i++) {
			list.add(new TasktypeDto(allTasktypes.get(i).getTaskType()));
		}
		return list;
	}

}
