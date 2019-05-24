package pl.minjas;

import pl.minjas.common.Pair;
import pl.minjas.function.Function;

import java.util.ArrayList;
import java.util.List;

public class SecantMethod {
	
	private final Function function;
	private final int steps;
	
	public SecantMethod(Function function, int steps) {
		this.function = function;
		this.steps = steps;
	}
	
	public SecantResult getResult(double x1, double x2) {
		SecantResult result = new SecantResult();
		result.addSecantPoint(new Pair<>(x1, function.getValue(x1)));
		result.addSecantPoint(new Pair<>(x2, function.getValue(x2)));
		double prePre = x1;
		double pre = x2;
		double now = 0;
		for (int i = 0; i < steps; i++) {
			if ((pre - prePre) == 0.0 || (function.getValue(pre) - function.getValue(prePre)) == 0.0) {
				System.out.println("Calculations stopped at " + (int) (i + 1.0) + " step, because comparision to 0 is positive.");
				break;
			}
			now = pre - function.getValue(pre) * ((pre - prePre) / (function.getValue(pre) - function.getValue(prePre)));
			System.out.println("Result " + (int) (i + 1.0) + ": " + now);
			result.addSecantPoint(new Pair<>(now, function.getValue(now)));
			prePre = pre;
			pre = now;
		}
		result.setResult(now);
		
		return result;
	}
	
	public class SecantResult {
		List<Pair<Double>> secantPoints = new ArrayList<>();
		double result;

		public List<Pair<Double>> getFunctionPairs(int range) {
			List<Pair<Double>> res = new ArrayList<>();
			for (int i = (int) result - range; i < result + range; i++) {
				res.add(new Pair<>((double) i, function.getValue(i)));
			}
			
			return res;
		}
		
		private void addSecantPoint(Pair<Double> secantPoint) {
			secantPoints.add(secantPoint);
		}
		
		public double getResult() {
			return result;
		}
		
		private void setResult(double result) {
			this.result = result;
		}
		
		public List<Pair<Pair<Double>>> getSecants() {
			List<Pair<Pair<Double>>> res = new ArrayList<>();
			for (int i = 1; i < secantPoints.size() - 1; i++)
				res.add(new Pair<>(secantPoints.get(i - 1), secantPoints.get(i)));
			return res;
		}
	}
	
}
