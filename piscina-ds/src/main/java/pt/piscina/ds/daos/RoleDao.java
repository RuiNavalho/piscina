package pt.piscina.ds.daos;

import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import pt.piscina.ds.entities.Role;

@Stateless
public class RoleDao extends AbstractDao<Role>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(RoleDao.class);

	public RoleDao() {
		super(Role.class);
	}
	
	public Role findRoleWithName(String role) {
		try {
			TypedQuery<Role> findRoleWithName = em.createNamedQuery("Role.findRoleWithName", Role.class);
			findRoleWithName.setParameter("role", role);
			return findRoleWithName.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
}
