package pt.uc.dei.aor.paj;

import java.io.Serializable;
import java.util.Map;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.richfaces.model.ChartDataModel.ChartType;
import org.richfaces.model.NumberChartDataModel;

@Named
@SessionScoped
public class Data implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private NumberChartDataModel model;
	private NumberChartDataModel reg;
	private double m;
	private double b;
	private double r2;
	
	public Data() {
		clear();
	}
	
	public void add(double x, double y) {
		model.put(x, y);
	}
	
	
	
	public String getName() {
		return name;
	}

	public void clear() {
		model = new NumberChartDataModel(ChartType.line);
		reg = new NumberChartDataModel(ChartType.line);
		this.name = "graph";
		m = 0;
		b = 0;
		setR2(0);
	}
	
	public NumberChartDataModel getModel() { return model; }
	public NumberChartDataModel getReg() { return reg; }
	
	
	public void complete() {
		Map<Number, Number> data = model.getData();
		int size = data.size();
		double[] x = new double[size];
		double[] y = new double[size];
		
		int i = 0;
		for (Number d : data.keySet()) {
			x[i] = d.doubleValue();
			y[i++] = data.get(d).doubleValue();
		}
		
		double[] par = MathHelper.regression(x, y);
		m = par[0];
		b = par[1];
		setR2(par[2]);
		
		for (int j = 0; j < x.length; j++) {
			reg.put(x[j], m*x[j]+b);
		}
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	public double getR2() {
		return r2;
	}

	public void setR2(double r2) {
		this.r2 = r2;
	}

}
