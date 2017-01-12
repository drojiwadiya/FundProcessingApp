package model.data;

public class FundCSVData {

	private String code;
	private String benchmarkCode;
	private String name;

	public FundCSVData(String code, String name, String benchmarkCode) {
		this.name = name;
		this.code = code;
		this.benchmarkCode = benchmarkCode;
	}

	public String getCode() {
		return code;
	}

	public String getBenchmarkCode() {
		return benchmarkCode;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Fund [code=" + code + ", benchmarkCode=" + benchmarkCode + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((benchmarkCode == null) ? 0 : benchmarkCode.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		FundCSVData other = (FundCSVData) obj;
		if (benchmarkCode == null) {
			if (other.benchmarkCode != null)
				return false;
		} else if (!benchmarkCode.equals(other.benchmarkCode))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
