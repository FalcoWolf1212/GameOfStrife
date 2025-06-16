package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import game.Board;
import game.Player;
import game.Tile;
import static game.SymbolicConstants.*;

/**
 * The BoardPanel class represents the visual game board component. It handles
 * rendering of the game board background, tiles, player pieces, and victory
 * star. The panel supports scaling and maintains player positions.
 */
public class BoardPanel extends JPanel {
	
	// Images
	private BufferedImage backgroundImage; // The board background image
	private Dimension originalBackgroundSize; // Original dimensions of background image
	private BufferedImage[] playerIcons; // Array of player icons
	private BufferedImage starIcon; // Icon for the victory tile

	// Game state references
	private Board board;
	private List<Player> players;

	// Position tracking
	private Map<Tile, Point[]> playerPositions;
	private Map<Player, Tile> playerCurrentTileMap;
	private Map<Tile, JPanel> tilePanels;

	// Scaling and positioning
	private double currentScale = 1.0;
	private Point boardOffset = new Point(0, 0);
	private Tile victoryTile;

	// Explicit type here to avoid diamond <> compilation error in anonymous class
	private static final Map<String, Color> tileColors = new HashMap<String, Color>() {
		{
			put("start", new Color(255, 238, 144));
			put("end", new Color(255, 182, 193));
			put("white", Color.WHITE);
			put("blue", new Color(173, 216, 230));
			put("green", new Color(152, 251, 152));
			put("red", new Color(255, 99, 71));
		}
	};

	/**
	 * Constructs a new BoardPanel with the specified game board and players.
	 * Parameter board: the game board. Parameter players: list of players in the
	 * game.
	 */
	public BoardPanel(Board board, List<Player> players) {
		this.board = board;
		this.players = players;

		// Initialize data structures
		this.playerPositions = new HashMap<>();
		this.playerCurrentTileMap = new HashMap<>();
		this.tilePanels = new HashMap<>();

		// Configure panel properties
		setLayout(null); // Use absolute positioning for tiles
		setOpaque(false); // Allow background to be seen

		try {
			// Load and set up background image
			backgroundImage = ImageIO.read(new File(DATA_PATH + "background.png"));
			originalBackgroundSize = new Dimension(backgroundImage.getWidth(), backgroundImage.getHeight());
			setPreferredSize(originalBackgroundSize);
		} catch (IOException e) {
			System.err.println("Could not load background image: " + e.getMessage());
		}

		// Initialize game elements on the board
		starIconOnTile();
		loadPlayerIcons();
		initTiles();
		initPlayerPositions();
		addResizeListener();
	}

	/**
	 * Loads player icons from image files based on player countries. Creates
	 * placeholder images if loading fails.
	 */
	private void loadPlayerIcons() {
		// Load player icons
		int numberOfPlayers = players.size();
		playerIcons = new BufferedImage[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			String country = players.get(i).getCountry();
			try {
				// Try to load player icon from file
				playerIcons[i] = ImageIO.read(new File(DATA_PATH + country + ".png"));
			} catch (IOException e) {
				System.err.println("Error loading icon for " + country);
				// Create a transparent placeholder if loading fails
				playerIcons[i] = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
			}
		}
	}

	/**
	 * Initializes all tile panels and adds them to the board. Creates a visual
	 * panel for each tile and sets up player positions.
	 */
	private void initTiles() {
		for (Tile tile : board.getAllTiles()) {
			JPanel tilePanel = createTilePanel(tile);
			tilePanel.setBounds(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
			tilePanels.put(tile, tilePanel);
			playerPositions.put(tile, new Point[PLAYERS_PER_TILE]);
			add(tilePanel);
		}

	}

	/**
	 * Creates a visual panel for a game tile with transparent coloring. Parameter
	 * tile: the tile to represent. Returns a JPanel that represents the tile.
	 */
	private JPanel createTilePanel(Tile tile) {
		// Get tile color from map or use default light gray
		Color tileColor = tileColors.getOrDefault(tile.getType(), Color.LIGHT_GRAY);

		// Create semi-transparent version of the color (alpha = 180)
		Color transparentColor = new Color(tileColor.getRed(), tileColor.getGreen(), tileColor.getBlue(), 180);

		return new JPanel() {
			{
				setOpaque(false); // Make panel transparent
			}

			@Override
			protected void paintComponent(Graphics g) {
				// Draw tile background
				g.setColor(transparentColor);
				g.fillRect(0, 0, getWidth(), getHeight());

				// Draw tile border
				g.setColor(Color.BLACK);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

				// Draw player icons on the tile
				Point[] playersOnTile = playerPositions.get(tile);
				int iconSize = Math.min(getWidth(), getHeight()) / 2;
				if (playersOnTile != null) {
					for (int playerIdx = 0; playerIdx < playersOnTile.length; playerIdx++) {
						if (playersOnTile[playerIdx] != null && playerIcons[playerIdx] != null) {

							// Calculate position based on player index (2x2 grid)
							int drawX = (playerIdx % 2) * iconSize;
							int drawY = (playerIdx / 2) * iconSize;
							g.drawImage(playerIcons[playerIdx], drawX, drawY, iconSize, iconSize, null);
						}
					}
				}

				// ⭐ Draw star icon if this tile is the victory tile
				if (tile.equals(victoryTile) && starIcon != null) {
					int starSize = (int) (Math.min(getWidth(), getHeight()) * 0.8);
					int starX = (getWidth() - starSize) / 2;
					int starY = getHeight() - starSize - 5; // Bottom center with 5px padding
					g.drawImage(starIcon, starX, starY, starSize, starSize, null);
				}

			}
		};
	}

	/**
	 * Initializes player positions on their starting tiles.
	 */
	private void initPlayerPositions() {
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			Tile tile = player.getCurrentTile();

			// Map player to their current tile
			playerCurrentTileMap.put(player, tile);

			// Get position array for this tile and set the player at position (0,0) within
			// this tile
			Point[] positions = playerPositions.get(tile);
			if (positions != null) {
				positions[i] = new Point(0, 0);
				tilePanels.get(tile).repaint();
			}
		}
	}

