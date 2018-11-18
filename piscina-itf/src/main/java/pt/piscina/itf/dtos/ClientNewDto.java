package pt.piscina.itf.dtos;

public class ClientNewDto {
	
	private String address;
	private String company;
	private String contact;
	private String phone;
	private String business;

	public ClientNewDto(){	
	}

	public ClientNewDto(String address, String company, String contact, String phone, String business) {
		this.address = address;
		this.company = company;
		this.contact = contact;
		this.phone = phone;
		this.business = business;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}
	
}
