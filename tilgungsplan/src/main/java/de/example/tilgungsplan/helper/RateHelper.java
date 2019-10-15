package de.example.tilgungsplan.helper;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;
import javax.money.MonetaryRounding;

import org.javamoney.moneta.Money;

/**
 * Helper methods for rate calculation.
 * 
 * @author FLo
 *
 */
public class RateHelper {

	private static MonetaryRounding rounding = Monetary.getDefaultRounding();

	private static MonetaryOperator roundDownOperator = (MonetaryAmount amount) -> {
		BigDecimal baseAmount = amount.getNumber().numberValue(BigDecimal.class);
		BigDecimal roundedDownAmount = baseAmount.setScale(2, RoundingMode.DOWN);
		return Money.of(roundedDownAmount, amount.getCurrency());
	};

	/**
	 * Calculates the interest per month based on the remaining debt and the
	 * borrowing rate. The interest is calculated per year and then divided by 12 to
	 * calculate its value per month.
	 * 
	 * @param remainingDebt the remaining debt
	 * @param borrowingRate the borrowing rate
	 * @return the interest per month
	 */
	public static MonetaryAmount getInterestPerMonth(MonetaryAmount remainingDebt, double borrowingRate) {

		MonetaryAmount interestPerYear = remainingDebt.multiply(borrowingRate / 100.00);
		return interestPerYear.divide(12).with(rounding);
	}

	/**
	 * Calculates the repayment per month for the given debt and the repayment rate.
	 * The calculated amount is rounded down.
	 * 
	 * @param debt          the debt
	 * @param repaymentRate the repayment rate
	 * @return the repayment per month
	 */
	public static MonetaryAmount getRepaymentPerMonth(MonetaryAmount debt, double repaymentRate) {
		MonetaryAmount repaymentPerYear = debt.multiply(repaymentRate).divide(100.00);
		return repaymentPerYear.divide(12).with(roundDownOperator);
	}

}
