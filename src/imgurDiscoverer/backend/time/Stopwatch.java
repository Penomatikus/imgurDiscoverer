package imgurDiscoverer.backend.time;

import java.util.Timer;
import java.util.TimerTask;

public class Stopwatch {
	
	private int timeInSeconds;
	private boolean isDone;
	
	public Stopwatch(int timeInSeconds) {
		this.timeInSeconds = timeInSeconds;
	}
	
	public void go(){
		Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            int i = timeInSeconds;
            public void run() {
                System.out.println(i--);
                if (i< 0) {
                    timer.cancel();
                    isDone = true;
                }
            }
        }, 0, 1000);
	}
	
	public boolean isDone(){
		return isDone;
	}

}
