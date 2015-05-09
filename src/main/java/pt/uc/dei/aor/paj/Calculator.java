package pt.uc.dei.aor.paj;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class Calculator {
	
	//Variable declaration
	@Inject Screen screen;
	@Inject History hist;
	@Inject Statistic stat;
	@Inject Statistics2 stat2;
	

	
	//Add another part to string (user input)
	public void add(String srt){
		screen.concat(srt);
	}
	
//	//Method that receives string from History and adds to expression
//	public void submitOp(ValueChangeEvent vce){
//		if (expression.equals("0")){
//			expression="";
//		}
//		
//		calculado = false;
//		if (expression.length()+(vce.getNewValue().toString().length())<25){
//			expression += vce.getNewValue().toString();
//		}
//	}
//
//	//Method that receives string from History and adds to expression
//		public void submitOp2(String exp){
//			if (expression.equals("0")){
//				expression="";
//			}
//			
//			calculado = false;
//			if (expression.length()+(exp.length())<25){
//				expression += exp.toString();
//			}
//		}
//		
	//Clear calc when user hits 'C'
	public void clearCalc(){
		screen.clear();
	}
	
	//Calculate the expression using exp4j
	public void calcExp(){
		Screen aux = screen.getClone();
		if (screen.evaluate()) {
			hist.addEntry(aux, screen.getExpression(), 0L);
			
		}
		
//		try{
//			long initialTime = System.nanoTime();
//			Expression e = new ExpressionBuilder(expression)
//			.variables("pi", "e")
//			.build()
//			.setVariable("pi", Math.PI)
//			.setVariable("e", Math.E);
//			String aux = expression;
//			try{
//				expression = String.valueOf(e.evaluate());
//				long finishTime = System.nanoTime();
//				hist.addHist(aux);
//				hist.addEntry(aux, expression, finishTime-initialTime);
//				stat.addStat(aux);
//				
//			} catch (Exception exp){
//				expression = exp.getMessage();
//			}	
//		} catch (Exception exp){
//			expression = exp.getMessage();
//		}	
		
	}
	

}
