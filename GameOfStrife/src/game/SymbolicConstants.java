package game;

import java.awt.Color;
import java.awt.Font;

public class SymbolicConstants {

	// This class stores most variables that are not going to be changed in any way
	// by the game or user.
	// In case anything needs to be changed, this class gives a good overview of
	// what constants are in the code
	// This class thus stores all symbolic constants and makes it easier to change
	// any of those constants if necessary

//	ANY grayed out line with /// (three slashes) indicates that this constant is already available in a different indicated class.

	public static final String DATA_PATH = "./data/";

// game
	// Symbolic constants for BlueCard
	public static final String CHOICE_PLAYER = "playerChoice";
	public static final String CHOICE_OPTIONS = "options";
	public static final String CHOICE_GAMBLE = "gamble";
	public static final String METHOD_SWAP_PLACES = "swapPlaces";
	public static final String METHOD_MOVE_TO_PLAYER = "moveToPlayer";
	public static final String METHOD_SWAP_MONEY = "swapMoney";
	public static final String METHOD_STEAL_MONEY = "stealMoney";
	public static final String METHOD_SELF_MONEY_CHANGE = "selfMoneyChange";
	public static final String METHOD_OTHER_MONEY_CHANGE = "otherMoneyChange";
	public static final String METHOD_INCOME_FOR_MONEY = "incomeForMoney";
	public static final String METHOD_MOVE_TO_START = "moveToStart";
	public static final String METHOD_CHANGE_STEPS = "changeSteps";
	public static final String METHOD_CHANGE_INCOME = "changeIncome";
	public static final String METHOD_MONEY_CHANCE = "moneyChance";
	public static final String METHOD_INCOME_CHANCE = "incomeChance";
	public static final String KEY_STEAL_AMOUNT = "stealAmount";
	public static final String KEY_DESCRIPTION = "description";
	public static final String KEY_METHOD_TYPE = "methodType";
	public static final String KEY_VALUE_CHANGE = "valueChange";
	public static final String KEY_INCOME_CHANGE = "incomeChange";
	public static final String KEY_MONEY_CHANGE = "moneyChange";
	public static final String KEY_DURATION = "duration";
	public static final String KEY_FIXED_COST = "fixedCost";
	public static final String KEY_PENALTY_CHANCE = "penaltyChance";
	public static final String KEY_PENALTY = "penalty";
	public static final String KEY_SUCCESS_CHANCE = "successChance";
	public static final String KEY_SUCCESS_EFFECT = "successEffect";
	public static final String KEY_FAILURE_EFFECT = "failureEffect";

	// Symbolic constants for CardDecks
	public static final String TYPE_GREEN = "green";
	public static final String TYPE_BLUE = "blue";
	public static final String TYPE_RED = "red";
	public static final String JSON_CARDS = "cards";
	public static final String JSON_DESCRIPTION = "description";
	public static final String JSON_TYPE = "type";
	public static final String JSON_VALUE_CHANGE = "valueChange";
	public static final String JSON_CHOICE_TYPE = "choiceType";
	public static final String JSON_METHOD_TYPE = "methodType";
	public static final String JSON_OPTIONS = "options";
	public static final String JSON_DURATION = "duration";

	// Symbolic constants for RedCard
	public static final String GREEN_CARD = "Green Card";
	public static final String SELF_MONEY_CHANGE = "selfMoneyChange";
	public static final String OTHER_MONEY_CHANGE = "otherMoneyChange";
	public static final String INCOME_CHANGE = "incomeChange";
	public static final String DIE_CHANGE = "dieChange";
	public static final String SKIP_TURN = "skipTurn";
	public static final String MOVE_TO_START = "moveToStart";
	public static final String ROLL_AGAIN = "rollAgain";
	public static final String RED_CARD = "Red Card";

// gui
	// Symbolic constants for BoardPanel
	public static final int PLAYERS_PER_TILE = 4; // Maximum players that can occupy a single tile

	
	// Symbolic constants for SettingsPanel
	public static final Font LABEL_FONT = new Font("Arial", Font.BOLD, 14);
	public static final Font COMBO_FONT = new Font("Arial", Font.PLAIN, 14);
	public static final Color TEXT_COLOR = new Color(70, 130, 180);
	public static final Color BUTTON_COLOR = new Color(70, 130, 180);
	public static final int DEFAULT_PLAYERS = 4;
	public static final int DEFAULT_DIE_FACES = 6;
	public static final int DEFAULT_WIN_POINTS = 3;

// tests
	// Symbolic constants CardDecksTest
	public static final String BLUE = "blue";
	public static final String GREEN = "green";
	public static final String RED = "red";
	public static final String JSON_PATH_CARDS = DATA_PATH + "cards1.json";
	public static final int EXPECTED_BLUE_CARDS = 16;
	public static final int EXPECTED_GREEN_CARDS = 13;
	public static final int EXPECTED_RED_CARDS = 18;

	// Symbolic constants for BlueCardTest
	public static final String PLAYER_NAME_1 = "Test Player 1";
	public static final String PLAYER_NAME_2 = "Test Player 2";
	public static final String PLAYER_COUNTRY_1 = "netherlands";
	public static final String PLAYER_COUNTRY_2 = "morocco";
	public static final String CARD_DESCRIPTION = "Test";
	public static final String CARD_TYPE = "blue";
	public static final String CARD_CATEGORY_PLAYER_CHOICE = "playerChoice";
	public static final String CARD_CATEGORY_OPTIONS = "options";
	/// public static final String METHOD_SWAP_PLACES = "swapPlaces";
	/// public static final String METHOD_SELF_MONEY_CHANGE = "selfMoneyChange";
	/// public static final String METHOD_CHANGE_STEPS = "changeSteps";
	/// public static final String METHOD_CHANGE_INCOME = "changeIncome";

	// Symbolic constants for RedCardTest
	public static final String METHOD_SELF_MONEY = "selfMoneyChange";
	public static final String METHOD_OTHER_MONEY = "otherMoneyChange";
	public static final String METHOD_INCOME_CHANGE = "incomeChange";
	public static final String METHOD_DIE_CHANGE = "dieChange";
	public static final String METHOD_SKIP_TURN = "skipTurn";
	public static final String METHOD_MOVE_TO_START_RED = "moveToStart";
	public static final String METHOD_ROLL_AGAIN = "rollAgain";
	public static final int VALUE_SELF_MONEY = 100;
	public static final int VALUE_OTHER_MONEY = -50;
	public static final int VALUE_INCOME_CHANGE = 20;
	public static final int VALUE_DIE_CHANGE = 4;
	public static final int VALUE_ZERO = 0;
	public static final int DURATION_INCOME = 2;
	public static final int DURATION_DIE = 3;
	/// public static final String TYPE_RED = "red";

	// Symbolic constants for TileTest
	public static final int TILE_ID = 10;
	public static final String TILE_COLOR_BLUE = "Blue";
	public static final int TILE_X = 0;
	public static final int TILE_Y = 0;
	public static final int TILE_WIDTH = 0;
	public static final int TILE_HEIGHT = 0;
	public static final boolean TILE_VICTORY_FALSE = false;
	public static final int VICTORY_TILE_ID = 1;
	public static final String TILE_COLOR_WHITE = "white";
	public static final int TILE_POS_X = 100;
	public static final int TILE_POS_Y = 100;
	public static final int TILE_DIMENSION = 50;
	public static final boolean TILE_VICTORY_TRUE = true;
	public static final int NORMAL_TILE_ID = 2;
	public static final String TILE_COLOR_GREEN = "green";

}
