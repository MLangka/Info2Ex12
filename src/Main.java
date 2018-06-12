import java.util.Random;

public abstract class Main {
	
	// array with the letters in the english version of scrabble
	public static final char[] BAG_OF_SCABBLE_TILES = {
		'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', 'e', // 12
		'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 'a', 				// 9
		'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 'i', 				
		'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 					// 8
		'n', 'n', 'n', 'n', 'n', 'n', 								// 6
		'r', 'r', 'r', 'r', 'r', 'r', 								
		't', 't', 't', 't', 't', 't',
		'l', 'l', 'l', 'l', 										// 4
		's', 's', 's', 's', 
		'u', 'u', 'u', 'u', 
		'd', 'd', 'd', 'd',
		'g', 'g', 'g', 												// 3
		'b', 'b', 'c', 'c', 'm', 'm', 'p', 'p', 'f', 'f', 			// 2
		'h', 'h', 'v', 'v', 'w', 'w', 'y', 'y',
		'k', 'j', 'x', 'q', 'z',									// 1
	};
	
	public static final boolean DEBUG_MODE = false;
	
	static Encyclopedia scrabbleEncyclopedia;
	
	// use small list for debug
	static boolean useSmallList = DEBUG_MODE;
	
	// default capacity for each dictionary
	static int capacity = 1009;
	
	// range of word length
	static int minLetters = 2;
	static int maxLetters = 7;
	
	
	/********
	 * MAIN *
	 *******/
	
	
	public static void main(String[] args) {
		
		// instantiate dictionary
		if(useSmallList) {
			capacity = 17;
			scrabbleEncyclopedia = new Encyclopedia("scrabble_small.txt", 
					minLetters, maxLetters, capacity);
		} else {
			capacity = 8179;
			scrabbleEncyclopedia = new Encyclopedia("scrabble_full.txt", 
					minLetters, maxLetters, capacity);
		}
	
		outputStats();
		
		// Test a known combination
		// takeTurn("babsla");
		
		// Try 10 random draws
		for(int i=0; i<1; i++) {
			takeTurn(7);
		}
	}
	
	
	/***********
	 * METHODS *
	 **********/
	
	
	/*
	 * Takes an integer parameter and
	 * Returns that many random tiles as string
	 * Scrabble rules are ignored (could be 10 times Q)
	 */
	private static String drawTiles(int numberOfTiles) {
		char[] tiles = new char[numberOfTiles];
		Random rnd = new Random();
		
		// all possible tiles for english scrabble
		char[] bag = BAG_OF_SCABBLE_TILES;

		// fisher-yates shuffle on bag
		for(int i=0; i<bag.length-1; i++) {
			int j = rnd.nextInt(bag.length-1);
			if(i!=j) {
				char swap = bag[i];
				bag[i] = bag[j];
				bag[j] = swap;
			}			
		}
		
		// take the first n tiles
		for(int i=0; i<numberOfTiles; i++) {
			tiles[i] = bag[i];			
		}
		
		return String.valueOf(tiles);
	}
	
	/*
	 * Draws tiles, outputs the draw and then
	 * calls the other takeTurn() method with
	 * the drawn tiles as parameter.
	 */
	private static void takeTurn(int numberOfTiles) {
		// generate tiles
		String tiles = drawTiles(numberOfTiles);
		
		// output tiles
		outputTiles(tiles);
		
		// then go on with other takeTurn method.
		takeTurn(tiles);
	}
	
	/*
	 * Takes a list of tiles as string parameter,
	 * looks up all possible words for that combination
	 * and outputs them to the console.
	 */
	private static void takeTurn(String tiles) {
		// Look up possible combinations
		String[] possibleWords = scrabbleEncyclopedia.getPossibleWords(tiles);
		
		// output results
		outputPossibleWords(possibleWords, tiles);
	}
	
	/*
	 * Outputs Scrabble tiles drawn from the bag 
	 */
	private static void outputTiles(String tiles) {
		System.out.println();
		System.out.print("The " + tiles.length() + " tiles you drew are: ");
		for(int i=0; i<tiles.length(); i++) {
			System.out.print("'" + tiles.charAt(i) + "' ");
		}
		System.out.println();
	}
	
	/*
	 * Outputs possible words found in the encyclopedia
	 */
	private static void outputPossibleWords(String[] possibleWords, String tiles) {
		System.out.println();
		if(possibleWords.length > 0) {
			System.out.println("The possible words for '" + tiles + "' are:");
			for(int i=0; i<possibleWords.length; i++) {
				System.out.println(possibleWords[i]);
			}
		} else {
			System.out.println("No possible words for '" + tiles + "'.");
		}
		System.out.println();
	}
	
	/*
	 * Outputs some statistics of the encyclopedia
	 */
	private static void outputStats() {
		// get statistics
		int valuesStored = scrabbleEncyclopedia.countValuesStored();
		int collisions = scrabbleEncyclopedia.getCollisionCount();
		int emptyBuckets = scrabbleEncyclopedia.countEmptyBuckets();
		
		// calculate more statistics
		int totalBuckets = capacity * (maxLetters-(minLetters-1));
		int usedBuckets = totalBuckets - emptyBuckets;
		double usedPercent = (usedBuckets / (double)totalBuckets) * 100;
		double collisionPercent = (collisions / (double)valuesStored) * 100;
		
		// output statistics
		System.out.println("Total buckets: " + totalBuckets);
		System.out.println("Used buckets: " + usedBuckets);
		System.out.println("Empty buckets: " + emptyBuckets);
		System.out.println(usedPercent + "% of capacity is being used.\n");
		
		System.out.println("Values stored: " + valuesStored);
		System.out.println("Collisions: " + collisions);
		System.out.println(collisionPercent + "% collision rate.\n");		
	}
	
}
