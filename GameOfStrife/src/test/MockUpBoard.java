package test;

/**
 * MockUpBoard is a class that inherits board, and overrides the two handleError functions below. It is only used in BoardTest
 * to make sure that the test does not shut down when there is a shut down in the code when error handling. 
 */
import game.Board;

/**
 * Method to override the handleFatalError
 */
public class MockUpBoard extends Board {
	@Override
	protected void handleFatalError(String message) {
		throw new RuntimeException(message);
	}

	/**
	 * Method to override the handleErrorTile
	 */
	protected void handleErrorTile(String message) {
		throw new RuntimeException(message);
	}
}