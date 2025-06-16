package game;

import gui.GameOfStrife;
import javax.swing.*;

import java.awt.Color;
import java.awt.SystemColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import static game.SymbolicConstants.*;

public class BlueCard extends Card implements BlueCardInterface {
    private String choiceType;
    private String methodType;
    private List<Map<String, Object>> options;
    private Map<String, Object> properties;

    /**
     * Initialize a blue card, by far the most complex of the cards as it presents choices.
     * @param description
     * @param type
     * @param choiceType
     * @param methodType
     * @param options
     * @param properties
     */
    public BlueCard(String description, String type, String choiceType, String methodType, List<Map<String, Object>> options, Map<String, Object> properties) {
        super(description, 0, type); // valueChange not used for BlueCards
        this.choiceType = choiceType;
        this.methodType = methodType;
        this.options = options;
        this.properties = properties != null ? properties : new HashMap<>();
    }

    /**
     * Execute the card, which calls methods based on one of the type types of the card:
     * CHOICE_PLAYER	-	Card that provides a choice between every other player in the game (not the current player).
     * CHOICE_OPTIONS	-	Card that provides a choice between two options with set outcomes.
     * CHOICE_GAMBLE	-	Card that provides a choice between two options with set and/or random outcomes.
     */
    @Override
    public void executeCard(GameOfStrife game) {
    	
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
    	
        switch (choiceType) {
            case CHOICE_PLAYER:
                handlePlayerChoice(game);
                break;
            case CHOICE_OPTIONS:
                handleOptions(game);
                break;
            case CHOICE_GAMBLE:
                handleGamble(game);
                break;
            default:
                JOptionPane.showMessageDialog(game, "Unknown choice type: " + choiceType, "Error", JOptionPane.ERROR_MESSAGE);
        
        // Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

        }
    }

