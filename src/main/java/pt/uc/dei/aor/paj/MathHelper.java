package pt.uc.dei.aor.paj;

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
	
	public static boolean concat(List<String> entries, String s) {
		String lastEntry = entries.get(entries.size()-1);
		
		if (s.equals(".")) {
			try {
				Double.valueOf(lastEntry);
				if (lastEntry.contains(".")) return false;
				else {
					lastEntry += ".";
					entries.set(entries.size()-1, lastEntry);
				}
			}
			catch(NumberFormatException e) {
				entries.add("0.");
			}
		}
		else {
			entries.add(s);
		}
		return true;
	}
	
	public static boolean evaluate(List<String> entries){
		boolean result = false;
		String expression = formExpression(entries);
		try{
			Expression e = new ExpressionBuilder(expression)
			.variables("pi", "e")
			.build()
			.setVariable("pi", Math.PI)
			.setVariable("e", Math.E);
			try{
				expression = String.valueOf(e.evaluate());
				result = true;
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
	
}
