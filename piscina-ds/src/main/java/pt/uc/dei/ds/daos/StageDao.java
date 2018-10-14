package pt.uc.dei.ds.daos;

import javax.persistence.TypedQuery;
import org.apache.log4j.Logger;
import pt.uc.dei.ds.entities.Stage;

public class StageDao extends AbstractDao<Stage>{

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = Logger.getLogger(StageDao.class);

	public StageDao() {
		super(Stage.class);
	}

	public Stage findStageByName(String stage) {
		try {
			TypedQuery<Stage> stageWithName = em.createNamedQuery("Stage.findStageByName", Stage.class);
			stageWithName.setParameter("stage", stage);
			return stageWithName.getSingleResult();
		} catch (Exception e) {
			logger.error("Exception "+e);
			return null;
		}
	}
	
}
