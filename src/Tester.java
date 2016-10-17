import java.util.Arrays;

public class Tester {

	public static void main(String[] args) {
		// String cipherText = FileIO.readFileAsString("cipher3.txt");
		String plainText = "Hello. I would like to eat some pie. However, eating pie is not always a necessity, as sometimes one can eat something else. Or, many others can eat cake. A famous ruler said that phrase. I admit that I do not agree to that phrase, and it started a revolution in some country in the East. After this revolution happened, a new dictatorship arose. It was almost the same thing as the old one. However, eventually this thing became slightly better and finally the country stopped becoming a dictatorship. Only a few wars with neighboring countries finally stopped this. Then, the United States came in and made some shady deals with the country and finally the country did stuff.";
		String cipherText = Cipher.vigenereCipherEncrypt(plainText, "hello9349jcki oerom322ii1");
		String code = Cipher.vigenereCipherCrack(cipherText);

		System.out.println("Plaintext: " + plainText);
		System.out.println("Ciphertext: " + cipherText);
		System.out.println("Code: " + code);
	}

}