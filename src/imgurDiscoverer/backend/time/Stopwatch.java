package imgurDiscoverer.backend.time;

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
	
	private static volatile int timeInSeconds;
	private static volatile boolean isDone;
	private static volatile Stopwatch self;
	private static volatile  boolean running;
	
	private Stopwatch(int timeInSeconds) {
		Stopwatch.timeInSeconds = timeInSeconds;
		Stopwatch.running = false;
	}
	
	public static synchronized Stopwatch createStopwatch(){
		return ( self == null ) ? self = new Stopwatch(5): self;
	}
	
	public static synchronized void go(){
		if ( !running ) {
			Stopwatch.running = true;
			Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            int i = timeInSeconds;
	            public void run() {
	                System.out.println(i--);
	                if (i< 0) {
	                    timer.cancel();
	                    isDone = true;
	                    Stopwatch.running = false;
	                }
	            }
	        }, 0, 1000);
		}
	}
	
	public static synchronized boolean isDone(){
		return isDone;
	}

}
