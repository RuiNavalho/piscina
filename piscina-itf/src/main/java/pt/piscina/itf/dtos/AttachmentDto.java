package pt.piscina.itf.dtos;

public class AttachmentDto {
	
	private byte[] document;
	private String documentName;
	
	public AttachmentDto() {
	}

	public AttachmentDto(byte[] document, String documentName) {
		this.document = document;
		this.documentName = documentName;
	}

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

}
