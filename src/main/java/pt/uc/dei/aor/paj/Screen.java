package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Named
@SessionScoped
public class Screen implements Serializable {
	private static final long serialVersionUID = 1L;
	private String expression;
	private List<String> entries;
	private int phase;
	
	public Screen() {
		entries = new LinkedList<>();
		entries.add("0");
		expression = MathHelper.formExpression(entries);
		phase = 0;
	}

	
	public void concat(String s) {
		if (MathHelper.concat(entries, s)) {
			expression = MathHelper.formExpression(entries);
		}
	}
	
	public void remove() {
		if (entries.size() == 1) clear();
		else entries.remove(entries.size()-1);
		expression = MathHelper.formExpression(entries);
	}
	
	public void clear() {
		entries.clear();
		entries.add("0");
		expression = MathHelper.formExpression(entries);
	}
	
	public boolean evaluate() {
		boolean result = MathHelper.evaluate(entries);
		expression = MathHelper.formExpression(entries);
		
		return result;
	}
	
	public Screen getClone() {
		Screen sc = new Screen();
		sc.entries.clear();
		for (String e : entries) {
			sc.entries.add(e);
			sc.expression = MathHelper.formExpression(sc.entries);
		}
		return sc;
	}
	
	
	@Override
	public String toString() {
		return expression;
	}
	
	
	
	// Getters and Setters
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public List<String> getEntries() {
		return entries;
	}

	public void setEntries(List<String> entries) {
		this.entries = entries;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}
}
