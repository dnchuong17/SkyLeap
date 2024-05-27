package gameStates;

import UI.MenuChoices;
import main.Game;
import utilz.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Menu extends State implements Statemethods {
	private MenuChoices[] buttons = new MenuChoices[3];
	private BufferedImage bgImage;
	private int menuX, menuY, menuWidth, menuHeight, index, rowIndex;
	private int choice = 0;

	private boolean keyOver, keyPressed;

	public Menu(Game game) throws IOException {
		super(game);
		loadButtons();
		loadBackground();
	}

	private void loadBackground() throws IOException {
		bgImage = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int)(bgImage.getWidth()* Game.SCALE);
		menuHeight = (int)(bgImage.getHeight()* Game.SCALE);
		menuX = Game.GAME_WIDTH / 2 - menuWidth /2;
		menuY = (int) (45 * Game.SCALE);

	}

	private void loadButtons() throws IOException {
		buttons[0] = new MenuChoices(Game.GAME_WIDTH / 2, (int) (150 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuChoices(Game.GAME_WIDTH / 2, (int) (220 * Game.SCALE), 1, Gamestate.OPTION);
		buttons[2] = new MenuChoices(Game.GAME_WIDTH / 2, (int) (290 * Game.SCALE), 2, Gamestate.QUIT);
	}
//test choice
	@Override
	public void update() {
		for (MenuChoices mb : buttons)
			mb.update(choice);
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(bgImage, menuX, menuY, menuWidth, menuHeight, null);

		for (MenuChoices mb : buttons)
			mb.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {

	}

	private void select() {
		if (choice == 0) {
			Gamestate.state = Gamestate.PLAYING;
		}
		if (choice == 1) {
			//help
			Gamestate.state = Gamestate.OPTION;

		}
		if (choice == 2) {
			System.exit(0);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (MenuChoices mb : buttons) {
			if (isInKey(e, mb)) {
				mb.setKeyPressed(true);
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			select();
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			choice--;
			if (choice == -1) {
				choice = buttons.length - 1;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			choice++;
			if (choice == buttons.length) {
				choice = 0;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
