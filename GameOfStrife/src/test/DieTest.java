package test;

import junit.framework.TestCase;
import game.Die;

public class DieTest extends TestCase {

	/**
	 * Test to see if the die is created correctly with the correct number of faces
	 */
	public void testDieCreation() {
		Die testDie6 = new Die(6); // This will become a 6-sided die
		assertEquals(6, testDie6.getNumFaces());

		Die testDie12 = new Die(12); // This will become a 12-sided die
		assertEquals(12, testDie12.getNumFaces());
	}

	/**
	 * Test to check if the die has a value between 1 and the set number of faces of
	 * the die
	 */
	public void testDieRoll() {
		Die testDie = new Die(6); // This will become a 6-sided die
		testDie.roll();
		int DieValue = testDie.getValue();
		assertTrue(DieValue >= 1 && DieValue <= 6);

		// Test that rolling the die multiple times also returns values that are part of
		// the die
		for (int i = 0; i < 50; i++) {
			testDie.roll();
			int value = testDie.getValue();
			assertTrue("Value should be between 1 and 6", value >= 1 && value <= 6);
		}
	}

	/**
	 * Test that a die with 0 or negative faces defaults to 1 on roll.
	 */
	public void testZeroOrNegativeFaces() {
		Die testDieZero = new Die(0);
		testDieZero.roll();
		assertEquals(1, testDieZero.getValue());

		Die testDieNegative = new Die(-5);
		testDieNegative.roll();
		assertEquals(1, testDieNegative.getValue());
	}

	/**
	 * Test that setting the number of faces to 0 or negative numbers does not cause
	 * errors.
	 */
	public void testSetNumFacesToInvalid() {
		Die testDie = new Die(6);
		testDie.setNumFaces(-10);
		testDie.roll();
		assertEquals(1, testDie.getValue());

		testDie.setNumFaces(0);
		testDie.roll();
		assertEquals(1, testDie.getValue());
	}
}
