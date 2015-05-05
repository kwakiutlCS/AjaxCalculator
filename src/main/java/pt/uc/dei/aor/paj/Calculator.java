package projecto2;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

@Named
@SessionScoped
public class Calculator implements Serializable{
	
	/**
	 * 
	 */
	//Variable declaration
	private static final long serialVersionUID = 3883884812159598031L;
	private String expression;
	private boolean calculado=false;	

	@Inject History hist;
	@Inject Statistic stat;

	

	//Constructor
	public Calculator() {
		expression="0";
	}
	
	//Returns expression
	public String getExpression() {
		return expression;
	}

	//Expression setter	
	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	//Add another part to string (user input)
	public void add(String srt){
		if(calculado == true){
			expression = "0";
			calculado = false;
		}
		if (expression.equals("0")){
			expression="";
		}
		if (expression.length()+srt.length()<25){
			expression+=srt;
		}
	}
	
	//Method that receives string from History and adds to expression
	public void submitOp(ValueChangeEvent vce){
		if (expression.equals("0")){
			expression="";
		}
		
		calculado = false;
		if (expression.length()+(vce.getNewValue().toString().length())<25){
			expression += vce.getNewValue().toString();
		}
	}

	
	//Clear calc when user hits 'C'
	public String clearCalc(){
		expression="0";
		return expression;
	}
	
	//Calculate the expression using exp4j
	public void calcExp(){
		try{
			Expression e = new ExpressionBuilder(expression)
			.variables("pi", "e")
			.build()
			.setVariable("pi", Math.PI)
			.setVariable("e", Math.E);
			String aux = expression;
			try{
				expression = String.valueOf(e.evaluate());
				hist.addHist(aux);
				stat.addStat(aux);
			} catch (Exception exp){
				expression = exp.getMessage();
			}	
		} catch (Exception exp){
			expression = exp.getMessage();
		}	
		calculado=true;
	}
	
}
