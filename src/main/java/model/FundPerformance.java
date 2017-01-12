package model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class FundPerformance implements IFundPerformance {

	private String name;
	private LocalDate date;
	private double fundReturn;
	private double benchmarkReturn;

	public FundPerformance(String name, LocalDate date, double fundReturn, double benchmarkReturn) {
		this.name = name;
		this.date = date;
		this.fundReturn = fundReturn;
		this.benchmarkReturn = benchmarkReturn;
	}
	
	@Override
	public double getExcess() {
		BigDecimal bd = new BigDecimal(getFundReturn() - getBenchmarkReturn());
		bd = bd.setScale(2, RoundingMode.HALF_DOWN);
		return bd.doubleValue();
	}

	@Override
	public String getOutperformance() {
		double excess = getExcess();

		if (excess < -1) {
			return "underPerformed";
		} else if (excess > 1) {
			return "outPerformed";
		}
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(benchmarkReturn);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		temp = Double.doubleToLongBits(fundReturn);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FundPerformance other = (FundPerformance) obj;
		if (Double.doubleToLongBits(benchmarkReturn) != Double.doubleToLongBits(other.benchmarkReturn))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (Double.doubleToLongBits(fundReturn) != Double.doubleToLongBits(other.fundReturn))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "FundPerformance [name=" + name + ", date=" + date + ", fundReturn=" + fundReturn + ", benchmarkReturn="
				+ benchmarkReturn + "]";
	}

	public String getName() {
		return name;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getFundReturn() {
		return fundReturn;
	}

	public double getBenchmarkReturn() {
		return benchmarkReturn;
	}

}
