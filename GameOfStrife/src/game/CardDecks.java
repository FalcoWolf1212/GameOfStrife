package game;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import static game.SymbolicConstants.*;

/**
 * The CardDecks class manages all card decks in the game, including loading
 * cards from a JSON file, shuffling, and retrieving cards by type.
 */

public class CardDecks {

	// Map to store decks by type (different colored decks)
	private Map<String, ArrayList<Card>> decks = new HashMap<>();
	private Map<String, ArrayList<Card>> usedDecks = new HashMap<>();

	/**
	 * Loads cards from a JSON file and adds them to the appropriate deck based on
	 * their type. Cards are shuffled after loading.
	 *
	 * @param filePath the path to the JSON file containing card data
	 */
	public void loadCardDeckFromJson(String filePath) {
		try {
			// Read and parse the JSON file
			String jsonData = readJSONFile(filePath);
			JSONObject jsonObject = new JSONObject(jsonData);
			JSONArray pathArray = jsonObject.getJSONArray(JSON_CARDS);

			// Loop over each card entry in the JSON
			for (int i = 0; i < pathArray.length(); i++) {
				JSONObject cardData = pathArray.getJSONObject(i);
				String cardType = cardData.getString(JSON_TYPE);

				if (cardType.equals(TYPE_GREEN)) {
					// Create and store GreenCard from JSON and add to the deck
					GreenCard card = new GreenCard(cardData.getString(JSON_DESCRIPTION),
							cardData.getInt(JSON_VALUE_CHANGE), cardType);
					String type = card.getType().toUpperCase();
					decks.computeIfAbsent(type, k -> new ArrayList<>());
					usedDecks.computeIfAbsent(type, k -> new ArrayList<>());
					decks.get(type).add(card);

				} else if (cardType.equals(TYPE_BLUE)) {
					// Parse basic BlueCard fields
					String description = cardData.getString(JSON_DESCRIPTION);
					String choiceType = cardData.getString(JSON_CHOICE_TYPE);
					String methodType = cardData.optString(JSON_METHOD_TYPE, "");
					JSONArray optionsArray = cardData.optJSONArray(JSON_OPTIONS);
					List<Map<String, Object>> options = new ArrayList<>();

					// Read options array if present, and convert the array to a list of maps
					if (optionsArray != null) {
						for (int j = 0; j < optionsArray.length(); j++) {
							JSONObject optionObj = optionsArray.getJSONObject(j);
							Map<String, Object> optionMap = new HashMap<>();
							Iterator<String> keys = optionObj.keys();
							while (keys.hasNext()) {
								String key = keys.next();
								Object value = optionObj.get(key);
								optionMap.put(key, value);
							}
							options.add(optionMap);
						}
					}

					// Store remaining properties in a map
					Map<String, Object> properties = new HashMap<>();
					Iterator<String> keys = cardData.keys();
					while (keys.hasNext()) {
						String key = keys.next();
						if (!key.equals(JSON_DESCRIPTION) && !key.equals(JSON_TYPE) && !key.equals(JSON_CHOICE_TYPE)
								&& !key.equals(JSON_METHOD_TYPE) && !key.equals(JSON_OPTIONS)) {
							properties.put(key, cardData.get(key));
						}
					}

					// Create and store BlueCard in the deck
					BlueCard card = new BlueCard(description, cardType, choiceType, methodType, options, properties);
					String type = cardType.toUpperCase();
					decks.computeIfAbsent(type, k -> new ArrayList<>());
					usedDecks.computeIfAbsent(type, k -> new ArrayList<>());
					decks.get(type).add(card);

				} else if (cardType.equals(TYPE_RED)) {
					// Create RedCard from JSON and store in deck
					RedCard card = new RedCard(cardData.getString(JSON_DESCRIPTION), cardData.getInt(JSON_VALUE_CHANGE),
							cardType, cardData.getString(JSON_METHOD_TYPE), cardData.getInt(JSON_DURATION));
					String type = card.getType().toUpperCase();
					decks.computeIfAbsent(type, k -> new ArrayList<>());
					usedDecks.computeIfAbsent(type, k -> new ArrayList<>());
					decks.get(type).add(card);

				} else {
					// When an unexpected card type occurs
					System.out.print("This error should not be happening");
				}

				// Shuffle all decks after adding each card
				shuffleAllDecks();
			}

		} catch (IOException e) {
			// Handle file not found or read error
			JOptionPane.showMessageDialog(null, "You don't have the correct Cards JSON file", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		} catch (org.json.JSONException e) {
			// Handle wrong JSON file structure
			JOptionPane.showMessageDialog(null, "Take a look at your Cards JSON file, there is an error in the file",
					"Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}

	/**
	 * Reads and returns the contents of a JSON file as a String.
	 *
	 * @param filePath the file path to read from
	 * @return the content of the JSON file
	 * @throws IOException if an error occurs during file reading
	 */
	private static String readJSONFile(String filePath) throws IOException {
		StringBuilder content = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line;
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n"); // Add newline for better readability
			}
		}
		return content.toString();
	}

	/**
	 * Returns the set of card deck types currently loaded (e.g., RED, GREEN, BLUE).
	 *
	 * @return a set of string deck types.
	 */
	public Set<String> getDeckTypes() {
		return decks.keySet();
	}

	/**
	 * Returns the list of cards for the given type.
	 *
	 * @param type the card type
	 * @return a list of Card objects for the given type, or null if invalid type
	 */
	public ArrayList<Card> getCardsByType(String type) {
		if (type.equalsIgnoreCase(TYPE_GREEN) || type.equalsIgnoreCase(TYPE_BLUE) || type.equalsIgnoreCase(TYPE_RED)) {
			return decks.get(type.toUpperCase());
		} else {
			return null;
		}
	}

	/**
	 * Returns the list of used cards for the given type.
	 *
	 * @param type the card type
	 * @return a list of used Card objects for the given type, or null if invalid
	 *         type
	 */
	public ArrayList<Card> getUsedCardsByType(String type) {
		if (type.equalsIgnoreCase(TYPE_GREEN) || type.equalsIgnoreCase(TYPE_BLUE) || type.equalsIgnoreCase(TYPE_RED)) {
			return usedDecks.get(type.toUpperCase());
		} else {
			return null;
		}
	}

	/**
	 * Shuffles all card decks currently loaded in the game.
	 */
	public void shuffleAllDecks() {
		for (ArrayList<Card> deck : decks.values()) {
			Collections.shuffle(deck);
		}
	}
}
