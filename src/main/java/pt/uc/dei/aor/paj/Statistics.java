package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class Statistics {
	private List<Stat> stats;
	private List<String> symbols;
	private List<String> descriptions;
	
	public Statistics() {
		descriptions = Arrays.asList(new String[]{"Addition", "Subtraction", "Multiplication", "Division", "Sine",
				"Cosine", "Tangent", "Arcsine", "Arccosine", "Arctangent", "Logarithm base 10", "Natural logarithm",
				"Logarithm", "Square root", "Root", "Exponent", "Factorial", "Mod", "Permutations", "Combinations",
				"Hyperbolic sine",	"Hyperbolic cosine", "Hyperbolic tangent", 
				"Hyperbolic arcsine", "Hyperbolic arccosine", "Hyperbolic arctangent"});
		symbols = Arrays.asList(new String[]{"+", "-", "*", "/", "sin(", "cos(", "tan(","asin(", "acos(", "atan(", "log10(", 
				"log(","logb(", "sqrt(", "root(", "^", "!", "mod", "perm(", "comb(",
				"sinh(", "cosh(", "tanh(","asinh(", "acosh(", "atanh("});

		stats = Collections.synchronizedList(new ArrayList<Stat>());
		for (int i = 0; i < symbols.size(); i++) {
			stats.add(new Stat(descriptions.get(i), symbols.get(i), 0));
		}
	}
	
	
	public synchronized void add(List<String> entries) {
		for (Stat stat : stats) {
			stat.add(countOcurrences(entries, stat.getSymbol()));
		}
		Collections.sort(stats);
	}

	
	public List<Stat> getStats() {
		return stats;
	}


	public void setStats(List<Stat> stats) {
		this.stats = stats;
	}


	private int countOcurrences(List<String> entries, String sub) {
		int counter = 0;
		for (String s : entries) {
			if (s.equals(sub)) counter++;
			if (s.equals("^2") && sub.equals("^")) counter++;
		}
		if (sub.equals("*")) {
			for (int i = 0; i < entries.size()-1; i++) {
				if (MathHelper.isNumber(entries.get(i)) && MathHelper.isFunction(entries.get(i+1))) counter++;
				else if (MathHelper.isNumber(entries.get(i)) && MathHelper.isConstant(entries.get(i+1))) counter++;
				else if (MathHelper.isNumber(entries.get(i)) && entries.get(i+1).equals("(")) counter++;
				else if (MathHelper.isConstant(entries.get(i)) && MathHelper.isFunction(entries.get(i+1))) counter++;
				else if (MathHelper.isConstant(entries.get(i)) && entries.get(i+1).equals("(")) counter++;
				else if (MathHelper.isConstant(entries.get(i)) && MathHelper.isConstant(entries.get(i+1))) counter++;
				else if (MathHelper.isConstant(entries.get(i)) && MathHelper.isNumber(entries.get(i+1))) counter++;
				else if (entries.get(i).equals(")") && entries.get(i+1).equals("(")) counter++;
				else if (entries.get(i).equals(")") && MathHelper.isNumber(entries.get(i+1))) counter++;
				else if (entries.get(i).equals(")") && MathHelper.isConstant(entries.get(i+1))) counter++;
				else if (entries.get(i).equals(")") && MathHelper.isFunction(entries.get(i+1))) counter++;
				
			}
		}
		return counter;
	}


	public List<String> getSymbols() {
		return symbols;
	}

	public void setSymbols(List<String> symbols) {
		this.symbols = symbols;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}


	public void setDescriptions(List<String> descriptions) {
		this.descriptions = descriptions;
	}
	
}