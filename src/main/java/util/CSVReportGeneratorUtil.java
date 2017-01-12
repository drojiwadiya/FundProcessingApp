package util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import model.FundPerformanceReport;

public class CSVReportGeneratorUtil {

	public void generateReport(String outputFile, List<FundPerformanceReport> fundPerformanceReportData) throws IOException {
		try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
			String[] header = { "FundName", "Date", "Excess", "OutPerformance", "Return", "Rank" };
			writer.writeNext(header);

			for (FundPerformanceReport fundPerformance : fundPerformanceReportData) {

				String[] rowData = { fundPerformance.getName(), String.valueOf(fundPerformance.getDate()),
						String.valueOf(fundPerformance.getExcess()), fundPerformance.getOutperformance(),
						String.valueOf(fundPerformance.getFundReturn()), String.valueOf(fundPerformance.getRank()) };
				// Write the record to file
				writer.writeNext(rowData);
			}
		}
	}
}
