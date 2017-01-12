package model;

import java.time.LocalDate;

public class FundPerformanceReport implements IFundPerformanceReport {
	
	private FundPerformance fundPerformance;
	private int rank;

	public FundPerformanceReport(FundPerformance fundPerformance, int rank) {
		this.fundPerformance = fundPerformance;
		this.rank = rank;
	}

	@Override
	public String getName() {
		return fundPerformance.getName();
	}

	@Override
	public LocalDate getDate() {
		return fundPerformance.getDate();
	}

	@Override
	public double getFundReturn() {
		return fundPerformance.getFundReturn();
	}

	@Override
	public double getExcess() {
		return fundPerformance.getExcess();
	}

	@Override
	public String getOutperformance() {
		return fundPerformance.getOutperformance();
	}

	@Override
	public int getRank() {
		return rank;
	}
}
