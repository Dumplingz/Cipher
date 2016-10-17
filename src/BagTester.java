public class BagTester {
	/* This class demonstrates how to use the Bag data structure. */
	public static void main(String[] args) {
		Bag bag = new Bag();
		bag.add("aaaaabbcdjjowppejjgifdjnbjrejiqpiouoqjwhipqweinmzzzksjdzozjosjzihwoiqiowperdbwepjdklb");

		// Then you can ask it about frequencies:
		System.out.println("Total items: " + bag.getTotalWords());
		System.out.println("Total unique items: " + bag.getNumUniqueWords());
		System.out.println("'a' occured: " + bag.getNumOccurences("a") + " times ");
		System.out.println("Most frequently occuring is: " + bag.getNMostFrequentStrings(1));
		System.out.println("Top 2 most frequent items: " + bag.getNMostFrequentStrings(2));
	}
}