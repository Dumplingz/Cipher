import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BagTest {
	Bag bag = new Bag();

	@Before
	public void setUp() {
		bag.clear();
		bag.add("aaaaabbcdjjowppejjgifdjnbjrejiqpiouoqjwhipqweinmzzzksjdzozjosjzihwoiqiowperdbwepjdklb");
	}

	@Test
	public void occurencesTest() {
		assertEquals(bag.getNumOccurences("a"), 5);
	}
	
	@Test
	public void mostFrequentTest(){
		assertEquals(bag.getNMostFrequentStrings(1),"j");
	}
	
	@Test
	public void totalWordsTest(){
		assertEquals(bag.getTotalWords(), 85);
	}
	
	@Test
	public void uniqueWordsTest(){
		assertEquals(bag.getNumUniqueWords(),22);
	}

}
