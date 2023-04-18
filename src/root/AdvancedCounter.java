package root;
//Count in nanosecond

public class AdvancedCounter{ //Pause & resume operation
	private Counter ct;
	private boolean isRunning;
	private long startTime;
	private long pauseTime;
	private long padding;
	public AdvancedCounter() {
		ct = new Counter();
		isRunning = false;
		startTime = pauseTime = padding = 0;
	}
	public void start() {
		isRunning = true;
		pauseTime = System.nanoTime();
		startTime = System.nanoTime();
	}
	public boolean running() {
		return isRunning;
	}
	public void resume() {
		if(isRunning) return; //no effect
		isRunning = true;
		padding -= ct.read();
	}
	public void pause() {
		if(!isRunning) return; //no effect
		isRunning = false;
		pauseTime = System.nanoTime();
		padding += pauseTime - startTime;
		startTime = pauseTime;
		ct.start();
	}
	public long read(){
		if(isRunning) return System.nanoTime() - startTime + padding;
		return pauseTime - startTime + padding;
	}
	public long readS() {
		if(isRunning) return (System.nanoTime() - startTime + padding)/((long)1e9);
		return padding/((long)(1e9));
	}
}
 
class Counter{ //Concurrence with time line
	private long startTime;
	public Counter() {
		startTime = 0;
	}
	public void start() {
		startTime = System.nanoTime();
	}
	public long read(){
		return System.nanoTime() - startTime;
	}
}