package de.example.tilgungsplan;

import java.util.Calendar;

import javax.money.MonetaryAmount;

/**
 * This class holds all the information for a single entry in a repayment
 * schedule.
 * 
 * @author FLo
 *
 */
public class RepaymentScheduleEntry {

	private Calendar date;

	private MonetaryAmount remainingDebt;

	private MonetaryAmount interest;

	private MonetaryAmount repayment;

	private MonetaryAmount rate;

	public RepaymentScheduleEntry(Calendar date, MonetaryAmount monetaryAmount, MonetaryAmount monetaryAmount2,
			MonetaryAmount monetaryAmount3, MonetaryAmount monetaryAmount4) {
		super();
		this.date = date;
		this.remainingDebt = monetaryAmount;
		this.interest = monetaryAmount2;
		this.repayment = monetaryAmount3;
		this.rate = monetaryAmount4;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public MonetaryAmount getRemainingDebt() {
		return remainingDebt;
	}

	public void setRemainingDebt(MonetaryAmount remainingDebt) {
		this.remainingDebt = remainingDebt;
	}

	public MonetaryAmount getInterest() {
		return interest;
	}

	public void setInterest(MonetaryAmount interest) {
		this.interest = interest;
	}

	public MonetaryAmount getRepayment() {
		return repayment;
	}

	public void setRepayment(MonetaryAmount repayment) {
		this.repayment = repayment;
	}

	public MonetaryAmount getRate() {
		return rate;
	}

	public void setRate(MonetaryAmount rate) {
		this.rate = rate;
	}

}
