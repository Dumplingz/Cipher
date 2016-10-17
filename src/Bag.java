import java.util.Arrays;

public class Bag {
	private final String alphabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?-=:";
	private int[] letterFrequencies = new int[alphabet.length()];

	public Bag() {

	}

	/**
	 * Adds the input to the bag
	 * 
	 * @param input
	 *            the letter wanted to add
	 */
	public void add(String input) {

		for (int i = 0; i < input.length(); i++) {
			String letter = input.substring(i, i + 1);

			int index = getIndexForLetter(letter);
			if (index >= 0) {
				letterFrequencies[index] += 1;
			}
		}
	}

	/**
	 * Clears all items in the bag
	 */
	public void clear() {
		for (int i = 0; i < letterFrequencies.length; i++) {
			letterFrequencies[i] = 0;
		}
	}

	/**
	 * Gets the index of the alphabet in the bag
	 * 
	 * @param letter
	 *            the letter the where the index should be found
	 * @return returns an int representing the index
	 */
	private int getIndexForLetter(String letter) {
		return alphabet.indexOf(letter);
	}

	/**
	 * Gets the total number of letters in the bag
	 * 
	 * @return returns an int representing the total number of letters
	 */
	public int getTotalWords() {
		int words = 0;
		for (int i = 0; i < letterFrequencies.length; i++) {
			words += letterFrequencies[i];
		}
		return words;

	}

	/**
	 * Gets the number of unique items in the bag
	 * 
	 * @return returns the int describing the total number of unique items
	 */
	public int getNumUniqueWords() {
		int uniqueWords = 0;
		for (int i = 0; i < letterFrequencies.length; i++) {
			int letters = letterFrequencies[i];
			if (letters > 0) {
				uniqueWords++;
			}
		}
		return uniqueWords;
	}

	/**
	 * Gets the number of times a certain letter occurs in the bag
	 * 
	 * @param letter
	 *            the letter to find how many times the letter occurs
	 * @return returns an int that represents the times the letter occurs
	 */
	public int getNumOccurences(String letter) {
		int index = getIndexForLetter(letter);
		return letterFrequencies[index];
	}

	public int getNumOccurences(int letterIndex) {
		return letterFrequencies[letterIndex];
	}

	/**
	 * Gets the most frequent strings, in most frequent and then arranged
	 * alphabetically, inside the bag
	 * 
	 * @param number
	 *            the number of most frequent strings to find
	 * @return returns a string with the most frequent strings, sorted
	 */
	public String getNMostFrequentStrings(int number) {
		String output = "";
		if (number > alphabet.length()) {
			number = alphabet.length();
		}
		int[] tempLetterFrequencies = new int[letterFrequencies.length];
		for (int i = 0; i < tempLetterFrequencies.length; i++) {
			tempLetterFrequencies[i] = letterFrequencies[i];
		}
		for (int i = 0; i < number; i++) {
			int max = Integer.MIN_VALUE;
			int maxIndex = 0;
			for (int j = 0; j < tempLetterFrequencies.length; j++) {
				if (max < tempLetterFrequencies[j]) {
					max = tempLetterFrequencies[j];
					maxIndex = j;

				}
			}
			output += alphabet.substring(maxIndex, maxIndex + 1);
			tempLetterFrequencies[maxIndex] = -1;
		}
		return output;
	}



}
