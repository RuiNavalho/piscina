package pt.uc.dei.itf.dtos;

import java.util.List;

public class TaskworkNewDto {
	
	private int allocationId;
	private String comments;
	private int hoursWorked;
	private int remainingHours;
	private int expectedRemainingHours;
	private List<byte[]> documents;
	private List<String> documentNames;
	
	public TaskworkNewDto() {
	}
	
	public TaskworkNewDto(int allocationId, String comments, int hoursWorked, int remainingHours,
			int expectedRemainingHours, List<byte[]> documents, List<String> documentNames) {
		this.allocationId = allocationId;
		this.comments = comments;
		this.hoursWorked = hoursWorked;
		this.remainingHours = remainingHours;
		this.expectedRemainingHours = expectedRemainingHours;
		this.documents = documents;
		this.documentNames=documentNames;
	}

	public int getAllocationId() {
		return allocationId;
	}
	public void setAllocationId(int allocationId) {
		this.allocationId = allocationId;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public int getHoursWorked() {
		return hoursWorked;
	}
	public void setHoursWorked(int hoursWorked) {
		this.hoursWorked = hoursWorked;
	}
	public List<byte[]> getDocuments() {
		return documents;
	}
	public void setDocuments(List<byte[]> documents) {
		this.documents = documents;
	}

	public int getRemainingHours() {
		return remainingHours;
	}

	public void setRemainingHours(int remainingHours) {
		this.remainingHours = remainingHours;
	}

	public int getExpectedRemainingHours() {
		return expectedRemainingHours;
	}

	public void setExpectedRemainingHours(int expectedRemainingHours) {
		this.expectedRemainingHours = expectedRemainingHours;
	}

	public List<String> getDocumentNames() {
		return documentNames;
	}

	public void setDocumentNames(List<String> documentNames) {
		this.documentNames = documentNames;
	}

}
