package pt.piscina.itf.dtos;

public class UserNewDto {
	
	private String email;
	private String fullName;
	private byte[] photo;
	private String passw;
	
	public UserNewDto() {	
	}

	public UserNewDto(String email, String fullName, byte[] photo, String passw) {
		this.email = email;
		this.fullName = fullName;
		this.photo = photo;
		this.passw = passw;
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

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

}
