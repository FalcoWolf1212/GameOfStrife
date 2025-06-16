package game;

import java.util.Map;

import gui.GameOfStrife;

public interface BlueCardInterface {

	/**
	 * Execute the card, which calls methods based on one of the type types of the card:
	 * CHOICE_PLAYER	-	Card that provides a choice between every other player in the game (not the current player).
	 * CHOICE_OPTIONS	-	Card that provides a choice between two options with set outcomes.
	 * CHOICE_GAMBLE	-	Card that provides a choice between two options with set and/or random outcomes.
	 */
	void executeCard(GameOfStrife game);

	/**
	 * Logic for removing any active effects with a duration:
	 * METHOD_CHANGE_STEPS		-		Subtracts the amount of steps defined on the card from the current player's step bonus.
	 * METHOD_CHANGE_INCOME		-		Subtracts the income bonus defined on the card from the current player's income.
	 */
	void removeEffect(GameOfStrife game, Map<String, Object> effectData);

}