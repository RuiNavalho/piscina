package pt.uc.dei.itf.charts;

import java.util.Date;

public class CpiSpiDto {
	
	private Date date;
	private Double cpi;
	private Double spi;
	
	public CpiSpiDto() {
	}
	
	public CpiSpiDto(Date date, Double cpi, Double spi) {
		this.date = date;
		this.cpi = cpi;
		this.spi=spi;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Double getCpi() {
		return cpi;
	}
	public void setCpi(Double cpi) {
		this.cpi = cpi;
	}
	public Double getSpi() {
		return spi;
	}
	public void setSpi(Double spi) {
		this.spi = spi;
	}

}
