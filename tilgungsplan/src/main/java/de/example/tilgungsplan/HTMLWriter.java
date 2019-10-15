package de.example.tilgungsplan;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Writes a repayment schedule to a HTML document
 * 
 * @author FLo
 *
 */
public class HTMLWriter {

	private static final Logger logger = LogManager.getLogger(HTMLWriter.class);

	private File outputDirectory;

	private String filename;

	private MonetaryAmountFormat fmt = MonetaryFormats.getAmountFormat(Locale.GERMANY);

	/**
	 * Creates a HTMLWriter object with the given outputDirectory
	 * 
	 * @param outputDirectory
	 */
	public HTMLWriter(File outputDirectory) {
		this.outputDirectory = outputDirectory;
		this.filename = null;
	}

	/**
	 * Creates a HTMLWriter object with the given outputDirectory
	 * 
	 * @param outputDirectory
	 */
	public HTMLWriter(File outputDirectory, String filename) {
		this.outputDirectory = outputDirectory;
		this.filename = filename;
	}

	/**
	 * Writes the repayment schedule to the outputdirectory
	 * 
	 * @param repaymentSchedule the repayment schedule
	 */
	public void writeRepaymentScheduleToHTML(RepaymentSchedule repaymentSchedule) {
		StringBuilder htmlStringBuilder = new StringBuilder();

		htmlStringBuilder.append("<html><head><title>Tilgungsplan </title></head>");

		// append body
		htmlStringBuilder.append("<body>");
		// append table
		htmlStringBuilder.append("<table border=\"1\" bordercolor=\"#000000\">");

		// write table header entry
		htmlStringBuilder.append(
				"<tr><td><b>Datum</b></td><td><b>Restschuld</b></td><td><b>Zinsen</b></td><td><b>Tilgung(+)/Auszahlung(-)</b></td><td><b>Rate</b></td></tr>");
		// close html file
		for (RepaymentScheduleEntry tpe : repaymentSchedule.getEntries()) {
			htmlStringBuilder.append("<tr><td>" + tpe.getDate().get(Calendar.DATE) + "."
					+ (tpe.getDate().get(Calendar.MONTH) + 1) + "." + tpe.getDate().get(Calendar.YEAR) + "</td><td>"
					+ fmt.format(tpe.getRemainingDebt()) + "</td><td>" + fmt.format(tpe.getInterest()) + "</td><td>"
					+ fmt.format(tpe.getRepayment()) + "</td><td>" + fmt.format(tpe.getRate()) + "</td></tr>");
		}
		htmlStringBuilder.append("<tr><td>Zinsbindungsende</td><td>"
				+ fmt.format(repaymentSchedule.getEntries().get(repaymentSchedule.getEntries().size() - 1)
						.getRemainingDebt())
				+ "</td><td>" + fmt.format(repaymentSchedule.getTotalInterest()) + "</td><td>"
				+ fmt.format(repaymentSchedule.getTotalRepayment()) + "</td><td>"
				+ fmt.format(repaymentSchedule.getTotalRate()) + "</td></tr>");
		htmlStringBuilder.append("</table></body></html>");

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd_M_yyyy_hh_mm_ss");
			File outputFile;
			if (this.filename == null) {
				outputFile = new File(outputDirectory,
						"tilgungsplan_" + sdf.format(Calendar.getInstance().getTime()) + ".html");
			} else {
				outputFile = new File(outputDirectory, this.filename + ".html");
			}

			logger.info("Write to file " + outputFile.getAbsolutePath());

			FileUtils.write(outputFile, htmlStringBuilder.toString(), "UTF-8");
		} catch (IOException e) {
			logger.info("Could not write html file", e);
		}

	}

}
