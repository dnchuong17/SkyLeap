package gameStates;

import Levels.LevelsManager;
import UI.PauseOverlay;
import entities.Player;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelsManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false;

	private int yLvlOffSet;
	private int downBorder = (int) (0.8 * Game.GAME_HEIGHT);
	private int upBorder = (int) (0.8 * Game.GAME_HEIGHT);
	private int lvlTilesHigh = LoadSave.getLevelData().length; //92
	private int maxTilesOffSet = lvlTilesHigh - Game.TILE_IN_HEIGHT; //92 - 24 = 68
	private int maxlvlOffSetY = maxTilesOffSet * Game.TILE_SIZE;

	private BufferedImage backgroundPlayingImg;

	public Playing(Game game) throws IOException {
		super(game);
		initClasses(game);

		backgroundPlayingImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
	}

	private void initClasses(Game game) throws IOException {
		levelManager = new LevelsManager(game);
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
			checkCloseToBorder();
		}
		else pauseOverlay.update();
	}

	private void checkCloseToBorder() {
		int playerY = (int) player.getHitBox().y;
		int difference = playerY - yLvlOffSet;

		if(difference > upBorder) {
			System.out.println("diff > upBorder: " + difference + ", " + upBorder);
			yLvlOffSet += difference - upBorder;
			System.out.println("yLvOffSet: " + yLvlOffSet);
		} else if(difference < downBorder) {
			System.out.println("diff < downBorder: " + difference + ", " + downBorder);
			yLvlOffSet += difference - downBorder;
			System.out.println("yLvOffSet: " + yLvlOffSet);
		}

		if(yLvlOffSet > maxlvlOffSetY){
			yLvlOffSet = maxlvlOffSetY;
		} else if(yLvlOffSet < 0){
			yLvlOffSet = 0;
		}
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(backgroundPlayingImg, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);

		levelManager.draw(g, yLvlOffSet);
		player.render(g, yLvlOffSet);
		if(paused){
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

	public void unpauseGame(){
		paused = false;
	}
	public void windowFocusLost() {
		player.resetDirBooleans();
	}

	public Player getPlayer() {
		return player;
	}

}