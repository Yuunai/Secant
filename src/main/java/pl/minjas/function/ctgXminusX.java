package pl.minjas.function;

public class ctgXminusX implements Function {
    public double getValue(double x) {
    return (1/Math.tan(x))-x;
}
}
