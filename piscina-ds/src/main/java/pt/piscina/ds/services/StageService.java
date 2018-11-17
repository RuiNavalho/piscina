package pt.piscina.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;

import pt.piscina.ds.daos.StageDao;
import pt.piscina.ds.entities.Stage;
import pt.piscina.ds.util.InternalError;
import pt.uc.dei.itf.dtos.StageDto;

@Stateless
public class StageService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(StageService.class);

	@Inject
	private StageDao stageDao;

	public StageService() {	
	}

	public Response getAllStages() {
		try {
			List<Stage> findAll = stageDao.findAll();
			List<StageDto> list = getStagesListAsDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////


	private List<StageDto> getStagesListAsDto(List<Stage> allStages) {
		List<StageDto> list = new ArrayList<StageDto>();
		for (int i=0; i<allStages.size(); i++) {
			list.add(new StageDto(allStages.get(i).getStage()));
		}
		return list;
	}
}
