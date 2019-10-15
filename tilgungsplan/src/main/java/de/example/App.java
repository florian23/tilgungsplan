package de.example;

import java.io.File;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.example.tilgungsplan.HTMLWriter;
import de.example.tilgungsplan.RepaymentSchedule;

/**
 * Hello world!
 *
 */
public class App {

	protected static final Logger logger = LogManager.getLogger(App.class);

	public static void main(String[] args) {

		Options options = new Options();

		Option amountOpt = OptionBuilder.withArgName("amount").hasArg().withDescription("the loan amount")
				.create("amount");
		Option interestOpt = OptionBuilder.withArgName("interest").hasArg().withDescription("the debit interest")
				.create("interest");
		Option repaymentOpt = OptionBuilder.withArgName("repayment").hasArg().withDescription("the initial repayment")
				.create("repayment");
		Option durationOpt = OptionBuilder.withArgName("duration").hasArg().withDescription("the duration in years")
				.create("duration");

		Option filenameOpt = OptionBuilder.withArgName("filename").hasArg().withDescription("the filename")
				.create("filename");

		options.addOption(amountOpt);
		options.addOption(interestOpt);
		options.addOption(repaymentOpt);
		options.addOption(durationOpt);
		options.addOption(filenameOpt);

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
		} catch (ParseException e) {
			logger.warn("Could not parse arguments", e);
			return;
		}

		String amountStr = cmd.getOptionValue("amount");
		String interestStr = cmd.getOptionValue("interest");
		String repaymentStr = cmd.getOptionValue("repayment");
		String durationStr = cmd.getOptionValue("duration");

		MonetaryAmount amount = Monetary.getDefaultAmountFactory().setCurrency("EUR")
				.setNumber(Double.parseDouble(amountStr)).create();
		double interest = Double.parseDouble(interestStr);
		double repayment = Double.parseDouble(repaymentStr);
		Integer duration = Integer.parseInt(durationStr);

		RepaymentSchedule tp = new RepaymentSchedule(amount, interest, repayment, duration);

		tp.calculate();

		HTMLWriter writer;
		if (cmd.hasOption("filename")) {
			writer = new HTMLWriter(new File("."), cmd.getOptionValue("filename"));
		} else {
			writer = new HTMLWriter(new File("."));
		}

		writer.writeRepaymentScheduleToHTML(tp);
	}
}
