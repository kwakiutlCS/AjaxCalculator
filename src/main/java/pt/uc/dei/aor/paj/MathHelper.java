package pt.uc.dei.aor.paj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

public class MathHelper {
	
	public static final int MAX_SCREEN_SIZE = 22;

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
			if (lastEntry.equals("E")) return false;
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
				if (lastEntry.equals("0")) entries.set(entries.size()-1, "(");
				else entries.add("(");
			else
				entries.set(0, "(");
		}
		else if (s.equals(")")) {
			if (isBinOperator(lastEntry)) return false;
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
			else if (isNumber(lastEntry) || lastEntry.equals(")")) entries.add(s);
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
				if (!acceptsComma(s)) entries.add(")");
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
		else if (s.equals(",")) {
			if (!isCommaValid(entries)) return false;
			entries.add(",");
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
		if (getScreenSize(entries) > MAX_SCREEN_SIZE) {
			int size = entries.size();
			if (isNumber(entries.get(size-1))) {
				entries.set(size-1, entries.get(size-1).substring(0, entries.get(size-1).length()-1));
			}
			else entries.remove(size-1);
		}
		return true;
	}
	
	
	public static int evaluate(List<String> entries, AngleUnit angle) {
		complete(entries);
		List<String> direct = Arrays.asList(new String[]{"sin(", "cos(", "tan(", "sinh(", "cosh(", "tanh("});
		List<String> inverse = Arrays.asList(new String[]{"asin(", "acos(", "atan(", "asinh(", "acosh(", "atanh("});
		
		for (int i = entries.size()-1; i >= 0; i--) {
			if (direct.contains(entries.get(i))) {
				int close = getCloseParenthesis(entries, i);
				entries.add(close, ")");
				entries.add(i+1, "(");
				entries.add(i+1, "*");
				entries.add(i+1, String.valueOf(angle.getFactor()));
			}
		}
		for (int i = entries.size()-1; i >= 0; i--) {
			if (inverse.contains(entries.get(i))) {
				entries.add(i, "*");
				entries.add(i, String.valueOf(1/angle.getFactor()));
			}
		}
		
		return evaluate(entries);
	}
	
	private static void complete(List<String> entries) {
		int counter = 0;
		for (int i = 0; i < entries.size(); i++) {
			if (getLastChar(entries.get(i)) == '(') counter++;
			else if (entries.get(i).equals(")")) counter--;
		}
		
		while(counter-- > 0) {
			entries.add(")");
		}
	}

	public static int evaluate(List<String> entries){
		int result = 2;
		String expression = formExpression(entries);
		expression = expression.replaceAll("mod", "%");
		try{
			Expression e = new ExpressionBuilder(expression)
			.operator(factorial)
			.function(asinh).function(acosh).function(atanh).function(logb).function(root).function(comb).function(perm)
			.variables("pi", "e")
			.build()
			.setVariable("pi", Math.PI)
			.setVariable("e", Math.E);
			try{
				expression = String.valueOf(e.evaluate());
				result = 1;
				if (expression.equals("Infinity")) {
					expression = "limit exceeded";
					result = 2;
				}
				else if (expression.equals("NaN") || expression.equals("-Infinity")) {
					expression = "invalid arguments";
					result = 2;
				}
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
	private static int getCloseParenthesis(List<String> entries, int index) {
		int counter = 1;
		int i = index+1;
		
		while (counter > 0) {
			if (entries.get(i).equals("(")) counter++;
			else if (entries.get(i).equals(")")) counter--;
			i++;
		}
		return i;
	}
	
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
	
	public static boolean isNumber(String s) {
		try {
			Double.valueOf(s);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public static boolean isBinOperator(String s) {
		List<String> binOperators = Arrays.asList(new String[]{"+", "-", "*", "/", "^", "mod"});
		return binOperators.contains(s);
	}
	
	public static boolean isUnuaryOperator(String s) {
		List<String> operators = Arrays.asList(new String[]{"^2", "!"});
		return operators.contains(s);
	}
	
	public static boolean isFunction(String s) {
		List<String> functions = Arrays.asList(new String[]{"sin(", "cos(", "tan(", "atan(", "asin(", "acos(", "comb(", "perm(",
							"log(","root(", "logb(", "sqrt(", "log10(", "sinh(", "cosh(", "tanh(", "asinh(", "acosh(", "atanh("});
		return functions.contains(s);
	}
	
	public static boolean isConstant(String s) {
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
	
	private static int getScreenSize(List<String> entries) {
		String s = "";
		for (String x : entries) {
			s += x;
		}
		return s.length();
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
	
	private static Function logb = new Function("logb", 2) {
	    @Override
	    public double apply(double... args) {
	    	if (args[0] <= 0 || args[1] <= 0) throw new IllegalArgumentException("Invalid parameters for logb");
	        return Math.log(args[0]) / Math.log(args[1]);
	    }
	};
	
	private static Function root = new Function("root", 2) {
	    @Override
	    public double apply(double... args) {
	        return Math.pow(args[0], 1/args[1]);
	    }
	};
	
	private static Function comb = new Function("comb", 2) {
	    @Override
	    public double apply(double... args) {
	    	int n, r, k;
	    	try {
	    		n = (int) args[0];
	    		r = (int) args[1];
		    	k = n-r;
	    	}
	    	catch(Exception e) {
	    		throw new IllegalArgumentException("Arguments for combinations need to be integer");
	    	}
	    	if (n <= 0 || r <= 0 || k < 0) throw new IllegalArgumentException("Arguments for combinations need to be positive");
	    	
	        return getComb(n, r, k);
	    }
	};
	
	private static Function perm = new Function("perm", 2) {
	    @Override
	    public double apply(double... args) {
	    	int n, r, k;
	    	try {
	    		n = (int) args[0];
	    		r = (int) args[1];
		    	k = n-r;
	    	}
	    	catch(Exception e) {
	    		throw new IllegalArgumentException("Arguments for permutations need to be integer");
	    	}
	    	if (n <= 0 || r <= 0 || k < 0) throw new IllegalArgumentException("Arguments for permutations need to be positive");
	    	
	    	double fact = getFactorial(r);
	    	if (fact == Double.POSITIVE_INFINITY) return fact;
	        return getComb(n, r, k)*fact;
	    }
	};
	
	private static double getFactorial(int n) {
		if (n > 170) return Double.POSITIVE_INFINITY;
		double res = 1;
		for (int i = 1; i <= n; i++) {
			res *= i;
		}
		return res;
	}
	
	public static double getComb(int n, int r, int k) {
		int a, b;
		if (k > r) {
			a = k; 
			b = r;
		}
		else {
			b = k; 
			a = r;
		}
		
		List<Integer> top = new ArrayList<>();
		List<Integer> bottom = new ArrayList<>();
		for (int i = n; i > a; i--) {
			top.add(i);
		}
		for (int i = 1; i <= b; i++) {
			bottom.add(i);
		}
		
		for (int i = bottom.size()-1; i >= 0; i--) {
			for (int j = 0; j < top.size(); j++) {
				if (top.get(j) % bottom.get(i) == 0) {
					top.set(j, top.get(j)/bottom.get(i));
					bottom.remove(i);
					break;
				}
			}
		}
		
		double result = 1;
		for (int t : top) result *= t;
		for (int z : bottom) result /= z;
		return result;
	}
	
	public static double[] regression(double[] x, double[] y) {
		double xAvg = avg(x);
		double yAvg = avg(y);
		
		double[] xy = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			xy[i] = x[i]*y[i];
		}
		double xyAvg = avg(xy);
		
		double[] xx = new double[x.length];
		for (int i = 0; i < x.length; i++) {
			xx[i] = x[i]*x[i];
		}
		double xxAvg = avg(xx);
		
		double m = (xAvg*yAvg-xyAvg)/(xAvg*xAvg-xxAvg);
		double b = yAvg - m*xAvg;
				
		// SStotal
		double ssTotal = 0;
		for (int i = 0; i < y.length; i++) {
			ssTotal += (y[i]-yAvg)*(y[i]-yAvg);
		}
		
		// SSres
		double ssRes = 0;
		for (int i = 0; i < y.length; i++) {
			double f = x[i]*m+b;
			ssRes += (y[i]-f)*(y[i]-f);
		}
		
		double R2 = 1 - ssRes/ssTotal;
		return new double[]{m,b,R2};
	}
	
	public static double avg(double[] x) {
		double total = 0;
		for (double i : x) {
			total += i;
		}
		return total/x.length;
	}
	
	// custom operators
	public static Operator factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {

	    @Override
	    public double apply(double... args) {
	        final int arg = (int) args[0];
	        if (arg != args[0]) {
	            throw new IllegalArgumentException("Argument for factorial has to be an integer");
	        }
	        if (arg < 0) {
	            throw new IllegalArgumentException("Argument for factorial can not be negative");
	        }
	        if (arg > 170) return Double.POSITIVE_INFINITY;
	        double result = 1;
	        for (int i = 1; i <= arg; i++) {
	            result *= i;
	        }
	        return result;
	    }
	};

	public static boolean acceptsComma(String string) {
		List<String> functions = Arrays.asList(new String[]{"comb(", "root(", "perm(", "logb("});
		return functions.contains(string);
	}

	public static String formatDecimalNumber(String res) {
		if (res.indexOf('.') == -1) return res;
		
		String result = "";
		String[] dotSplit = res.split("\\.");
		result += dotSplit[0];
		
		if (dotSplit[1].indexOf('E') > 0) {
			String[] eSplit = dotSplit[1].split("E");
			if (eSplit[0].length() > 2) {
				result += "."+eSplit[0].substring(0, 2);
			}
			else {
				result += "."+eSplit[0];
			}
			result += "E"+eSplit[1];
		}
		else {
			if (dotSplit[1].length() > 2) {
				result += "."+dotSplit[1].substring(0, 2);
			}
			else {
				result += "."+dotSplit[1];
			}
		}
		
		return result;
	}
	
	
	private static boolean isCommaValid(List<String> entries) {
		int counter = 0;
		int index = entries.size()-1;
		String entry;
		
		while(index >= 0) {
			entry = entries.get(index);
			if (entry.equals(")")) counter++;
			else if (entry.equals(",") && counter == 0) return false;
			else if (entry.equals("(") || isFunction(entry)) {
				if (counter > 0) {
					counter--;
					index--;
					continue;
				}
				else {
					if (acceptsComma(entry)) return true;
					return false;
				}
			}
			index--;
		}
		
		return false;
	}
}
