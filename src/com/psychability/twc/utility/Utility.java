package com.psychability.twc.utility;

import java.util.Calendar;
import java.util.UUID;

public class Utility {
	public static int getWeekHour(Calendar runningCalendar) {
		int dayOfWeek = runningCalendar.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = runningCalendar.get(Calendar.HOUR_OF_DAY);
		return ((dayOfWeek - 1) * 24) + hourOfDay;
	}

	public static String getRandomUUID(int length) {
		return (UUID.randomUUID().toString() + UUID.randomUUID().toString())
				.toUpperCase().replaceAll("-", "").substring(0, length);
	}
}
