package pt.piscina.itf.dtos;

import java.util.Date;

public class GraphTimeAllocPerc {
	
	private Date begin;
	private Date end;
	private Float allocPerc;
		
	public GraphTimeAllocPerc() {
	}

	public GraphTimeAllocPerc(Date begin, Date end, Float allocPerc) {
		this.begin = begin;
		this.end = end;
		this.allocPerc = allocPerc;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Float getAllocPerc() {
		return allocPerc;
	}

	public void setAllocPerc(Float allocPerc) {
		this.allocPerc = allocPerc;
	}

}
