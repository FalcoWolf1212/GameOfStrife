package test;

import game.Player;
import game.Tile;
import gui.GameMovementController;
import junit.framework.TestCase;
import java.util.ArrayList;

/**
 * Unit tests for the Player class, ensuring its behavior within the context of
 * the GameofStrife game logic: Handling resources, movement, victory points,
 * and interactions with the GUI interface.
 */
public class PlayerTest extends TestCase {
	// Symbolic constants for initial player state
	private static final String PLAYER_NAME = "TestPlayer";
	private static final String PLAYER_COUNTRY = "TestCountry";
	private static final int INITIAL_RESOURCES = 500;
	private static final int INITIAL_INCOME = 50;

	private Player player;
	private Tile startTile;

	/**
	 * Sets up a new Player instance and starting Tile before each test. Initializes
	 * "player" with default resources, income, and position.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		startTile = new Tile(1, "blue", 0, 0, 10, 10, false);
		player = new Player(PLAYER_NAME, PLAYER_COUNTRY, true, startTile);
	}

	/**
	 * Tests that the Player constructor and getters initialize and return correct
	 * values.
	 */
	public void testConstructorAndGetters() {
		assertEquals(PLAYER_NAME, player.getName());
		assertEquals(PLAYER_COUNTRY, player.getCountry());
		assertTrue(player.getState());
		assertEquals(INITIAL_RESOURCES, player.getResources());
		assertEquals(INITIAL_INCOME, player.getIncome());
		assertEquals(startTile, player.getCurrentTile());
		assertEquals(0, player.getRemainingSteps());
		assertEquals(0, player.getStepsBonus());
		assertEquals(0, player.getVictoryPoints());
	}

	/**
	 * Verifies that setters correctly update the Player state.
	 */
	public void testSettersAndState() {
		player.setName("NewName");
		assertEquals("NewName", player.getName());
		player.setState(false);
		assertFalse(player.getState());
	}

	/**
	 * Ensures resource adjustments add and subtract correctly and do not allow
	 * negative values.
	 */
	public void testResourcesAdjustment() {
		// Positive adjustment
		player.adjustResources(100);
		assertEquals(INITIAL_RESOURCES + 100, player.getResources());
		// Over-subtraction clamps at zero
		player.adjustResources(-1000);
		assertEquals(0, player.getResources());
		// Directly setting resources
		player.setResources(300);
		assertEquals(300, player.getResources());
	}

	/**
	 * Confirms getPaid() increases resources by current income.
	 */
	public void testGetPaid() {
		player.setResources(0);
		player.getPaid();
		assertEquals(INITIAL_INCOME, player.getResources());
	}

	/**
	 * Tests adding victory points and step bonuses functions correctly.
	 */
	public void testVictoryPointsAndSteps() {
		player.addVictoryPoints(3);
		assertEquals(3, player.getVictoryPoints());
		player.addStepsBonus(5);
		assertEquals(5, player.getStepsBonus());
	}

	/**
	 * Validates remaining steps setter and adjustment.
	 */
	public void testRemainingSteps() {
		player.setRemainingSteps(4);
		assertEquals(4, player.getRemainingSteps());
		player.adjustRemainingSteps(-2);
		assertEquals(2, player.getRemainingSteps());
	}

	/**
	 * Checks getColor() returns the current tile's type or null when tile is unset.
	 */
	public void testGetColor() {
		assertEquals("blue", player.getColor());
		Tile redTile = new Tile(2, "red", 0, 0, 5, 5, false);
		player.setCurrentTile(redTile);
		assertEquals("red", player.getColor());
		player.setCurrentTile(null);
		assertNull(player.getColor());
	}

	/**
	 * Tests move() along a single path reduces steps and updates current tile.
	 */
	public void testMoveSinglePath() {
		Tile next = new Tile(4, "white", 0, 0, 5, 5, false);
		startTile.addNextTile(next);
		player.setCurrentTile(startTile);
		DummyGUI gui = new DummyGUI(startTile, startTile);
		player.move(1, gui);
		assertEquals(next, player.getCurrentTile());
		assertEquals(0, player.getRemainingSteps());
	}

	/**
	 * Ensures handleVictoryTileOpportunity does not deduct resources when below
	 * purchase threshold.
	 */
	public void testHandleVictoryTileOpportunity_InsufficientResources() {
		DummyGUI gui = new DummyGUI(startTile, startTile);
		player.setResources(500);
		player.handleVictoryTileOpportunity(gui);
		assertEquals(500, player.getResources());
	}

	/**
	 * Dummy GUI stub implementing {@link GameMovementController} for movement and
	 * victory tests.
	 */
	private static class DummyGUI implements GameMovementController {
		private final Tile victoryTile;
		private final Tile startTile;

		public DummyGUI(Tile victoryTile, Tile startTile) {
			this.victoryTile = victoryTile;
			this.startTile = startTile;
		}

		@Override
		public int showPathSelectionPopup(ArrayList<Tile> nextTiles) {
			return 0;
		}

		@Override
		public boolean showVictoryPointsPopup() {
			return true; // Simulate purchase choice
		}

		@Override
		public Tile getVictoryTile() {
			return victoryTile;
		}

		@Override
		public void setVictoryTile() {
			// no-op
		}

		@Override
		public void repaint() {
			// no-op
		}

		@Override
		public Tile getStartingTile() {
			return startTile;
		}

		@Override
		public void updatePlayerPosition(Player player, int playerIndex) {
			// no-op
		}

		@Override
		public Player getCurrentPlayer() {
			return null;
		}

		@Override
		public int getCurrentPlayerTurn() {
			return 1;
		}

		@Override
		public int getNumWinPoints() {
			return Integer.MAX_VALUE;
		}

		@Override
		public void showEndOfGamePopup() {
			// no-op
		}
	}
}
