package gui;

import javax.swing.*;

import static game.SymbolicConstants.*;

import java.awt.*;
import game.Player;

/**
 * The PlayerPanel class displays player information in a scrollable panel. It
 * shows each player's name, country, resources, income, and victory points,
 * along with the players icon.
 */
public class PlayerPanel extends JPanel {
	// UI Components
	private JLabel[][] statLabels;
	private JPanel[] playerPanels;
	private ImageIcon[] playerIcons;
	private int numberOfPlayers;

	// Game data
	private Player[] players;
	private int currentPlayerIndex = -1;

	// Visual styling
	private Color[] playerColors = { new Color(70, 130, 180), // Player 1 - Steel Blue
			new Color(220, 20, 60), // Player 2 - Crimson
			new Color(34, 139, 34), // Player 3 - Forest Green
			new Color(255, 215, 0) // Player 4 - Gold
	};

	/**
	 * Constructs a PlayerPanel with the specified players.
	 * 
	 * Parameter players: array of Player objects. Parameter numberOfPlayers: number
	 * of players in the game.
	 */
	public PlayerPanel(Player[] players, int numberOfPlayers) {
		this.players = players;
		this.statLabels = new JLabel[numberOfPlayers][4]; // 4 stats per player
		this.playerPanels = new JPanel[numberOfPlayers];
		this.playerIcons = new ImageIcon[numberOfPlayers];
		this.numberOfPlayers = numberOfPlayers;

		// Load player icons
		for (int i = 0; i < numberOfPlayers; i++) {
			String country = players[i].getCountry();
			String path = DATA_PATH + country + ".png";
			ImageIcon icon = new ImageIcon(path);

			// Verify that icon loaded successfully
			if (icon.getImageLoadStatus() != MediaTracker.COMPLETE) {
				JOptionPane.showMessageDialog(null, "Error loading icon for " + country + " from " + path, "Error",
						JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				playerPanels[i] = null;
			} else {

				// Scale icon
				Image image = icon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				playerIcons[i] = new ImageIcon(image);
			}
		}
		// Configure the main panel
		setLayout(new BorderLayout());
		setBackground(new Color(240, 240, 240));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Create a scrollable container
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(new Color(240, 240, 240));

		// Add player panels to content panel
		for (int i = 0; i < numberOfPlayers; i++) {
			playerPanels[i] = createPlayerPanel(players[i].getName(), i);
			contentPanel.add(playerPanels[i]);

			// Add separation between players (except after last player)
			if (i < numberOfPlayers - 1) {
				contentPanel.add(Box.createVerticalStrut(15));
				contentPanel.add(createSeparator());
				contentPanel.add(Box.createVerticalStrut(15));
			}
		}

		// Add content panel to scroll pane
		JScrollPane scrollPane = new JScrollPane(contentPanel);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(scrollPane, BorderLayout.CENTER);

		// Set minimum size to ensure content remains visible
		setMinimumSize(new Dimension(250, 200));
	}

	/**
	 * Creates a horizontal separator between the player panels.
	 * 
	 * @return the JSeparator.
	 */
	private JSeparator createSeparator() {
		JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
		separator.setForeground(new Color(200, 200, 200));
		separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
		return separator;
	}

	/**
	 * Creates an individual player information panel.
	 * 
	 * Parameter playerName: the player's name. Parameter index: the player's index
	 * (for colour assignment).
	 * 
	 * @return the configured JPanel for the player.
	 */
	private JPanel createPlayerPanel(String playerName, int index) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setOpaque(false); // Transparent
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel.setMinimumSize(new Dimension(220, 120));

		// Main content panel with player color
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.setBackground(new Color(playerColors[index].getRed(), playerColors[index].getGreen(),
				playerColors[index].getBlue(), 50)); // Semi-transparent
		contentPanel
				.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(playerColors[index], 2),
						BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		contentPanel.setMinimumSize(new Dimension(200, 100));

		// Player name label
		JLabel nameLabel = new JLabel(playerName);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 15));
		nameLabel.setForeground(playerColors[index]);
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPanel.add(nameLabel);

		JPanel statsContainer = new JPanel(new BorderLayout());
		statsContainer.setBackground(Color.YELLOW);

		// Add player icon if available
		if (playerIcons[index] != null) {
			contentPanel.add(Box.createVerticalStrut(5));
			JLabel iconLabel = new JLabel(playerIcons[index]);
			iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			contentPanel.add(iconLabel);
		}

		// Panel for the player stats
		JPanel statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
		statsPanel.setOpaque(false);
		statsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statsPanel.setMinimumSize(new Dimension(180, 60));

		// Create stat rows
		statLabels[index][0] = createStatRow(statsPanel, "Country", players[index].getCountry(), playerColors[index]);
		statLabels[index][1] = createStatRow(statsPanel, "Budget", String.valueOf(players[index].getResources()),
				playerColors[index]);
		statLabels[index][2] = createStatRow(statsPanel, "Income", String.valueOf(players[index].getIncome()),
				playerColors[index]);
		statLabels[index][3] = createStatRow(statsPanel, "Win Points",
				String.valueOf(players[index].getVictoryPoints()), playerColors[index]);
		contentPanel.add(Box.createVerticalStrut(10));
		contentPanel.add(statsPanel);
		panel.add(contentPanel);

		return panel;
	}

	/**
	 * Creates a single stat row with text and values.
	 * 
	 * Parameter parent: the container panel to add the row to. Parameter title: the
	 * stat name (text). Parameter value: the stat value. Parameter playerColor: the
	 * player's color for styling.
	 * 
	 * @return the value Jlabel.
	 */
	private JLabel createStatRow(JPanel parent, String title, String value, Color playerColor) {
		JPanel rowPanel = new JPanel();
		rowPanel.setLayout(new BoxLayout(rowPanel, BoxLayout.X_AXIS));
		rowPanel.setOpaque(false);
		rowPanel.setMaximumSize(new Dimension(200, 25));
		rowPanel.setBackground(SystemColor.control);
		rowPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

		// Title label
		JLabel titleLabel = new JLabel(title + ": ");
		titleLabel.setFont(new Font("Arial", Font.BOLD, 13));
		titleLabel.setForeground(playerColor.darker());
		rowPanel.add(titleLabel);

		// Pushes the value to right
		rowPanel.add(Box.createHorizontalGlue());

		// Value label
		JLabel valueLabel = new JLabel(value);
		valueLabel.setFont(new Font("Arial", Font.PLAIN, 13));
		valueLabel.setForeground(Color.BLACK);
		rowPanel.add(valueLabel);

		parent.add(rowPanel);
		parent.add(Box.createVerticalStrut(5)); // Spacing between rows
		return valueLabel;
	}

	/**
	 * Sets the current player and updates visual indicators.
	 * 
	 * Parameter playerIndex: index of the current player. Parameter
	 * numberOfPlayers: total number of players.
	 */
	public void setCurrentPlayer(int playerIndex) {
		if (playerIndex < 0 || playerIndex >= numberOfPlayers)
			return;

		// Update borders to highlight current player
		for (int i = 0; i < playerPanels.length; i++) {
			Component[] comps = playerPanels[i].getComponents();
			if (comps.length > 0 && comps[0] instanceof JPanel) {
				JPanel contentPanel = (JPanel) comps[0];
				if (i == playerIndex) {
					// Thick black border for current player
					contentPanel.setBorder(
							BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 3),
									BorderFactory.createEmptyBorder(10, 10, 10, 10)));
				} else {
					// Normal colored border for others
					contentPanel.setBorder(
							BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(playerColors[i], 2),
									BorderFactory.createEmptyBorder(10, 10, 10, 10)));
				}
			}
		}
		currentPlayerIndex = playerIndex;
		highlightCurrentPlayer(currentPlayerIndex);
	}

	/**
	 * Updates all player stats in the UI.
	 * 
	 * Parameter numberOfPlayers: number of players to update.
	 */
	public void updatePlayerTables() {
		for (int i = 0; i < numberOfPlayers; i++) {
			statLabels[i][1].setText(String.valueOf(players[i].getResources()));
			statLabels[i][2].setText(String.valueOf(players[i].getIncome()));
			statLabels[i][3].setText(String.valueOf(players[i].getVictoryPoints()));
		}
	}

	/**
	 * Highlights the current player.
	 * 
	 * Parameter currentPlayerIndex: index of player to highlight.
	 */
	public void highlightCurrentPlayer(int currentPlayerIndex) {
		for (int i = 0; i < playerPanels.length; i++) {
			Component[] comps = playerPanels[i].getComponents();
			if (comps.length > 0 && comps[0] instanceof JPanel) {
				JPanel contentPanel = (JPanel) comps[0];
				if (i == currentPlayerIndex) {
					contentPanel.setBackground(new Color(playerColors[i].getRed(), playerColors[i].getGreen(),
							playerColors[i].getBlue(), 100)); // More opaque for current player
				} else {
					contentPanel.setBackground(new Color(playerColors[i].getRed(), playerColors[i].getGreen(),
							playerColors[i].getBlue(), 50)); // Semi-transparent
				}
			}
		}
	}
}