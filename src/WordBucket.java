import java.util.ArrayList;
import java.util.HashSet;


public class WordBucket implements ScrabbleStorage {
	
	ArrayList<String> bucket;
	
	public WordBucket() {
		bucket = new ArrayList<String>();
	}
	
	
	/*********************
	 * INTERFACE METHODS *
	 ********************/
	
	
	/*
	 * Add word to bucket
	 */
	public void add(String str) {
		bucket.add(str);
	}
	
	/*
	 * Returns all matches for a string
	 */
	public String[] getPossibleWords(String tiles) {
		ArrayList<String> wordsFound = new ArrayList<String>();
		
		for(String word : bucket) {
			if(this.isPermutation(word, tiles))
				wordsFound.add(word);
		}
		
		// return the remaining words
		String[] wordsArray = new String[wordsFound.size()];
		return wordsFound.toArray(wordsArray);
	}
	
	/*
	 * Returns the number of collisions in the bucket
	 */
	public int getCollisionCount() {
		int collisions = 0;
		// count collisions via HashSet
		HashSet<String> collisionTest = new HashSet<String>();
		for(String s : bucket) {
			// normalize each word and put in hashset
			collisionTest.add(this.normalize(s));
		}
		// count words in hashset - first is not a collision
		collisions += collisionTest.size() - 1;
		
		// make sure count isn't below zero (e.g. for empty buckets)
		if(collisions < 0)
			collisions = 0;
		
		return collisions;
	}
	
	/*
	 * Returns the number of words in the bucket
	 */
	public int countValuesStored() {
		return bucket.size();
	}
	
	
	/******************
	 * PUBLIC METHODS *
	 *****************/

	/*
	 * Return all words as String array
	 */
	public String[] toArray() {
		String[] returnArray = new String[bucket.size()];
		returnArray = bucket.toArray(returnArray);
		
		return returnArray;
	}
	
	/*
	 * Return all words as ArrayList<String>
	 */
	public ArrayList<String> asList() {
		return bucket;
	}

	/*
	 * Returns a boolean indicating whether the bucket is empty
	 */
	public boolean isEmpty() {
		if(bucket.size() == 0)
			return true;
		
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String returnString = "";
		int size = this.bucket.size();
		
		for(int i=0; i<size; i++) {
			returnString += "'" + this.bucket.get(i) + "'  ";
		}
		
		return returnString;
	}	
	
	
	/*******************
	 * PRIVATE METHODS *
	 ******************/
	
	
	/*
	 * Takes a word as parameter and returns the normalized String.
	 * Sorts the characters alphabetically.
	 * MAKE PRIVATE
	 */
	private String normalize(String word) {
		// convert to char array and convert capital letters to lower case
		char[] letters = word.toLowerCase().toCharArray();
		int length = word.length();
		
		// use bubble sort -- what performs best at this size < 20?? quick? heap?
		char swap;
		boolean isUnsorted = true;

		while(isUnsorted) {
			isUnsorted = false;
			for(int i=0; i<length-1; i++) {
				if(letters[i] > letters[i+1]) {
					swap = letters[i];
					letters[i] = letters[i+1];
					letters[i+1] = swap;
					isUnsorted = true;
				}
			}
		}

		return String.valueOf(letters);
	}
	
	/*
	 * Takes to strings as parameter and checks whether
	 * they are permutations of each other by normalizing both.
	 */
	private boolean isPermutation(String a, String b) {
		// DEBUG
		if(Main.DEBUG_MODE)
			System.out.println("Testing permutation between '" + a + "' and '" + b + "'.");
		return this.normalize(a).equals(this.normalize(b));
	}
	
}
