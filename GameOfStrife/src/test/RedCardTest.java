package test;

import game.RedCard;
import game.Tile;
import static game.SymbolicConstants.*;
import junit.framework.TestCase;
import gui.GameOfStrife;

public class RedCardTest extends TestCase {
	private RedCard selfMoneyCard;
	private RedCard otherMoneyCard;
	private RedCard incomeChangeCard;
	private RedCard dieChangeCard;
	private RedCard skipTurnCard;
	private RedCard moveToStartCard;
	private RedCard rollAgainCard;
	private GameOfStrife game;

	/**
	 * Setup the game, which is needed for the card function to work
	 */
	public void setUp() throws Exception {
		String[][] testPlayerInfo = { { "Test Player 1", "netherlands" }, { "Test Player 2", "morocco" },
				{ "Test Player 3", "friesland" }, { "Test Player 4", "hungary" } };
		game = new GameOfStrife(4, 6, 4, testPlayerInfo);

		selfMoneyCard = new RedCard("Self money change", VALUE_SELF_MONEY, TYPE_RED, METHOD_SELF_MONEY, 0);
		otherMoneyCard = new RedCard("Other money change", VALUE_OTHER_MONEY, TYPE_RED, METHOD_OTHER_MONEY, 0);
		incomeChangeCard = new RedCard("Income change", VALUE_INCOME_CHANGE, TYPE_RED, METHOD_INCOME_CHANGE,
				DURATION_INCOME);
		dieChangeCard = new RedCard("Die change", VALUE_DIE_CHANGE, TYPE_RED, METHOD_DIE_CHANGE, DURATION_DIE);
		skipTurnCard = new RedCard("Skip turn", VALUE_ZERO, TYPE_RED, METHOD_SKIP_TURN, 0);
		moveToStartCard = new RedCard("Move to start", VALUE_ZERO, TYPE_RED, METHOD_MOVE_TO_START_RED, 0);
		rollAgainCard = new RedCard("Roll again", VALUE_ZERO, TYPE_RED, METHOD_ROLL_AGAIN, 0);
	}

	/**
	 * Test if the cards that are supposed to change the current player's money work
	 * correctly
	 */
	public void testSelfMoneyChange() {
		int initialResources = game.getCurrentPlayer().getResources();
		selfMoneyCard.executeCard(game);
		assertEquals(initialResources + VALUE_SELF_MONEY, game.getCurrentPlayer().getResources());
	}

	/**
	 * Test if the cards that are supposed to change the other players' money work
	 * correctly
	 */
	public void testOtherMoneyChange() {
		int[] initialResources = new int[game.getPlayers().size()];
		for (int i = 0; i < game.getPlayers().size(); i++) {
			initialResources[i] = game.getPlayers().get(i).getResources();
		}

		otherMoneyCard.executeCard(game);

		assertEquals(initialResources[0], game.getCurrentPlayer().getResources());
		for (int i = 1; i < game.getPlayers().size(); i++) {
			assertEquals(initialResources[i] + VALUE_OTHER_MONEY, game.getPlayers().get(i).getResources());
		}
	}

	/**
	 * test if cards that change income work correctly
	 */
	public void testIncomeChange() {
		int initialIncome = game.getCurrentPlayer().getIncome();
		incomeChangeCard.executeCard(game);
		assertEquals(initialIncome + VALUE_INCOME_CHANGE, game.getCurrentPlayer().getIncome());
		assertEquals(1, game.getActiveCardsList().size());
	}

	/**
	 * test if cards that change die faces work correctly
	 */
	public void testDieChange() {
		dieChangeCard.executeCard(game);
		assertEquals(10, game.getDie().getNumFaces());
		assertEquals(1, game.getActiveCardsList().size());
	}

	/**
	 * test if cards that skip the next player's turn work correctly
	 */
	public void testSkipTurn() {
		int initialTurn = game.getCurrentPlayerTurn();
		skipTurnCard.executeCard(game);
		assertEquals((initialTurn % game.getNumberOfPlayers()) + 1, game.getCurrentPlayerTurn());
	}

	/**
	 * test if cards that move the player to start work correctly
	 */
	public void testMoveToStart() {
		Tile nonStartTile = game.getBoard().getAllTiles().get(1);
		game.getCurrentPlayer().setCurrentTile(nonStartTile);
		moveToStartCard.executeCard(game);
		assertEquals(0, game.findTileByID(0).getID());
		assertEquals(0, game.getCurrentPlayer().getCurrentTile().getID());
	}

	/**
	 * test if cards that allow the player to roll again work correctly
	 */
	public void testRollAgain() {
		int initialTurn = game.getCurrentPlayerTurn();
		rollAgainCard.executeCard(game);
		game.nextTurn();
		assertEquals(initialTurn, game.getCurrentPlayerTurn());
	}

	/**
	 * test if removing the income effect works correctly
	 */
	public void testRemoveIncomeEffect() {
		int initialIncome = game.getCurrentPlayer().getIncome();
		incomeChangeCard.executeCard(game);
		incomeChangeCard.removeEffect(game, null);
		assertEquals(initialIncome, game.getCurrentPlayer().getIncome());
	}

	/**
	 * test if the duration for which the die remains changed is correct
	 */
	public void testDieChangeDuration() {
		int originalFaces = game.getDie().getNumFaces();
		int expectedDuration = 1 + DURATION_DIE * game.getNumberOfPlayers();

		dieChangeCard.executeCard(game);
		assertEquals(10, game.getDie().getNumFaces());
		assertEquals(1, game.getActiveCardsList().size());

		for (int i = 0; i < expectedDuration - 1; i++) {
			game.nextTurn();
			assertEquals(10, game.getDie().getNumFaces());
			assertEquals(1, game.getActiveCardsList().size());
		}

		game.nextTurn();
		assertEquals(originalFaces, game.getDie().getNumFaces());
		assertEquals(0, game.getActiveCardsList().size());
	}

	/**
	 * test if the duration for which the income remains changed is correct for a
	 * player
	 */
	public void testIncomeChangeDuration() {
		int originalIncome = game.getCurrentPlayer().getIncome();
		int expectedDuration = 1 + DURATION_INCOME * game.getNumberOfPlayers();

		incomeChangeCard.executeCard(game);
		assertEquals(originalIncome + VALUE_INCOME_CHANGE, game.getCurrentPlayer().getIncome());
		assertEquals(1, game.getActiveCardsList().size());

		for (int i = 0; i < expectedDuration - 1; i++) {
			game.nextTurn();
			assertEquals(originalIncome + VALUE_INCOME_CHANGE, game.getPlayers().get(0).getIncome());
			assertEquals(1, game.getActiveCardsList().size());
		}

		game.nextTurn();
		assertEquals(originalIncome, game.getCurrentPlayer().getIncome());
		assertEquals(0, game.getActiveCardsList().size());
	}

	/**
	 * test card properties
	 */
	public void testCardProperties() {
		assertEquals("Self money change", selfMoneyCard.getDescription());
		assertEquals(VALUE_SELF_MONEY, selfMoneyCard.getValueChange());
		assertEquals(TYPE_RED, selfMoneyCard.getType());

		assertEquals("Die change", dieChangeCard.getDescription());
		assertEquals(VALUE_DIE_CHANGE, dieChangeCard.getValueChange());
		assertEquals(TYPE_RED, dieChangeCard.getType());
	}
}
