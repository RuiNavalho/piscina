package pt.uc.dei.itf.dtos;

public class UserUpdateSelfDto {
	
	private String fullName;
	private byte[] photo;
	private String passw;
	
	public UserUpdateSelfDto() {
	}

	public UserUpdateSelfDto(String fullName, byte[] photo, String passw) {
		this.fullName = fullName;
		this.photo = photo;
		this.passw = passw;
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

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

}
