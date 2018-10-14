package pt.uc.dei.itf.dtos;

import java.util.List;

public class UserProfileDto {
	
	private boolean active;
	private String email;
	private String fullName;
	private byte[] photo;
	private List<RoleDto> roles;
	private List<SkillDto> skills;
	private String passw;
	
	public UserProfileDto() {	
	}

	public UserProfileDto(boolean active, String email, String fullName, byte[] photo, List<RoleDto> roles,
			List<SkillDto> skills, String passw) {
		this.active = active;
		this.email = email;
		this.fullName = fullName;
		this.photo = photo;
		this.roles = roles;
		this.skills = skills;
		this.passw=passw;
	}

	//////////////////////////////////////////////////////////////////////////////////
	///////////////////////////    GETTERS and SETTERS   /////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}
	
}

