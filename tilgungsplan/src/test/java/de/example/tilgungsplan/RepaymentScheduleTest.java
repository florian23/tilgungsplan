package de.example.tilgungsplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;
import java.util.List;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.junit.jupiter.api.Test;

import de.example.tilgungsplan.helper.DateHelper;

public class RepaymentScheduleTest {

	@Test
	public void shouldCalculateTheFixedRatePerMonth() {
		MonetaryAmount debt = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		RepaymentSchedule tp = new RepaymentSchedule(debt, 2.12, 2.0, 10);

		MonetaryAmount fixedRatePerMonth = tp.calculateFixedRatePerMonth();

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				fixedRatePerMonth);
	}

	@Test
	public void shouldCalculateSecondEntry() {

		Calendar lastDayOfMonth = DateHelper.getLastDayOfThisMonth();
		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		MonetaryAmount rate = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create();
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);
		RepaymentScheduleEntry entry = new RepaymentScheduleEntry(lastDayOfMonth, loanAmount.multiply(-1),
				Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create(), loanAmount.multiply(-1),
				loanAmount.multiply(-1));

		RepaymentScheduleEntry nextEntry = tp.nextEntry(entry, interestPerYear, rate);

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-99833.34).create(),
				nextEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(176.67).create(),
				nextEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(166.66).create(),
				nextEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				nextEntry.getRate());
	}

	@Test
	public void shouldCalculateThirdEntry() {

		Calendar lastDayOfMonth = DateHelper.getLastDayOfThisMonth();
		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		MonetaryAmount rate = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create();
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);
		RepaymentScheduleEntry entry = new RepaymentScheduleEntry(lastDayOfMonth, loanAmount.multiply(-1),
				Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create(), loanAmount.multiply(-1),
				loanAmount.multiply(-1));

		RepaymentScheduleEntry nextEntry = tp.nextEntry(entry, interestPerYear, rate);
		nextEntry = tp.nextEntry(nextEntry, interestPerYear, rate);

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-99666.38).create(),
				nextEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(176.37).create(),
				nextEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(166.96).create(),
				nextEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				nextEntry.getRate());
	}

	@Test
	public void shouldCalculateSecondToLastEntryAfter10Years() {

		Calendar lastDayOfMonth = DateHelper.getLastDayOfThisMonth();
		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		MonetaryAmount rate = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create();
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);
		RepaymentScheduleEntry entry = new RepaymentScheduleEntry(lastDayOfMonth, loanAmount.multiply(-1),
				Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create(), loanAmount.multiply(-1),
				loanAmount.multiply(-1));
		RepaymentScheduleEntry nextEntry = entry;
		for (int i = 0; i < (12 * 10 - 1); i++) {
			nextEntry = tp.nextEntry(nextEntry, interestPerYear, rate);
		}

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-77949.76).create(),
				nextEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(138.07).create(),
				nextEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(205.26).create(),
				nextEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				nextEntry.getRate());
	}

	@Test
	public void shouldCalculateLastEntryAfter10Years() {

		Calendar lastDayOfMonth = DateHelper.getLastDayOfThisMonth();
		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		MonetaryAmount rate = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create();
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);
		RepaymentScheduleEntry entry = new RepaymentScheduleEntry(lastDayOfMonth, loanAmount.multiply(-1),
				Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create(), loanAmount.multiply(-1),
				loanAmount.multiply(-1));
		;
		RepaymentScheduleEntry nextEntry = entry;
		for (int i = 0; i < (12 * 10); i++) {
			nextEntry = tp.nextEntry(nextEntry, interestPerYear, rate);
		}

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-77744.14).create(),
				nextEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(137.71).create(),
				nextEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(205.62).create(),
				nextEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				nextEntry.getRate());
	}

	@Test
	public void shouldCalculateTilgungsplan() {
		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);

		tp.calculate();

		List<RepaymentScheduleEntry> entries = tp.getEntries();

		RepaymentScheduleEntry firstEntry = entries.get(0);
		RepaymentScheduleEntry secondEntry = entries.get(1);
		RepaymentScheduleEntry thirdEntry = entries.get(2);
		RepaymentScheduleEntry secondToLastEntry = entries.get(entries.size() - 2);
		RepaymentScheduleEntry lastEntry = entries.get(entries.size() - 1);

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-100000.00).create(),
				firstEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create(),
				firstEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-100000.00).create(),
				firstEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-100000.00).create(),
				firstEntry.getRate());

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-99833.34).create(),
				secondEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(176.67).create(),
				secondEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(166.66).create(),
				secondEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				secondEntry.getRate());

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-99666.38).create(),
				thirdEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(176.37).create(),
				thirdEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(166.96).create(),
				thirdEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				thirdEntry.getRate());

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-77949.76).create(),
				secondToLastEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(138.07).create(),
				secondToLastEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(205.26).create(),
				secondToLastEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				secondToLastEntry.getRate());

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(-77744.14).create(),
				lastEntry.getRemainingDebt());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(137.71).create(),
				lastEntry.getInterest());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(205.62).create(),
				lastEntry.getRepayment());
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(343.33).create(),
				lastEntry.getRate());

	}

	@Test
	public void shouldCalculateTotalInterest() {

		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);

		tp.calculate();

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(18943.74).create(),
				tp.getTotalInterest());

	}

	@Test
	public void shouldCalculateTotalRepayment() {

		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);

		tp.calculate();

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(22255.86).create(),
				tp.getTotalRepayment());

	}

	@Test
	public void shouldCalculateTotalRate() {

		MonetaryAmount loanAmount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double interestPerYear = 2.12;
		double repayment = 2.00;
		RepaymentSchedule tp = new RepaymentSchedule(loanAmount, interestPerYear, repayment, 10);

		tp.calculate();

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(41199.60).create(),
				tp.getTotalRate());

	}

}
