package pt.piscina.ds.services;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.log4j.Logger;

import pt.piscina.ds.daos.BusinessDao;
import pt.piscina.ds.entities.Business;
import pt.piscina.ds.security.MyJwt;
import pt.piscina.ds.util.InternalError;
import pt.piscina.ds.util.Trimmer;
import pt.piscina.itf.dtos.BusinessDto;
import pt.piscina.itf.dtos.BusinessStatsDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class BusinessService implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(BusinessService.class);

	@Inject
	private BusinessDao businessDao;

	public BusinessService() {	
	}

	public Response createNewBusiness(BusinessDto businessDto, String token) {
		ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		try {
			String email = MyJwt.parseJWT(token).getSubject();
			String area = Trimmer.clean(businessDto.getArea());
			boolean businessExists = businessDao.checkBusinessNameExistence(area);
			if (!businessExists) {
				
				if (area.equals("")) {		
					errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
					return statusNOK.entity(errorMessages).build();	
				} else {
					Business business= new Business();
					business.setArea(area);
					businessDao.persist(business);
					logger.info("SUCESS - User  "+email+" created new Business: "+businessDto.getArea());
					return Response.ok(true).build();
				}
			} else {
				logger.info("FAILED - User  "+email+" tryed to create new Business: "+businessDto.getArea());
				errorMessages.add(ErrorMessage.UNIQUE_NAME_ALREADY_EXISTS);
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getAllBusinesses() {
		try {
			List<Business> findAll = businessDao.findAll();	
			List<BusinessDto> list = getAllBusinessesListDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response getAllBusinessesStats() {
		try {
			List<Business> findAll = businessDao.findAll();	
			List<BusinessStatsDto> list = getAllBusinessesStatsDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}
	
	public Response getBusinessStats(String area) {
		try {	
			Business b = businessDao.findBusinessByName(area);
			List<Business> findAll=new ArrayList<Business>();
			findAll.add(b);
			List<BusinessStatsDto> list = getAllBusinessesStatsDto(findAll);
			return Response.ok(list.get(0)).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	private List<BusinessStatsDto> getAllBusinessesStatsDto(List<Business> all) {
		List<BusinessStatsDto> list = new ArrayList<BusinessStatsDto>();
		for (int i=0; i<all.size(); i++) {
			Long totalNumberOfProjects=businessDao.findTotalNumberOfProjects(all.get(i));
			Long activeNumberOfProjects=businessDao.findActiveNumberOfProjects(all.get(i));
			float totalBudget=businessDao.findTotalBudget(all.get(i)).floatValue();
			float activeTotalBudget=businessDao.findActiveTotalBudget(all.get(i)).floatValue();
			list.add(new BusinessStatsDto(all.get(i).getArea(), totalNumberOfProjects.intValue(), activeNumberOfProjects.intValue(),
					totalBudget, activeTotalBudget));
		}
		return list;
	}

	private List<BusinessDto> getAllBusinessesListDto(List<Business> allBusinesses) {
		List<BusinessDto> list = new ArrayList<BusinessDto>();
		for (int i=0; i<allBusinesses.size(); i++) {
			list.add(new BusinessDto(allBusinesses.get(i).getArea()));
		}
		return list;
	}




}
