package pt.piscina.ds.daos;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Skill;

@Stateless
public class SkillDao extends AbstractDao<Skill>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(AbstractDao.class);

	public SkillDao() {
		super(Skill.class);
	}
	
	public Skill findSkillWithName(String skill) {
		try {
			TypedQuery<Skill> findSkillWithName = em.createNamedQuery("Skill.findSkillWithName", Skill.class);
			findSkillWithName.setParameter("skill", skill);
			return findSkillWithName.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
	public Boolean checkSkillNameExistence(String skill) {
		try {
			TypedQuery<Skill> list = em.createNamedQuery("Skill.findSkillWithName", Skill.class);
			list.setParameter("skill", skill);
			if (list.getResultList().size()!=0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}

}