    /**
     * Method that handles and presents the choice popup and logic behind choosing a player.
     * @param game
     */
    private void handlePlayerChoice(GameOfStrife game) {
    	
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
    	
        List<Player> otherPlayers = new ArrayList<>();
        for (Player player : game.getPlayers()) {
            if (player != game.getCurrentPlayer()) {
                otherPlayers.add(player);
            }
        }

        if (otherPlayers.isEmpty()) {
            JOptionPane.showMessageDialog(game, "No other players available.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] playerNames = new String[otherPlayers.size()];
        for (int i = 0; i < otherPlayers.size(); i++) {
            playerNames[i] = otherPlayers.get(i).getName();
        }

        int choice = JOptionPane.showOptionDialog(
                game,
                getDescription(),
                "Choose a Player",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                playerNames,
                playerNames[0]
        );

        if (choice == -1) choice = 0;

        Player selectedPlayer = otherPlayers.get(choice);
        executePlayerChoiceAction(game, selectedPlayer);
        
        // Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

    }

    /**
     * Method called by handlePlayerChoice to determine what action should be committed based on the type of player choice card:
     * METHOD_SWAP_PLACES		-	Card that lets you swap places with another player of your choice.
     * METHOD_MOVE_TO_PLAYER	-	Card that lets you move to the same tile as a player of your choice.
     * METHOD_SWAP_MONEY		-	Card that lets you swap your gold reserve with another player of your choice.
     * METHOD_STEAL_MONEY		-	Card that lets you steal a set amount of gold from another player of your choice.
     * @param game
     * @param selectedPlayer
     */
    private void executePlayerChoiceAction(GameOfStrife game, Player selectedPlayer) {
    	
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
    	
        switch (methodType) {
            case METHOD_SWAP_PLACES:
                swapPlaces(game.getCurrentPlayer(), selectedPlayer, game);
                break;
            case METHOD_MOVE_TO_PLAYER:
                moveToPlayer(game.getCurrentPlayer(), selectedPlayer, game);
                break;
            case METHOD_SWAP_MONEY:
                swapMoney(game.getCurrentPlayer(), selectedPlayer);
                break;
            case METHOD_STEAL_MONEY:
                int amount = (int) properties.getOrDefault(KEY_STEAL_AMOUNT, 0);
                stealMoney(game.getCurrentPlayer(), selectedPlayer, amount);
                gainMoney(game.getCurrentPlayer(), selectedPlayer, amount);
                break;
            default:
                JOptionPane.showMessageDialog(game, "Unknown action: " + methodType, "Error", JOptionPane.ERROR_MESSAGE);
        
        // Reset UI manager settings
        UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

        }
    }

    /**
     * Logic for swapping places with another player
     * @param current
     * @param target
     * @param game
     */
    private void swapPlaces(Player current, Player target, GameOfStrife game) {
        Tile temp = current.getCurrentTile();
        current.setCurrentTile(target.getCurrentTile());
        target.setCurrentTile(temp);
        game.getBoardPanel().updatePlayerPosition(current, game.getPlayers().indexOf(current));
        game.getBoardPanel().updatePlayerPosition(target, game.getPlayers().indexOf(target));
    }

    /**
     * Logic for moving to the same tile as another player
     * @param current
     * @param target
     * @param game
     */
    private void moveToPlayer(Player current, Player target, GameOfStrife game) {
        current.setCurrentTile(target.getCurrentTile());
        game.getBoardPanel().updatePlayerPosition(current, game.getPlayers().indexOf(current));
    }

    /**
     * Logic for swapping gold reserves with another player
     * @param current
     * @param target
     */
    private void swapMoney(Player current, Player target) {
        int currentMoney = current.getResources();
        int targetMoney = target.getResources();
        current.setResources(targetMoney);
        target.setResources(currentMoney);
    }

    /**
     * Logic for stealing money from another player
     * @param current
     * @param target
     * @param amount
     */
    private void stealMoney(Player current, Player target, int amount) {
        int stolen = Math.min(target.getResources(), amount);
        target.adjustResources(-stolen);
    }
    private void gainMoney(Player current, Player target, int amount) {
        int stolen = Math.min(target.getResources(), amount);
        current.adjustResources(stolen);
    }

    /**
     * Method that handles and presents the choice pop-up and logic behind choosing an option. 
     * This shows the number of choices (given by the json file per card) as buttons and applies functions to them.
     * If someone presses the X to close the pop-up, the first option is automatically chosen.
     * @param game
     */
    private void handleOptions(GameOfStrife game) {
    	
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
    	
        String[] optionDescriptions = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            optionDescriptions[i] = (String) options.get(i).get(KEY_DESCRIPTION);
        }

        int choice = JOptionPane.showOptionDialog(
                game,
                getDescription(),
                "Choose an Option",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionDescriptions,
                optionDescriptions[0]
        );

        if (choice == -1) choice = 0;

        Map<String, Object> selectedOption = options.get(choice);
        String optionMethodType = (String) selectedOption.get(KEY_METHOD_TYPE);
        executeOptionAction(game, selectedOption, optionMethodType);
        
        // Reset UI manager settings
        UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);
    }

