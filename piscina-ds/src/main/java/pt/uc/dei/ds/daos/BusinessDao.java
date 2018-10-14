package pt.uc.dei.ds.daos;

import java.sql.Timestamp;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.uc.dei.ds.entities.Business;
import pt.uc.dei.ds.entities.Project;
import pt.uc.dei.ds.util.TimeCalc;

@Stateless
public class BusinessDao extends AbstractDao<Business>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(BusinessDao.class);

	public BusinessDao() {
		super(Business.class);
	}
	
	public List<Project> findAllBusinessProjectsByAscendingCreationDate(String area) {
		try {
			TypedQuery<Project> all = em.createNamedQuery("Business.findAllBusinessProjectsByAscendingCreationDate", Project.class);
			all.setParameter("area", area);
			return all.getResultList();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Boolean checkBusinessNameExistence(String businessName) {
		try {
			TypedQuery<Business> businessByName = em.createNamedQuery("Business.findBusinessByName", Business.class);
			businessByName.setParameter("area", businessName);
			if (businessByName.getResultList().size()!=0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Business findBusinessByName(String businessName) {
		try {
			TypedQuery<Business> businessByName = em.createNamedQuery("Business.findBusinessByName", Business.class);
			businessByName.setParameter("area", businessName);
			return businessByName.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Long findTotalNumberOfProjects(Business business) {
		try {
			TypedQuery<Long> totalNumberOfProjects = em.createNamedQuery("Business.findTotalNumberOfProjects", Long.class);
			totalNumberOfProjects.setParameter("id", business.getId());
			return totalNumberOfProjects.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Long findActiveNumberOfProjects(Business business) {
		try {
			Timestamp now = TimeCalc.todayMidnight();
			TypedQuery<Long> list = em.createNamedQuery("Business.findActiveNumberOfProjects", Long.class);
			list.setParameter("id", business.getId());
			list.setParameter("now", now);
			return list.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Double findTotalBudget(Business business) {
		try {
			TypedQuery<Double> budget = em.createNamedQuery("Business.findTotalBudget", Double.class);
			budget.setParameter("id", business.getId());
			if (budget.getSingleResult()==null) {
				return 0.0;
			} else {
				return budget.getSingleResult();
			}		
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

	public Double findActiveTotalBudget(Business business) {
		try {
			Timestamp now = TimeCalc.todayMidnight();
			TypedQuery<Double> budget = em.createNamedQuery("Business.findActiveTotalBudget", Double.class);
			budget.setParameter("id", business.getId());
			budget.setParameter("now", now);
			if (budget.getSingleResult()==null) {
				return 0.0;
			} else {
				return budget.getSingleResult();
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
}
