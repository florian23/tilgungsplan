package de.example.tilgungsplan;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.money.MonetaryOperator;

import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import de.example.tilgungsplan.helper.RateHelper;

/**
 * Verifies that the RateHelper works correctly
 * 
 * @author FLo
 *
 */
public class RateHelperTest {

	/**
	 * Verifies that the correct rate is calculated for the first month.
	 * 
	 */
	@Test
	public void shouldCalculateInterestForFirstMonth() {
		MonetaryAmount remainingDebt = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double borrowingRate = 2.12;

		MonetaryAmount interestFirstMonth = RateHelper.getInterestPerMonth(remainingDebt, borrowingRate);

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(176.67).create(),
				interestFirstMonth);

	}

	/**
	 * Verifies that the correct rate is calculated for the second month.
	 * 
	 */
	@Test
	public void shouldCalculateInterestForSecondMonth() {
		MonetaryAmount remainingDebt = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(99833.34)
				.create();
		double borrowingRate = 2.12;

		MonetaryAmount interestFirstMonth = RateHelper.getInterestPerMonth(remainingDebt, borrowingRate);

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(176.37).create(),
				interestFirstMonth);

	}

	/**
	 * Verifies that the correct rate is calculated for the third month.
	 * 
	 */
	@Test
	public void shouldCalculateInterestForLastMonth() {
		MonetaryAmount remainingDebt = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(77949.76)
				.create();
		double borrowingRate = 2.12;

		MonetaryAmount interestFirstMonth = RateHelper.getInterestPerMonth(remainingDebt, borrowingRate);

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(137.71).create(),
				interestFirstMonth);

	}

	@Test
	public void shouldCalculateRepaymentForGivenDebtAndRepaymentRatePerMonth() {
		MonetaryAmount debt = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(100000).create();
		double repaymentRate = 2.00;

		MonetaryAmount repaymentPerMonth = RateHelper.getRepaymentPerMonth(debt, repaymentRate);

		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(166.66).create(),
				repaymentPerMonth);
	}

	@Test
	public void shouldRoundDown() {
		MonetaryOperator roundDownOperator = (MonetaryAmount amount) -> {
			BigDecimal baseAmount = amount.getNumber().numberValue(BigDecimal.class);
			BigDecimal roundedDownAmount = baseAmount.setScale(2, RoundingMode.DOWN);
			return Money.of(roundedDownAmount, amount.getCurrency());
		};

		MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1337.137).create();
		assertEquals(Monetary.getDefaultAmountFactory().setCurrency("EUR").setNumber(1337.13).create(),
				amount.with(roundDownOperator));
	}
}
