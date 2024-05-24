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
	private int menuX, menuY, menuWidth, menuHeight;

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

	@Override
	public void update() {
		for (MenuChoices mb : buttons)
			mb.update();
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
		for (MenuChoices mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuChoices mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}

		resetButtons();
	}
	private void resetButtons() {
		for (MenuChoices mb : buttons)
			mb.resetBools();

	}
	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuChoices mb : buttons)
			mb.setMouseOver(false);
		for (MenuChoices mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

		}
	}

	@Override
	public void keyPressed(KeyEvent e) {


	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
