package game;

import java.util.Map;

import gui.GameOfStrife;

/**
 * The CardAction interface defines the behavior that all cards in the Game of
 * Strife must implement. This ensures that all cards can interact with the game
 * in a consistent way, even if their effects differ.
 */
public interface CardAction {
	/**
	 * Applies the card's effect to the game. This method is called when a card is
	 * played and should implement the logic to modify for instance the the player
	 * resources.
	 * 
	 * @param game the current instance of the GameOfStrife
	 */
	void executeCard(GameOfStrife game);

	/**
	 * Removes or reverses the effect of the card, typically used for cards with a
	 * duration. This method should undo any changes made by executeCard, often by
	 * doing the opposite action. The effectData map can hold additional context or
	 * state needed for reversal.
	 *
	 * @param game       the current instance of the GameOfStrife
	 * @param effectData a map containing effect-specific data needed to remove the
	 *                   effect
	 */

	// General function that simply removes the effect of a given card if that card
	// has a duration (usually just by doing the opposite of applying the card)
	void removeEffect(GameOfStrife game, Map<String, Object> effectData);
}
