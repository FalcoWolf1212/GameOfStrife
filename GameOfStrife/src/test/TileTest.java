package test;

import game.Tile;
import static game.SymbolicConstants.*;
import junit.framework.TestCase;

public class TileTest extends TestCase {

    private Tile testTile;
    private Tile victoryTile;
    private Tile normalTile;

    /**
     * Sets up the test fixtures by creating Tile instances before each test method runs.
     */
    @Override
    protected void setUp() {
        testTile = new Tile(TILE_ID, TILE_COLOR_BLUE, TILE_X, TILE_Y, TILE_WIDTH, TILE_HEIGHT, TILE_VICTORY_FALSE);
        victoryTile = new Tile(VICTORY_TILE_ID, TILE_COLOR_WHITE, TILE_POS_X, TILE_POS_Y, TILE_DIMENSION, TILE_DIMENSION, TILE_VICTORY_TRUE);
        normalTile = new Tile(NORMAL_TILE_ID, TILE_COLOR_GREEN, TILE_POS_X, TILE_POS_Y, TILE_DIMENSION, TILE_DIMENSION, TILE_VICTORY_FALSE);

    }

    /**
     * Tests if a tile is properly created.
     */
    public void testCreation() {
        assertNotNull(testTile);
        assertNotNull(victoryTile);
        assertNotNull(normalTile);
    }

    /**
     * Tests if a victory tile is correctly identified as a victory tile.
     */
    public void testGetVictory_True() {
        assertTrue("Tile should be a victory tile", victoryTile.getVictory());
    }

    /**
     * Tests if a normal tile is correctly identified as not being a victory tile.
     */
    public void testGetVictory_False() {
        assertFalse("Tile should not be a victory tile", normalTile.getVictory());
    }

    /**
     * Tests the getID method to ensure it returns the correct tile ID.
     */
    public void testGetID() {
        assertEquals("Tile ID should match", TILE_ID, testTile.getID());
        assertEquals("Victory tile ID should match", VICTORY_TILE_ID, victoryTile.getID());
    }

    /**
     * Tests the type getter and setter methods.
     */
    public void testTypeGetterAndSetter() {
        assertEquals("Initial type should match", TILE_COLOR_BLUE, testTile.getType());
        testTile.setType("red");
        assertEquals("Type should change after set", "red", testTile.getType());
    }

    /**
     * Tests the coordinate getter methods.
     */
    public void testCoordinateGetters() {
        assertEquals("X coordinate should match", TILE_X, testTile.getX());
        assertEquals("Y coordinate should match", TILE_Y, testTile.getY());
    }

    /**
     * Tests the dimension getter methods.
     */
    public void testDimensionGetters() {
        assertEquals("Width should match", TILE_WIDTH, testTile.getWidth());
        assertEquals("Height should match", TILE_HEIGHT, testTile.getHeight());
    }

    /**
     * Tests the nextTiles functionality by adding a tile and checking the list.
     */
    public void testNextTilesFunctionality() {
        assertEquals("Next tiles list should be empty initially", 0, testTile.getNextTiles().size());
        
        Tile nextTile = new Tile(3, "yellow", 200, 200, 50, 50, false);
        testTile.addNextTile(nextTile);
        
        assertEquals("Next tiles list should have one tile after adding", 1, testTile.getNextTiles().size());
        assertSame("Added tile should be in the next tiles list", nextTile, testTile.getNextTiles().get(0));
    }

}