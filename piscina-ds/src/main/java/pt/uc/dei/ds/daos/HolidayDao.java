package pt.uc.dei.ds.daos;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;
import org.joda.time.LocalDate;

import pt.uc.dei.ds.entities.Holiday;
import pt.uc.dei.ds.util.TimeCalc;

@Stateless
public class HolidayDao extends AbstractDao<Holiday>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(HolidayDao.class);

	public HolidayDao() {
		super(Holiday.class);
	}

	public Boolean checkDateHolidayExistence(Timestamp day) {
		try {
			TypedQuery<Holiday> holidayDate = em.createNamedQuery("Holiday.findHolidayByDate", Holiday.class);
			holidayDate.setParameter("day", day);
			if (holidayDate.getResultList().size()!=0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public List<Holiday> findFutureHolidays() {
		try {
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Timestamp now = new Timestamp(sdf.parse(sdf.format( new Date() )).getTime());
			Timestamp now = TimeCalc.todayMidnight();
			TypedQuery<Holiday> list = em.createNamedQuery("Holiday.findHolidaysAfterDate", Holiday.class);
			list.setParameter("beginDate", now);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public List<Holiday> findHolidaysInDate() {
		try {
			Timestamp now = TimeCalc.todayMidnight();
			TypedQuery<Holiday> list = em.createNamedQuery("Holiday.findHolidaysAfterDate", Holiday.class);
			list.setParameter("beginDate", now);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public List<Holiday> findHolidaysBetweenDates(LocalDate begin, LocalDate end) {
		try {
			Timestamp beginTs=new Timestamp(begin.toDateTimeAtStartOfDay().getMillis());
			Timestamp endTs=new Timestamp(end.toDateTimeAtStartOfDay().getMillis());
			TypedQuery<Holiday> list = em.createNamedQuery("Holiday.findHolidaysBetweenDates", Holiday.class);
			list.setParameter("beginDate", beginTs);
			list.setParameter("endDate", endTs);
			return list.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

}
