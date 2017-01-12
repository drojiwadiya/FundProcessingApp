package service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class GenerateOutperformanceReportTest {

	private App generateOutperformanceReport;
	private final ClassLoader classLoader = GenerateOutperformanceReportTest.class.getClassLoader();
	
	@Before
	public void setUp() throws Exception {
		generateOutperformanceReport = new App();
		generateOutperformanceReport.setFundCSVPath(classLoader.getResource("fund.csv").getPath());
		generateOutperformanceReport.setBenchmarkReturnCSVPath(classLoader.getResource("BenchReturnSeries.csv").getPath());
		generateOutperformanceReport.setFundReturnCSVPath(classLoader.getResource("FundReturnSeries.csv").getPath());
		generateOutperformanceReport.setOutputCSVPath(classLoader.getResource(".").getPath()+"/monthlyOutperformance.csv");
	}

	@Test
	public void testGenerateReport() throws IOException {
		generateOutperformanceReport.generateReport();
		Stream<String> fileStream = App.getFileStream(generateOutperformanceReport.getOutputCSVPath());
		List<String> actual = fileStream.collect(Collectors.toList());
		
		fileStream = App.getFileStream(classLoader.getResource("expectedMonthlyOutperformance.csv").getPath());
		List<String> expected = fileStream.collect(Collectors.toList());
		assertEquals(expected, actual);
	}

}
