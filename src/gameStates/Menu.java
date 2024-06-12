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
	private BufferedImage bgImage, bgImageFull, banner;
	private int menuX, menuY, menuWidth, menuHeight, index, rowIndex;
	private int choice = 0;

	private boolean keyOver, keyPressed;

	public Menu(Game game) throws IOException {
		super(game);
		loadButtons();
		loadBackground();
		banner = LoadSave.getSpriteAtlas(LoadSave.BANNER_IMG);
		bgImageFull = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND_IMG);
	}

	private void loadBackground() throws IOException {
		bgImage = LoadSave.getSpriteAtlas(LoadSave.MENU_BACKGROUND);
		menuWidth = (int)(bgImage.getWidth()* Game.SCALE) -100;
		menuHeight = (int)(bgImage.getHeight()* Game.SCALE) -100;
		menuX = Game.GAME_WIDTH / 2 - menuWidth /2;
		menuY = (int) (130 * Game.SCALE) + 40;

	}

	private void loadButtons() throws IOException {
		buttons[0] = new MenuChoices(Game.GAME_WIDTH / 2, (int) (245 * Game.SCALE), 0, Gamestate.PLAYING);
		buttons[1] = new MenuChoices(Game.GAME_WIDTH / 2, (int) (300 * Game.SCALE), 1, Gamestate.OPTION);
		buttons[2] = new MenuChoices(Game.GAME_WIDTH / 2, (int) (355 * Game.SCALE), 2, Gamestate.QUIT);
	}
//test choice
	@Override
	public void update() {
		for (MenuChoices mb : buttons)
			mb.update(choice);
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(bgImageFull, 0, 0, Game.GAME_WIDTH, Game.GAME_HEIGHT, null);
		g.drawImage(banner, Game.GAME_WIDTH/2 - 150, 1, (int)(150 * Game.SCALE),(int)(150 * Game.SCALE), null);

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
		if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W) {
			choice--;
			if (choice == -1) {
				choice = buttons.length - 1;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S) {
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
