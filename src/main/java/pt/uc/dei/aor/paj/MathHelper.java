package pt.uc.dei.aor.paj;

import java.util.Arrays;
import java.util.List;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;

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
		
		if (getLastChar(lastEntry) == '.' && !(isNumber(s) || s.equals("+/-"))) {
			lastEntry = lastEntry.substring(0, lastEntry.length()-1);
			entries.set(entries.size()-1, lastEntry);
		}
		
		if (s.equals(".")) {
			if (isNumber(lastEntry) && phase == 0) {
				if (lastEntry.contains(".") || isLastEntryExponent(entries)) return false;
				else {
					lastEntry += ".";
					entries.set(entries.size()-1, lastEntry);
				}
			}
			else if (phase > 0) {
				entries.set(0, "0.");
			}
			else if (isUnuaryOperator(lastEntry)) return false;
			else if (isConstant(lastEntry)) {
				entries.add("*");
				entries.add("0.");
			}
			else {
				entries.add("0.");
			}
		}
		else if (s.equals("(")) {
			if (phase == 0) 
				entries.add("(");
			else
				entries.set(0, "(");
		}
		else if (s.equals(")")) {
			if (getLastChar(lastEntry) == '(' || phase > 0) return false;
			int counter = 0;
			for (String e : entries) {
				if (getLastChar(e) == '(') counter++;
				else if (getLastChar(e) == ')') counter--;
			}
			if (counter == 0) return false;
			entries.add(")");
		}
		else if (s.equals("E")) {
			if (isNumber(lastEntry) && !isLastEntryExponent(entries) && phase == 0) {
				entries.add("E");
			}
			else return false;
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
			else if (isConstant(lastEntry)) {
				entries.add("*");
				entries.add(s);
			}
			else if (isUnuaryOperator(lastEntry)) return false;
			else {
				entries.add(s);
			}
		}
		else if (isConstant(s)) {
			if (lastEntry.equals("E")) return false;
			if (lastEntry.equals("0")) {
				entries.set(entries.size()-1, s);
			}
			else if (phase > 0) {
				entries.set(0, s);
			}
			else if (isConstant(lastEntry)) {
				entries.add("*");
				entries.add(s);
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
			else if (isConstant(lastEntry)) {
				entries.add("*");
				entries.add(s);
			}
			else {
				if (lastEntry.equals("0")) entries.remove(entries.size()-1);
				entries.add(s);
			}
		}
		else if (s.equals("+/-")) {
			if (phase == 2) return false;
			else if (phase == 1) {
				if (entries.size() == 1 && !entries.get(0).equals("0")) {
					entries.add(0, "-");
				}
				else if (entries.size() == 2) {
					entries.remove(0);
				}
			}
			else {
				int lastExpQty = getLastExpression(entries);
				int index = entries.size()-lastExpQty;
				
				if (lastEntry.equals("0") || lastEntry.equals("0.") || lastExpQty == 0) return false;
				else if (getLastChar(lastEntry) == '.' || getLastChar(lastEntry) == '(') return false;
				else if (entries.size() == lastExpQty) {
					entries.add(0, "-");
				}
				else if (entries.get(index-1).equals("+")) {
					entries.set(index-1, "-");
				}
				else if (entries.get(index-1).equals("-")) {
					if (entries.size() == lastExpQty+1 || getLastChar(entries.get(index-2)) == '(')
						entries.remove(index-1);
					else
						entries.set(index-1, "+");
				}
				else if (isBinOperator(entries.get(index-1))){
					List<String> inner = entries.subList(0, entries.size()-1);
					int innerSize = getLastExpression(inner);
					if (entries.get(index).equals("(") && lastExpQty == innerSize+3 && entries.get(index+1).equals("-")) {
						entries.remove(index);
						entries.remove(index);
						entries.remove(index+innerSize);
					}
					else {
						entries.add(index, "(");
						entries.add(index+1, "-");
						entries.add(")");
					}
				}
				else if (getLastChar(entries.get(index-1)) == '(') {
					entries.add(index, "-");
				}
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
			.function(asinh).function(acosh).function(atanh)
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
	public static int getLastExpression(List<String> entries) {
		int index = entries.size()-1;
		if (index == -1) return 0;
		
		if (entries.get(index).equals(")")) {
			int counter = 1;
			int result = 1;
			index--;
			while (counter > 0) {
				String tmp = entries.get(index--);
				if (getLastChar(tmp) == '(') counter--;
				else if (tmp.equals(")")) counter++;
				result++;
			}
			return result;
		}
		else if (getLastChar(entries.get(index)) == '(') return 0;
		else if (isUnuaryOperator(entries.get(index))) return 0;
		else if (isConstant(entries.get(index))) {
			if (entries.size() > 3) {
				if (entries.get(index-2).equals("E")) return 4;
				if (isNumber(entries.get(index-1))) return 2;
			}
			else if (entries.size() > 1) {
				if (isNumber(entries.get(index-1))) return 2;
			}
			return 1;
		}
		else {
			if (isLastEntryExponent(entries)) {
				return 3;
			}
			return 1;
		}
	}
	
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
							"log(", "sqrt(", "log10(", "sinh(", "cosh(", "tanh(", "asinh(", "acosh(", "atanh("});
		return functions.contains(s);
	}
	
	private static boolean isConstant(String s) {
		List<String> constants = Arrays.asList(new String[]{"pi", "e", "\u03C0"});
		return constants.contains(s);
	}
	
	private static boolean isLastEntryExponent(List<String> entries) {
		if (entries.size() >= 3) {
			if (entries.get(entries.size()-2).equals("E")) return true;
		}
		return false;
	}
	
	private static char getLastChar(String s) {
		return s.charAt(s.length()-1);
	}
	
	
	// custom functions
	private static Function asinh = new Function("asinh", 1) {
		@Override
	    public double apply(double... args) {
	        double a = args[0];
	        return Math.log(a+Math.sqrt(1+a*a));
	    }
	};
	private static Function acosh = new Function("acosh", 1) {
		@Override
	    public double apply(double... args) {
	        double a = args[0];
	        return Math.log(a+Math.sqrt(a*a-1));
	    }
	};
	private static Function atanh = new Function("atanh", 1) {
		@Override
	    public double apply(double... args) {
	        double a = args[0];
	        return Math.log((1+a)/(1-a))/2;
	    }
	};
	
	// custom operators
}
