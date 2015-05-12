package pt.uc.dei.aor.paj;

public class ExperimentalPoint implements Comparable<ExperimentalPoint> {
	private int id;
	private double x;
	private double y;
	
	public ExperimentalPoint(String s, int id) {
		this(Double.valueOf(s.split(",")[0]), Double.valueOf(s.split(",")[1]), id);
	}
	
	public ExperimentalPoint(double x, double y, int id) {
		this.setX(x);
		this.setY(y);
		this.setId(id);
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	@Override
	public int compareTo(ExperimentalPoint o) {
		if (x > o.x) return 1;
		return -1;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
