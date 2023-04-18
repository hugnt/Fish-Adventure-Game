package root;

public class SimpleTimer { //1/10^(-6)s
	private long nanosecond;
	private AdvancedCounter act;
	public SimpleTimer(){
		nanosecond = 0;
		act = new AdvancedCounter();
	}
	public void set(long ns) {
		nanosecond = ns;
		act = new AdvancedCounter();
	}
	public void start() {
		act.start();
	}
	public void pause() {
		act.pause();
	}
	public void resume() {
		act.resume();
	}
	public long timeLeft() {
		return Math.max(0, nanosecond - act.read());
	}
	public long timeLeftS() {
		return (Math.max(0, nanosecond - act.read()))/((long)(1e9));
	}
}
