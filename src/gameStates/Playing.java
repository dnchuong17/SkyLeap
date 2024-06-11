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
	private boolean paused = false;

	public Playing(Game game) throws IOException {
		super(game);
		initClasses(game);
	}

	private void initClasses(Game game) throws IOException {
		levelManager = new LevelsManager(game);
		//player = new Player(200, 200, (int) (64 * Game.SCALE), (int) (40 * Game.SCALE));
		//player.loadLevelData(levelManager.getCurentLevel().getLevelData());
		pauseOverlay = new PauseOverlay(this);
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	@Override
	public void update() {
		if (!paused) {
			levelManager.update();
			player.update();
		}
		else pauseOverlay.update();
	}

	@Override
	public void draw(Graphics g) {
		levelManager.draw(g);
		player.render(g);
		if(paused){
			g.setColor(new Color(0, 0, 0, 100));
			g.fillRect(0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT);
			pauseOverlay.draw(g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e){
		if (paused)
			pauseOverlay.mouseDragged(e);
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
				paused = !paused;
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
	public void mouseReleased(MouseEvent e) throws IOException {
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

	public void unpauseGame(){
		paused = false;
	}
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

	public void resetAll() throws IOException {
		levelManager.reset();
		player.reset();
	}

}
