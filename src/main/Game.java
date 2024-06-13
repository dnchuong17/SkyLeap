package main;

import Levels.LevelsManager;
import UI.AudioOptions;
import audio.AudioPlayer;
import entities.Player;
import gameStates.Gamestate;
import gameStates.Menu;
import gameStates.Option;
import gameStates.Playing;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;


public class Game implements Runnable {

    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;
    private final int FPS = 120;
    private final int UPS = 200;


    private Playing playing;
    private Menu menu;
    private Option option;
    private Player player;
    private LevelsManager levelsManager;
    private AudioOptions audioOptions;
    private AudioPlayer audioPlayer;


    //New Map
    public static final int TILE_DEFAULT_SIZE = 16; // 16PIXELS => 64x64
    public static final float SCALE = 2.0f;
    public static final int TILE_IN_WIDTH = 30;    // 30 => 960 1920 sau khi scale
    public static final int TILE_IN_HEIGHT = 24;	//20
    public static final int TILE_SIZE = (int)(TILE_DEFAULT_SIZE * SCALE);
    public static final int GAME_WIDTH = TILE_SIZE * TILE_IN_WIDTH;
    public static final int GAME_HEIGHT = TILE_SIZE * TILE_IN_HEIGHT;

    public Game() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
		initClasses();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
	}


    private void initClasses() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        audioOptions = new AudioOptions(this);
        audioPlayer = new AudioPlayer();
        menu = new Menu(this);
        playing = new Playing(this);
        option = new Option(this);
        levelsManager = new LevelsManager(this);
        player = new Player((14 * TILE_SIZE),  (92 * TILE_SIZE) - (TILE_SIZE * 2), TILE_SIZE, TILE_SIZE, playing); //old Y = 1472-3
        player.loadLevelData(levelsManager.getCurentLevel().getLevelData());
        playing.setPlayer(player); // Pass player to playing
    }

    private void startGameLoop(){
        gameThread = new Thread(this);
        gameThread.start();
    }


    public void update() {
        levelsManager.update();

        switch (Gamestate.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            case OPTION:
                option.update();
                break;
            case QUIT:
            default:
                System.exit(0);
                break;
        }
    }

    public void draw(Graphics g) {
//        levelsManager.render(g);
//        player.render(g);
        switch (Gamestate.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            case OPTION:
                option.draw(g);
            default:
                break;
        }
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

        while (true) {
            long currentTime = System.nanoTime();
            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                update();
                updates++;
                deltaU--;
            }

            if (deltaF >= 1) {
                gamePanel.repaint();
                frames++;
                deltaF--;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                System.out.println("FPS: " + frames + " | UPS: " + updates); // Debugging info
                lastCheck = System.currentTimeMillis();
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        if (Gamestate.state == Gamestate.PLAYING)
            playing.getPlayer().resetDirBooleans();
    }

    public Menu getMenu() {
        return menu;
    }

    public Playing getPlaying() {
        return playing;
    }

    public Option getOption() {
        return option;
    }

    public Player getPlayer() {
        return player;
    }
    public AudioOptions getAudioOptions() {
        return audioOptions;
    }
    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }
}
