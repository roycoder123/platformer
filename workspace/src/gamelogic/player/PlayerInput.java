/*
 * 
 */
package gamelogic.player;

import java.awt.event.KeyEvent;

import gameengine.input.KeyboardInputManager;

/**
 * 
 * @author Paul
 *
 */
class PlayerInput {
	/**
	 * 
	 * @return true if the walk-up-key is down
	 */
	public static boolean isUpKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_UP);
	}
	/**
	 * 
	 * @return true if the walk-left-key is down
	 */
	public static boolean isLeftKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_LEFT);
	}
	/**
	 * 
	 * @return true if the walk-right-key is down
	 */
	public static boolean isRightKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_RIGHT);
	}

	//PLAYER 2
	/**
	 * 
	 * @return true if the walk-left-key is down
	 */
	public static boolean isAKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_A);
	}
	/**
	 * 
	 * @return true if the walk-right-key is down
	 */
	public static boolean isDKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_D);
	}
	/**
	 * 
	 * @return true if the walk-up-key is down
	 */
	public static boolean isWKeyDown() {
		return KeyboardInputManager.isKeyDown(KeyEvent.VK_W);
	}
}
