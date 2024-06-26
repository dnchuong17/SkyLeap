package INPUT;

import gameStates.Gamestate;
import main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private GamePanel gamePanel;
	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel= gamePanel;
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
			case PLAYING:
				gamePanel.getGame().getPlaying().mouseDragged(e);
				break;
			default:
				break;

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU:
				gamePanel.getGame().getMenu().mouseMoved(e);
				break;
			case PLAYING:
				gamePanel.getGame().getPlaying().mouseMoved(e);
				break;
			case OPTION:
				gamePanel.getGame().getOption().mouseMoved(e);
				break;
			default:
				break;

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
			case PLAYING:
				gamePanel.getGame().getPlaying().mouseMoved(e);
				break;
			default:
				break;

		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU:
				gamePanel.getGame().getMenu().mousePressed(e);
				break;
			case PLAYING:
				gamePanel.getGame().getPlaying().mousePressed(e);
				break;
			case OPTION:
				gamePanel.getGame().getOption().mousePressed(e);
				break;
			default:
				break;

		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
			case MENU:
				gamePanel.getGame().getMenu().mouseReleased(e);
				break;
			case PLAYING:
                try {
                    gamePanel.getGame().getPlaying().mouseReleased(e);
                } catch (IOException exception) {
                    exception.printStackTrace();
                }
                break;
			case OPTION:
				gamePanel.getGame().getOption().mouseReleased(e);
				break;
			default:
				break;
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
