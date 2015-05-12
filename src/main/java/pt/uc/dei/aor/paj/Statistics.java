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
		descriptions = Arrays.asList(new String[]{"Adição", "Subtracção", "Multiplicação", "Divisão", "Seno",
				"Coseno", "Tangente", "Arco seno", "Arco coseno", "Arco tangente", "Logaritmo base 10", "Logaritmo natural", "Raiz quadrada", "Expoente", "Factorial"});
		symbols = Arrays.asList(new String[]{"+", "-", "*", "/", "sin", "cos", "tan","asin", "acos", "atan", "log10(", 
				"log(", "sqrt(", "^", "!"});

		stats = new ArrayList<>();
		for (int i = 0; i < symbols.size(); i++) {
			stats.add(new Stat(descriptions.get(i), symbols.get(i), 0));
		}
	}
	
	
	public void add(String s) {
		for (Stat stat : stats) {
			stat.add(countOcurrences(s, stat.getSymbol()));
		}
		sort();
	}

	
	public List<Stat> getStats() {
		return stats;
	}


	public void setStats(List<Stat> stats) {
		this.stats = stats;
	}


	private int countOcurrences(String s, String sub) {
		int c = 0;
		int lastIndex = 0;
		int i;
		while ((i = s.indexOf(sub, lastIndex)) != -1 && (i == 0 || s.charAt(i-1) != 'a')) {
			c++;
			lastIndex = s.indexOf(sub, lastIndex)+1;
		}
		
		return c;
	}

	public void sort() {
		Collections.sort(stats);
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