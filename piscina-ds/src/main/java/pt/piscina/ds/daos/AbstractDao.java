package pt.piscina.ds.daos;

import java.io.Serializable;
import java.util.List;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import org.apache.log4j.Logger;

@TransactionAttribute(TransactionAttributeType.REQUIRED)
public abstract class AbstractDao<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(AbstractDao.class);
	
	private final Class<T> clazz;

	@Inject
	protected EntityManager em;

	/**
	 * Instantiates the class with the given class.
	 * @param clazz - the class to instantiate the new object with
	 */
	public AbstractDao(Class<T> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Adds a new object to the DB.
	 * @param entity - the object to add
	 */
	public void persist(final T entity) {
		try {
			em.persist(entity);
			em.flush();
		} catch (Exception e) {
			logger.error("Exception "+e);
		}
	}


	/**
	 * Removes everything from the DB.
	 */
	public void deleteAll() {
		final CriteriaDelete<T> criteriaDelete = em.getCriteriaBuilder().createCriteriaDelete(clazz);
		criteriaDelete.from(clazz);
		em.createQuery(criteriaDelete).executeUpdate();
	}
	
	public synchronized List<T> findAll() {

		final CriteriaQuery<T> criteriaQuery = em.getCriteriaBuilder().createQuery(clazz);
		criteriaQuery.select(criteriaQuery.from(clazz));
		return em.createQuery(criteriaQuery).getResultList();
	}
	
	public T find(Object id) {
		return em.find(clazz, id);
	}

	/**
	 * Removes from the DB the given object.
	 * @param entity - the object to remove
	 */
	public synchronized void remove(final T entity) {
		em.remove(em.merge(entity));
	}

	/**
	 * Merges the given object with the object with the same id in the DB.
	 * @param entity - the object to merge
	 */
	public synchronized void merge(final T entity) {
		try {
			em.merge(entity);
			em.flush();
		} catch (Exception e) {
			logger.error("Exception "+e);
		}
	}

}
