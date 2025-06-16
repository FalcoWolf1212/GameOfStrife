package game;

import java.awt.Color;
import java.awt.SystemColor;
import java.util.Map;
import static game.SymbolicConstants.*;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import gui.GameOfStrife;

public class RedCard extends Card {
	private String methodType;
	private int duration;

	/**
	 * Define RedCard, a card with many chaotic possibilities that either make life
	 * worse for you, or for your enemies. Or maybe something completely crazy will
	 * happen?
	 * 
	 * @param description
	 * @param valueChange
	 * @param type
	 * @param methodType
	 * @param duration
	 */
	public RedCard(String description, int valueChange, String type, String methodType, int duration) {
		super(description, valueChange, type);
		this.methodType = methodType;
		this.duration = duration;
	}

	/**
	 * Relatively large method for executing a Red Card, due to the many things a
	 * red card can do. Possible options: SELF_MONEY_CHANGE - Changes the current
	 * player's gold reserve. OTHER_MONEY_CHANGE - Changes every player except the
	 * current player's gold reserve. INCOME_CHANGE - Changes the current player's
	 * income for a set amount of turns. DIE_CHANGE - Changes the number of die
	 * faces for a set amount of turns. SKIP_TURN - Skips the next player's turn
	 * MOVE_TO_START - Moves the current player to the starting square (the tile
	 * with ID=0). ROLL_AGAIN - Allows the current player to take another turn
	 * (without granting them additional income).
	 */
	@Override
	public void executeCard(GameOfStrife game) {
		
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
		
		JOptionPane.showMessageDialog(game, this.getDescription(), RED_CARD, JOptionPane.INFORMATION_MESSAGE);

		if (methodType.equals(SELF_MONEY_CHANGE)) {
			Player currentPlayer = game.getCurrentPlayer();
			currentPlayer.adjustResources(valueChange);
		} else if (methodType.equals(OTHER_MONEY_CHANGE)) {
			for (Player player : game.getPlayers()) {
				if (player != game.getCurrentPlayer()) {
					player.adjustResources(valueChange);
				}
			}
		} else if (methodType.equals(INCOME_CHANGE)) {
			game.getCurrentPlayer().adjustIncome(this.valueChange);
			game.getActiveCardsList().add(new Object[] { this, 1 + this.duration * game.getNumberOfPlayers(), null });
		} else if (methodType.equals(DIE_CHANGE)) {
			game.getDie().setNumFaces(game.getDie().getNumFaces() + this.valueChange);
			game.getActiveCardsList().add(new Object[] { this, 1 + this.duration * game.getNumberOfPlayers(), null });
		} else if (methodType.equals(SKIP_TURN)) {
			game.nextTurn();
		} else if (methodType.equals(MOVE_TO_START)) {
			game.getCurrentPlayer().setCurrentTile(game.findTileByID(0));
			game.getBoardPanel().updatePlayerPosition(game.getCurrentPlayer(), game.getCurrentPlayerTurn() - 1);
		} else if (methodType.equals(ROLL_AGAIN)) {
			game.previousTurn();
		}
		
		// Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);
	}

	/**
	 * Small method that removes the effects of active cards if they have a
	 * duration. Possible options: INCOME_CHANGE - Removes the income that is stated
	 * on the card from the current player. DIE_CHANGE - Reduces the amount of die
	 * faces by what is stated on the card.
	 */
	public void removeEffect(GameOfStrife game, Map<String, Object> effectData) {
		if (methodType.equals(INCOME_CHANGE)) {
			game.getCurrentPlayer().adjustIncome(-this.valueChange);
		}
		if (methodType.equals(DIE_CHANGE)) {
			game.getDie().setNumFaces(game.getDie().getNumFaces() - this.valueChange);
		}
	}
}
