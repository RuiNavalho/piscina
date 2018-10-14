package pt.uc.dei.itf.charts;

import java.util.List;

public class ChartCpiSpiDto {
	
	private String title;
	private List<CpiSpiDto> list;
	
	public ChartCpiSpiDto() {
	}
	
	public ChartCpiSpiDto(String title, List<CpiSpiDto> list) {
		this.title = title;
		this.list = list;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<CpiSpiDto> getList() {
		return list;
	}
	public void setList(List<CpiSpiDto> list) {
		this.list = list;
	}

}
