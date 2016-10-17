import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;

public class Cipher {
	private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789,.() '\"![]/%-_;?=:"
			+ '\n' + '\r';
	private static final String SIMPLE_ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	private static final String LETTER_ALPHABET = "abcdefghijklmnopqrstuvwxyz";

	private static final double[] LETTER_FREQUENCIES = { 8.17, 1.49, 2.78, 4.25, 12.70, 2.23, 2.01, 6.09, 6.97, 0.15,
			0.77, 4.03, 2.40, 6.75, 7.50, 1.93, 0.09, 5.99, 6.33, 9.1, 2.8, 0.98, 2.36, 0.15, 1.98, 0.07 };

	// Set this variable to the default alphabet you wish to use
	private static final String DEFAULT_ALPHABET = ALPHABET;

	private static Dictionary dictionary = Dictionary.buildDictionary("words.txt");

	public static String getAlphabet() {
		return ALPHABET;
	}

	public static String getSimpleAlphabet() {
		return SIMPLE_ALPHABET;
	}

	public static String getDefaultAlphabet() {
		return DEFAULT_ALPHABET;
	}

	/**
	 * Returns plainText encrypted by the rotation cipher with a shift of
	 * movement.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param plainText
	 *            the plain text to be encrypted.
	 * @param shiftAmount
	 *            the number of characters in ALPHABET to shift by.
	 * @return returns the encrypted plainText.
	 */
	public static String rotationCipherEncrypt(String plainText, int shiftAmount, String alphabet) {
		plainText = stripInvalidChars(plainText, alphabet);
		return rotate(plainText, shiftAmount, alphabet);
	}

	public static String rotationCipherEncrypt(String plainText, int shiftAmount) {
		return rotationCipherEncrypt(plainText, shiftAmount, DEFAULT_ALPHABET);
	}

	/**
	 * Returns a string rotated to the specified shift amount.
	 * 
	 * @param plainText
	 *            the text to be rotated
	 * @param shiftAmount
	 *            the number of characters to shift by
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @return returns the rotated string
	 */
	private static String rotate(String plainText, int shiftAmount, String alphabet) {
		String rotatedText = "";
		for (int i = 0; i < plainText.length(); i++) {

			int index = getLetterIndexFromAlphabet(plainText.substring(i, i + 1), alphabet);
			index = shiftIndex(index, shiftAmount, alphabet.length());
			rotatedText += alphabet.substring(index, index + 1);
		}
		return rotatedText;
	}

	/**
	 * Returns an integer that represents an index that is within
	 * alphabetLength.
	 * 
	 * @param index
	 *            the index to re-represent
	 * @param shiftAmount
	 *            the amount to shift the index by
	 * @param alphabetLength
	 *            the length of the alphabet that index has to be in
	 * @return returns an index between 0 and alphabetLength
	 */

	private static int shiftIndex(int index, int shiftAmount, int alphabetLength) {
		int shiftIndex = shiftToPositive(index + shiftAmount, alphabetLength);
		return shiftIndex;
	}

	private static int shiftToPositive(int index, int alphabetLength) {
		return ((((index) % alphabetLength) + alphabetLength) % alphabetLength);
	}

	/**
	 * Returns a the result of decrypting cipherText by shiftAmount using the
	 * rotation cipher.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @param shiftAmount
	 *            the key to decrypt the cipher.
	 * @return returns the decrypted cipherText.
	 */
	public static String rotationCipherDecrypt(String cipherText, int shiftAmount, String alphabet) {
		String decryptedText = rotationCipherEncrypt(cipherText, -shiftAmount, alphabet);
		return decryptedText;
	}

	public static String rotationCipherDecrypt(String cipherText, int shiftAmount) {
		return rotationCipherDecrypt(cipherText, shiftAmount, DEFAULT_ALPHABET);
	}

	public static String rotationCipherCrack(String plainText, String alphabet) {
		for (int i = 1; i < alphabet.length(); i++) {
			String decrypted = rotationCipherDecrypt(plainText, i, alphabet);
			if (isEnglish(decrypted, 0.5)) {
				return decrypted;
			}
		}
		return null;
	}

