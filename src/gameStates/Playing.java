package gameStates;

import Levels.LevelsManager;
import UI.PauseOverlay;
import entities.Player;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelsManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false;

//	private int xLvlOffSet;
//	private int leftBorder = (int) (0.2 * Game.GAME_WIDTH);
//	private int rightBorder = (int) (0.8 * Game.GAME_WIDTH);
//	private int lvlTilesWide = LoadSave.getLevelData()[0].length;
//	private int maxTilesOffSet = lvlTilesWide - Game.TILE_IN_WIDTH;
//	private int maxlvlOffSetX = maxTilesOffSet * Game.TILE_SIZE;

	private int yLvlOffSet;
	private int downBorder = (int) (0.2 * Game.GAME_HEIGHT);
	private int upBorder = (int) (0.8 * Game.GAME_HEIGHT);
	private int lvlTilesHigh = LoadSave.getLevelData().length; //92
	private int maxTilesOffSet = lvlTilesHigh - Game.TILE_IN_HEIGHT; //92 - 24 = 68
	private int maxlvlOffSetY = maxTilesOffSet * Game.TILE_SIZE;

	public Playing(Game game) throws IOException {
		super(game);
		initClasses(game);
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
			//checkCloseToBorder();
		}
		else pauseOverlay.update();
	}

	//Bigger lvl X
//	private void checkCloseToBorder() {
//		int playerX = (int) player.getHitBox().x;
//		int difference = playerX - xLvlOffSet;
//
//		if(difference > rightBorder) {
//			xLvlOffSet += difference - rightBorder;
//		} else if(difference < leftBorder) {
//			xLvlOffSet += difference - leftBorder;
//		}
//
//		if(xLvlOffSet > maxlvlOffSetX){
//			xLvlOffSet = maxlvlOffSetX;
//		} else if(xLvlOffSet < 0){
//			xLvlOffSet = 0;
//		}
//	}

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

	//Bigger lvl X
//	@Override
//	public void draw(Graphics g) {
//		levelManager.draw(g, xLvlOffSet);
//		player.render(g, xLvlOffSet);
//		if(paused){
//			pauseOverlay.draw(g);
//		}
//	}

	@Override
	public void draw(Graphics g) {
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
