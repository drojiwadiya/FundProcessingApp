package model.data;

import java.time.LocalDate;

public class BenchmarkReturnCSVData {

	private String code;
	private LocalDate date;
	private Double benchmarkReturn;

	public BenchmarkReturnCSVData(String code, LocalDate date, Double benchmarkReturn) {
		this.code = code;
		this.date = date;
		this.benchmarkReturn = benchmarkReturn;
	}

	public String getCode() {
		return code;
	}

	public LocalDate getDate() {
		return date;
	}

	public Double getBenchmarkReturn() {
		return benchmarkReturn;
	}

	@Override
	public String toString() {
		return "BenchmarkReturnData [code=" + code + ", date=" + date + ", benchmarkReturn=" + benchmarkReturn + "]";
	}

}
