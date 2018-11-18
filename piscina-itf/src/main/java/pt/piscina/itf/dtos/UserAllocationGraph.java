package pt.piscina.itf.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserAllocationGraph {
	
	private String email;
	private String fullName;
	private float freeAllocationPercentage;
	private List<GraphTimeAllocPerc> xy;
	private boolean gotSkillNeededTask;
	
	public UserAllocationGraph() {
		this.email="";
		this.fullName="";
		this.freeAllocationPercentage=0;
		this.xy=new ArrayList<GraphTimeAllocPerc>();
		this.gotSkillNeededTask=false;
	}
	
	public UserAllocationGraph(String email, String fullName, float freeAllocationPercentage, List<GraphTimeAllocPerc> xy, boolean gotSkillNeededTask) {
		this.email = email;
		this.freeAllocationPercentage = freeAllocationPercentage;
		this.xy = xy;
		this.gotSkillNeededTask= gotSkillNeededTask;
		this.fullName=fullName;
	}
	
	public String toString() {
		return fullName+" - "+email;
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
	public float getFreeAllocationPercentage() {
		return freeAllocationPercentage;
	}
	public void setFreeAllocationPercentage(float freeAllocationPercentage) {
		this.freeAllocationPercentage = freeAllocationPercentage;
	}
	public List<GraphTimeAllocPerc> getXy() {
		return xy;
	}
	public void setXy(List<GraphTimeAllocPerc> xy) {
		this.xy = xy;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}
