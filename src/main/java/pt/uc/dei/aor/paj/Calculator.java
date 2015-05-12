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
	@Inject AngleUnitList angleUnits;
	@Inject Mode mode;
	
	//Add another part to string (user input)
	public void add(String srt){
		if (mode.isModeAdvanced() && mode.getMode() == 1) {
			screen.graphConcat(srt);
		}
		else screen.concat(srt);
	}
	

	//Method that receives string from History and adds to expression
		public void submitOp(Screen sc){
			screen.add(sc);
		}
	
		public void submitOp(String s){
			screen.add(s);
		}
		
	//Clear calc when user hits 'C'
	public void clearCalc(){
		screen.clear();
	}
	
	public void clearEntry() {
		screen.remove();
	}
	
	//Calculate the expression using exp4j
	public void calcExp(){
		Screen aux = screen.getClone();
		long initialTime = System.nanoTime();
		boolean res = screen.evaluate(angleUnits.getAngle());
		long finishTime = System.nanoTime();
		
		if (res) {
			hist.addEntry(aux, screen.getExpression(), finishTime-initialTime);
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
	
	public AngleUnitList getAngleUnits() {
		return angleUnits;
	}
}
