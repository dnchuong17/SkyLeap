package main;

import entities.Player;
import levels.LevelManager;

import java.awt.*;


public class Game implements Runnable {
	
	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS = 120;
	private final int UPS = 200;

	private Player player;
	private LevelManager levelManager;

	public final static int TILES_DEFAULT_SIZE = 16;
	public final static float SCALE = 1.0f;
	public final static int TILES_IN_WIDTH = 32;
	public final static int TILES_IN_HEIGHT = 48;
	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE); //16 * 1 = 16
	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; //16*32 = 512 (map 480)
	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; //16*48 = 768 (map 1472)

//	public final static int TILES_DEFAULT_SIZE = 32;
//	public final static float SCALE = 1.0f;
//	public final static int TILES_IN_WIDTH = 26;
//	public final static int TILES_IN_HEIGHT = 14;
//	public final static int TILES_SIZE = (int)(TILES_DEFAULT_SIZE * SCALE); //16 * 1 = 16
//	public final static int GAME_WIDTH = TILES_SIZE * TILES_IN_WIDTH; //16*32 = 512 (map 480)
//	public final static int GAME_HEIGHT = TILES_SIZE * TILES_IN_HEIGHT; //16*48 = 768 (map 1472)

	public Game() {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();

		startGameLoop();

	}

	private void initClasses() {
		player = new Player(0, GAME_HEIGHT - 64 );
		levelManager = new LevelManager(this);
	} //Game_Height - 64 == ground level

	private void startGameLoop(){
		gameThread = new Thread(this);
		gameThread.start();
	}


	public void update(){
		player.update();
		levelManager.update();
	}

	public void render(Graphics g){
		levelManager.draw(g);
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
