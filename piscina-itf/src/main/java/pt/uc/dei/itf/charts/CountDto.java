package pt.uc.dei.itf.charts;

public class CountDto {
	
	private String title;
	private int count;
	
	public CountDto() {
	}
	
	public CountDto(String title, int count) {
		this.title = title;
		this.count = count;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
