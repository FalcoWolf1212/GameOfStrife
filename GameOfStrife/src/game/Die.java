package game;

import java.util.Random;

/**
 * The die class represents the die that can be rolled to generate a random
 * value. The number of faces is configurable, and the current roll value can be
 * retrieved.
 */

public class Die {
	private int numFaces; // Number of faces on the Die
	private int value; // Current rolled value
	private Random random; // Random object for rolling the Die

	/**
	 * Constructs a Die with a specified number of faces.
	 * 
	 * @param numFaces The number of faces on the die (e.g., 6 for a standard die).
	 */
	public Die(int numFaces) {
		this.numFaces = numFaces;
		this.random = new Random();
		this.value = 0; // Initialize the roll as 0 so that we know it hasn't been rolled yet
	}

	/**
	 * Rolls the die to generate a random value between 1 and the number of faces
	 * (inclusive). If the number of faces is less than 1, it sets the value to 1 as
	 * a fallback.
	 */
	public void roll() {
		if (this.numFaces < 1) {
			this.value = 1;
		} else {
			this.value = random.nextInt(numFaces) + 1;
		}
	}

	/**
	 * Sets the number of faces on the die.
	 * 
	 * @param numFaces The new number of faces on the die.
	 */
	public void setNumFaces(int numFaces) {
		this.numFaces = numFaces;
	}

	/**
	 * Returns the current value of the die after it has been rolled.
	 * 
	 * @return The current rolled value of the die.
	 */
	public int getValue() {
		return this.value;
	}

	/**
	 * Returns the number of faces on the die.
	 * 
	 * @return The number of faces on the die.
	 */
	public int getNumFaces() {
		return this.numFaces;
	}
}