    /**
     * Method called by handleOptions to determine what action should be committed based on the type of option card option:
     * METHOD_SELF_MONEY_CHANGE		-		Changes the current player's gold reserve by a given amount.
     * METHOD_OTHER_MONEY_CHANGE	-		Changes all players except the current player's gold reserve by a given amount.
     * METHOD_INCOME_FOR_MONEY		-		Changes the current player's income and gold reserves by a given amount.
     * METHOD_MOVE_TO_START			-		Moves the current player to the start square.
     * METHOD_CHANGE_STEPS			-		Adds a set amount of extra steps to the current player's remaining moves whenever they roll the die for a set duration.
     * METHOD_CHANGE_INCOME			-		Changes the current player's income for a set duration.
     * @param game
     * @param option
     * @param methodType
     */
    private void executeOptionAction(GameOfStrife game, Map<String, Object> option, String methodType) {
    	
		// Customize option pane appearance
		UIManager.put("OptionPane.background", SystemColor.control);
		UIManager.put("Panel.background", SystemColor.control);
		UIManager.put("Button.background", BUTTON_COLOR);
		UIManager.put("Button.foreground", Color.WHITE);
		UIManager.put("Button.font", LABEL_FONT);
    	
        switch (methodType) {
            case METHOD_SELF_MONEY_CHANGE:
                int valueChange = (int) option.get(KEY_VALUE_CHANGE);
                game.getCurrentPlayer().adjustResources(valueChange);
                break;
            case METHOD_OTHER_MONEY_CHANGE:
                int amount = (int) option.get(KEY_VALUE_CHANGE);
                for (Player p : game.getPlayers()) {
                    if (p != game.getCurrentPlayer()) p.adjustResources(amount);
                }
                break;
            case METHOD_INCOME_FOR_MONEY:
                int incomeChange = (int) option.get(KEY_INCOME_CHANGE);
                int moneyChange = (int) option.get(KEY_MONEY_CHANGE);
                game.getCurrentPlayer().adjustIncome(incomeChange);
                game.getCurrentPlayer().adjustResources(moneyChange);
                break;
            case METHOD_MOVE_TO_START:
                game.getCurrentPlayer().setCurrentTile(game.findTileByID(0));
                game.getBoardPanel().updatePlayerPosition(game.getCurrentPlayer(), game.getCurrentPlayerTurn() - 1);
                break;
            case METHOD_CHANGE_STEPS:
                int steps = (int) option.get(KEY_VALUE_CHANGE);
                int duration = (int) option.get(KEY_DURATION);
                Map<String, Object> effectData = new HashMap<>();
                effectData.put(KEY_METHOD_TYPE, methodType);
                effectData.put(KEY_VALUE_CHANGE, steps);
                effectData.put(KEY_DURATION, duration);
                game.getActiveCardsList().add(new Object[]{this, 1 + duration * game.getNumberOfPlayers(), effectData});
                game.getCurrentPlayer().addStepsBonus(steps);
                break;
            case METHOD_CHANGE_INCOME:
                int income = (int) option.get(KEY_VALUE_CHANGE);
                int incomeDuration = (int) option.get(KEY_DURATION);
                Map<String, Object> incomeEffectData = new HashMap<>();
                incomeEffectData.put(KEY_METHOD_TYPE, methodType);
                incomeEffectData.put(KEY_VALUE_CHANGE, income);
                incomeEffectData.put(KEY_DURATION, incomeDuration);
                game.getActiveCardsList().add(new Object[]{this, 1 + incomeDuration * game.getNumberOfPlayers(), incomeEffectData});
                game.getCurrentPlayer().adjustIncome(income);
                break;
            default:
                JOptionPane.showMessageDialog(game, "Unknown option: " + methodType, "Error", JOptionPane.ERROR_MESSAGE);
        
        // Reset UI manager settings
		UIManager.put("OptionPane.background", null);
		UIManager.put("Panel.background", null);
		UIManager.put("Button.background", null);
		UIManager.put("Button.foreground", null);
		UIManager.put("Button.font", null);

        }
    }

    /**
     * Method that handles and presents the choice pop-up and logic behind choosing an option for random chance events. 
     * Works similar to the handleOptions() method, however the methods here work slightly differently,
     * where choosing an option has a chance to give one result, and a chance to give another.
     * @param game
     */
    private void handleGamble(GameOfStrife game) {
        String[] optionDescriptions = new String[options.size()];
        for (int i = 0; i < options.size(); i++) {
            optionDescriptions[i] = (String) options.get(i).get(KEY_DESCRIPTION);
        }

        int choice = JOptionPane.showOptionDialog(
                game,
                getDescription(),
                "Choose an Option",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                optionDescriptions,
                optionDescriptions[0]
        );

        if (choice == -1) choice = 0;

        Map<String, Object> selectedOption = options.get(choice);
        String methodType = (String) selectedOption.get(KEY_METHOD_TYPE);
        executeGambleAction(game, selectedOption, methodType);
    }

