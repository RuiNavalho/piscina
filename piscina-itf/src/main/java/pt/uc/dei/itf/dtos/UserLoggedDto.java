package pt.uc.dei.itf.dtos;

import java.io.Serializable;
import java.util.List;

public class UserLoggedDto implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String email;
	private String fullName;
	private byte[] photo;
	private List<RoleDto> roles;
	private List<SkillDto> skills;
	
	public UserLoggedDto(){
	}

	public UserLoggedDto(String email, String fullName, byte[] photo, List<RoleDto> roles, List<SkillDto> skills) {
		this.email = email;
		this.fullName = fullName;
		this.photo = photo;
		this.roles = roles;
		this.skills = skills;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public List<RoleDto> getRoles() {
		return roles;
	}

	public void setRoles(List<RoleDto> roles) {
		this.roles = roles;
	}

	public List<SkillDto> getSkills() {
		return skills;
	}

	public void setSkills(List<SkillDto> skills) {
		this.skills = skills;
	}
}
