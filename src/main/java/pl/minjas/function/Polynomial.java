package pl.minjas.function;

import java.util.List;

public class Polynomial implements Function {
	
	private List<Double> factors;
	
	public Polynomial(List<Double> factors) {
		this.factors = factors;
	}
	
	public double getValue(double x) {
		double result = 0;
		
		for (int i = 0; i < factors.size(); i++)
			result += factors.get(i) * Math.pow(x, i);
		
		return result;
	}
}
