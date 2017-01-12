package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Table;

import model.FundPerformance;
import model.data.BenchmarkReturnCSVData;
import model.data.FundCSVData;

public class DataExtractorUtil {

	public static Map<String, FundCSVData> extractFundData(Stream<String> lines) {
		
		return lines.skip(1).parallel().map((line) -> {
			String[] f = line.split(",");
			return new FundCSVData(StringUtils.trim(f[0]), StringUtils.trim(f[1]),StringUtils.trim(f[2]));
		}).collect(Collectors.toMap(fund -> fund.getCode(), fund -> fund));
	}

	public static void extractBenchmarkReturnDataInTable(Stream<String> lines,
			Table<String, LocalDate, Double> bmReturnTable) {

		List<BenchmarkReturnCSVData> benchmarkReturnCSVData = lines.parallel().skip(1).map((line) -> {
			String[] b = line.split(",");
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate dateTime = null;
			try {
				dateTime = LocalDate.parse(StringUtils.trim(b[1]), formatter);
	        } catch (DateTimeParseException ex) {
	        	System.out.println("DateFormat exception while parsing BenchmarkReturnSeries file expected format is yyyy-MM-dd");
	            throw ex;
	        } 
		
			return new BenchmarkReturnCSVData(StringUtils.trim(b[0]), dateTime, Double.valueOf(StringUtils.trim(b[2])));
		}).collect(Collectors.toList());
		
		benchmarkReturnCSVData.forEach(b -> {
			bmReturnTable.put(b.getCode(), b.getDate(), b.getBenchmarkReturn());
		});
	}

	public static Map<LocalDate, List<FundPerformance>> extractFundReturnData(Stream<String> lines,
			Map<String, FundCSVData> funds, Table<String, LocalDate, Double> bmReturnTable) {

		Set<FundPerformance> fundPerformanceData = lines.skip(1).parallel().map((line) -> line.split(",")).filter(b -> {
			FundCSVData fund = funds.get(StringUtils.trim(b[0]));
			if(fund == null){
				System.out.println("Ignore : No associated fund founf");
				return false;
			}
			LocalDate dateTime = null;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			try {
				dateTime = LocalDate.parse(StringUtils.trim(b[1]), formatter);
	        } catch (DateTimeParseException ex) {
	        	System.out.println("DateFormat exception while parsing FundReturnSeries file expected format is dd/MM/yyyy");
	            throw ex;
	        } 
			if (bmReturnTable.get(fund.getBenchmarkCode(), dateTime) == null) {
				System.out.println("Ignore : No associated benchmarkreturn found for fundreturn");
				return false;
			}
			return true;
		}).map(b -> {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate dateTime = LocalDate.parse(StringUtils.trim(b[1]), formatter);
			FundCSVData fund = funds.get(StringUtils.trim(b[0]));
			return new FundPerformance(fund.getName(), dateTime, Double.valueOf(StringUtils.trim(b[2])),
					bmReturnTable.get(fund.getBenchmarkCode(), dateTime));
		}).collect(Collectors.toSet());

		Map<LocalDate, List<FundPerformance>> fundPerformanceMap = fundPerformanceData.stream()
				.collect(Collectors.groupingBy(FundPerformance::getDate));
		return fundPerformanceMap;
	}
}
