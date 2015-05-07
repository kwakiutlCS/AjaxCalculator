package pt.uc.dei.aor.paj;

public class Stat implements Comparable<Stat> {
	private String description;
	private String symbol;
	private int counter;

	public Stat(String description, String symbol, int counter) {
		this.description = description;
		this.symbol = symbol;
		this.counter = counter;
	}

	@Override
	public int compareTo(Stat o) {
		if (counter > o.counter) return -1;
		else if (counter < o.counter) return 1;
		return description.compareTo(o.description);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Stat) {
			Stat other = (Stat) o;
			return symbol.equals(other.symbol);
		}
		return false;
	}
	
	
	public void add(int c) {
		counter += c;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
	public int getCounter() { return counter; }
	public String getDescription() { return description; }
	public String getSymbol() { return symbol; }
	
}
