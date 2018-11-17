package pt.piscina.ds.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeCalc {
	
	public TimeCalc(){	
	}
	
	public static Timestamp todayMidnight() throws ParseException {
		Timestamp today= null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		today = new Timestamp(sdf.parse(sdf.format( new Date() )).getTime());
		return today;
	}
	
	public static Timestamp tomorrowMidnight() throws ParseException {
		Timestamp tomorrow= null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date day = new Date();	
		tomorrow = new Timestamp(sdf.parse(sdf.format( new Date(day.getTime() + TimeUnit.DAYS.toMillis(1)) )).getTime());
		return tomorrow;
	}
	
	public static Timestamp yesterdayMidnight() throws ParseException {
		Timestamp yesterday= null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date day = new Date();	
		yesterday = new Timestamp(sdf.parse(sdf.format( new Date(day.getTime() - TimeUnit.DAYS.toMillis(1)) )).getTime());
		return yesterday;
	}

}
