package game;

import java.awt.Color;
import java.awt.SystemColor;
import java.util.Map;
import static game.SymbolicConstants.*;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import gui.GameOfStrife;

public class GreenCard extends Card {
	/**
	 * Constructs a GreenCard with the specified description, value change, and
	 * type.
	 * 
	 * @param description A description of what the GreenCard does (e.g., "Grants
	 *                    you 50 gold").
	 * @param valueChange The amount of gold that will be added or subtracted
	 *                    (typically positive for GreenCard).
	 * @param type        The type of card (e.g., "GreenCard").
	 */
	public GreenCard(String description, int valueChange, String type) {
		super(description, valueChange, type);
	}

	/**
	 * Executes the effect of the GreenCard by adjusting the current player's
	 * resources. It shows a message to the player indicating the effect of the
	 * GreenCard and changes the player's gold reserve.
	 * 
	 * @param game The instance of the GameofStrife game, used to get the current
	 *             player.
	 */
	@Override
	public void executeCard(GameOfStrife game) {
		
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
		
		JOptionPane.showMessageDialog((game), this.getDescription(), GREEN_CARD, JOptionPane.INFORMATION_MESSAGE);
		Player currentPlayer = game.getCurrentPlayer();
		currentPlayer.adjustResources(valueChange);
		
		// Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);
	}

	/**
	 * Removes the effect of the GreenCard. Since GreenCard has no timed effects,
	 * this method does nothing.
	 * 
	 * @param game       The instance of the GameofStrife game, passed for
	 *                   consistency.
	 * @param effectData Additional effect data (not used by GreenCard).
	 */
	public void removeEffect(GameOfStrife game, Map<String, Object> effectData) {
	}
}
