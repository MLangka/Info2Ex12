public interface ScrabbleStorage {

	/*
	 * Takes a string as parameter and adds it to the storage
	 */
	public abstract void add(String str);

	/*
	 * Looks up a normalized string, the letter tiles, 
	 * in the storage and returns all matching words.
	 */
	public abstract String[] getPossibleWords(String word);

	/*
	 * Returns the number of collisions in the storage
	 */
	public abstract int getCollisionCount();

	/*
	 * Returns the number of words in all dictionaries
	 */
	public abstract int countValuesStored();

}