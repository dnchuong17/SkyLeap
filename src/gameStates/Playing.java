package gameStates;

import Levels.LevelsManager;
import UI.PauseOverlay;
import entities.Player;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelsManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = true;

	public Playing(Game game) throws IOException {
		super(game);
		initClasses();
	}

	private void initClasses() throws IOException {
		levelManager = new LevelsManager(game);
		player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
		player.loadLevelData(levelManager.getCurentLevel().getLevelData());
		pauseOverlay = new PauseOverlay();

	}

	@Override
	public void update() {
		levelManager.update();
		player.update();
		pauseOverlay.update();

	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);

		pauseOverlay.draw(g);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
				System.out.println("Jump charging");
				player.setJumping(true);
				break;
			case KeyEvent.VK_A:
				player.setLeft(true);
				break;
			case KeyEvent.VK_D:
				player.setRight(true);
				break;
			case KeyEvent.VK_ESCAPE:
				Gamestate.state = Gamestate.MENU;
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			player.setLeft(false);
			break;
		case KeyEvent.VK_D:
			player.setRight(false);
			break;
		case KeyEvent.VK_SPACE:
			player.setJumping(false);
			break;
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (paused){
			pauseOverlay.mousePressed(e);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (paused){
			pauseOverlay.mouseReleased(e);
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (paused){
			pauseOverlay.mouseMoved(e);
		}
	}

	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

}
