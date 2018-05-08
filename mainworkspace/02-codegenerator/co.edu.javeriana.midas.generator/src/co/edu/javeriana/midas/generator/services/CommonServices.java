package co.edu.javeriana.midas.generator.services;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CommonServices {
	public String getDate() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 1);
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy_MM_dd");
		String formatedDate = format1.format(cal.getTime());
		return formatedDate + "_100000";
	}

}
