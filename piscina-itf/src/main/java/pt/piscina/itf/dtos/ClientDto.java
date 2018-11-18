package pt.piscina.itf.dtos;

public class ClientDto {
	
	private String address;
	private String company;
	private String contact;
	private String phone;
	private BusinessDto businessDto;
	private int numeroProjetos;
	private float budgetTotal;

	public ClientDto() {
	}

	public ClientDto(String address, String company, String contact, String phone, BusinessDto businessDto, int numeroProjetos,
			float budgetTotal) {
		this.address = address;
		this.company = company;
		this.contact = contact;
		this.phone = phone;
		this.businessDto = businessDto;
		this.numeroProjetos = numeroProjetos;
		this.budgetTotal = budgetTotal;
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

	public BusinessDto getBusinessDto() {
		return businessDto;
	}

	public void setBusinessDto(BusinessDto businessDto) {
		this.businessDto = businessDto;
	}

	public int getNumeroProjetos() {
		return numeroProjetos;
	}

	public void setNumeroProjetos(int numeroProjetos) {
		this.numeroProjetos = numeroProjetos;
	}

	public float getBudgetTotal() {
		return budgetTotal;
	}

	public void setBudgetTotal(float budgetTotal) {
		this.budgetTotal = budgetTotal;
	}
	
}
