package game;

import java.util.Map;

import gui.GameOfStrife;

/**
 * The abstract Card class represents a generic card used in the Game of Strife.
 * Cards can be of the type GreenCard, RedCard, and BlueCard. Each of these
 * types extends this class and implements specific behavior. This class
 * implements the CardAction interface and provides a basis for applying and
 * removing card effects.
 */
public abstract class Card implements CardAction {

	// Card attributes
	protected String description;
	protected int valueChange;
	protected String type;

	/**
	 * Constructs a new Card object with the specified description, value change,
	 * and type.
	 *
	 * @param description a brief description of the card's effect
	 * @param valueChange the numeric value that affects the player's resources
	 * @param type        the type of the card
	 */
	public Card(String description, int valueChange, String type) {
		this.description = description;
		this.valueChange = valueChange;
		this.type = type;
	}

	/**
	 * Returns the description of the card.
	 * 
	 * @return a String containing the description text
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Returns the value change associated with the card.
	 * 
	 * @return an integer representing the value change
	 */
	public int getValueChange() {
		return valueChange;
	}

	/**
	 * Returns the type of the card.
	 * 
	 * @return a String identifying the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Applies the effect of the card to the current player in the game. This
	 * adjusts the player's resources based on valueChange.
	 */
	public void executeCard(GameOfStrife game) {
		game.getCurrentPlayer().adjustResources(valueChange);
	}

	/**
	 * Abstract method because it does nothing at its base. It is implemented by the
	 * Card subclasses to revert the card effects.
	 */
	public abstract void removeEffect(GameOfStrife game, Map<String, Object> effectData);

}