package gameStates;

import UI.MenuChoices;
import main.Game;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class State {

	protected Game game;

	public State(Game game) {
		this.game = game;
	}
	public boolean isIn(MouseEvent e, MenuChoices mb) {
		return mb.getBounds().contains(e.getX(), e.getY());
	}

	public boolean isInKey(KeyEvent e, MenuChoices mb) {
		return mb.getBounds().contains(e.getKeyCode(),e.getKeyCode());
	}


	public Game getGame() {
		return game;
	}

}
