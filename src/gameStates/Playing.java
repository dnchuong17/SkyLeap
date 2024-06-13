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

import static main.Game.*;

public class Playing extends State implements Statemethods {
	private Player player;
	private LevelsManager levelManager;
	private PauseOverlay pauseOverlay;
	private boolean paused = false;

	private int yLvlOffSet;
	private int downBorder = (int) (0.2 * Game.GAME_HEIGHT);
	private int upBorder = (int) (0.2 * Game.GAME_HEIGHT);
	private int lvlTilesHigh = LoadSave.getLevelData().length; //92
	private int maxTilesOffSet = lvlTilesHigh - Game.TILE_IN_HEIGHT; //92 - 24 = 68
	private int maxlvlOffSetY = maxTilesOffSet * TILE_SIZE;

	private BufferedImage backgroundPlayingImg;
	private BufferedImage backgroundPlayingImg1;
	private BufferedImage backgroundPlayingImg2;
	private BufferedImage backgroundPlayingImg3;
	private BufferedImage backgroundPlayingImg4;

	public Playing(Game game) throws IOException {
		super(game);
		initClasses(game);
		backgroundPlayingImg = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG);
		backgroundPlayingImg1 = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG_1);
		backgroundPlayingImg2 = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG_2);
		backgroundPlayingImg3 = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG_3);
		backgroundPlayingImg4 = LoadSave.getSpriteAtlas(LoadSave.PLAYING_BACKGROUND_IMG_4);

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
//		g.drawImage(backgroundPlayingImg, 0, 0, 1002, (int)(GAME_HEIGHT* SCALE), null);
		int imageHeight = backgroundPlayingImg3.getHeight(null);
		int startY = GAME_HEIGHT - imageHeight;

		g.drawImage(backgroundPlayingImg3, 0, startY + 16 , Game.GAME_WIDTH, 30 * 32 , null);
//		g.drawImage(backgroundPlayingImg2, 0, Game.GAME_HEIGHT, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
//		g.drawImage(backgroundPlayingImg1, 0, Game.GAME_HEIGHT * 2, Game.GAME_WIDTH,  Game.GAME_HEIGHT, null);

//		drawBackground(g);


		levelManager.draw(g, yLvlOffSet);
		player.render(g, yLvlOffSet);
		if(paused){
			pauseOverlay.draw(g);
		}
	}

//	private void drawBackground(Graphics g) {
//		for(int i = 0; i < 3; i++){
//			g.drawImage(backgroundPlayingImg4, 0, 0 + i * GAME_HEIGHT , 1002, GAME_HEIGHT, null);
//		}
//
//	}

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