package imgurDiscoverer.backend.logic;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Provides an object for storing and providing char[] ( hashes ). <br>
 * <b>Usage:</b>
 * <pre>
 *  <code>
 *   HashProvider provider = HashProvider.createProvider();
 *   provider.add(new char[]{ 'h', 'e', 'l', 'l', 'o' });
 *   provider.add(new char[]{ 'w', 'o', 'r', 'l', 'd' });
 *   
 *   char[] hello = provider.getHashAsChar();
 *   String world = provider.getHashAsString();
 *   
 *   // Output is: Hello world
 *   System.out.print(String.valueOf(hello) + " " + world);
 *  </code>
 * </pre>
 * Since the data structure for storing the hashes is a {@link ConcurrentLinkedQueue},
 * we will receive the hashes in FIFO order ( <a href="https://en.wikipedia.org/wiki/FIFO_(computing_and_electronics)">
 * Wikipedia</a> ). <br>
 * Creating/receiving the {@link HashProvider}, adding hashes and receiving hashes is threadsafe.<br>
 * 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public class HashProvider implements Singleton {
	
	/**
	 * Holds the passed hashes for storing and providing them
	 */
	private static ConcurrentLinkedQueue<char[]> hashes;
	
	/**
	 * The HashProvider its self ( singleton ) 
	 */
	private static HashProvider self;
	
	/**
	 * Provides an object for storing and providing char[] ( hashes ). <br>
	 * <b>Usage:</b>
	 * <pre>
	 *  <code>
	 *   HashProvider provider = HashProvider.createProvider();
 	 *   provider.add(new char[]{ 'h', 'e', 'l', 'l', 'o' });
 	 *   provider.add(new char[]{ 'w', 'o', 'r', 'l', 'd' });
 	 *   
 	 *   char[] hello = provider.getHashAsChar();
 	 *   String world = provider.getHashAsString();
 	 *   
 	 *   // Output is: Hello world
 	 *   System.out.print(String.valueOf(hello) + " " + world);
 	 *  </code>
 	* </pre>
 	* Since the data structure for storing the hashes is a {@link ConcurrentLinkedQueue},
 	* we will receive the hashes in FIFO order ( <a href="https://en.wikipedia.org/wiki/FIFO_(computing_and_electronics)">
 	* Wikipedia</a> ). <br>
 	* Creating/receiving the {@link HashProvider}, adding hashes and receiving hashes is threadsafe.<br>
 	* @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
	 */
	private HashProvider() {
		hashes = new ConcurrentLinkedQueue<>();
	}
	
	/**
	 * Factory method for creating and / or receiving the  {@link HashProvider}
	 * @return	The HashProvider instance of the program ( singleton )
	 */
	public static synchronized HashProvider createProvider() {
		return ( self == null ) ? new HashProvider() : self;
	}
	
	/**
	 * Adds a new char[] to {@link HashProvider#hashes}
	 * @param hash	the char[] to add
	 */
	public synchronized void addHash(char[] hash){
		hashes.add(hash);
	}
	
	/**
	 * Returns oldest hash from {@link HashProvider#hashes} as char[] by
	 * removing it from the queue.
	 * @return	The oldest hash from {@link HashProvider#hashes} as char[]
	 */
	public synchronized char[] getHashAsChar(){
		return hashes.poll();
	}
	
	/**
	 * The oldest hash from {@link HashProvider#hashes} as String by removing 
	 * it from the queue.
	 * @return	The oldest hash from {@link HashProvider#hashes} as String
	 */
	public synchronized String getHashAsString(){
		return String.valueOf(hashes.poll());
	}
	
}