	public static String rotationCipherCrack(String plainText) {
		return rotationCipherCrack(plainText, DEFAULT_ALPHABET);
	}

	/**
	 * Returns the index of a specified letter in an alphabet string.
	 * 
	 * @param letter
	 *            The letter to find in the alphabet string
	 * @param alphabet
	 *            the alphabet string used
	 * @return returns the index of the letter in the alphabet string.
	 */
	private static int getLetterIndexFromAlphabet(String letter, String alphabet) {
		int i = 0;
		while (!letter.equals(alphabet.substring(i, i + 1))) {
			i++;
		}
		return i;
	}

	/**
	 * Returns an encrypted String with random values assigned to each letter.
	 * 
	 * @param plainText
	 *            The plain text to be encrypted
	 * @param permutation
	 *            The key for each of the values assigned
	 * @param alphabet
	 *            The alphabet used for the encryption
	 * @return returns the encrypted PlainText
	 */

	public static String substitutionCipherEncrypt(String plainText, int[] permutation, String alphabet) {
		String randomizedAlphabet = "";
		for (int i = 0; i < permutation.length; i++) {
			randomizedAlphabet += alphabet.substring(permutation[i], permutation[i] + 1);
		}
		String encryptedText = substitutionCipherEncrypt(plainText, randomizedAlphabet, alphabet);
		return encryptedText;
	}

	public static String substitutionCipherEncrypt(String plainText, String randomizedAlphabet, String alphabet) {
		String encryptedText = "";
		for (int i = 0; i < plainText.length(); i++) {
			int letterIndex = getLetterIndexFromAlphabet(plainText.substring(i, i + 1), alphabet);
			encryptedText += randomizedAlphabet.substring(letterIndex, letterIndex + 1);
		}
		return encryptedText;
	}

	public static String substitutionCipherEncrypt(String plainText, int[] permutation) {
		return substitutionCipherEncrypt(plainText, permutation, DEFAULT_ALPHABET);
	}

	/**
	 * Returns a the result of decrypting cipherText by matching the random
	 * values assigned to each letter to the alphabet.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @param shiftAmount
	 *            the key to decrypt the cipher.
	 * @return returns the decrypted cipherText.
	 */

	public static String substitutionCipherDecrypt(String cipherText, int[] permutation, String alphabet) {
		String randomizedAlphabet = "";
		for (int i = 0; i < permutation.length; i++) {
			randomizedAlphabet += alphabet.substring(permutation[i], permutation[i] + 1);
		}
		String encryptedText = substitutionCipherDecrypt(cipherText, randomizedAlphabet, alphabet);
		return encryptedText;
	}

	public static String substitutionCipherDecrypt(String cipherText, String randomizedAlphabet, String alphabet) {
		String decryptedText = "";
		for (int i = 0; i < cipherText.length(); i++) {
			int letterIndex = getLetterIndexFromAlphabet(cipherText.substring(i, i + 1), randomizedAlphabet);
			
			decryptedText += alphabet.substring(letterIndex, letterIndex + 1);
		}
		return decryptedText;
	}

	public static String substitutionCipherDecrypt(String plainText, int[] permutation) {
		return substitutionCipherDecrypt(plainText, permutation, DEFAULT_ALPHABET);
	}

	/**
	 * Generates an alphabet in a random order, given an alphabet to use
	 * 
	 * @param alphabet
	 *            The alphabet to be used for the randomization
	 * @return returns a new string for all of the replaced values
	 */
	public static String randomizeAlphabet(String alphabet) {
		String newEncryption = "";
		boolean[] lettersUsed = new boolean[alphabet.length()];
		for (int i = 0; i < alphabet.length(); i++) {
			int randomIndex = (int) (Math.random() * alphabet.length());
			while (!lettersUsed[randomIndex]) {
				randomIndex = (int) (Math.random() * alphabet.length());
			}
			newEncryption += alphabet.substring(randomIndex, randomIndex + 1);
			lettersUsed[randomIndex] = true;
		}
		return newEncryption;
	}

