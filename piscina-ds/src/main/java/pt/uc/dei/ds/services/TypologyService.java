package pt.uc.dei.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import org.apache.log4j.Logger;
import pt.uc.dei.ds.daos.TypologyDao;
import pt.uc.dei.ds.entities.Tipology;
import pt.uc.dei.ds.util.InternalError;
import pt.uc.dei.itf.dtos.TypologyDto;

@Stateless
public class TypologyService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(TypologyService.class);

	@Inject
	private TypologyDao typologyDao;

	public TypologyService() {	
	}

	public Response getAllTypologies() {	
		try {
			List<Tipology> findAll = typologyDao.findAll();
			List<TypologyDto> list = getTypologiesListAsDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<TypologyDto> getTypologiesListAsDto(List<Tipology> allTypologies) {
		List<TypologyDto> list = new ArrayList<TypologyDto>();
		for (int i=0; i<allTypologies.size(); i++) {
			list.add(new TypologyDto(allTypologies.get(i).getTipology()));
		}
		return list;
	}
}
