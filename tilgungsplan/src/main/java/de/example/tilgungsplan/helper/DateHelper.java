package de.example.tilgungsplan.helper;

import java.util.Calendar;

/**
 * Helper for anything regarding dates.
 * 
 * @author FLo
 *
 */
public class DateHelper {

	/**
	 * Returns the last day of the month for the given cal
	 * 
	 * @param cal a calendar entry
	 * @return the last day of the month for the given cal
	 */
	public static Calendar getLastDayOfMonth(Calendar cal) {
		Calendar lastDayOfMonthCal = Calendar.getInstance();

		lastDayOfMonthCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		lastDayOfMonthCal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		lastDayOfMonthCal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));

		return lastDayOfMonthCal;
	}

	/**
	 * Returns the calendar object representing the last day of the current month
	 * 
	 * @return calendar object representing the last day of the current month
	 */
	public static Calendar getLastDayOfThisMonth() {
		Calendar lastDayOfMonthCal = Calendar.getInstance();

		lastDayOfMonthCal.set(Calendar.DATE, lastDayOfMonthCal.getActualMaximum(Calendar.DATE));

		return lastDayOfMonthCal;
	}

	/**
	 * Returns the last day of the next month of the given calendar object
	 * 
	 * @param cal a calendar object
	 * @return last day of the next month of the cal object
	 */
	public static Calendar getLastDayOfNextMonth(Calendar cal) {
		Calendar lastDayOfNextMonthCal = Calendar.getInstance();

		lastDayOfNextMonthCal.set(Calendar.YEAR, cal.get(Calendar.YEAR));
		lastDayOfNextMonthCal.set(Calendar.MONTH, cal.get(Calendar.MONTH));
		lastDayOfNextMonthCal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DATE));

		lastDayOfNextMonthCal.add(Calendar.MONTH, 1);
		lastDayOfNextMonthCal.set(Calendar.DATE, lastDayOfNextMonthCal.getActualMaximum(Calendar.DATE));

		return lastDayOfNextMonthCal;
	}

}
