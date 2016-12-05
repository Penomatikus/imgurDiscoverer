package imgurDiscoverer.backend.monitoring;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import imgurDiscoverer.backend.net.Downloader;

/**
 * This class does only exists because I am not sure if the
 * socketread0 ( native method ) error is fixed. This caused 
 * that a {@link Downloader} becomes stuck at opening an input stream
 * to the URL because the server messed something up and letting
 * the thread wait for ( not appearing ) bytes until the program was closed. 
 * If I am sure that its fixed, I will remove this class.
 * @author stefan
 *
 */
public final class ThreadPolice implements Runnable {
	
	private static ThreadPolice officer;
	private static Thread[] threads;
	
	private ThreadPolice(Thread[] threads){
		ThreadPolice.threads = threads;
	}
	

	@Override
	public void run() {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {		
			@Override
			public void run() {
				if ( threads.length == 0)
					System.out.println("No threads are alive.");
				else
					printAliveThreads();
			}
		}, 2000, 3000);
		printAliveThreads();
	}
	
	public synchronized static ThreadPolice create(Thread[] threads){
		return ( officer != null ) ? officer : new ThreadPolice(threads);
	}
	
	public void printAliveThreads() throws NullPointerException {
		for ( Thread t : threads )
			if ( t.isAlive() ) 
				System.out.println("[Thread Police] : " + t.getName() + " is alive.");
	}
	
	public List<Thread> getAliveThreads() throws NullPointerException {
		List<Thread> tmp = new ArrayList<>(threads.length);
		for ( Thread t : threads )
			if ( t.isAlive() ) 
				tmp.add(t);
		return tmp;
	}
}
