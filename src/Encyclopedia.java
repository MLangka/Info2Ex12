import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashSet;


public class Encyclopedia implements ScrabbleStorage {

	Dictionary[] dictionaries;
	int minLetters, maxLetters;
	
	public Encyclopedia(String filename, int minLetters, int maxLetters, int capacity) {
		// eg min=2; max=7; ArraySize=6 --> 0-5
		this.minLetters = minLetters;
		this.maxLetters = maxLetters;
		int numberOfDictionaries = maxLetters-(minLetters-1);
		this.dictionaries = new Dictionary[numberOfDictionaries];
		
		// initialize each dictionary
		for(int i=0; i<numberOfDictionaries; i++) {
			this.dictionaries[i] = new Dictionary(capacity);
		}
		
		// read file
		readFile(filename);
		
		// DEBUG output encyclopedia
		if(Main.DEBUG_MODE)
			System.out.println(this.toString());
	}
	
	
	/*********************
	 * INTERFACE METHODS *
	 ********************/
	
	
	/*
	 * Takes a string as parameter and adds it to the relevant dictionary
	 */
	@Override
	public void add(String str) {			
		// add word to dictionary
		this.dictionaries[str.length()-minLetters].add(str);
				
	}
	
	/*
	 * Looks up a normalized string, the letter tiles, 
	 * in the table and returns all matching words.
	 */
	@Override
	public String[] getPossibleWords(String word) {
		// Hashset for possible words found 
		HashSet<String> possibleWords = new HashSet<String>();
		
		// get permutations from string
		Permutation perm = new Permutation(word, minLetters);
		String[] possibleCombinations = perm.getValues();
		
		// System.out.println(perm.toString());
		
		// look for every permutation in corresponding dictionary
		for(String tiles : possibleCombinations) {
			// DEBUG
			if(Main.DEBUG_MODE)
				System.out.println("Tiles: " + tiles);
						
			// all words found for these tiles in bucket
			String[] wordBucket = this.dictionaries[tiles.length()-minLetters]
										.getPossibleWords(tiles);
			
			// add matches to hashset of possible words
			for(String w : wordBucket) {
				possibleWords.add(w);
			}
		}
		
		// convert hashset to string array and return
		String[] wordsArray = new String[possibleWords.size()];
		return possibleWords.toArray(wordsArray);
	}
	
	/*
	 * Returns the number of collisions in all dictionaries
	 */
	@Override
	public int getCollisionCount() {
		int collisions = 0;
		
		for(Dictionary dict : dictionaries) {
			collisions += dict.getCollisionCount();
		}
		
		return collisions;
	}
	
	/*
	 * Returns the number of words in all dictionaries
	 */
	@Override
	public int countValuesStored() {
		int valuesStored = 0;
		
		for(Dictionary dict : dictionaries) {
			valuesStored += dict.countValuesStored();
		}
		
		return valuesStored;
	}
	
	
	/******************
	 * PUBLIC METHODS *
	 *****************/

	
	/*
	 * Returns the number of empty buckets in all dictionaries
	 */
	public int countEmptyBuckets() {
		int emptyBuckets = 0;
		
		for(Dictionary dict : dictionaries) {
			emptyBuckets += dict.countEmptyBuckets();
		}
		
		return emptyBuckets;
	}
		
	/* (non-Javadoc)
	 * @see ScrabbleStorage#toString()
	 */
	@Override
	public String toString() {
		String returnString = "";
		
		for(int i=0; i<this.dictionaries.length; i++) {
			returnString += "Dictionary for " + (minLetters + i) + " words:\n" 
							+ this.dictionaries[i].toString();
		}
		
		return returnString;
	}
	
	
	/*******************
	 * PRIVATE METHODS *
	 ******************/

	
	/*
	 * Takes a file name as parameter, and generates a Hashtable
	 * from the words in it.
	 */
	private void readFile(String filename) {
		File myFile = new File(filename);
		
		try {
			RandomAccessFile raf = new RandomAccessFile(myFile, "r");
			
			String nextWord;
			while((nextWord = raf.readLine()) != null) {
				int length = nextWord.length();
				
				if(length <= maxLetters && length >= minLetters) {
					this.add(nextWord);
				}						
			}
			
			raf.close();
		} catch(IOException e) {
			e.printStackTrace();
		}	
	}
}
