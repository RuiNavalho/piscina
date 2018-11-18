package pt.piscina.itf.dtos;

public class ClientLightDto {
	
	private String company;
	private BusinessDto businessDto;
	
	public ClientLightDto() {
	}
	
	public ClientLightDto(String company, BusinessDto businessDto) {
		this.company = company;
		this.businessDto = businessDto;
	}
	
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public BusinessDto getBusinessDto() {
		return businessDto;
	}
	public void setBusinessDto(BusinessDto businessDto) {
		this.businessDto = businessDto;
	}

}
