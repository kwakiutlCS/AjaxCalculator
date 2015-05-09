package pt.uc.dei.aor.paj;

import java.util.Arrays;
import java.util.List;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MathHelper {
	
	public static String formExpression(List<String> entries) {
		String expression = "";
		for (String s : entries) {
			expression += s;
		}
		return expression;
	}
	
	public static boolean concat(List<String> entries, String s, int phase) {
		String lastEntry = entries.get(entries.size()-1);
		
		if (s.equals(".")) {
			if (isNumber(lastEntry) && phase == 0) {
				if (lastEntry.contains(".")) return false;
				else {
					lastEntry += ".";
					entries.set(entries.size()-1, lastEntry);
				}
			}
			else if (phase > 0) {
				entries.set(0, "0.");
			}
			else {
				entries.add("0.");
			}
		}
		else if (isBinOperator(s)) {
			if (isBinOperator(lastEntry)) {
				entries.remove(entries.size()-1);
				entries.add(s);
			}
			else if (phase < 2) {
				entries.add(s);
			}
			else {
				return false;
			}
		}
		else if (isNumber(s)) {
			if (isNumber(lastEntry) && phase == 0) {
				if (lastEntry.equals("0"))
					lastEntry = s;
				else 
					lastEntry += s;
				entries.set(entries.size()-1, lastEntry);
			}
			else if (phase > 0) {
				entries.set(0, s);
			}
			else {
				entries.add(s);
			}
		}
		
		return true;
	}
	
	

	public static int evaluate(List<String> entries){
		int result = 2;
		String expression = formExpression(entries);
		try{
			Expression e = new ExpressionBuilder(expression)
			.variables("pi", "e")
			.build()
			.setVariable("pi", Math.PI)
			.setVariable("e", Math.E);
			try{
				expression = String.valueOf(e.evaluate());
				result = 1;
			} catch (Exception exp){
				expression = exp.getMessage();
			}	
		} catch (Exception exp){
			expression = exp.getMessage();
		}	
		
		entries.clear();
		entries.add(expression);
		return result;
	}
	
	
	
	// helper methods
	private static boolean isNumber(String s) {
		try {
			Double.valueOf(s);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	private static boolean isBinOperator(String s) {
		List<String> binOperators = Arrays.asList(new String[]{"+", "-", "*", "/", "^", "%"});
		
		return binOperators.contains(s);
	}
	
	
}
