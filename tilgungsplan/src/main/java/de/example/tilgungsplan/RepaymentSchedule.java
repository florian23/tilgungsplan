package de.example.tilgungsplan;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import de.example.tilgungsplan.helper.DateHelper;
import de.example.tilgungsplan.helper.RateHelper;

/**
 * This class represents a repayment schedule. It holds all the necessary
 * information to calculate a repayment schedule for a given duration, a given
 * interest in percentage and a given starting repayment in percentage.
 * 
 * @author FLo
 *
 */
public class RepaymentSchedule {

	private MonetaryAmount loanAmount;

	private double interestPerYear;

	private double startingRepayment;

	private Integer numberOfYears;

	private MonetaryAmount fixedRatePerMonth;

	private List<RepaymentScheduleEntry> entries;

	/**
	 * Returns all entries of the repayment schedule
	 * 
	 * @return
	 */
	public List<RepaymentScheduleEntry> getEntries() {
		return entries;
	}

	/**
	 * Creates a repayment schedule based on the given initial values of the loan
	 * amount in euros, the interest (Zins) per year, the starting repayment rate
	 * per year and the number of years for how long this repayment schedule should
	 * last.
	 * 
	 * @param loanAmount        the loan amount in euros
	 * @param interestPerYear   the interest per year
	 * @param startingRepayment the staring repayment
	 * @param numberOfYears     the number of years for how long the repayment
	 *                          schedule should last
	 */
	public RepaymentSchedule(MonetaryAmount loanAmount, double interestPerYear, double startingRepayment,
			Integer numberOfYears) {

		this.loanAmount = loanAmount;
		this.interestPerYear = interestPerYear;
		this.startingRepayment = startingRepayment;
		this.numberOfYears = numberOfYears;

		this.entries = new ArrayList<RepaymentScheduleEntry>();

	}

	/**
	 * Calculates the repayment schedule based on its initial values.
	 */
	public void calculate() {
		// Calculate the fixedRatePerMonth
		this.fixedRatePerMonth = calculateFixedRatePerMonth();

		// Create the first entry as starting point
		Calendar lastDayOfThisMonth = DateHelper.getLastDayOfThisMonth();
		RepaymentScheduleEntry firstEntry = new RepaymentScheduleEntry(lastDayOfThisMonth, loanAmount.multiply(-1.0),
				Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create(),
				loanAmount.multiply(-1.0), loanAmount.multiply(-1.0));
		entries.add(firstEntry);
		RepaymentScheduleEntry currentEntry = firstEntry;
		for (int i = 0; i < 12 * this.numberOfYears; i++) {
			currentEntry = nextEntry(currentEntry);
			this.entries.add(currentEntry);
		}

	}

	/**
	 * Calculates the total interest
	 * 
	 * @return the total interest
	 */
	public MonetaryAmount getTotalInterest() {
		MonetaryAmount totalInterest = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create();
		for (RepaymentScheduleEntry tpe : getEntries()) {
			totalInterest = totalInterest.add(tpe.getInterest());
		}
		return totalInterest;
	}

	/**
	 * Gets the total repayment value of this repayment schedule
	 * 
	 * @return the total repayment value
	 */
	public MonetaryAmount getTotalRepayment() {
		MonetaryAmount totalRepayment = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create();
		for (RepaymentScheduleEntry tpe : getEntries()) {
			totalRepayment = totalRepayment.add(tpe.getRepayment());
		}
		return totalRepayment.add(loanAmount);
	}

	/**
	 * Gets the total rate as summed up value over the total duration
	 * 
	 * @return the total rate
	 */
	public MonetaryAmount getTotalRate() {
		MonetaryAmount totalRate = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(0.00).create();
		for (RepaymentScheduleEntry tpe : getEntries()) {
			totalRate = totalRate.add(tpe.getRate());
		}
		return totalRate.add(loanAmount);
	}

	/**
	 * Calculates the fixed rate per month for this repayment plan. The value is
	 * calculated based on the initial loan amount, the interest per month and the
	 * first repayment value per month
	 * 
	 * @return the fixed rate per month for this repayment plan
	 */
	protected MonetaryAmount calculateFixedRatePerMonth() {
		return calculateFixedRatePerMonth(loanAmount, interestPerYear, startingRepayment);
	}

	/**
	 * Calculates the fixed rate per month. The value is calculated based on the
	 * initial loan amount, the interest per month and the first repayment value per
	 * month
	 * 
	 * @param loanAmount2        the loanAmount
	 * @param interestPerYear2   the interest per year
	 * @param startingRepayment2 the starting repayment rate per year
	 * @return the fixed rate per month based on the given values
	 */
	private MonetaryAmount calculateFixedRatePerMonth(MonetaryAmount loanAmount2, double interestPerYear2,
			double startingRepayment2) {
		MonetaryAmount interestPerMonth = RateHelper.getInterestPerMonth(loanAmount2, interestPerYear2);
		MonetaryAmount repaymentPerMonth = RateHelper.getRepaymentPerMonth(loanAmount2, startingRepayment2);
		return interestPerMonth.add(repaymentPerMonth);
	}

	/**
	 * Calculates the next repayment schedule entry based on a given repayment
	 * schedule entry and the interestPerYear and rate of this repayment schedule
	 * 
	 * @param entry the current entry
	 * @return the next entry
	 */
	protected RepaymentScheduleEntry nextEntry(RepaymentScheduleEntry entry) {
		return nextEntry(entry, this.interestPerYear, this.fixedRatePerMonth);
	}

	/**
	 * Calculates the next repayment schedule entry based on a given "Tilgungsplan"
	 * entry and the interestPerYear and rate
	 * 
	 * @param entry           the current entry
	 * @param interestPerYear the interest per year as percent
	 * @param rate            the total rate per year in euro
	 * @return the next entry based on the given values.
	 */
	protected RepaymentScheduleEntry nextEntry(RepaymentScheduleEntry entry, double interestPerYear,
			MonetaryAmount rate) {
		MonetaryAmount loan = entry.getRemainingDebt();
		Calendar nextMonth = DateHelper.getLastDayOfNextMonth(entry.getDate());
		MonetaryAmount interest = RateHelper.getInterestPerMonth(loan.abs(), interestPerYear);
		MonetaryAmount repayment = rate.subtract(interest);
		MonetaryAmount remainingDebt = loan.add(repayment);
		RepaymentScheduleEntry nextEntry = new RepaymentScheduleEntry(nextMonth, remainingDebt, interest, repayment,
				rate);
		return nextEntry;
	}

}
