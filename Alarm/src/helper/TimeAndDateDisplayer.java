package helper;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class TimeAndDateDisplayer {
	private static final String[] DAY_OF_WEEK = {"SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
	private static final String[] MONTH = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private static DecimalFormat df = new DecimalFormat("00");

	public static String clockDisplay(boolean is24) {
		GregorianCalendar cal = new GregorianCalendar();
		String hour24 = cal.get(Calendar.AM_PM) == 0 ? df.format(cal.get(Calendar.HOUR)) : df.format(cal.get(Calendar.HOUR) + 12);
		String hour12 = df.format(cal.get(Calendar.HOUR)).equals("0") ? "12" : df.format(cal.get(Calendar.HOUR));
		String minute = df.format(cal.get(Calendar.MINUTE));
		String second = df.format(cal.get(Calendar.SECOND));
		String amPm = cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM";
		String currentTime24 = hour24 + " : " + minute + " : " + second + " ";
		String currentTime12 = hour12 + " : " + minute + " : " + second + " " + amPm;
		String currentTime;
		currentTime = is24 ? currentTime24 : currentTime12;

		return currentTime;
	}

	public static String dateDisplay() {
		GregorianCalendar cal = new GregorianCalendar();

		int year = cal.get(Calendar.YEAR);
		String dayOfWeek = DAY_OF_WEEK[cal.get(Calendar.DAY_OF_WEEK)];
		String month = MONTH[cal.get(Calendar.MONTH)];

		String date = df.format(cal.get(Calendar.DATE));
		return dayOfWeek + " - " + month + " " + date + ". " + year;
	}
}
