package test;

import game.GreenCard;
import junit.framework.TestCase;
import gui.GameOfStrife;

/**
 * Unit tests for the GreenCard class.
 */
public class GreenCardTest extends TestCase {
	private GreenCard card1;
	private GreenCard card2;
	private GreenCard card3;
	private GameOfStrife game;

	/**
	 * Sets up the test environment before each test. Initializes a GameofStrife
	 * instance with players and several GreenCard instances to simulate both
	 * positive and negative effects.
	 */
	public void setUp() throws Exception {
		String[][] testPlayerInfo = { { "Test Player 1", "netherlands" }, { "Test Player 2", "morocco" },
				{ "Test Player 3", "friesland" }, { "Test Player 4", "hungary" } };
		game = new GameOfStrife(4, 6, 4, testPlayerInfo);

		card1 = new GreenCard("You won the beauty contest!!", 100, "GREEN"); // Positive effect (+100)
		card2 = new GreenCard("You paid a fine", 50, "GREEN"); // Negative effect (-50)
		card3 = new GreenCard("You found money on the street!", 200, "GREEN"); // Positive effect (+200)
	}

	/**
	 * Tests if a GreenCard correctly applies a positive budget change to the
	 * current player in the game.
	 */
	public void testPositiveBudgetChange() {
		card1.executeCard(game);
		assertEquals(650, game.getCurrentPlayer().getResources());
	}

	/**
	 * Tests if the properties (description, value, and type) of a GreenCard are
	 * stored and retrieved correctly.
	 */
	public void testCardProperties() {
		assertEquals("You won the beauty contest!!", card1.getDescription());
		assertEquals(100, card1.getValueChange());
		assertEquals("GREEN", card1.getType());

		assertEquals("You paid a fine", card2.getDescription());
		assertEquals(50, card2.getValueChange());
		assertEquals("GREEN", card2.getType());

		assertEquals("You found money on the street!", card3.getDescription());
		assertEquals(200, card3.getValueChange());
		assertEquals("GREEN", card3.getType());
	}
}
