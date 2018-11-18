package pt.piscina.itf.dtos;

public class TypologyDto {
	
	private String typology;

	public TypologyDto() {
	}

	public TypologyDto(String typology) {
		this.typology = typology;
	}

	public String getTypology() {
		return typology;
	}

	public void setTypology(String typology) {
		this.typology = typology;
	}
	
	public String toString() {
		return this.typology;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((typology == null) ? 0 : typology.hashCode());
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
		TypologyDto other = (TypologyDto) obj;
		if (typology == null) {
			if (other.typology != null)
				return false;
		} else if (!typology.equals(other.typology))
			return false;
		return true;
	}

}
