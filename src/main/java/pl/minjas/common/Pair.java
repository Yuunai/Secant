package pl.minjas.common;

public class Pair<T> {
	
	private T x;
	private T y;
	
	public Pair(T x, T y) {
		this.x = x;
		this.y = y;
	}
	
	public T getX() {
		return x;
	}
	
	public T getY() {
		return y;
	}
}
