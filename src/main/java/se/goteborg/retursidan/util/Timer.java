package se.goteborg.retursidan.util;

public class Timer {
	private long startTime;

	public void start() {
		startTime = System.currentTimeMillis();
	}
	
	public double end() {
		return (System.currentTimeMillis() - startTime) / 1000; 
	}
}
