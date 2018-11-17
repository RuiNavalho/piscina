package pt.piscina.ds.util;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.TriggeringEventEvaluator;

public class MyEvaluator implements TriggeringEventEvaluator {

	@Override
	public boolean isTriggeringEvent(LoggingEvent arg0) {
		return true;
	} 

}
