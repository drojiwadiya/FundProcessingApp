package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import model.FundPerformance;
import model.data.FundCSVData;
import service.App;

public class DataExtractorUtilTest {

	@Test
	public void testExtractFundData() throws IOException {

		Stream<String> fileStream = App.getFileStream(DataExtractorUtilTest.class.getClassLoader().getResource("fund.csv").getPath());
		Map<String, FundCSVData> extractFundData = DataExtractorUtil.extractFundData(fileStream);
		assertNotNull(extractFundData);
		assertEquals(6, extractFundData.size());
		assertNotNull(extractFundData.get("MF-1-31705"));
		assertEquals(new FundCSVData("MF-1-31705", "Pengana Global Small Companies Fund","BM-672"), 
				extractFundData.get("MF-1-31705"));
	}


	@Test
	public void testExtractBenchmarkReturnDataInTable() throws IOException {
		Stream<String> fileStream = App.getFileStream(DataExtractorUtilTest.class.getClassLoader().getResource("BenchReturnSeries.csv").getPath());
		
		Table<String, LocalDate, Double> bmReturnTable = HashBasedTable.create();

		DataExtractorUtil.extractBenchmarkReturnDataInTable(fileStream, bmReturnTable);
		assertEquals(14, bmReturnTable.size());
	}


	@Test
	public void testExtractFundReturnData() throws IOException {
		
		Stream<String> fileStream = App.getFileStream(DataExtractorUtilTest.class.getClassLoader().getResource("fund.csv").getPath());
		Map<String, FundCSVData> extractFundData = DataExtractorUtil.extractFundData(fileStream);
		
		
		fileStream = App.getFileStream(DataExtractorUtilTest.class.getClassLoader().getResource("BenchReturnSeries.csv").getPath());
		Table<String, LocalDate, Double> bmReturnTable = HashBasedTable.create();
		DataExtractorUtil.extractBenchmarkReturnDataInTable(fileStream, bmReturnTable);
		
		
		fileStream = App.getFileStream(DataExtractorUtilTest.class.getClassLoader().getResource("FundReturnSeries.csv").getPath());
		
		Map<LocalDate, List<FundPerformance>>  fundPerformanceData = 
				DataExtractorUtil.extractFundReturnData(fileStream,extractFundData,  bmReturnTable);
		
		//data for 6 individual dates   
		assertEquals(6, fundPerformanceData.size());
		List<FundPerformance> fundsOn_20160630 = fundPerformanceData.get(LocalDate.of(2016, 06, 30));
		assertEquals(6, fundsOn_20160630.size());
	}

}
