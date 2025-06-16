package test;

import game.CardDecks;
import game.Card;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Set;
import static game.SymbolicConstants.*;

/**
 * Test class for {@link game.CardDecks} to verify its functionality including:
 * - Loading cards from JSON file - Organizing cards by type - Shuffling decks -
 * Retrieving used cards
 */
public class CardDecksTest extends TestCase {

	private CardDecks cardDecks;

	/**
	 * Sets up the test fixture before each test method. Initializes a new CardDecks
	 * instance and loads cards from JSON.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		cardDecks = new CardDecks();
		cardDecks.loadCardDeckFromJson(JSON_PATH_CARDS);
	}

	/**
	 * Tests that CardDecks loads correctly from JSON and separates cards by type.
	 * Verifies: - All card decks are initialized - Correct number of cards in each
	 * deck - Cards have the correct type
	 */
	public void testCreationCardDecks() {
		ArrayList<Card> blueCards = cardDecks.getCardsByType(BLUE);
		ArrayList<Card> greenCards = cardDecks.getCardsByType(GREEN);
		ArrayList<Card> redCards = cardDecks.getCardsByType(RED);

		assertNotNull("Blue cards deck should not be null", blueCards);
		assertNotNull("Green cards deck should not be null", greenCards);
		assertNotNull("Red cards deck should not be null", redCards);

		assertEquals("Blue cards count should match", EXPECTED_BLUE_CARDS, blueCards.size());
		assertEquals("Green cards count should match", EXPECTED_GREEN_CARDS, greenCards.size());
		assertEquals("Red cards count should match", EXPECTED_RED_CARDS, redCards.size());

		// Verify card types
		assertEquals("First blue card should be BLUE type", BLUE, blueCards.get(0).getType().toLowerCase());
		assertEquals("First green card should be GREEN type", GREEN, greenCards.get(0).getType().toLowerCase());
		assertEquals("First red card should be RED type", RED, redCards.get(0).getType().toLowerCase());
	}

	/**
	 * Tests the getDeckTypes method to ensure it returns all available deck types.
	 */
	public void testGetDeckTypes() {
		Set<String> deckTypes = cardDecks.getDeckTypes();
		assertNotNull("Deck types should not be null", deckTypes);
		assertEquals("Should contain 3 deck types", 3, deckTypes.size());
		assertTrue("Should contain BLUE deck", deckTypes.contains(BLUE.toUpperCase()));
		assertTrue("Should contain GREEN deck", deckTypes.contains(GREEN.toUpperCase()));
		assertTrue("Should contain RED deck", deckTypes.contains(RED.toUpperCase()));
	}

	/**
	 * Tests the getUsedCardsByType method to ensure used decks are properly
	 * initialized.
	 */
	public void testGetUsedCardsByType() {
		ArrayList<Card> usedBlueCards = cardDecks.getUsedCardsByType(BLUE);
		ArrayList<Card> usedGreenCards = cardDecks.getUsedCardsByType(GREEN);
		ArrayList<Card> usedRedCards = cardDecks.getUsedCardsByType(RED);

		assertNotNull("Used blue cards deck should not be null", usedBlueCards);
		assertNotNull("Used green cards deck should not be null", usedGreenCards);
		assertNotNull("Used red cards deck should not be null", usedRedCards);

		assertTrue("Used blue cards deck should be empty initially", usedBlueCards.isEmpty());
		assertTrue("Used green cards deck should be empty initially", usedGreenCards.isEmpty());
		assertTrue("Used red cards deck should be empty initially", usedRedCards.isEmpty());
	}

	/**
	 * Tests that getCardsByType returns null for invalid card types.
	 */
	public void testGetCardsByType_InvalidType() {
		assertNull("Should return null for invalid card type", cardDecks.getCardsByType("invalid"));
		assertNull("Should return null for null card type", cardDecks.getCardsByType(null));
	}

	/**
	 * Tests that getUsedCardsByType returns null for invalid card types.
	 */
	public void testGetUsedCardsByType_InvalidType() {
		assertNull("Should return null for invalid card type", cardDecks.getUsedCardsByType("invalid"));
		assertNull("Should return null for null card type", cardDecks.getUsedCardsByType(null));
	}

	/**
	 * Tests that shuffling doesn't lose any cards and maintains the same card
	 * count.
	 */
	public void testShuffleAllDecks() {
		// Get original card lists
		ArrayList<Card> originalBlueCards = new ArrayList<>(cardDecks.getCardsByType(BLUE));
		ArrayList<Card> originalGreenCards = new ArrayList<>(cardDecks.getCardsByType(GREEN));
		ArrayList<Card> originalRedCards = new ArrayList<>(cardDecks.getCardsByType(RED));

		// Shuffle decks
		cardDecks.shuffleAllDecks();

		// Get shuffled card lists
		ArrayList<Card> shuffledBlueCards = cardDecks.getCardsByType(BLUE);
		ArrayList<Card> shuffledGreenCards = cardDecks.getCardsByType(GREEN);
		ArrayList<Card> shuffledRedCards = cardDecks.getCardsByType(RED);

		// Verify counts remain the same
		assertEquals("Blue cards count should remain same after shuffle", originalBlueCards.size(),
				shuffledBlueCards.size());
		assertEquals("Green cards count should remain same after shuffle", originalGreenCards.size(),
				shuffledGreenCards.size());
		assertEquals("Red cards count should remain same after shuffle", originalRedCards.size(),
				shuffledRedCards.size());

		// Verify all original cards are still present (order may be different)
		assertTrue("All blue cards should still be present after shuffle",
				shuffledBlueCards.containsAll(originalBlueCards));
		assertTrue("All green cards should still be present after shuffle",
				shuffledGreenCards.containsAll(originalGreenCards));
		assertTrue("All red cards should still be present after shuffle",
				shuffledRedCards.containsAll(originalRedCards));
	}
}