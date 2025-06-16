package gui;

import java.util.ArrayList;

import game.Player;
import game.Tile;

/**
 * Interface for the GUI component of the Game of Strife, so not the entire
 * GameOfStrife has to be passed to the player.
 * 
 * This interface defines the contract between the game logic and the user
 * interface (UI). Implementations of this interface are responsible for
 * rendering the board, handling user interactions like tile selections,
 * displaying game state updates, and managing popups related to game events
 * (like victory or end-of-game).
 * 
 * Key responsibilities include: Displaying tile selection options, showing
 * popups for victory points and game end, providing access to the starting and
 * victory tiles, and updating the UI to reflect player movement and game state.
 */

public interface GameMovementController {

	int showPathSelectionPopup(ArrayList<Tile> nextTiles);

	boolean showVictoryPointsPopup();

	void showEndOfGamePopup();

	Tile getVictoryTile();

	Tile getStartingTile();

	void updatePlayerPosition(Player player, int playerIndex);

	void setVictoryTile();

	void repaint();

	int getNumWinPoints();

	Player getCurrentPlayer();

	int getCurrentPlayerTurn();
}