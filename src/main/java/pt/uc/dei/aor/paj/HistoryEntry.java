package pt.uc.dei.aor.paj;

public class HistoryEntry {
	public Screen expression;
	public String result;
	public String deltaT;
	
	
	public HistoryEntry(Screen expression, String result, String deltaT) {
		this.expression = expression;
		this.result = result;
		this.deltaT = deltaT;
	}


	public Screen getExpression() {
		return expression;
	}

	
	public void setExpression(Screen expression) {
		this.expression = expression;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	public String getDeltaT() {
		return deltaT;
	}


	public void setDeltaT(String deltaT) {
		this.deltaT = deltaT;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof HistoryEntry) {
			HistoryEntry o = (HistoryEntry) other;
			return o.expression.equals(expression);
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		return expression.hashCode();
	}
}
