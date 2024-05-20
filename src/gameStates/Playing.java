package gameStates;

import Levels.LevelsManager;
import entities.Player;
import main.Game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Playing extends State implements stateMethods{
    private Player player;
    private LevelsManager levelsManager;

    public Playing(Game game) throws IOException {
        super(game);
        initClasses();
    }

    private void initClasses() throws IOException {
        levelsManager = new LevelsManager(game);
        player = new Player(200, 200, 96,96);
        player.loadLevelData(levelsManager.getCurentLevel().getLevelData());
    }


    public void windowFocusLost(){
        player.resetDirBooleans();
    }
    public Player getPlayer() {
        return player;
    }

    @Override
    public void update() {
        levelsManager.update();
        player.update();
    }

    @Override
    public void draw(Graphics g) {
        levelsManager.draw(g);
        player.render(g);
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                System.out.println("Jump charging");
                player.setJumping(true);
                break;
            case KeyEvent.VK_W:
                player.setUp(true);
                break;
            case KeyEvent.VK_A:
                player.setLeft(true);
                break;
            case KeyEvent.VK_D:
                player.setRight(true);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                System.out.println("Jump released");
                player.setJumping(false);
                break;
            case KeyEvent.VK_W:
                player.setUp(false);
                break;
            case KeyEvent.VK_A:
                player.setLeft(false);
                break;
            case KeyEvent.VK_D:
                player.setRight(false);
                break;
        }

    }
}
