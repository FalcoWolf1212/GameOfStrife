package game;

// Imports
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class Board {

	// Creates a new arraylist pathList, a new hashmap tileMap, and a new arraylist
	// victoryTiles
	private List<Tile> pathList = new ArrayList<>();
	private Map<Integer, Tile> tileMap = new HashMap<>(); // ID -> Tile for linking
	private List<Tile> victoryTiles = new ArrayList<>();

	/**
	 * Loads all tiles from a JSON file and builds the game board. - Tiles are
	 * stored in pathList (arraylist) and tileMap (hashmap). - Tiles marked as
	 * "victory" are stored in victoryTiles. - Tile connections (via 'next') are
	 * established. Exceptions are handled for when something is wrong with the name
	 * and contents of the JSON.
	 * 
	 * @param filePath the path to the JSON file containing the board layout
	 */

	public void loadPathFromJson(String filePath) {
		try {
			String jsonData = readJSONFile(filePath);
			JSONObject jsonObject = new JSONObject(jsonData);
			JSONArray pathArray = jsonObject.getJSONArray("path");

			// Create and store all tiles
			for (int i = 0; i < pathArray.length(); i++) {
				JSONObject tileData = pathArray.getJSONObject(i);
				Tile tile = new Tile(tileData.getInt("ID"), tileData.getString("type"), tileData.getInt("x_coord"),
						tileData.getInt("y_coord"), tileData.getInt("width"), tileData.getInt("height"),
						tileData.getBoolean("victory"));
				tileMap.put(tile.getID(), tile);
				pathList.add(tile);

				if (tile.getVictory()) {
					victoryTiles.add(tile);

					// If no victory tiles exist, pick one tile randomly
					if (this.victoryTiles.isEmpty()) {
						Random rand = new Random();
						Tile randomTile = this.pathList.get(rand.nextInt(this.pathList.size()));
						this.victoryTiles.add(randomTile);
					}
				}

			}

			// Link tiles via their "next" connections
			for (int i = 0; i < pathArray.length(); i++) {
				JSONObject tileData = pathArray.getJSONObject(i);
				int id = tileData.getInt("ID");
				Tile tile = tileMap.get(id);

				if (tileData.has("next")) {
					JSONArray nextArray = tileData.getJSONArray("next");

					for (int j = 0; j < nextArray.length(); j++) {
						int nextID = nextArray.getInt(j);
						Tile nextTile = tileMap.get(nextID);

						if (nextTile != null) {
							tile.addNextTile(nextTile);
						}
					}
				}
			}

		} catch (IOException e) {
			handleFatalError("You don't have the correct path JSON file");
		} catch (org.json.JSONException e) {
			handleFatalError("Take a look at your path JSON file, there is an error in the file");
		}
	}

	/**
	 * Displays an error message and stops the program.
	 */
	protected void handleFatalError(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		System.exit(0);

	}

	/**
	 * Reads a JSON file from disk and returns its contents as a string.
	 */
	private static String readJSONFile(String filePath) throws IOException {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n"); // Add newline for better readability
			}
		}
		return content.toString();
	}

	/**
	 * @return the tile map (ID -> Tile)
	 */
	public Map<Integer, Tile> getPath() {
		return this.tileMap;
	}

	/**
	 * @return the full list of tiles in order
	 */
	public List<Tile> getAllTiles() {
		return this.pathList;
	}

	/**
	 * @return the list of tiles that are marked as victory tiles
	 */
	public List<Tile> getVictoryTiles() {
		return this.victoryTiles;
	}

	/**
	 * Gets the starting tile of the board. If no tile with type "start" is found,
	 * it falls back to the tile with ID = 0. If neither are found, the program
	 * exits with an error message.
	 *
	 * @return the starting Tile
	 */
	public Tile getStartingTile() {
		for (Tile tile : pathList) {
			if ("start".equalsIgnoreCase(tile.getType())) {
				return tile;
			} else if (tile.getID() == 0) {
				return tile;
			}

			else {
				handleErrorTile("You don't have a starting tile");

			}
		}
		return null;

	}

	/**
	 * Shows an error dialog and exits the program due to a missing or invalid start
	 * tile.
	 */
	protected void handleErrorTile(String message) {
		JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
		System.exit(0);
	}
}
