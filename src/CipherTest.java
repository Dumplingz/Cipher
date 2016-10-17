import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CipherTest {

	@Test
	public void rotationCipherEncryptBy3() {
		String plaintext = "the quick brown fox jumped over the lazy dogs";
		String correctCipherText = "wkh!txlfn!eurzq!irA!mxpshg!ryhu!wkh!odCB!grjv";
		String testCipherText = Cipher.rotationCipherEncrypt(plaintext, 3);
		assertEquals(testCipherText, correctCipherText);
	}
	
	@Test
	public void rotationCipherDecryptBy3() {
		String plaintext = "the quick brown fox jumped over the lazy dogs";
		String correctCipherText = "wkh!txlfn!eurzq!irA!mxpshg!ryhu!wkh!odCB!grjv";
		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 3);
		assertEquals(testPlainText, plaintext);
	}
	
	@Test
	public void rotationCipherDecryptBy100() {
		String plaintext = "the quick brown fox jumped over the lazy dogs";
		String correctCipherText = "KyvaHLztBasIFNEawFOaALDGvuaFMvIaKyvaCrQPauFxJ";
		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 100);
		assertEquals(testPlainText, plaintext);
	}
	
	@Test
	public void rotationCipherEncryptBy100() {
		String plaintext = "the quick brown fox jumped over the lazy dogs";
		String correctCipherText = "KyvaHLztBasIFNEawFOaALDGvuaFMvIaKyvaCrQPauFxJ";
		String testCipherText = Cipher.rotationCipherEncrypt(plaintext, 100);
		assertEquals(testCipherText, correctCipherText);
	}
	
	@Test
	public void rotationCipherEncryptBy3CapsWithPunctuation() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String correctCipherText = "]WKHbcTXLFNbEURZQ!IR0 !MXPSHG!RYHU!WKH!OD21!GRJV/]";
		String testCipherText = Cipher.rotationCipherEncrypt(plaintext, 3);
		assertEquals(testCipherText, correctCipherText);
	}
	
	@Test
	public void rotationCipherDecryptBy3CapsWithPunctuation() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String correctCipherText = "]WKHbcTXLFNbEURZQ!IR0 !MXPSHG!RYHU!WKH!OD21!GRJV/]";
		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 3);
		assertEquals(testPlainText, plaintext);
	}
	
	@Test
	public void rotationCipherDecryptBy100CapsWithPunctuation() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String correctCipherText = "c,YVpq7.ZT1pS85)4aW5 :a0.36VUa5(V8a,YVa2R\"'aU5X9dc";
		String testPlainText = Cipher.rotationCipherDecrypt(correctCipherText, 100);
		assertEquals(testPlainText, plaintext);
	}
	
	@Test
	public void vignereCipher() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String code = "EVAN YAPPPPP" + "YO";
		String testCipherText = Cipher.vigenereCipherEncrypt(plaintext, code);
		String textCipherDecrypt = Cipher.vigenereCipherDecrypt(testCipherText, code);
		assertEquals(textCipherDecrypt, plaintext);
		
	}
	@Test
	public void vignereCipher2() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String code = "c,YVpq7.ZT1pS85)4aW5 :a0.36VUa5(V8a,YVa2R\"'aU5X9dc";
		String testCipherText = Cipher.vigenereCipherEncrypt(plaintext, code);
		String textCipherDecrypt = Cipher.vigenereCipherDecrypt(testCipherText, code);
		assertEquals(textCipherDecrypt, plaintext);
	}
	@Test
	public void vignereCipher3() {
		String plaintext = "\"THE\n\n\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String code = "THE ACTUAL CODE";
		String testCipherText = Cipher.vigenereCipherEncrypt(plaintext, code);
		String textCipherDecrypt = Cipher.vigenereCipherDecrypt(testCipherText, code);
		assertEquals(textCipherDecrypt, plaintext);
	}
	@Test
	public void vignereCipher4() {
		String plaintext = "\"THE\n\rQUICK\nBROWN FOX. JUMPED OVER THE LAZY DOGS!\"";
		String code = "Warren Liu";
		String testCipherText = Cipher.vigenereCipherEncrypt(plaintext, code);
		String textCipherDecrypt = Cipher.vigenereCipherDecrypt(testCipherText, code);
		assertEquals(textCipherDecrypt, plaintext);
	}
}
