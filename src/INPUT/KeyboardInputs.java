package INPUT;

import gameStates.Gamestate;
import main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardInputs implements KeyListener {

    private GamePanel gamePanel;

    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyReleased(KeyEvent e) {
        // TODO Auto-generated method stub
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyReleased(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyReleased(e);
                break;
            case OPTION:
                gamePanel.getGame().getOption().keyReleased(e);
                break;
            default:
                break;

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (Gamestate.state) {
            case MENU:
                gamePanel.getGame().getMenu().keyPressed(e);
                break;
            case PLAYING:
                gamePanel.getGame().getPlaying().keyPressed(e);
                break;
            case OPTION:
                gamePanel.getGame().getOption().keyPressed(e);
                break;
            default:
                break;
        }
//        pressedKeys.add(e.getKeyCode());
//        updateKey(e.getKeyCode(), true);
    }

//    @Override
//    public void run() {
//        while (running) {
//            try {
//                Thread.sleep(10);
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//                System.out.println("Input thread interrupted");
//                break;
//            }
//        }
//	}

//    private void updateKey(int keyCode, boolean pressed) {
//        switch (keyCode) {
//            case KeyEvent.VK_SPACE:
//                System.out.println(pressed ? "Jump button pressed" : "Jump button released");
//                gamePanel.getGame().getPlayer().setJumping(pressed);
//                break;
//            case KeyEvent.VK_W:
//                System.out.println(pressed ? "Move up button pressed" : "Move up button released");
//                gamePanel.getGame().getPlayer().setUp(pressed);
//                break;
//            case KeyEvent.VK_A:
//                System.out.println(pressed ? "Move left button pressed" : "Move left button released");
//                gamePanel.getGame().getPlayer().setLeft(pressed);
//                break;
//            case KeyEvent.VK_D:
//                System.out.println(pressed ? "Move right button pressed" : "Move right button released");
//                gamePanel.getGame().getPlayer().setRight(pressed);
//                break;
//            // Add more keys if necessary
//        }
//    }

//	public void stop() {
//		running = false;
//	}
}

