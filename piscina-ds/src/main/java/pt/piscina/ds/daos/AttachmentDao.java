package pt.piscina.ds.daos;

import javax.ejb.Stateless;

import pt.piscina.ds.entities.Attachment;

@Stateless
public class AttachmentDao extends AbstractDao<Attachment>{

	private static final long serialVersionUID = 1L;

	public AttachmentDao() {
		super(Attachment.class);
	}

}
