package gui;

import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Color;

import game.Die;
import game.Board;
import game.Card;
import game.Player;
import game.Tile;
import game.CardDecks;
import static game.SymbolicConstants.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.SystemColor;

/**
 * GameofStrife is the main GUI window and controller class for the board game.
 * It initializes the game state, handles user interaction, updates the board
 * and player states, and manages the game loop including die rolls, card
 * execution, and turn transitions.
 * 
 * This class extends JFrame and implements GameOfStrifeInterface.
 */
public class GameOfStrife extends JFrame implements GameMovementController {
	private static final long serialVersionUID = 1L;
	private JLabel lblDieOutput;

	// Game state variables
	private int currentPlayerTurn = 1;
	private int numberOfPlayers;
	private Die die;
	private List<Player> players = new ArrayList<>();
	private CardDecks cardDecks = new CardDecks();
	private Board board = new Board();
	private Player currentPlayer = null;
	private List<Object[]> activeCardsList = new ArrayList<>();
	private int numWinPoints;

	private BoardPanel boardPanel;
	private PlayerPanel playerPanel;

	/**
	 * Launches the GameofStrife application. This method shows the game setup UI,
	 * creates the game frame, and makes it visible.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					int[] settings = SettingsPanel.showGameSettingsPopup();
					int numberOfPlayers = settings[0];
					int dieNumFaces = settings[1];
					int numOfWinPoints = settings[2];
					String[][] playerInfo = SettingsPanel.showPlayerNameInputPopup(numberOfPlayers);
					GameOfStrife frame = new GameOfStrife(numberOfPlayers, dieNumFaces, numOfWinPoints, playerInfo);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Constructs a new GameofStrife instance with the specified number of players,
	 * die faces, number of victory points to win, and player information.
	 *
	 * @param numberOfPlayers the number of players in the game
	 * @param dieNumFaces     the number of faces on the die
	 * @param numOfWinPoints  the required number of victory points to win
	 * @param playerInfo      a 2D array containing player names and countries
	 */
	public GameOfStrife(int numberOfPlayers, int dieNumFaces, int numOfWinPoints, String[][] playerInfo) {
		// Initialize game state
		currentPlayerTurn = 1;
		this.setNumberOfPlayers(numberOfPlayers);
		setDie(new Die(dieNumFaces));
		this.numWinPoints = numOfWinPoints;

		// Load the Path from the JSON file.
		getBoard().loadPathFromJson(DATA_PATH + "path1.json");

		// Initialize number of players
		// Player 1 gets state True, the others state False
		Tile startTile = getBoard().getStartingTile();
		for (int i = 0; i < playerInfo.length; i++) {
			String name = playerInfo[i][0];
			String country = playerInfo[i][1];
			Player player = new Player(name, country, i == 0, startTile);
			getPlayers().add(player);
		}

		// Set Player 1 as current Player and pay the first income
		currentPlayer = getPlayers().get(0);
		currentPlayer.getPaid();

		// Load all the cardDecks
		cardDecks.loadCardDeckFromJson(DATA_PATH + "cards1.json");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setMinimumSize(new Dimension(1000, 700));
		setLayout(new BorderLayout());

		// Set the custom icon for the window
		try {
			Image icon = ImageIO.read(new File(DATA_PATH + "icon.png"));
			setIconImage(icon);
		} catch (IOException e) {
			System.err.println("Error loading custom icon: " + e.getMessage());
		}

		// Create the different JPanels
		add(createPlayerPanel(), BorderLayout.WEST);
		add(createBoardPanel(), BorderLayout.CENTER);
		add(createInteractionPanel(), BorderLayout.EAST);

		setVisible(true);

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				updateUIForCurrentPlayer();
			}
		});

	}

	/**
	 * Handles the action when the die is rolled. Moves the current player, executes
	 * card if drawn, updates player position, and transitions to the next turn.
	 */
	private void handleDieRoll() {
		getDie().roll();
		int roll = getDie().getValue();
		lblDieOutput.setText("Roll result: " + roll);

		currentPlayer.move(roll, this);
		getBoardPanel().updatePlayerPosition(currentPlayer, currentPlayerTurn - 1);

		Card card = currentPlayer.drawCard(cardDecks);
		if (card != null) {
			card.executeCard(this);
		}

		nextTurn();
		updatePlayerTables();
	}

	/**
	 * Creates and returns the panel containing player information.
	 *
	 * @return JPanel representing the player panel
	 */
	private JPanel createPlayerPanel() {
		playerPanel = new PlayerPanel(getPlayers().toArray(new Player[0]), getNumberOfPlayers());
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(playerPanel, BorderLayout.CENTER);
		panel.setPreferredSize(new Dimension(250, 0));
		return panel;
	}

	/**
	 * Creates and returns the panel representing the game board.
	 *
	 * @return JPanel representing the board panel
	 */
	private JPanel createBoardPanel() {
		setBoardPanel(new BoardPanel(getBoard(), getPlayers()));

		// Set a random Victory Tile
		getBoardPanel().setVictoryTile();
		getBoardPanel().repaint();

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(getBoardPanel(), BorderLayout.CENTER);
		return panel;
	}

	/**
	 * Creates and returns the panel for die rolling.
	 *
	 * @return JPanel representing the interaction panel
	 */
	private JPanel createInteractionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(300, 0));
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

		// Create a button to roll the die
		JButton btnRollDie = new JButton("Roll Die");
		btnRollDie.setFont(new Font("Arial", Font.BOLD, 18));
		btnRollDie.setBackground(new Color(70, 130, 180));
		btnRollDie.setForeground(Color.WHITE);
		btnRollDie.setFocusPainted(false);
		btnRollDie.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		btnRollDie.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRollDie.addActionListener(e -> handleDieRoll());

		// Displays result of die roll
		lblDieOutput = new JLabel("Roll result: -", SwingConstants.CENTER);
		lblDieOutput.setFont(new Font("Arial", Font.BOLD, 20));
		lblDieOutput.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblDieOutput.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

		panel.add(btnRollDie);
		panel.add(Box.createVerticalStrut(10));
		panel.add(lblDieOutput);

		return panel;
	}

	/**
	 * Advances the game to the next player's turn. Applies income, updates UI, and
	 * handles countdowns for card effects.
	 */
	public void nextTurn() {
		countdown();
		currentPlayerTurn = (currentPlayerTurn % getNumberOfPlayers()) + 1;
		currentPlayer = getPlayers().get(currentPlayerTurn - 1);
		currentPlayer.getPaid();
		updateUIForCurrentPlayer();
	}

	/**
	 * Reverts the game to the previous player's turn. Reverts the resources,
	 * updates the UI and handles countdowns for card effects accordingly.
	 */
	public void previousTurn() {
		countup();
		currentPlayer.adjustResources(-currentPlayer.getIncome());

		currentPlayerTurn = (currentPlayerTurn - 1);
		if (currentPlayerTurn == 0) {
			currentPlayerTurn = getNumberOfPlayers();
		}
		currentPlayer = getPlayers().get(currentPlayerTurn - 1);

		updateUIForCurrentPlayer();
	}

	/**
	 * Updates the UI to visually reflect which player's turn it is. Adds a black
	 * border around the current player in the player tables.
	 */
	private void updateUIForCurrentPlayer() {
		playerPanel.setCurrentPlayer(currentPlayerTurn - 1);
	}

	/**
	 * Updates all player stats in the UI. Updates all the resources, income and
	 * victory points the players have.
	 */
	public void updatePlayerTables() {
		playerPanel.updatePlayerTables();
	}

	/**
	 * Finds and returns a tile by its unique ID.
	 *
	 * @param ID the ID of the tile
	 * @return the first Tile with the specified ID, or null if not found
	 */
	public Tile findTileByID(int ID) {
		for (Tile tile : getBoard().getAllTiles()) {
			if (tile.getID() == ID) {
				return tile;
			}
		}
		return null;
	}

	/**
	 * Shows a popup dialog to let the player choose between multiple paths.
	 *
	 * @param nextTiles the possible next tiles the player can move to
	 * @return the index of the selected path (0 for Left, 1 for Right)
	 */
	@Override
	public int showPathSelectionPopup(ArrayList<Tile> nextTiles) {
		
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
		
		String[] options = { "Left", "Right" };

		int choice = JOptionPane.showOptionDialog(null, "Choose a path:", "Path Selection", JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		// Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

		// if user closes the popup return 0 (Left)
		return (choice == -1) ? 0 : choice;
	}

	/**
	 * Decreases the duration of active card effects and removes any that have
	 * expired.
	 */
	private void countdown() {
		for (int i = getActiveCardsList().size() - 1; i >= 0; i--) {
			Object[] pair = getActiveCardsList().get(i);
			int count = (Integer) pair[1];
			count--;

			if (count == 0) {
				Card card = (Card) pair[0];
				Map<String, Object> effectData = (Map<String, Object>) pair[2]; // Retrieve effect data
				card.removeEffect(this, effectData); // Pass data to removeEffect
				getActiveCardsList().remove(i);
			} else {
				pair[1] = count;
			}
		}
	}

	/**
	 * Increases the duration count of active cards. Used when reverting a turn.
	 */
	private void countup() {
		for (int i = getActiveCardsList().size() - 1; i >= 0; i--) {
			Object[] pair = getActiveCardsList().get(i);
			pair[1] = (Integer) pair[1] + 1;
		}
	}

	/**
	 * Shows a popup asking the player if they want to buy a victory point.
	 *
	 * @return true if the player chooses to buy, false otherwise
	 */
	@Override
	public boolean showVictoryPointsPopup() {
		String[] options = new String[2];
		options[0] = "Buy";
		options[1] = "Don't Buy";

		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
		
		int choice = JOptionPane.showOptionDialog(null, "Do you want to buy a Victory Point for a 1000 gold?",
				"Victory Point", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		
		// Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

		// Return true if the user chose to buy, false otherwise
		return choice == 0;
	}

	/**
	 * Displays the end-of-game dialog when a player wins and prompts for restart or
	 * exit.
	 */
	@Override
	public void showEndOfGamePopup() {
		String[] options = { "Restart the Game", "Exit" };

		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
		
		int choice = JOptionPane.showOptionDialog(this,
				currentPlayer.getName() + " has won the game!\nWhat would you like to do?", "Game Over",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
		
		// Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

		if (choice == 0) {
			// Close current game window
			this.dispose();

			// Relaunch game setup in a new window
			EventQueue.invokeLater(() -> {
				try {
					int[] settings = SettingsPanel.showGameSettingsPopup();
					int numberOfPlayers = settings[0];
					int dieNumFaces = settings[1];
					int numOfWinPoints = settings[2];
					String[][] playerInfo = SettingsPanel.showPlayerNameInputPopup(numberOfPlayers);
					GameOfStrife newGame = new GameOfStrife(numberOfPlayers, dieNumFaces, numOfWinPoints, playerInfo);
					newGame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		} else {
			// Exit game
			System.exit(0);
		}
	}

	/**
	 * Returns the tile designated as the victory tile.
	 *
	 * @return the victory Tile
	 */
	@Override
	public Tile getVictoryTile() {
		return getBoardPanel().getVictoryTile();
	}

	/**
	 * Designate a tile on the board as the victory tile.
	 */
	@Override
	public void setVictoryTile() {
		getBoardPanel().setVictoryTile();
	}

	/**
	 * Returns the starting tile of the board.
	 *
	 * @return the starting Tile
	 */
	@Override
	public Tile getStartingTile() {
		return getBoard().getStartingTile();
	}

	/**
	 * Gets the number of victory points required to win the game.
	 *
	 * @return the number of victory points
	 */
	@Override
	public int getNumWinPoints() {
		return this.numWinPoints;
	}

	/**
	 * Returns the current active player.
	 *
	 * @return the current Player
	 */
	@Override
	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	/**
	 * Returns the turn number of the current player (1-based index).
	 *
	 * @return the current player's turn number
	 */
	@Override
	public int getCurrentPlayerTurn() {
		return this.currentPlayerTurn;
	}

	/**
	 * Updates the board Panel to reflect a player's new position.
	 *
	 * @param player      the player to move
	 * @param playerIndex the index of the player in the list
	 */
	@Override
	public void updatePlayerPosition(Player player, int playerIndex) {
		getBoardPanel().updatePlayerPosition(player, playerIndex);
	}

	/**
	 * Repaints the board Panel.
	 */
	@Override
	public void repaint() {
		getBoardPanel().repaint();
	}

	/**
	 * Returns the list of players in the game.
	 * 
	 * @return the list of Player objects representing the players
	 */
	public List<Player> getPlayers() {
		return players;
	}

	/**
	 * Sets the list of players for the game.
	 * 
	 * @param players the list of Player objects to set
	 */
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

	/**
	 * Returns the board panel component.
	 * 
	 * @return the BoardPanel instance representing the game board display
	 */
	public BoardPanel getBoardPanel() {
		return boardPanel;
	}

	/**
	 * Sets the board panel component.
	 * 
	 * @param boardPanel the BoardPanel instance to set
	 */
	public void setBoardPanel(BoardPanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	/**
	 * Returns the list of active cards in the game.
	 * Each card is represented as an Object array containing card data.
	 * 
	 * @return the list of active cards
	 */
	public List<Object[]> getActiveCardsList() {
		return activeCardsList;
	}

	/**
	 * Sets the list of active cards in the game.
	 * 
	 * @param activeCardsList the list of card data (as Object arrays) to set
	 */
	public void setActiveCardsList(List<Object[]> activeCardsList) {
		this.activeCardsList = activeCardsList;
	}

	/**
	 * Returns the current number of players in the game.
	 * 
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}

	/**
	 * Sets the number of players for the game.
	 * 
	 * @param numberOfPlayers the number of players to set
	 */
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	/**
	 * Returns the die used in the game.
	 * 
	 * @return the Die object representing the game die
	 */
	public Die getDie() {
		return die;
	}

	/**
	 * Sets the die to be used in the game.
	 * 
	 * @param die the Die object to set
	 */
	public void setDie(Die die) {
		this.die = die;
	}

	/**
	 * Returns the game board.
	 * 
	 * @return the Board object representing the game board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Sets the game board.
	 * 
	 * @param board the Board object to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}
}
