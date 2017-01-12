package model.data;

import java.time.LocalDate;

public class FundReturnCSVData {

	private String code;
	private LocalDate date;
	private double returnPercentage;

	public FundReturnCSVData(String code, LocalDate date, double returnPercentage) {
		this.code = code;
		this.date = date;
		this.returnPercentage = returnPercentage;
	}

	public String getCode() {
		return code;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getReturnPercentage() {
		return returnPercentage;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((date == null) ? 0 : date.hashCode());
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
		FundReturnCSVData other = (FundReturnCSVData) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		return true;
	}

}
