public class Dictionary implements ScrabbleStorage {

	WordBucket[] buckets;
	int capacity;
	
	public Dictionary(int capacity) {
		// set capacity and valuesStored
		this.capacity = capacity;
		
		// Initialize array
		buckets = new WordBucket[capacity];
		
		// Initialize buckets
		for(int i=0; i<capacity; i++) {
			buckets[i] = new WordBucket();
		}
	}
	
	
	/*********************
	 * INTERFACE METHODS *
	 ********************/
	
	
	/*
	 * Add a word to bucket at hash index
	 */
	public void add(String str) {
		int hash = this.getHash(str);
		buckets[hash].add(str);
		
		// DEBUG
		if(Main.DEBUG_MODE)
			System.out.println("Adding '" + str + "' at hash index " + hash);
	}
	
	/*
	 * Looks up a normalized string, the letter tiles, 
	 * in the table and returns all matching words.
	 */
	public String[] getPossibleWords(String tiles) {
		// make sure the tiles string is normalized
		tiles = this.normalize(tiles);
		
		// get all entries at hash index
		int index = this.getHash(tiles);
		return this.buckets[index].getPossibleWords(tiles);
	}
	
	/*
	 * Returns the number of collisions in all buckets
	 */
	public int getCollisionCount() {
		int collisions = 0;
		
		for(WordBucket bucket : buckets) {
			collisions += bucket.getCollisionCount();
		}
		
		return collisions;
	}
	
	/*
	 * Returns the number of words in the dictionary
	 */
	public int countValuesStored() {
		int valuesStored = 0;
		
		for(WordBucket bucket : buckets) {
			valuesStored += bucket.countValuesStored();
		}
		
		return valuesStored;
	}
	
	
	/******************
	 * PUBLIC METHODS *
	 *****************/

	
	/*
	 * Returns the number of empty buckets in the dictionary
	 */
	public int countEmptyBuckets() {
		int emptyBuckets = 0;
		
		for(WordBucket bucket : buckets) {
			if(bucket.isEmpty())
				emptyBuckets++;
		}
		
		return emptyBuckets;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String returnString = "";
		
		for(int i=0; i<capacity; i++) {
			returnString += i + ": " + this.buckets[i].toString() + "\n";
		}
		
		returnString += "\n";
		
		return returnString;
	}
	
	
	/*******************
	 * PRIVATE METHODS *
	 ******************/
	
	
	/*
	 * Takes a string as parameter and
	 * returns a hash value.
	 */
	private int getHash(String str) {
		// make sure string is normalized
		str = this.normalize(str);
		
		// starting values
		int hash = 31;
		int prime = 503;
		
		for(int i=0; i<str.length(); i++) {
			hash = prime * hash + str.charAt(i);
			hash %= this.capacity;
		}
		
		return hash;
	}
	
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
}
