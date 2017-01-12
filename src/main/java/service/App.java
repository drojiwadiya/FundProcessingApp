package service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import model.FundPerformance;
import model.FundPerformanceReport;
import model.data.FundCSVData;
import util.CSVReportGeneratorUtil;
import util.DataExtractorUtil;

public class App {

	private static final Table<String, LocalDate, Double> bmReturnTable = HashBasedTable.create();

	@Parameter(names = { "--help", "-h" }, help = true)
	private boolean help = false;
	
	@Parameter(names={"--fundCSVPath", "-f"}, description = "Fund CSV File name with full path. ex: /../fund.csv ")
    private String fundCSVPath;
	
    @Parameter(names={"--benchmarkReturnCSVPath", "-bmr"}, description = "BenchmarkReturn CSV File name with full path.")
    private String benchmarkReturnCSVPath;
    
    @Parameter(names={"--fundReturnCSVPath", "-fr"}, description = "FundReturn CSV File name with full path.")
    private String fundReturnCSVPath;
    
    @Parameter(names={"--outputCSVPath", "-o"}, description = "File name of the generated report file with full path.")
    private String outputCSVPath;
    
	public static void main(String[] args) throws IOException {
		
		App main = new App();
		JCommander jCommander = new JCommander(main,  args);
		if (main.help || StringUtils.isBlank(main.fundCSVPath) 
				||  StringUtils.isBlank(main.benchmarkReturnCSVPath) || StringUtils.isBlank(main.fundReturnCSVPath) || StringUtils.isBlank(main.outputCSVPath)) {
			jCommander.usage();
			return;
		}
		main.generateReport();
	}

	public void generateReport() throws IOException {
		Stream<String> fundCSV = getFileStream(fundCSVPath);
		Stream<String> benchmarkReturnCSV = getFileStream(benchmarkReturnCSVPath);
		Stream<String> fundReturnCSV = getFileStream(fundReturnCSVPath);
		
		Map<String, FundCSVData> funds = DataExtractorUtil.extractFundData(fundCSV);
		DataExtractorUtil.extractBenchmarkReturnDataInTable(benchmarkReturnCSV,bmReturnTable);
		Map<LocalDate, List<FundPerformance>> fundPerformanceMap  = 
				DataExtractorUtil.extractFundReturnData(fundReturnCSV, funds, bmReturnTable);

		CSVReportGeneratorUtil reportGeneratorUtil = new CSVReportGeneratorUtil();
		List<FundPerformanceReport> fundPerformanceReportData = generateCSVReportData(fundPerformanceMap);
		
		reportGeneratorUtil.generateReport(outputCSVPath, fundPerformanceReportData);
	}
	
	private List<FundPerformanceReport> generateCSVReportData(Map<LocalDate, List<FundPerformance>> fundPerformanceMap)
			throws IOException {

		List<FundPerformanceReport> fundPerformanceReportData = new ArrayList<FundPerformanceReport>();
		List<LocalDate> dates = fundPerformanceMap.keySet().stream().sorted((e1, e2) -> -1 * e1.compareTo(e2))
				.collect(Collectors.toList());

		for (LocalDate dateKey : dates) {
			List<FundPerformance> list = fundPerformanceMap.get(dateKey);
			list = list.stream().sorted(Comparator.comparingDouble(FundPerformance::getFundReturn).reversed())
					.collect(Collectors.toList());
			int rank = 0;
			Double previousFundReturn = null;
			for (FundPerformance fundPerformance : list) {
				if (previousFundReturn == null || fundPerformance.getFundReturn() != previousFundReturn) {
					rank++;
				}
				fundPerformanceReportData.add(new FundPerformanceReport(fundPerformance, rank));
				previousFundReturn = fundPerformance.getFundReturn();
			}
		}
		return fundPerformanceReportData;
	}
	
	public static Stream<String> getFileStream(String filePath) throws IOException{
		Path path = Paths.get(filePath);
		if (Files.exists(path)) {
			return Files.lines(path);
		} else {
			throw new FileNotFoundException(filePath);
		}
	}

	public void setFundCSVPath(String fundCSVPath) {
		this.fundCSVPath = fundCSVPath;
	}

	public void setBenchmarkReturnCSVPath(String benchmarkReturnCSVPath) {
		this.benchmarkReturnCSVPath = benchmarkReturnCSVPath;
	}

	public void setFundReturnCSVPath(String fundReturnCSVPath) {
		this.fundReturnCSVPath = fundReturnCSVPath;
	}

	public void setOutputCSVPath(String outputCSVPath) {
		this.outputCSVPath = outputCSVPath;
	}

	public String getOutputCSVPath() {
		return outputCSVPath;
	}

}
