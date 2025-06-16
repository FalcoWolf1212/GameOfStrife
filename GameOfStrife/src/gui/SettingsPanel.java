package gui;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static game.SymbolicConstants.*;

/**
 * A settings panel class that provides UI dialogs for game configuration and
 * player information input
 */
public class SettingsPanel {

	/**
	 * Displays a game settings pop-up and returns user selections. Returns array
	 * containing [numPlayers, dieFaces, winPoints] or null if canceled.
	 */
	public static int[] showGameSettingsPopup() {
		// Available options for game settings
		Integer[] playerOptions = { 2, 3, 4 };
		Integer[] dieOptions = { 3, 4, 5, 6, 7, 8, 9, 10 };
		Integer[] winPointsOptions = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

		try {
			// Set cross-platform look and feel
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Player count combo box setup
		JComboBox<Integer> playerCombo = new JComboBox<>(playerOptions);
		playerCombo.setSelectedItem(DEFAULT_PLAYERS);
		playerCombo.setFont(COMBO_FONT);
		playerCombo.setBackground(Color.WHITE);
		playerCombo.setForeground(TEXT_COLOR);

		// Die faces combo box setup
		JComboBox<Integer> dieCombo = new JComboBox<>(dieOptions);
		dieCombo.setSelectedItem(DEFAULT_DIE_FACES);
		dieCombo.setFont(COMBO_FONT);
		dieCombo.setBackground(Color.WHITE);
		dieCombo.setForeground(TEXT_COLOR);

		// Win points combo box setup
		JComboBox<Integer> winPointsCombo = new JComboBox<>(winPointsOptions);
		winPointsCombo.setSelectedItem(DEFAULT_WIN_POINTS);
		winPointsCombo.setFont(COMBO_FONT);
		winPointsCombo.setBackground(Color.WHITE);
		winPointsCombo.setForeground(TEXT_COLOR);

		// Main panel layout
		JPanel panel = new JPanel(new GridLayout(3, 3, 5, 5));
		panel.setBackground(SystemColor.control);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		// Add components to panel
		JLabel playersLabel = new JLabel("Number of players:");
		playersLabel.setFont(LABEL_FONT);
		playersLabel.setForeground(TEXT_COLOR);
		panel.add(playersLabel);
		panel.add(playerCombo);

		JLabel dieLabel = new JLabel("Number of die faces (recommended: 6):");
		dieLabel.setFont(LABEL_FONT);
		dieLabel.setForeground(TEXT_COLOR);
		panel.add(dieLabel);
		panel.add(dieCombo);

		JLabel winPointsLabel = new JLabel("Required number of win points to win:");
		winPointsLabel.setFont(LABEL_FONT);
		winPointsLabel.setForeground(TEXT_COLOR);
		panel.add(winPointsLabel);
		panel.add(winPointsCombo);

		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);

		// Show the pop-up
		int result = JOptionPane.showConfirmDialog(null, panel, "Game Settings", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		// Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

		// Process user response
		if (result == JOptionPane.OK_OPTION) {
			int selectedPlayers = (Integer) playerCombo.getSelectedItem();
			int selectedDie = (Integer) dieCombo.getSelectedItem();
			int selectedNumOfWinPoints = (Integer) winPointsCombo.getSelectedItem();
			return new int[] { selectedPlayers, selectedDie, selectedNumOfWinPoints };
		} else {
			System.out.println("User stopped the game by cancelling");
			System.exit(0);
			return null; // stop the game
		}
	}

	/**
	 * Displays a player information settings input pop-up. Parameter
	 * numberOfPlayers. Returns 2D array where each row contains [playerName,
	 * country] or null if canceled.
	 */
	public static String[][] showPlayerNameInputPopup(int numberOfPlayers) {
		// Available country options
		String[] countryOptions = { "Netherlands", "Morocco", "Friesland", "Hungary", "Iran", "Turkiye", "Ethiopia", "France" };
		JPanel panel = new JPanel(new GridLayout(numberOfPlayers, 2, 10, 10));

		// Main panel setup
		panel.setBackground(SystemColor.control);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

		// Arrays to store input components
		JTextField[] nameFields = new JTextField[numberOfPlayers];
		JComboBox<String>[] countryCombos = new JComboBox[numberOfPlayers];

		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Create labels and text fields for each player
		for (int i = 0; i < numberOfPlayers; i++) {
			JLabel label = new JLabel("Player " + (i + 1) + " name:");
			label.setFont(LABEL_FONT);
			label.setForeground(TEXT_COLOR);
			panel.add(label);

			try {
				// Create formatted text field with character limit
				MaskFormatter formatter = new MaskFormatter("***************");
				nameFields[i] = new JFormattedTextField(formatter);
				nameFields[i].setFont(COMBO_FONT);
				nameFields[i].setBackground(Color.WHITE);
				nameFields[i].setForeground(TEXT_COLOR);
				panel.add(nameFields[i]);
			} catch (java.text.ParseException e) {
				e.printStackTrace();
				// Use regular text field if the formatter fails
				nameFields[i] = new JFormattedTextField();
				panel.add(nameFields[i]);
			}

			// Country selection
			JLabel countryLabel = new JLabel("Country:");
			countryLabel.setFont(LABEL_FONT);
			countryLabel.setForeground(TEXT_COLOR);
			panel.add(countryLabel);
			countryCombos[i] = new JComboBox<>(countryOptions);
			countryCombos[i].setSelectedIndex(i);
			countryCombos[i].setFont(COMBO_FONT);
			countryCombos[i].setBackground(Color.WHITE);
			countryCombos[i].setForeground(TEXT_COLOR);
			panel.add(countryCombos[i]);
		}

		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);

		// Show the pop-up
		int result = JOptionPane.showConfirmDialog(null, panel, "Enter Player Names", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		// Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

		// Process user response
		if (result == JOptionPane.OK_OPTION) {
			String[][] playerData = new String[numberOfPlayers][2];
			Set<String> selectedCountries = new HashSet<>();

			// Collect and validate input data
			for (int i = 0; i < numberOfPlayers; i++) {
				String name = nameFields[i].getText().trim();
				// Set default name if empty
				if (name.isEmpty()) {
					name = "Player " + (i + 1);
				}
				String country = (String) countryCombos[i].getSelectedItem();

				// Ensure unique country selection
				if (selectedCountries.contains(country)) {
					JOptionPane.showMessageDialog(null, "Each country must be unique!", "Error",
							JOptionPane.ERROR_MESSAGE);
					return showPlayerNameInputPopup(numberOfPlayers); // Re-prompt on duplicates
				}
				selectedCountries.add(country);
				playerData[i][0] = name;
				playerData[i][1] = country;
			}
			return playerData;
		} else {
			System.out.println("User stopped the game by cancelling");
			System.exit(0);
			return null;
		}
	}
}
