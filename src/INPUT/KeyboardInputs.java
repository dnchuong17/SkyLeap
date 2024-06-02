/*package INPUT;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JFrame;
import main.GamePanel;

public class KeyboardInputs implements KeyListener, Runnable {

	private GamePanel gamePanel;
	private Set<Integer> pressedKeys = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
	private boolean running = true;
	private int lastDirection = 0; // -1 for left, 1 for right, 0 for none

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		Thread inputThread = new Thread(this);
		inputThread.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// Not used in this context
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
		updateKey(e.getKeyCode(), false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		updateKey(e.getKeyCode(), true);
	}

	private void updateKey(int keyCode, boolean pressed) {
		switch (keyCode) {
			case KeyEvent.VK_SPACE:
				if (pressed) {
					System.out.println("Start charging jump");
					gamePanel.getGame().getPlayer().startJumpCharge();
				} else {
					System.out.println("End charging jump and perform jump");
					gamePanel.getGame().getPlayer().endJumpCharge();
					gamePanel.getGame().getPlayer().setJumpDirection(lastDirection);
				}
				break;
			case KeyEvent.VK_A:
				System.out.println(pressed ? "Move left button pressed" : "Move left button released");
				if (pressed) {
					lastDirection = -1;
				}
				break;
			case KeyEvent.VK_D:
				System.out.println(pressed ? "Move right button pressed" : "Move right button released");
				if (pressed) {
					lastDirection = 1;
				}
				break;
			// Handle other keys if necessary
		}
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Input thread interrupted");
				break;
			}
		}
	}

	public void stop() {
		running = false;
	}
}*/

package INPUT;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JFrame;
import main.GamePanel;

public class KeyboardInputs implements KeyListener, Runnable {

	private GamePanel gamePanel;
	// Thread-safe set for tracking pressed keys.
	private Set<Integer> pressedKeys = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
	private boolean running = true;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		// Starting a new thread for input handling.
		Thread inputThread = new Thread(this);
		inputThread.start();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// This method is required by KeyListener but not used here.
	}

	@Override
	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
		updateKey(e.getKeyCode(), false);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		pressedKeys.add(e.getKeyCode());
		updateKey(e.getKeyCode(), true);
	}

	// Updates the player's movement state based on key presses.
	private void updateKey(int keyCode, boolean pressed) {
		switch (keyCode) {
			case KeyEvent.VK_SPACE:
				System.out.println(pressed ? "Jump button pressed" : "Jump button released");
				gamePanel.getGame().getPlayer().setJumping(pressed);
				break;
			case KeyEvent.VK_W:
				System.out.println(pressed ? "Move up button pressed" : "Move up button released");
				gamePanel.getGame().getPlayer().setUp(pressed);
				break;
			case KeyEvent.VK_A:
				System.out.println(pressed ? "Move left button pressed" : "Move left button released");
				gamePanel.getGame().getPlayer().setLeft(pressed);
				break;
			case KeyEvent.VK_D:
				System.out.println(pressed ? "Move right button pressed" : "Move right button released");
				gamePanel.getGame().getPlayer().setRight(pressed);
				break;
			// Add more keys if necessary
		}
	}

	@Override
	public void run() {
		while (running) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				System.out.println("Input thread interrupted");
				break;
			}
		}
	}

	public void stop() {
		running = false;
	}
}

