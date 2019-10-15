package de.example.tilgungsplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import de.example.tilgungsplan.helper.DateHelper;

public class DateHelperTest {

	@Test
	public void shouldGetLastDayOfMonth() {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfMonth(cal);

		assertEquals(2019, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.OCTOBER, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(31, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthOctober() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2019, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.NOVEMBER, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(30, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthDecember() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2020, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.JANUARY, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(31, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthFebruary() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2019, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.MARCH, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(31, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthFebruaryWithLeapYear() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2020, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.MARCH, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(31, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthJanuaryNonLeapYear() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2019, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.FEBRUARY, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(28, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthJanuaryWithLeapYear() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2020, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.FEBRUARY, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(29, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthJanuaryLastDate() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 31);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2019, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.FEBRUARY, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(28, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthOctoberLastDate() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DATE, 31);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2019, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.NOVEMBER, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(30, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfNextMonthDecemberLastDate() {
		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfNextMonth(cal);

		assertEquals(2020, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.JANUARY, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(31, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfMonthFebruaryIn2019() {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2019);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfMonth(cal);

		assertEquals(2019, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.FEBRUARY, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(28, lastDayOfMonth.get(Calendar.DATE));
	}

	@Test
	public void shouldGetLastDayOfMonthFebruaryIn2020() {

		Calendar cal = Calendar.getInstance();

		cal.set(Calendar.YEAR, 2020);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DATE, 14);

		Calendar lastDayOfMonth = DateHelper.getLastDayOfMonth(cal);

		assertEquals(2020, lastDayOfMonth.get(Calendar.YEAR));
		assertEquals(Calendar.FEBRUARY, lastDayOfMonth.get(Calendar.MONTH));
		assertEquals(29, lastDayOfMonth.get(Calendar.DATE));
	}

}