	/**
	 * Adds a component listener that handles board scaling.
	 */
	private void addResizeListener() {
//		 Add resize listener
		addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentResized(java.awt.event.ComponentEvent evt) {
				rescaleBoard();
			}
		});
	}

	/**
	 * Rescales the board and all tiles to fit the current panel size while
	 * maintaining aspect ratio.
	 */
	private void rescaleBoard() {
		if (originalBackgroundSize == null)
			return;

		// Calculate scaling ratios
		Dimension panelSize = getSize();
		double widthRatio = (double) panelSize.width / originalBackgroundSize.width;
		double heightRatio = (double) panelSize.height / originalBackgroundSize.height;

		// Use the smaller ratio to maintain aspect ratio
		currentScale = Math.min(widthRatio, heightRatio);

		// Calculate centered position
		int scaledWidth = (int) (originalBackgroundSize.width * currentScale);
		int scaledHeight = (int) (originalBackgroundSize.height * currentScale);
		boardOffset.x = (panelSize.width - scaledWidth) / 2;
		boardOffset.y = (panelSize.height - scaledHeight) / 2;

		// Reposition all tiles
		for (Tile tile : board.getAllTiles()) {
			JPanel panel = tilePanels.get(tile);
			if (panel != null) {
				int newX = boardOffset.x + (int) (tile.getX() * currentScale);
				int newY = boardOffset.y + (int) (tile.getY() * currentScale);
				int newW = (int) (tile.getWidth() * currentScale);
				int newH = (int) (tile.getHeight() * currentScale);
				panel.setBounds(newX, newY, newW, newH);
			}
		}

		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		// Draw background image with scaling if available
		if (backgroundImage != null && originalBackgroundSize != null) {
			int scaledWidth = (int) (originalBackgroundSize.width * currentScale);
			int scaledHeight = (int) (originalBackgroundSize.height * currentScale);
			g.drawImage(backgroundImage, boardOffset.x, boardOffset.y, scaledWidth, scaledHeight, this);
		}
	}

	/**
	 * Updates a player's position on the board. Parameter player: the player to
	 * move. Parameter playerIndex: the index of the player in the players list.
	 */
	public void updatePlayerPosition(Player player, int playerIndex) {
		// Remove player from old tile
		Tile oldTile = playerCurrentTileMap.get(player);
		if (oldTile != null && playerPositions.containsKey(oldTile)) {
			playerPositions.get(oldTile)[playerIndex] = null;
			tilePanels.get(oldTile).repaint();
		}

		// Add player to new tile
		Tile newTile = player.getCurrentTile();
		if (newTile != null && playerPositions.containsKey(newTile)) {
			playerPositions.get(newTile)[playerIndex] = new Point(0, 0);
			playerCurrentTileMap.put(player, newTile);
			tilePanels.get(newTile).repaint();
		}
	}

	/**
	 * Returns the preferred size for this component. 
	 * If an original background size has been set, that size will be returned.
	 * Otherwise, falls back to the parent class's preferred size implementation.
	 * 
	 * @return The preferred Dimension for this component
	 */
	@Override
	public Dimension getPreferredSize() {
		return originalBackgroundSize != null ? originalBackgroundSize : super.getPreferredSize();
	}

	/**
	 * Randomly selects a victory tile from the board's victory tiles (where
	 * 'victory' = True).
	 */
	public void setVictoryTile() {
		List<Tile> victoryTiles = board.getVictoryTiles();
		int randomNum = (int) (Math.random() * victoryTiles.size());
		this.victoryTile = victoryTiles.get(randomNum);
	}

	/**
	 * Loads the star icon image for the victory tile.
	 */
	private void starIconOnTile() {
		// Load player icons
		try {
			starIcon = ImageIO.read(new File("data/star.png"));
		} catch (IOException e) {
			System.err.println("Error loading icon for star");
		}

	}

	/**
	 * Gets the current victory tile. Returns the victory tile.
	 */
	public Tile getVictoryTile() {
		return victoryTile;
	}
}
