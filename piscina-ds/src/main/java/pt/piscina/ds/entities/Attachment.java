package pt.piscina.ds.entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the attachment database table.
 * 
 */
/*
@Entity
@NamedQuery(name="Attachment.findAll", query="SELECT a FROM Attachment a")
*/
public class Attachment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;

	@Lob
	private byte[] document;
	
	private String documentName;

	//bi-directional many-to-one association to Taskwork
	@ManyToOne
	private Taskwork taskwork;

	public Attachment() {
	}
	
	public Attachment(byte[] document, Taskwork taskwork, String documentName) {
		this.document = document;
		this.taskwork = taskwork;
		this.documentName=documentName;
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getDocument() {
		return this.document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public Taskwork getTaskwork() {
		return this.taskwork;
	}

	public void setTaskwork(Taskwork taskwork) {
		this.taskwork = taskwork;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

}