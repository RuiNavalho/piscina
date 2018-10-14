package pt.uc.dei.ds.daos;

import javax.ejb.Stateless;
import pt.uc.dei.ds.entities.Attachment;

@Stateless
public class AttachmentDao extends AbstractDao<Attachment>{

	private static final long serialVersionUID = 1L;

	public AttachmentDao() {
		super(Attachment.class);
	}

}