    /**
     * Logic for handling whenever you press a Gamble type button:
     * METHOD_MONEY_CHANCE		-		Pay a fixed cost from your gold reserve. One of two results happens based on KEY_PENALTY_CHANCE:
     * 		result 1			-		Nothing happens, and you are notified of that.
     * 		result 2			-		Lose more gold, equal on KEY_PENALTY.
     * METHOD_INCOME_CHANCE		-		One of two results happens based on KEY_SUCCESS_CHANCE:
     * 		result 1			-		Your income is changed by KEY_SUCCESS_EFFECT.
     * 		result 2			-		Your income is changed by KEY_FAILURE_EFFECT.
     * @param game
     * @param option
     * @param methodType
     */
    private void executeGambleAction(GameOfStrife game, Map<String, Object> option, String methodType) {
        switch (methodType) {
            case METHOD_MONEY_CHANCE:
                Number fixedCostNumber = (Number) option.get(KEY_FIXED_COST);
                int fixedCost = fixedCostNumber != null ? fixedCostNumber.intValue() : 0;
                game.getCurrentPlayer().adjustResources(fixedCost);

                Number penaltyChanceNumber = (Number) option.get(KEY_PENALTY_CHANCE);
                double penaltyChance = penaltyChanceNumber != null ? penaltyChanceNumber.doubleValue() : 0.0;

                if (Math.random() < penaltyChance) {
                    Number penaltyNumber = (Number) option.get(KEY_PENALTY);
                    int penalty = penaltyNumber != null ? penaltyNumber.intValue() : 0;
                    game.getCurrentPlayer().adjustResources(penalty);
                    JOptionPane.showMessageDialog(game, "Unlucky! Penalty applied: " + penalty, "Result", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(game, "Success! You incur no further costs.", "Result", JOptionPane.INFORMATION_MESSAGE);
                }
                break;

            case METHOD_INCOME_CHANCE:
                if (option.containsKey(KEY_SUCCESS_EFFECT)) {
                    Number successChanceNumber = (Number) option.get(KEY_SUCCESS_CHANCE);
                    double successChance = successChanceNumber != null ? successChanceNumber.doubleValue() : 0.0;

                    if (Math.random() < successChance) {
                        Number successNumber = (Number) option.get(KEY_SUCCESS_EFFECT);
                        int success = successNumber != null ? successNumber.intValue() : 0;
                        game.getCurrentPlayer().adjustIncome(success);
                        JOptionPane.showMessageDialog(game, "Your gamble paid off! Your income increases by " + successNumber, "Result", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        Number failureNumber = (Number) option.get(KEY_FAILURE_EFFECT);
                        int failure = failureNumber != null ? failureNumber.intValue() : 0;
                        game.getCurrentPlayer().adjustIncome(failure);
                        JOptionPane.showMessageDialog(game, "Unlucky! Your income has gone down by " + failureNumber, "Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                break;

            default:
                JOptionPane.showMessageDialog(game, "Unknown gamble: " + methodType, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Logic for removing any active effects with a duration:
     * METHOD_CHANGE_STEPS		-		Subtracts the amount of steps defined on the card from the current player's step bonus.
     * METHOD_CHANGE_INCOME		-		Subtracts the income bonus defined on the card from the current player's income.
     */
    @Override
    public void removeEffect(GameOfStrife game, Map<String, Object> effectData) {
        String effectMethodType = (String) effectData.get(KEY_METHOD_TYPE);
        switch (effectMethodType) {
            case METHOD_CHANGE_STEPS:
                int steps = (int) effectData.get(KEY_VALUE_CHANGE);
                game.getCurrentPlayer().addStepsBonus(-steps);
                break;
            case METHOD_CHANGE_INCOME:
                int incomeChange = (int) effectData.get(KEY_VALUE_CHANGE);
                game.getCurrentPlayer().adjustIncome(-incomeChange);
                break;
        }
    }
}