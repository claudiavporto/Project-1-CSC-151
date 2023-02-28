package common;
/**
 * JUnit test class.  Use these tests as models for your own.
 */
import org.junit.*;
import org.junit.rules.Timeout;
import static org.junit.Assert.*;

import proj1.Coinbank;

public class CoinbankTest {
	
	@Rule // a test will fail if it takes longer than 1/10 of a second to run
 	public Timeout timeout = Timeout.millis(100); 

	/**
	 * Sets up a bank with the given coins
	 * @param pennies number of pennies you want
	 * @param nickels number of nickels you want
	 * @param dimes number of dimes you want
	 * @param quarters number of quarters you want
	 * @return the Coinbank filled with the requested coins of each type
	 */
	private Coinbank makeBank(int pennies, int nickels, int dimes, int quarters) {
		Coinbank c = new Coinbank();
		int[] money = new int[]{pennies, nickels, dimes, quarters};
		int[] denom = new int[]{1,5,10,25};
		for (int index=0; index<money.length; index++) {
			int numCoins = money[index];
			for (int coin=0; coin<numCoins; coin++) {
				c.insert(denom[index]);
			}
		}
		return c;
	}

	@Test // bank should be empty upon construction
	public void testConstruct() {
		Coinbank emptyDefault = new Coinbank();
		assertEquals(0, emptyDefault.get(1));
		assertEquals(0, emptyDefault.get(5));
		assertEquals(0, emptyDefault.get(10));
		assertEquals(0, emptyDefault.get(25));
		String expected = "The bank currently holds $0.0 consisting of \n0 pennies\n0 nickels\n0 dimes\n0 quarters\n";
		assertEquals(expected,emptyDefault.toString());
	}
	
	@Test //test toString
	public void testToString(){
		Coinbank c = makeBank(4,2,13,3);
		String expected = "The bank currently holds $2.19 consisting of \n4 pennies\n2 nickels\n13 dimes\n3 quarters\n";
		assertEquals(expected, c.toString());
	}

	@Test // inserting nickel should return true & one nickel should be in bank
	public void testInsertNickel_return()
	{
		Coinbank c = new Coinbank();
		assertTrue(c.insert(5));
		assertEquals(1,c.get(5));
		String expected = "The bank currently holds $0.05 consisting of \n0 pennies\n1 nickels\n0 dimes\n0 quarters\n";
		assertEquals(expected,c.toString());
	}
	
	@Test // getter should return correct values
	public void testGet()
	{
		Coinbank c = makeBank(0,2,15,1);
		assertEquals(0,c.get(1));
		assertEquals(2,c.get(5));
		assertEquals(15,c.get(10));
		assertEquals(1,c.get(25));
		String expected = "The bank currently holds $1.85 consisting of \n0 pennies\n2 nickels\n15 dimes\n1 quarters\n";
		assertEquals(expected,c.toString());
	}
	
	@Test // getter should not alter the bank
	public void testGet_contents()
	{
		Coinbank c = makeBank(0,2,15,1);
		assertEquals(0, c.get(1));
		assertEquals(2, c.get(5));
		assertEquals(15, c.get(10));
		assertEquals(1, c.get(25));
		String expected = "The bank currently holds $1.85 consisting of \n0 pennies\n2 nickels\n15 dimes\n1 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // getter should return correct values
	public void testGetInvalid()
	{
		Coinbank c = makeBank(0,2,15,1);
		assertEquals(-1,c.get(2));
		assertEquals(-1,c.get(6));
		assertEquals(-1,c.get(11));
		assertEquals(-1,c.get(43));
		String expected = "The bank currently holds $1.85 consisting of \n0 pennies\n2 nickels\n15 dimes\n1 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove
	public void testRemove_justEnough()
	{
		Coinbank c = makeBank(4,1,3,5);
		assertEquals(5,c.remove(25,5));
		String expected = "The bank currently holds $0.39 consisting of \n4 pennies\n1 nickels\n3 dimes\n0 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove
	public void testRemove_Less()
	{
		Coinbank c = makeBank(4,1,3,5);
		assertEquals(3,c.remove(25,3));
		String expected = "The bank currently holds $0.89 consisting of \n4 pennies\n1 nickels\n3 dimes\n2 quarters\n";
		assertEquals(expected,c.toString());
	}
	
	@Test // remove should not do anything if a 3-cent coin is requested
	public void testRemove_invalidCoin()
	{
		Coinbank c = makeBank(4,1,3,5);
		assertEquals(0,c.remove(3,1));
		String expected = "The bank currently holds $1.64 consisting of \n4 pennies\n1 nickels\n3 dimes\n5 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove if requested coins are greater than coins in bank
	public void testRemove_moreThanInBank()
	{
		Coinbank c = makeBank(4, 1, 3, 5);
		assertEquals(3, c.remove(10, 5));
		String expected = "The bank currently holds $1.34 consisting of \n4 pennies\n1 nickels\n0 dimes\n5 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of remove with invalid requestedCoins
	public void testRemove_invalidRequest()
	{
		Coinbank c = makeBank(4, 1, 3, 5);
		assertEquals(0, c.remove(5, -2));
		String expected = "The bank currently holds $1.64 consisting of \n4 pennies\n1 nickels\n3 dimes\n5 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of insert with valid coinType
	public void testInsert_validCoin()
	{
		Coinbank c = makeBank(4, 1, 3, 5);
		assertTrue(c.insert(25));
		String expected = "The bank currently holds $1.89 consisting of \n4 pennies\n1 nickels\n3 dimes\n6 quarters\n";
		assertEquals(expected,c.toString());
	}

	@Test // test of insert with invalid coinType
	public void testInsert_invalidCoin()
	{
		Coinbank c = makeBank(4, 1, 3, 5);
		assertFalse(c.insert(12));
		String expected = "The bank currently holds $1.64 consisting of \n4 pennies\n1 nickels\n3 dimes\n5 quarters\n";
		assertEquals(expected,c.toString());
	}
}
