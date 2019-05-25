package pl.minjas.function;

public class Log implements Function {
	@Override
	public double getValue(double x) {
		return Math.log10(x);
	}
}
