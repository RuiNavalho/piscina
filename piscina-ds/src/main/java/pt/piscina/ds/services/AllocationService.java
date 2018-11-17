package pt.piscina.ds.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import org.apache.log4j.Logger;
import io.jsonwebtoken.Claims;
import pt.piscina.ds.daos.AllocationDao;
import pt.piscina.ds.entities.Allocation;
import pt.piscina.ds.security.MyJwt;
import pt.piscina.ds.util.InternalError;
import pt.uc.dei.itf.dtos.AllocationDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class AllocationService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(AllocationService.class);
	
	@Inject
	private AllocationDao allocationDao;
	
	public AllocationService() {
	}

	public Response findMyAllocations(String token, String type) {
		try {
			Claims claims = MyJwt.parseJWT(token);
			String email = claims.getSubject();
			List<Allocation> list = allocationDao.findMyAllocations(email, type);
			List<AllocationDto> listDto = getAllocationsAsDto(list);
			return Response.ok(listDto).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}
	
	public Response getMyAllocationsBetweenDates(String token, String beginDate1, String endDate1) {
		try {
			ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
			List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date beginDate2 = sdf.parse(beginDate1);
			Date endDate2 = sdf.parse(endDate1);
			Timestamp begin = new Timestamp(beginDate2.getTime());
			Timestamp end = new Timestamp(endDate2.getTime());
			if (begin.after(end)) {
				errorMessages.add(ErrorMessage.END_DATE_MUST_BE_AFTER_BEGIN_DATE);
				return statusNOK.entity(errorMessages).build();
			}
			String email = MyJwt.parseJWT(token).getSubject();
			List<Allocation> list = allocationDao.findMyAllocationsBetweenDates(email, begin, end);
			List<AllocationDto> listDto = getAllocationsAsDto(list);
			return Response.ok(listDto).build();
		} catch (ParseException e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}
	
	public Response getUserAllocationsBetweenDates(String email, String beginDate1, String endDate1) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date beginDate2 = sdf.parse(beginDate1);
			Date endDate2 = sdf.parse(endDate1);
			Timestamp begin = new Timestamp(beginDate2.getTime());
			Timestamp end = new Timestamp(endDate2.getTime());		
			List<Allocation> allocs = allocationDao.findMyAllocationsBetweenDates(email, begin, end);		
			List<AllocationDto> allocsDto = getAllocationsAsDto(allocs);		
			return Response.ok(allocsDto).build();
		} catch (ParseException e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}
	
//	private List<AllocationDto> getAllocationListAsDto(List<Allocation> all) {
//		List<AllocationDto> list = new ArrayList<AllocationDto>();
//		for (int i=0; i<all.size(); i++) {
//			list.add(new AllocationDto(all.get(i).getAllocPercentage(), all.get(i).getTask().getId(),
//					all.get(i).getUser().getEmail(),all.get(i).getUser().getFullName(), null, all.get(i).getBeginDate(), all.get(i).getEndDate(), all.get(i).getId(), all.get(i).getTask().getEndDate(), all.get(i).getTask().getTaskName(), null));
//		}
//		return list;
//	}

	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<AllocationDto> getAllocationsAsDto(List<Allocation> all) {
		List<AllocationDto> list = new ArrayList<AllocationDto>();
		for (int i=0; i<all.size(); i++) {
			list.add(new AllocationDto(all.get(i).getAllocPercentage(), all.get(i).getTask().getId(),
					all.get(i).getUser().getEmail(),all.get(i).getUser().getFullName(), null, all.get(i).getBeginDate(), all.get(i).getEndDate(), all.get(i).getId(), all.get(i).getTask().getEndDate(), all.get(i).getTask().getTaskName(), all.get(i).getTask().getProject().getIdProject()));
		}
		return list;
	}



}
