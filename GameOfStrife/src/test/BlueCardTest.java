package test;

import game.BlueCard;
import game.BlueCardInterface;
import game.Tile;
import gui.GameOfStrife;
import junit.framework.TestCase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Unit tests for the {@link BlueCard} class, ensuring its behavior within the context
 * of the GameofStrife game logic, particularly how cards affect players and game state.
 */
public class BlueCardTest extends TestCase {
    // Symbolic constants
    private static final String PLAYER_NAME_1 = "Test Player 1";
    private static final String PLAYER_NAME_2 = "Test Player 2";
    private static final String PLAYER_COUNTRY_1 = "netherlands";
    private static final String PLAYER_COUNTRY_2 = "morocco";
    private static final String CARD_DESCRIPTION = "Test";
    private static final String CARD_TYPE = "blue";
    private static final String CARD_CATEGORY_PLAYER_CHOICE = "playerChoice";
    private static final String CARD_CATEGORY_OPTIONS = "options";
    private static final String METHOD_SWAP_PLACES = "swapPlaces";
    private static final String METHOD_SELF_MONEY_CHANGE = "selfMoneyChange";
    private static final String METHOD_CHANGE_STEPS = "changeSteps";
    private static final String METHOD_CHANGE_INCOME = "changeIncome";

    private BlueCardInterface swapPlacesCard;
    private BlueCard selfMoneyCard;
    private BlueCardInterface changeStepsCard;
    private BlueCardInterface changeIncomeCard;
    private GameOfStrife game;
    private int originalDieFaces;

    /**
     * Sets up the test environment before each test case, initializing players,
     * game instance, and various BlueCard instances with different effects.
     */
    public void setUp() throws Exception {
        String[][] testPlayerInfo = {
            {PLAYER_NAME_1, PLAYER_COUNTRY_1}, 
            {PLAYER_NAME_2, PLAYER_COUNTRY_2}
        };
        game = new GameOfStrife(2, 6, 4, testPlayerInfo);
        originalDieFaces = game.getDie().getNumFaces();

        // Create test cards
        swapPlacesCard = createPlayerChoiceCard(METHOD_SWAP_PLACES);
        selfMoneyCard = createOptionCard(METHOD_SELF_MONEY_CHANGE, 100);
        changeStepsCard = createOptionCard(METHOD_CHANGE_STEPS, 3, 2);
        changeIncomeCard = createOptionCard(METHOD_CHANGE_INCOME, 20, 3);
    }

    /**
     * Creates a BlueCard of category "playerChoice" with the specified method type.
     *
     * @param methodType the behavior the card should invoke
     * @return a BlueCard configured for player choice interaction
     */
    private BlueCardInterface createPlayerChoiceCard(String methodType) {
        List<Map<String, Object>> options = new ArrayList<>();
        return new BlueCard(CARD_DESCRIPTION, CARD_TYPE, CARD_CATEGORY_PLAYER_CHOICE, methodType, options, new HashMap<>());
    }

    /**
     * Creates a BlueCard of category "options" with a specified method type and value change.
     *
     * @param methodType the card's effect method
     * @param value the value to be applied
     * @return a BlueCard with a single option
     */
    private BlueCard createOptionCard(String methodType, int value) {
        return createOptionCard(methodType, value, 0);
    }

    /**
     * Creates a BlueCard of category "options" with method type, value, and duration.
     *
     * @param methodType the card's effect method
     * @param value the value to be applied
     * @param duration how long the effect should last
     * @return a BlueCard configured with options
     */
    private BlueCard createOptionCard(String methodType, int value, int duration) {
        List<Map<String, Object>> options = new ArrayList<>();
        Map<String, Object> option = new HashMap<>();
        option.put("methodType", methodType);
        option.put("valueChange", value);
        option.put("duration", duration);
        options.add(option);
        return new BlueCard(CARD_DESCRIPTION, CARD_TYPE, CARD_CATEGORY_OPTIONS, "", options, new HashMap<>());
    }

    /**
     * Tests whether the "swapPlaces" card correctly swaps the positions of the two players.
     */
    public void testSwapPlaces() {
        Tile p1Start = game.getPlayers().get(0).getCurrentTile();
        Tile p2Start = game.getPlayers().get(1).getCurrentTile();

        swapPlacesCard.executeCard(game);

        assertSame(game.getPlayers().get(0).getCurrentTile(), p2Start);
        assertSame(game.getPlayers().get(1).getCurrentTile(), p1Start);
    }

    /**
     * Verifies basic BlueCard properties, including description and type,
     * and checks that valueChange is accessed through options rather than the main card.
     */
    public void testCardProperties() {
        assertEquals(CARD_DESCRIPTION, selfMoneyCard.getDescription());
        assertEquals(CARD_TYPE, selfMoneyCard.getType());
        assertEquals(0, selfMoneyCard.getValueChange()); // BlueCards use options for values
    }

    /**
     * Resets the die face count to its original value after each test.
     */
    public void tearDown() {
        game.getDie().setNumFaces(originalDieFaces);
    }
}
