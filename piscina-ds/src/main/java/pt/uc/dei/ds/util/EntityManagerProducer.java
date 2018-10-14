package pt.uc.dei.ds.util;

import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EntityManagerProducer {

	@PersistenceContext(unitName = "aclgomes-rnavalho-project-ds")
	private EntityManager em;

	@Produces
	public EntityManager em() {
		return em;
	}

	public void dispose(@Disposes EntityManager em) {
		em.close();
	}
}