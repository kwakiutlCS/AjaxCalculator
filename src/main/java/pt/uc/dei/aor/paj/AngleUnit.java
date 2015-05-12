package pt.uc.dei.aor.paj;


public class AngleUnit {
	private String name;
	private double factor;
	
	public AngleUnit(String name, double factor) {
		this.name = name;
		this.factor = factor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getFactor() {
		return factor;
	}

	public void setFactor(double factor) {
		this.factor = factor;
	}
	
	@Override
	public String toString() { return name; }
}
