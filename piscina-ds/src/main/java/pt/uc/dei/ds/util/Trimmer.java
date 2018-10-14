package pt.uc.dei.ds.util;

public class Trimmer {
	
	public Trimmer(){	
	}

	public static String clean(String string) {
		if (string==null) {
			return "";
		}
		return string.trim().replaceAll("\\s{2,}", " ");
	}
}
