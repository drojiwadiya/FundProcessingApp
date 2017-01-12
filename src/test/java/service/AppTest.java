package service;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

public class AppTest {

	private App app;
	private final ClassLoader classLoader = AppTest.class.getClassLoader();
	
	@Before
	public void setUp() throws Exception {
		app = new App();
		app.setFundCSVPath(classLoader.getResource("fund.csv").getPath());
		app.setBenchmarkReturnCSVPath(classLoader.getResource("BenchReturnSeries.csv").getPath());
		app.setFundReturnCSVPath(classLoader.getResource("FundReturnSeries.csv").getPath());
		app.setOutputCSVPath(classLoader.getResource(".").getPath()+"/monthlyOutperformance.csv");
	}

	@Test
	public void testGenerateReport() throws IOException {
		app.generateReport();
		Stream<String> fileStream = App.getFileStream(app.getOutputCSVPath());
		List<String> actual = fileStream.collect(Collectors.toList());
		
		fileStream = App.getFileStream(classLoader.getResource("expectedMonthlyOutperformance.csv").getPath());
		List<String> expected = fileStream.collect(Collectors.toList());
		assertEquals(expected, actual);
	}

}
