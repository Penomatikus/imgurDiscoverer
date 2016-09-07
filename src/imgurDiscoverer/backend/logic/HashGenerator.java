package imgurDiscoverer.backend.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SplittableRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Provides an object, for generating imgur-image-name hashes with a lenght
 * between five and eight characters. Please see {@link HashGenerator#characters} for
 * the used characters, the hash is generated with. <br>
 * <b>Usage:</b>
 * <pre>
 * 	<code>
 *   HashGenerator generator = HashGenerator.createGenerator();
 *   generator.generateHash();
 *  </code>
 * </pre>
 * The declaration of the {@link HashGenerator} its self and its generateHash() Method
 * is thread-safe. 
 * @author Stefan Jagdmann <a href="https://github.com/Penomatikus">Meet me at Github</a>
 *
 */
public final class HashGenerator implements Singleton {
	
	/**
	 * {@link Set} holding all needed characters for imgur-image-name hashes
	 */
	private static final List<Character> characters = Stream.of(
			'A', 'a', 'B', 'b', 'C', 'c', 'D', 'd', 'E', 'e', 'F', 'f', 
			'G', 'g', 'H', 'h', 'I', 'i', 'J', 'j',	'K', 'k', 'L', 'l', 
			'M', 'm', 'N', 'n', 'O', 'o', 'P', 'p', 'Q', 'q', 'R', 'r', 
			'S', 's', 'T', 't',	'U', 'u', 'V', 'v', 'W', 'w', 'X', 'x', 
			'Y', 'y', 'Z', 'z', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', '0').collect(Collectors.toCollection(ArrayList::new));
	
	/**
	 * Indicates the number of elements in {@link HashGenerator#characters} <br>
	 * ( To lazy to count them or do simple math )
	 */
	private static final int charactersSize = characters.size();
	
	/**
	 * To pass the generated hash to
	 */
	private static final  HashProvider hashProvider = HashProvider.createProvider();
	
	/**
	 * Used to get a random index of {@link HashGenerator#characters}
	 */
	private static final SplittableRandom splittableRandom = new SplittableRandom();
	
	/**
	 * Singleton instance of the {@link HashGenerator}
	 */
	private static HashGenerator self;
	
	/**
	 * Provides an object, for generating imgur-image-name hashes with a lenght
	 * between five and eight characters. Please see {@link HashGenerator#characters} for
	 * the used characters, the hash is generated with. <br>
	 * <b>Usage:</b>
	 * <pre>
	 * 	<code>
	 * 	 HashGenerator generator = HashGenerator.createGenerator();
	 *	 generator.generateHash();
	 *  </code>
	 * </pre>
	 */
	private HashGenerator(){ }
	
	/**
	 * Factory method for creating and / or receiving the  {@link HashGenerator}.
	 * @return	The HashGenerator instance of the program ( singleton )
	 */
	public static synchronized HashGenerator createGenerator(){
		return (self == null ) ? new HashGenerator() : self;
	}
	
	/**
	 * Generates a imgur-image-name hash between five and eight characters and passes
	 * it to the {@link HashProvider}. <br>
	 */
	public synchronized void generateHash(){
		int lenght = splittableRandom.nextInt(5, 8);
		char[] hash = new char[lenght];
		for (int i = 0; i < lenght; i++ )
			hash[i] = characters.get(splittableRandom.nextInt(0, charactersSize));
		hashProvider.addHash(hash);
	}
}
