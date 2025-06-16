package game;

import java.util.ArrayList;

/**
 * The Tile class is a class for a single tile, Each tile has a unique ID, type,
 * position (x, y), size (width and height), and a boolean indicating if it is a
 * victory tile. and an arraylist with the tiles a player can move to from this
 * tile
 */
public class Tile {
	private int ID;
	private String type;
	private int x_coord;
	private int y_coord;
	private int width;
	private int height;
	private boolean victory;
	private ArrayList<Tile> nextTiles = new ArrayList<>(); // list with next tiles for each tile

	/**
	 * Constructs a Tile with specified properties.
	 * 
	 * @param ID      The unique ID of the tile.
	 * @param type    The type or color of the tile.
	 * @param x_coord The X-coordinate of the tile.
	 * @param y_coord The Y-coordinate of the tile.
	 * @param width   The width of the tile.
	 * @param height  The height of the tile.
	 * @param victory Whether the tile is a victory tile.
	 */
	public Tile(int ID, String type, int x_coord, int y_coord, int width, int height, boolean victory) {
		this.ID = ID;
		this.type = type;
		this.x_coord = x_coord;
		this.y_coord = y_coord;
		this.width = width;
		this.height = height;
		this.victory = victory;
	}

	/**
	 * Returns whether the tile is a victory tile.
	 * 
	 * @return true if the tile is a victory tile, otherwise false.
	 */
	public boolean getVictory() {
		return this.victory;
	}

	/**
	 * Adds a tile to the list of next tiles that follow this tile.
	 * 
	 * @param tile The tile to be added to the list of next tiles.
	 */
	public void addNextTile(Tile tile) {
		nextTiles.add(tile);
	}

	/**
	 * Gets the ID of the tile.
	 * 
	 * @return The ID of the tile.
	 */
	public int getID() {
		return this.ID;
	}

	/**
	 * Sets the type (color) of the tile.
	 * 
	 * @param type The type (color) of the tile.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the type (color) of the tile.
	 * 
	 * @return The type (color) of the tile.
	 */
	public String getType() {
		return this.type;
	}

	/**
	 * Gets the X-coordinate of the tile.
	 * 
	 * @return The X-coordinate of the tile.
	 */
	public int getX() {
		return this.x_coord;
	}

	/**
	 * Gets the Y-coordinate of the tile.
	 * 
	 * @return The Y-coordinate of the tile.
	 */
	public int getY() {
		return this.y_coord;
	}

	/**
	 * Gets the width of the tile.
	 * 
	 * @return The width of the tile.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * Gets the height of the tile.
	 * 
	 * @return The height of the tile.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Gets the list of next tiles that follow this tile.
	 * 
	 * @return A list of the next tiles following the current tile.
	 */
	public ArrayList<Tile> getNextTiles() {
		return nextTiles;
	}
}
