package test;

import junit.framework.TestCase;
import game.Board;
import game.Tile;

import java.util.List;
import java.util.Map;

public class BoardTest extends TestCase {

	/**
	 * Make sure that the path is loaded from a JSON file, and that the tile
	 * properties are correct
	 */
	public void testLoadPathFromJson() {
		Board board = new Board();
		board.loadPathFromJson("./data/pathTest.json");

		// Get the map of path tiles and the list of all tiles from the board
		Map<Integer, Tile> pathMap = board.getPath();
		List<Tile> tileList = board.getAllTiles();

		// Check tile count
		assertEquals(19, tileList.size());

		// Check specific tile values
		Tile tile0 = pathMap.get(0);
		Tile tile1 = pathMap.get(1);
		Tile tile3 = pathMap.get(3);
		Tile tile4 = pathMap.get(4);

		// Test properties of tile0
		assertNotNull(tile0);
		assertEquals("start", tile0.getType()); // Assert that tile0 is a starting tile
		assertEquals(1, tile0.getNextTiles().size()); // Assert that tile0 has only 1 following tile
		assertEquals(tile1, tile0.getNextTiles().get(0)); // Assert that tile0 has tile1 as next tile

		// Check that the next tile links are correctly established
		assertTrue("Tile 0 should link to Tile 1", tile0.getNextTiles().contains(tile1));
		assertTrue("Tile 3 should link to Tile 4", tile3.getNextTiles().contains(tile4));

	}

	/**
	 * Check that victory tiles are retrieved from the board.
	 */
	public void testGetVictoryTiles() {
		Board board = new Board();
		board.loadPathFromJson("./data/pathTest.json");

		List<Tile> victoryTiles = board.getVictoryTiles();

		// Make sure that there are victory tiles
		assertFalse(victoryTiles.isEmpty());

		// Check that all victory tiles have their "victory" property set to True
		for (Tile tile : victoryTiles) {
			assertTrue("Tile should be a victory tile", tile.getVictory());
		}

		// Check the number of victory tiles in the path is correct
		int expectedVictoryTileCount = 5;
		assertEquals("Victory tile count should match", expectedVictoryTileCount, victoryTiles.size());
	}

	/**
	 * Check that the starting tile is retrieved from the board.
	 */
	public void testGetStartingTile() {
		Board board = new MockUpBoard();
		board.loadPathFromJson("./data/pathTest.json");

		// Retrieve the starting tile from the board
		Tile startTile = board.getStartingTile();

		// Assert that the starting tile is not null
		assertNotNull(startTile);

		// Assert that the starting tile is type "start"
		assertEquals("start", startTile.getType().toLowerCase());
	}

	/**
	 * Test that there is no red error when using a path without a starting tile.
	 */
	public void testGetStartingTile_Error() {
		Board board = new MockUpBoard();

		try {
			board.loadPathFromJson("./data/pathWithoutStartTile.json");
			board.getStartingTile();
			fail("Expected an exception when no start tile is found");

		} catch (RuntimeException e) {
			assertTrue(e.getMessage().contains("You don't have a starting tile"));
		}
	}

	/**
	 * Test that an empty path JSON file does not give problems
	 */
	public void testEmptyPath() {
		Board board = new Board();
		board.loadPathFromJson("./data/emptyPath.json");

		List<Tile> tiles = board.getAllTiles();
		assertTrue("There should be no tiles", tiles.isEmpty());
	}

	/**
	 * Check the error handling when an invalid JSON file is loaded.
	 */
	public void testErrorPopupWithException() {
		Board board = new MockUpBoard();

		// Try to load a non-existent JSON file, which should give an exception,
		// otherwise test fails.
		try {
			board.loadPathFromJson("./data/WrongName.json");
			fail("Expected RuntimeException not thrown");
		} catch (RuntimeException e) {
			assertTrue(e.getMessage().startsWith("You"));
		}
	}

}
