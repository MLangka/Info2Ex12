import java.util.HashSet;


public class Permutation {

	HashSet<String> permutations;
	String originalWord;
	int minLetters;
	
	public Permutation(String word, int minLetters) {
		this.originalWord = word;
		this.minLetters = minLetters;
		permutations = new HashSet<String>();
		String normalizedWord = this.normalize(word);
		findPermutations(normalizedWord, permutations);
	}
	
	
	/******************
	 * PUBLIC METHODS *
	 *****************/
	
	
	/*
	 * Returns the size of the permutations hashset
	 */
	public int size() {
		return this.permutations.size();
	}
	
	/*
	 * Returns all values in the permutations hashset as String array
	 */
	public String[] getValues() {
		String[] returnArray = new String[this.permutations.size()];
		returnArray = this.permutations.toArray(returnArray);
		
		return returnArray;
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	 * Override toString() method for convenient output
	 */
	@Override
	public String toString() {
		String returnString = "Permutations of '" + this.originalWord + "':\n";
		
		// convert HashSet to String-Array
		String[] list = new String[permutations.size()];
		list = this.permutations.toArray(list);
		
		// append each element to the string
		for(int i=0; i<list.length; i++) {
			returnString += (list[i] + "\n");
		}
		
		return returnString;
	}
	
	
	/*******************
	 * PRIVATE METHODS *
	 ******************/
	
	/*
	 * Takes a string and a hashset as parameters
	 * and recursively puts all possible permutations
	 * of that string into the hashset.
	 */
	private void findPermutations(String word, HashSet<String> list) {
		// add word to the list
		list.add(word);
		
		// run again for every possible (if the word still has more than minimum letters)
		if(word.length() > minLetters) {
			// remove each character from the string and run again for the remaining word
			for(int i=0; i<word.length(); i++) {
				// convert to StringBuilder so we can delete chars
				findPermutations(new StringBuilder(word).deleteCharAt(i).toString(), list);
			}
		}
	}
	
	/*
	 * Takes a word as parameter and returns the normalized String.
	 * Sorts the characters alphabetically.
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