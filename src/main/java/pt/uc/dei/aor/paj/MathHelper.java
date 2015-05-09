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
			else if (lastEntry.charAt(lastEntry.length()-1) == '(') return false;
			else if (phase < 2) {
				entries.add(s);
			}
			else {
				return false;
			}
		}
		else if (isUnuaryOperator(s)) {
			if (phase == 2) return false;
			if (phase == 1) entries.add(s);
			else if (isUnuaryOperator(lastEntry)) {
				entries.remove(entries.size()-1);
				entries.add(s);
			}
			else if (isNumber(lastEntry)) entries.add(s);
			else if (lastEntry.charAt(lastEntry.length()-1) == '(') return false;
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
		else if (isFunction(s)) {
			if (phase == 1) {
				entries.add(0, s);
				entries.add(")");
			}
			else if (phase == 2) {
				entries.set(0, s);
			}
			else {
				if (lastEntry.equals("0")) entries.remove(entries.size()-1);
				entries.add(s);
			}
		}
		
		return true;
	}
	
	

	public static int evaluate(List<String> entries){
		int result = 2;
		String expression = formExpression(entries);
		expression = expression.replaceAll("mod", "%");
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
		entries.add(formatExpression(expression));
		return result;
	}
	
	
	
	// helper methods
	public static String formatExpression(String s) {
		String[] parts = s.split("\\.");
		try {
			if (parts.length == 2 && Integer.valueOf(parts[1]).equals(0)) {
				return parts[0];
			}
		}
		catch (Exception e) {
			return s;
		}
		return s;
	}
	
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
		List<String> binOperators = Arrays.asList(new String[]{"+", "-", "*", "/", "^", "mod"});
		return binOperators.contains(s);
	}
	
	private static boolean isUnuaryOperator(String s) {
		List<String> operators = Arrays.asList(new String[]{"^2"});
		return operators.contains(s);
	}
	
	private static boolean isFunction(String s) {
		List<String> functions = Arrays.asList(new String[]{"sin(", "cos(", "tan(", "atan(", "asin(", "acos(",
							"log(", "sqrt(", "log10("});
		return functions.contains(s);
	}
}
