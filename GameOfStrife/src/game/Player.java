package game;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import gui.GameMovementController;

public class Player {
	// Initialize variables
	private boolean state;
	private String name;
	private String country;
	protected int resources;
	private int remainingSteps;
	private Tile currentTile;
	private int income;
	private int stepsBonus;
	private int victoryPoints;

	/**
	 * Constructs a Player with the given attributes.
	 * 
	 * @param name         The name of the player.
	 * @param country      The country of the player.
	 * @param initialState The initial state of the player (active or not).
	 * @param startingTile The starting tile where the player begins.
	 */
	public Player(String name, String country, boolean initialState, Tile startingTile) {
		this.name = name;
		this.country = country;
		this.state = initialState;
		this.resources = 500;
		this.remainingSteps = 0;
		this.currentTile = startingTile;
		this.income = 50;
		this.stepsBonus = 0;
	}

	/**
	 * Returns the current state of the player (active or not).
	 * 
	 * @return true if the player is active, false otherwise.
	 */
	public boolean getState() {
		return state;
	}

	/**
	 * Returns the current number of victory points of the player.
	 * 
	 * @return The player's victory points.
	 */
	public int getVictoryPoints() {
		return this.victoryPoints;
	}

	/**
	 * Adds the specified number of victory points to the player.
	 * 
	 * @param victoryPoints The number of victory points to be added.
	 */
	public void addVictoryPoints(int victoryPoints) {
		this.victoryPoints += victoryPoints;
	}

	/**
	 * Sets the state of the player.
	 * 
	 * @param state The new state of the player.
	 */
	public void setState(boolean state) {
		this.state = state;
	}

	/**
	 * Returns the steps bonus of the player.
	 * 
	 * @return The player's steps bonus.
	 */
	public int getStepsBonus() {
		return this.stepsBonus;
	}

	/**
	 * Adds the specified bonus to the player's steps bonus.
	 * 
	 * @param bonus The bonus to be added.
	 */
	public void addStepsBonus(int bonus) {
		this.stepsBonus += bonus;
	}

	/**
	 * Returns the income of the player.
	 * 
	 * @return The player's income.
	 */
	public int getIncome() {
		return this.income;
	}

	/**
	 * Returns the name of the player.
	 * 
	 * @return The player's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the player.
	 * 
	 * @param name The new name of the player.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the resources (e.g., money) the player has.
	 * 
	 * @return The player's resources.
	 */
	public int getResources() {
		return resources;
	}

	/**
	 * Sets the resources of the player.
	 * 
	 * @param resources The new resources of the player.
	 */
	public void setResources(int resources) {
		this.resources = resources;
	}

	/**
	 * Adjusts the player's resources by the specified amount. If the resources go
	 * below 0, it is set to 0.
	 * 
	 * @param resources The amount to adjust the resources by (can be positive or
	 *                  negative).
	 */
	public void adjustResources(int resources) {
		this.resources += resources;
		if (this.resources < 0) {
			this.resources = 0;
		}
	}

	/**
	 * Pays the player their current income.
	 * 
	 */
	public void getPaid() {
		adjustResources(getIncome());
	}

	/**
	 * Sets the current tile the player is on.
	 * 
	 * @param tile The new current tile of the player.
	 */
	public void setCurrentTile(Tile tile) {
		this.currentTile = tile;
	}

	/**
	 * Returns the current tile the player is on.
	 * 
	 * @return The player's current tile.
	 */
	public Tile getCurrentTile() {
		return this.currentTile;
	}

	/**
	 * Returns the country of the player.
	 * 
	 * @return The player's country.
	 */
	public String getCountry() {
		return this.country;
	}

	/**
	 * Returns the remaining steps the player can take.
	 * 
	 * @return The remaining steps.
	 */
	public int getRemainingSteps() {
		return this.remainingSteps;
	}

	/**
	 * Sets the number of remaining steps the player can take.
	 * 
	 * @param steps The number of remaining steps.
	 */
	public void setRemainingSteps(int steps) {
		this.remainingSteps = steps;
	}

	/**
	 * Adjusts the number of remaining steps the player can take.
	 * 
	 * @param steps The number to adjust the remaining steps by (can be positive or
	 *              negative).
	 */
	public void adjustRemainingSteps(int steps) {
		this.remainingSteps += steps;
	}

