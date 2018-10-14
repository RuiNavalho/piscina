package pt.uc.dei.itf.dtos;

import java.util.ArrayList;
import java.util.List;

public class ChartUserAllocations {
	
	private boolean gotSkillNeededTask;
	private String email;
	private String fullName;
	private float freePercentage;
	private List<DatePercDto> dateAlloc;

	public ChartUserAllocations() {
		this.gotSkillNeededTask = false;
		this.email = "";
		this.fullName = "";
		this.freePercentage = 0;
		this.dateAlloc = new ArrayList<DatePercDto> ();
	}

	public ChartUserAllocations(boolean gotSkillNeededTask, String email, String fullName, float freePercentage,
			List<DatePercDto> dateAlloc) {
		this.gotSkillNeededTask = gotSkillNeededTask;
		this.email = email;
		this.fullName = fullName;
		this.freePercentage = freePercentage;
		this.dateAlloc = dateAlloc;
	}

	public boolean isGotSkillNeededTask() {
		return gotSkillNeededTask;
	}

	public void setGotSkillNeededTask(boolean gotSkillNeededTask) {
		this.gotSkillNeededTask = gotSkillNeededTask;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public float getFreePercentage() {
		return freePercentage;
	}

	public void setFreePercentage(float freePercentage) {
		this.freePercentage = freePercentage;
	}

	public List<DatePercDto> getDateAlloc() {
		return dateAlloc;
	}

	public void setDateAlloc(List<DatePercDto> dateAlloc) {
		this.dateAlloc = dateAlloc;
	}

}
