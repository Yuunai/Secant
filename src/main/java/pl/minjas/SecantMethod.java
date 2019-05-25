package pl.minjas;

import pl.minjas.common.Pair;
import pl.minjas.exception.SecantException;
import pl.minjas.function.Function;

import java.util.ArrayList;
import java.util.List;

public class SecantMethod {
	
	private final Function function;
	private final int steps;
	private double x1;
	private double x2;
	
	public SecantMethod(Function function, int steps) {
		this.function = function;
		this.steps = steps;
	}
	
	public SecantResult getResult(double x1, double x2) {
		this.x1 = x1;
		this.x2 = x2;
		SecantResult result = new SecantResult();
		result.addSecantPoint(new Pair<>(x1, function.getValue(x1)));
		result.addSecantPoint(new Pair<>(x2, function.getValue(x2)));
		double prePre = x1;
		double pre = x2;
		double now = 0;
		try {
			for (int i = 0; i < steps; i++) {
				if ((pre - prePre) == 0.0 || (function.getValue(pre) - function.getValue(prePre)) == 0.0) {
					System.out.println("Calculations stopped at " + (int) (i + 1.0) + " step, because comparision to 0 is positive.");
					break;
				} else if (Double.isNaN(function.getValue(pre)) || Double.isNaN(function.getValue(prePre))) {
					System.out.println("Calculations stopped at " + (int) (i + 1.0) + " step," +
							" because function have no value for arguments");
					break;
				}
				now = pre - function.getValue(pre) * ((pre - prePre) / (function.getValue(pre) - function.getValue(prePre)));
				
				System.out.println("Result " + (int) (i + 1.0) + ": " + now);
				result.addSecantPoint(new Pair<>(now, function.getValue(now)));
				
				if (Math.abs(function.getValue(now)) > Math.abs(function.getValue(pre))
						&& Math.abs(now - pre) < Math.abs(pre - prePre))
					throw new SecantException("Distance from 0 and difference between result approximations rose!");
				
				prePre = pre;
				pre = now;
			}
		} catch (SecantException e) {
			System.out.println(e.getMessage());
		}
		result.setResult(now);
		
		return result;
	}
	
	public class SecantResult {
		List<Pair<Double>> secantPoints = new ArrayList<>();
		double result;
		
		public List<Pair<Double>> getFunctionPairs(int range) {
			List<Pair<Double>> res = new ArrayList<>();
			for (double i = Math.min(result, Math.min(x1, x2)) - range;
			     i < Math.max(result, Math.max(x1, x2)) + range; i += 0.01) {
				res.add(new Pair<>(i, function.getValue(i)));
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
			for (int i = 1; i < secantPoints.size(); i++) {
				if (Double.isNaN(secantPoints.get(i - 1).getY()) || Double.isNaN(secantPoints.get(i).getY()))
					continue;
				Function sf = getLinearFunction(secantPoints.get(i - 1), secantPoints.get(i));
				double x1 = secantPoints.get(i - 1).getX();
				double x2 = secantPoints.get(i).getX();
				double newBeg = Math.min(x1, x2) - 5;
				double newEnd = Math.max(x1, x2) + 5;
				res.add(new Pair<>(new Pair<>(newBeg, sf.getValue(newBeg)), new Pair<>(newEnd, sf.getValue(newEnd))));
			}
			return res;
		}
		
		private Function getLinearFunction(Pair<Double> p1, Pair<Double> p2) {
			double a = (p1.getY() - p2.getY()) / (p1.getX() - p2.getX());
			double b = p1.getY() - a * p1.getX();
			
			return (x) -> a * x + b;
		}
	}
	
}