	/**
	 * Moves the player by the specified die value, adjusting for the player's steps
	 * bonus, and interacts with the game interface.
	 * 
	 * @param dieValue The value rolled on the die.
	 * @param gui      The game interface that manages game actions and displays.
	 */
	public void move(int dieValue, GameMovementController gui) {
		this.remainingSteps = dieValue + stepsBonus;

		while (shouldContinueMoving(gui)) {
			ArrayList<Tile> nextTiles = currentTile.getNextTiles();
			int pathIndex = selectPath(nextTiles, gui);
			moveToNextTile(nextTiles.get(pathIndex));
			remainingSteps--;

			handleVictoryTileOpportunity(gui);
		}
	}

	/**
	 * Determines if the player should continue moving.
	 * 
	 * @param gui The game interface used to check whether movement is allowed.
	 * @return true if the player can still move; false otherwise.
	 */
	private boolean shouldContinueMoving(GameMovementController gui) {
		return this.remainingSteps > 0 && currentTile != null && !currentTile.getNextTiles().isEmpty();
	}

	/**
	 * Allows the player to select a path from a list of next tiles.
	 * 
	 * @param nextTiles A list of next possible tiles.
	 * @param gui       The game interface that displays the path options.
	 * @return The index of the selected path.
	 */
	private int selectPath(ArrayList<Tile> nextTiles, GameMovementController gui) {
		if (nextTiles.size() > 1) {
			return gui.showPathSelectionPopup(nextTiles);
		}
		return 0;
	}

	/**
	 * Moves the player to the specified next tile.
	 * 
	 * @param nextTile The tile the player moves to.
	 */
	public void moveToNextTile(Tile nextTile) {
		setCurrentTile(nextTile);
	}

	/**
	 * Handles the player's opportunity to purchase a victory point when landing on
	 * a victory tile.
	 * 
	 * @param gui The game interface used to handle interactions with the victory
	 *            tile.
	 */
	public void handleVictoryTileOpportunity(GameMovementController gui) {
		if (currentTile == gui.getVictoryTile() && gui.showVictoryPointsPopup()) {
			if (getResources() >= 1000) {
				purchaseVictoryPoint(gui);
			} else {
				JOptionPane.showMessageDialog(null, "You do not have enough gold to buy the Victory Point");
			}
		}
	}

	/**
	 * Allows the player to purchase a victory point and updates the game interface
	 * accordingly.
	 * 
	 * @param gui The game interface used to update the game state.
	 */
	public void purchaseVictoryPoint(GameMovementController gui) {
		adjustResources(-1000);
		addVictoryPoints(1);
		gui.setVictoryTile();
		gui.repaint();

		// Move player back to start
		Tile startTile = gui.getStartingTile();
		setCurrentTile(startTile);
		gui.updatePlayerPosition(gui.getCurrentPlayer(), gui.getCurrentPlayerTurn() - 1);
		if (victoryPoints >= gui.getNumWinPoints()) {
			gui.showEndOfGamePopup();
		}
	}

	/**
	 * Returns the color of the tile the player is currently on.
	 * 
	 * @return The color (type) of the current tile.
	 */
	public String getColor() {
		if (currentTile != null) {
			return currentTile.getType();
		}
		return null;
	}

	/**
	 * Draws a card from a deck, shuffling the deck if necessary.
	 * 
	 * @param deck     The deck of cards to draw from.
	 * @param usedDeck The deck of previously used cards.
	 * @return A card drawn from the deck.
	 */
	private Card drawFromDeck(ArrayList<Card> deck, ArrayList<Card> useddeck) {
		// Fill the deck with a shuffled card list if empty
		if (deck.isEmpty()) {
			Collections.shuffle(useddeck);
			deck.addAll(useddeck);
			useddeck.clear();
		}

		Card card = deck.remove(0);
		useddeck.add(card);
		return card;
	}

	/**
	 * Draws a card from a deck based on the color of the tile the player is
	 * currently on.
	 * 
	 * @param cardDecks The card decks to draw from.
	 * @return A card drawn from the appropriate deck.
	 */
	public Card drawCard(CardDecks cardDecks) {
		String color = getColor().toUpperCase();

		ArrayList<Card> deck = cardDecks.getCardsByType(color);
		ArrayList<Card> usedDeck = cardDecks.getUsedCardsByType(color);

		if (deck != null) {
			Card card = drawFromDeck(deck, usedDeck);
			return card;
		} else {
			return null;
		}
	}

	public void adjustIncome(int i) {
		this.income += i;
	}
}