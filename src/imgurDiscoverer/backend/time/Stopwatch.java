package imgurDiscoverer.backend.time;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Provides an object, to stop the time in seconds. <br>
 * <b>Usage: </b>
 * <pre>
 *  <code>
 *   Stopwatch watch = new Stopwatch(10);
 *   watch.go();
 *   watch.go(); // this has no effect until the ten seconds are over
 *   while( !watch.isDone ); // wait until finished
 *   System.out.println("10 seconds passed.");
 *  </code>
 * </pre>
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 */
public class Stopwatch {
	
	/**
	 * The passed time in seconds
	 */
	private int timeInSeconds;
	/**
	 * Indicates if the task is done
	 */
	private boolean isDone;
	/**
	 * Indicates if the task is running. 
	 * <b>Note:</b> If so, {@link Stopwatch#go()} will have no effect.
	 */
	private  boolean running;
	
	/**
	 * Provides an object, to stop the time in seconds. <br>
	 * <b>Usage: </b>
	 * <pre>
	 *  <code>
	 *   Stopwatch watch = new Stopwatch(10);
	 *   watch.go();
	 *   watch.go(); // this has no effect until the ten seconds are over
	 *   while( !watch.isDone ); // wait until finished
	 *   System.out.println("10 seconds passed.");
	 *  </code>
	 * </pre>
	 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
	 */
	public Stopwatch(int timeInSeconds) {
		this.timeInSeconds = timeInSeconds;
		this.running = false;
	}
	
	/**
	 * Starts the stop watch. 
	 */
	public void go(){
		if ( !running ) {
			running = true;
			Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            int i = timeInSeconds;
	            @Override
				public void run() {
	                if ( i < 0 ) {
	                    timer.cancel();
	                    isDone = true;
	                    running = false;
	                }
	            }
	        }, 0, 1000);
		}
	}
	
	
	/**
	 * Starts the stop watch with a temporary new time.
	 * @param overwriteInitTime	a temporary time 
	 */
	public void go(int overwriteInitTime){
		if ( !running ) {
			running = true;
			Timer timer = new Timer();
	        timer.scheduleAtFixedRate(new TimerTask() {
	            int i = overwriteInitTime;
	            @Override
				public void run() {
	                if ( i < 0 ) {
	                    timer.cancel();
	                    isDone = true;
	                    running = false;
	                }
	            }
	        }, 0, 1000);
		}
	}
	
	/**
	 * @return {@link Stopwatch#isDone}
	 */
	public boolean isDone(){
		return isDone;
	}

}
