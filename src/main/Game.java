package main;

import Levels.LevelsManager;
import entities.Player;

import java.awt.*;
import java.io.IOException;


public class Game implements Runnable {
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS = 120;
	private final int UPS = 200;

	private Player player;
	private LevelsManager levelsManager;

	public static final int TILE_DEFAULT_SIZE = 32; // 16PIXELS
	public static final float TILE_SCALE= 2.0f;
	public static final int TILE_IN_WIDTH = 26;    // 30
	public static final int TILE_IN_HEIGHT = 14;	//20
	public static final int TILE_SIZE = (int)(TILE_DEFAULT_SIZE * TILE_SCALE);
	public static final int GAME_WIDTH = TILE_SIZE * TILE_IN_WIDTH;
	public static final int GAME_HEIGHT = TILE_SIZE * TILE_IN_HEIGHT;
	public Game() throws IOException {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
	}

	private void initClasses() throws IOException {
		player = new Player(200, 200, 32,32);
		levelsManager = new LevelsManager(this);
	}

	private void startGameLoop(){
		gameThread = new Thread(this);
		gameThread.start();
	}


	public void update(){
		levelsManager.update();
		player.update();
	}

	public void render(Graphics g){
		levelsManager.render(g);
		player.render(g);
	}

	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS;
		double timePerUpdate = 1000000000.0 / UPS;


		long previousTime = System.nanoTime();

		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();

		double deltaU = 0;
		double deltaF = 0;
		while(true){

			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			if(deltaU >= 1){
				update();
				updates++;
				deltaU--;
			}

			if(deltaF >= 1){
				gamePanel.repaint();
				frames++;
				deltaF--;
			}


			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}
	}

	public void windowFocusLost(){
		player.resetDirBooleans();
	}
	public Player getPlayer() {
		return player;
	}
}