	/**
	 * Generates an array of numbers from that are all different.
	 * 
	 * @param length
	 *            int for the length of the array
	 * @return returns an array with randomized, different numbers
	 */
	public static int[] randomPermutation(int length) {
		int[] permutation = new int[length];
		for (int i = 0; i < length; i++) {
			permutation[i] = i;
		}
		for (int i = 0; i < 10000; i++) {
			int randomIndex = (int) (Math.random() * length);
			int randomIndex2 = (int) (Math.random() * length);
			int randomIndexValue = permutation[randomIndex];
			permutation[randomIndex] = permutation[randomIndex2];
			permutation[randomIndex2] = randomIndexValue;

		}
		return permutation;
	}

	/**
	 * Returns whether all of the numbers in an int[] array are different from
	 * each other, showing if the array contains a permutation.
	 * 
	 * @param permutation
	 *            The array to be tested for a valid permutation
	 * @return returns a boolean to tell if the array is valid
	 */
	public static boolean isValidPermutation(int[] permutation) {
		for (int i = 0; i < permutation.length; i++) {
			for (int j = i + 1; j < permutation.length; j++) {
				if (permutation[i] == permutation[j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Returns plainText encrypted by the vigenere cipher encoded with the
	 * String code
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param plainText
	 *            the plain text to be encrypted.
	 * @param code
	 *            the code to use as the encryption key.
	 * @return returns the encrypted plainText.
	 */
	public static String vigenereCipherEncrypt(String plainText, String code, String alphabet) {
		String encryptedText = "";
		for (int i = 0; i < plainText.length(); i++) {
			String letter = plainText.substring(i, i + 1);
			int index = alphabet.indexOf(letter);

			int codeIndex = i % code.length();
			String codeLetter = code.substring(codeIndex, codeIndex + 1);
			int codeLetterIndex = alphabet.indexOf(codeLetter);
			index = shiftIndex(index, codeLetterIndex, alphabet.length());
			encryptedText += alphabet.substring(index, index + 1);
		}
		return encryptedText;
	}

	public static String vigenereCipherEncrypt(String plainText, String code) {
		return vigenereCipherEncrypt(plainText, code, DEFAULT_ALPHABET);
	}

	/**
	 * Returns the result of decrypting cipherText with the key code.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @param code
	 *            the decryption key
	 * @return returns the decrypted cipherText.
	 */
	public static String vigenereCipherDecrypt(String cipherText, String code, String alphabet) {
		String decryptedText = "";
		for (int i = 0; i < cipherText.length(); i++) {
			String letter = cipherText.substring(i, i + 1);
			int index = alphabet.indexOf(letter);

			int codeIndex = i % code.length();
			String codeLetter = code.substring(codeIndex, codeIndex + 1);
			int codeLetterIndex = alphabet.indexOf(codeLetter);
			index = shiftIndex(index, -codeLetterIndex, alphabet.length());
			decryptedText += alphabet.substring(index, index + 1);
		}
		return decryptedText;
	}

	public static String vigenereCipherDecrypt(String cipherText, String code) {
		return vigenereCipherDecrypt(cipherText, code, DEFAULT_ALPHABET);
	}

	/**
	 * Returns the result of decrypting cipherText with the key code.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @return returns the decrypted cipherText.
	 */
	public static String vigenereCipherBruteForceDecrypt(String plainText, String alphabet) {
		for (int i = 0; i < alphabet.length(); i++) {
			for (int j = 0; j < alphabet.length(); j++) {
				String decrypted = vigenereCipherDecrypt(plainText,
						alphabet.substring(i, i + 1) + alphabet.substring(j, j + 1), alphabet);
				if (isEnglish(decrypted, 0.8)) {
					return decrypted;
				}
			}
		}
		return null;
	}

	public static String vigenereCipherBruteForceDecrypt(String plainText) {
		return vigenereCipherBruteForceDecrypt(plainText, DEFAULT_ALPHABET);
	}

	/**
	 * Returns the result of decrypting a given encrypted string with a 3-letter
	 * code.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the encryption
	 * @param cipherText
	 *            the encrypted text.
	 * @param attempts
	 *            the number of likely combinations to test
	 * @return the result of the decryption of cipherText.
	 */
	public static String vigenereCipherCrackThreeLetter(String cipherText, String alphabet, int attempts) {
		Bag bag = new Bag();
		String[] likelySpaces = new String[3];
		for (int i = 0; i < 3; i++) {
			for (int j = i; j < cipherText.length(); j += 3) {
				String letter = cipherText.substring(j, j + 1);

				bag.add(letter);
			}
			likelySpaces[i] = bag.getNMostFrequentStrings(attempts);
			bag.clear();
		}
		for (int i = 0; i < likelySpaces[0].length(); i++) {
			String letter1 = likelySpaces[0].substring(i, i + 1);
			for (int j = 0; j < likelySpaces[1].length(); j++) {
				String letter2 = likelySpaces[1].substring(j, j + 1);
				for (int k = 0; k < likelySpaces[2].length(); k++) {
					String letter3 = likelySpaces[2].substring(k, k + 1);
					int spaceIndex = alphabet.indexOf(" ");
					int rotationIndex1 = shiftToPositive(alphabet.indexOf(letter1) - spaceIndex, alphabet.length());
					int rotationIndex2 = shiftToPositive(alphabet.indexOf(letter2) - spaceIndex, alphabet.length());
					int rotationIndex3 = shiftToPositive(alphabet.indexOf(letter3) - spaceIndex, alphabet.length());
					String code = alphabet.substring(rotationIndex1, rotationIndex1 + 1)
							+ alphabet.substring(rotationIndex2, rotationIndex2 + 1)
							+ alphabet.substring(rotationIndex3, rotationIndex3 + 1);
					String decrypted = vigenereCipherDecrypt(cipherText, code, alphabet);
					if (isEnglish(decrypted, 0.7)) {
						return decrypted;
					}
				}
			}
		}
		return null;

	}

	public static String vigenereCipherCrackThreeLetter(String plainText, int attempts) {
		return vigenereCipherCrackThreeLetter(plainText, DEFAULT_ALPHABET, attempts);
	}

	/**
	 * Returns the result of decrypting cipherText with a vigenere cipher.
	 * 
	 * @param alphabet
	 *            the alphabet to be used for the decryption
	 * @param cipherText
	 *            the encrypted text.
	 * @return returns the decrypted cipherText.
	 */
	public static String vigenereCipherCrack(String cipherText) {
		return vigenereCipherCrack(cipherText, DEFAULT_ALPHABET);
	}

	public static String vigenereCipherCrack(String cipherText, String alphabet) {
		double bestScore = Double.MAX_VALUE;
		String bestCode = "";
		for (int codeLength = 1; codeLength < 50; codeLength++) {
			String code = findBestCode(cipherText, codeLength, alphabet);
			Bag totalLetters = new Bag();
			totalLetters.add(vigenereCipherDecrypt(cipherText, code, alphabet));
			double lengthScore = getSimilarityToEnglishScore(totalLetters);
			if (bestScore > lengthScore) {
				if (isEnglish(vigenereCipherDecrypt(cipherText, code, alphabet), 0.75)) {
					bestScore = lengthScore;
					bestCode = code;
					
				}
			}
		}
		return bestCode;
	}

	/**
	 * Returns the best code from decrypting cipherText.
	 * 
	 * @param cipherText
	 *            the encrypted text.
	 * @param codeLength
	 *            the length of the code for the best code decryption for
	 *            cipherText
	 * @param alphabet
	 *            the alphabet to be used for the decryption.
	 * @return
	 */
	public static String findBestCode(String cipherText, int codeLength, String alphabet) {
		String code = "";
		for (int i = 0; i < codeLength; i++) {
			String group = getGroup(cipherText, i, codeLength);
			double minimumScore = Double.MAX_VALUE;
			int index = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				Bag bag = new Bag();
				bag.add(rotate(group, j, alphabet));
				double score = getSimilarityToEnglishScore(bag);
				if (minimumScore > score) {
					index = j;
					minimumScore = score;
				}
			}

			index = (-index + alphabet.length()) % alphabet.length();
			code += alphabet.substring(index, index + 1);
		}
		return code;
	}

	/**
	 * Returns a string with every "jump" letter in cipherText starting from
	 * startIndex
	 * 
	 * @param cipherText
	 *            the text to take out
	 * @param startIndex
	 *            the index to start taking letters out
	 * @param jump
	 *            the number of letters to skip
	 * @return returns a string with the removed letters
	 */
	private static String getGroup(String cipherText, int startIndex, int jump) {
		String group = "";
		for (int i = startIndex; i < cipherText.length(); i += jump) {
			group += cipherText.substring(i, i + 1);
		}
		return group;
	}

	/**
	 * Returns a score of the percentage of differences between the frequency of
	 * certain letters English and a bag that contains letters.
	 * 
	 * @param bag
	 *            the bag with letters to compare to English
	 * @return returns the total percentage of differences
	 */
	private static double getSimilarityToEnglishScore(Bag bag) {
		double score = 0;
		for (int i = 0; i < LETTER_FREQUENCIES.length; i++) {
			double percentage = bag.getTotalWords();
			double numberOfLetters = bag.getNumOccurences(i);
			numberOfLetters += bag.getNumOccurences(i + 26);
			percentage = numberOfLetters / percentage;
			score += Math.abs(LETTER_FREQUENCIES[i] - (percentage * 100.0));
		}
		return score;
	}

	/**
	 * returns a copy of the input plainText String with invalid characters
	 * stripped out.
	 * 
	 * @param plainText
	 *            The plainText string you wish to remove illegal characters
	 *            from
	 * @param alphabet
	 *            A string of all legal characters.
	 * @return String A copy of plain with all characters not in alphabet
	 *         removed.
	 */
	private static String stripInvalidChars(String plainText, String alphabet) {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < plainText.length(); i++) { // loop through plainText
			if (alphabet.indexOf(plainText.charAt(i)) >= 0) // get index of char
				b.append(plainText.charAt(i)); // if it exists, keep it
			// else
			// otherwise skip it &
			// System.out.println("Stripping letter: \"" + plainText.charAt(i)
			// // display
			// a
			// message
			// + "\"");
		}
		return b.toString();
	}

	/**
	 * returns an array of Strings that contains the groups of letters in an
	 * input string with the spaces removed.
	 * 
	 * @param plainText
	 *            The string you wish to extract the words from.
	 * @return the String array of words
	 */

	private static String[] getWords(String plainText) {
		int numWords = getNumWords(plainText);
		boolean canMakeNewWord = true;
		String[] words = new String[numWords];
		for (int i = 0; i < words.length; i++) {
			words[i] = "";
		}
		int nextIndex = -1;
		for (int i = 0; i < plainText.length(); i++) {
			String letter = plainText.substring(i, i + 1);
			if (!letter.equals(" ")) {
				if (canMakeNewWord) {
					nextIndex++;
					canMakeNewWord = false;
				}
				words[nextIndex] += letter;

			} else {
				canMakeNewWord = true;
			}
		}
		return words;
	}

	/**
	 * returns an int that represents the number of groups of letters other than
	 * space in a given string.
	 * 
	 * @param plainText
	 *            the string you wish to find the number of words in
	 * @return the number of words that exist in the string
	 */

	private static int getNumWords(String plainText) {
		int numWords = 0;
		boolean canMakeNewWord = true;
		for (int i = 0; i < plainText.length(); i++) {
			if (!plainText.substring(i, i + 1).equals(" ")) {
				if (canMakeNewWord) {
					numWords++;
					canMakeNewWord = false;
				}
			} else {
				canMakeNewWord = true;
			}
		}
		return numWords;
	}

	/**
	 * returns true if plainText is valid English.
	 * 
	 * @param plainText
	 *            the text you wish to test for whether it's valid English
	 * @param miniumumCorrect
	 *            the percentage, from a scale of 0 to 1, of the total number of
	 *            English words in plainText.
	 * @return boolean returns true if plainText is valid English.
	 */
	private static boolean isEnglish(String plainText, double minimumCorrect) {
		String[] words = getWords(plainText);
		int numberOfValidWords = 0;
		for (int i = 0; i < words.length; i++) {
			String word = words[i];
			word = word.toLowerCase();
			word = stripInvalidChars(word, LETTER_ALPHABET);
			if (dictionary.isWord(word)) {
				numberOfValidWords++;

			}
		}
		return ((double) numberOfValidWords >= ((double) (words.length)) * minimumCorrect);
	}

}