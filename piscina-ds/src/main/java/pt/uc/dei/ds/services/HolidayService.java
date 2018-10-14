package pt.uc.dei.ds.services;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.log4j.Logger;

import pt.uc.dei.ds.daos.HolidayDao;
import pt.uc.dei.ds.entities.Holiday;
import pt.uc.dei.ds.security.MyJwt;
import pt.uc.dei.ds.util.InternalError;
import pt.uc.dei.ds.util.TimeCalc;
import pt.uc.dei.ds.util.Trimmer;
import pt.uc.dei.itf.dtos.HolidayDto;
import pt.uc.dei.itf.errors.ErrorMessage;

@Stateless
public class HolidayService implements Serializable{

	private static final long serialVersionUID = 1L;
		
	private final static Logger logger = Logger.getLogger(HolidayService.class);

	@Inject
	private HolidayDao holidayDao;

	public HolidayService() {	
	}

	/**
	 * Does not return past holidays
	 * @return
	 */
	public Response getAllHolidays() {
		try {
			List<Holiday> findAll = holidayDao.findFutureHolidays();	
			List<HolidayDto> list = getAllHolidayListDto(findAll);
			return Response.ok(list).build();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}
 
	public Response createNewHoliday(HolidayDto holidayDto, String token) {
		ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		try {
			//Messaged to be logged
			StringBuilder sb = new StringBuilder();

			String email = MyJwt.parseJWT(token).getSubject();
			
			boolean sucess=true;
			String holidayname = Trimmer.clean(holidayDto.getHolidayname());
			sb.append(" Name : ").append(holidayname).append(" Date : ").append(holidayDto.getDay());
			//fields must not be null nor empty
			if (holidayname.equals("") || holidayDto.getDay()==null) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}
			
			//can only create future holidays
			Timestamp now = TimeCalc.todayMidnight();
			if (holidayDto.getDay()!=null && now.after(holidayDto.getDay())) {
				if (now.after(holidayDto.getDay())) {
					sucess=false;
					errorMessages.add(ErrorMessage.HOLIDAY_DATE_MUST_BE_FUTURE);
					}	
			}
			
			//new date must not be already an holiday
			Timestamp date = new Timestamp(holidayDto.getDay().getTime());
			boolean holidayExists = holidayDao.checkDateHolidayExistence(date);
			if (holidayExists) {
				sucess=false;
				errorMessages.add(ErrorMessage.DATE_IS_ALREADY_AN_HOLIDAY);
			} 

			if (sucess) {
				Holiday holiday = new Holiday(holidayDto.getHolidayname(), date);
				holidayDao.persist(holiday);
				logger.info("SUCESS - User  "+email+" created new Holiday: "+sb.toString());
				return Response.ok(true).build();
			}
			else {
				logger.info("FAILED - User  "+email+" tryed create new Holiday: "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}

	public Response updateHoliday(HolidayDto holidayDto, String token) {
		ResponseBuilder statusNOK = Response.status(Response.Status.UNAUTHORIZED);
		List<ErrorMessage> errorMessages = new ArrayList<ErrorMessage>();
		boolean sucess=true;
		try {	
			//Messaged to be logged
			StringBuilder sb = new StringBuilder();

			String email = MyJwt.parseJWT(token).getSubject();
			
			Holiday holidayUpdate = holidayDao.find(holidayDto.getId());
			String holidayname = Trimmer.clean(holidayDto.getHolidayname());

			//fields must not be null nor empty
			if (holidayname.equals("") || holidayDto.getDay()==null) {
				sucess=false;
				errorMessages.add(ErrorMessage.NULL_OR_EMPTY_FIELDS);
			}
			
			//can only create future holiday dates
			Timestamp now = new Timestamp(System.currentTimeMillis());
			if (holidayDto.getDay()!=null && now.after(holidayDto.getDay())) {
				if (now.after(holidayDto.getDay())) {
					sucess=false;
					errorMessages.add(ErrorMessage.HOLIDAY_DATE_MUST_BE_FUTURE);
					}	
			}
			sb.append("User with email ").append(email);
			sb.append(". Holiday UPDATE in ID ").append(holidayUpdate.getId()).append(". BEFORE - Name: ").append(holidayUpdate.getHolidayname()).append(" Date: ").append(holidayUpdate.getDay());
			sb.append(" AFTER - Name: ").append(holidayname).append(" Date: ").append(holidayDto.getDay());
			
			Timestamp date = new Timestamp(holidayDto.getDay().getTime());

			//if we want to change date, we need to verify that new date is not already an holiday
			if (holidayUpdate.getDay().compareTo(holidayDto.getDay())!=0) {
				boolean holidayExists = holidayDao.checkDateHolidayExistence(date);
				if (holidayExists) {
					sucess=false;
					errorMessages.add(ErrorMessage.DATE_IS_ALREADY_AN_HOLIDAY);
				}
			}

			if (sucess) {
				holidayUpdate.setDay(date);
				holidayUpdate.setHolidayname(holidayDto.getHolidayname());
				holidayDao.merge(holidayUpdate);
				logger.info("SUCESS - "+sb.toString());
				return Response.ok(true).build();
			} else {
				logger.info("FAILED - "+sb.toString());
				return statusNOK.entity(errorMessages).build();
			}

		} catch (Exception e) {
			logger.error("Exception "+e);
			return InternalError.response;
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////// Private Auxiliary Methods ///////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private List<HolidayDto> getAllHolidayListDto(List<Holiday> findAll) {
		List<HolidayDto> list = new ArrayList<HolidayDto>();
		for (int i=0; i<findAll.size(); i++) {
			HolidayDto h = new HolidayDto(findAll.get(i).getHolidayname(), findAll.get(i).getDay());
			h.setId(findAll.get(i).getId());
			list.add(h);
		}
		return list;
	}

}
