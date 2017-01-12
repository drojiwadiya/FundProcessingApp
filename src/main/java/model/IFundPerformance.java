package model;

import java.time.LocalDate;

public interface IFundPerformance {
	
	String getName();

	LocalDate getDate();

	double getFundReturn();

	double getExcess();

	String getOutperformance();
}
